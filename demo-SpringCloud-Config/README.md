Nacos服务端安装教程：

- [Nacos2.x安装教程（Windows/Linux单机版 + Linux集群版 + 使用MySQL数据源）](https://maxqiu.com/article/detail/103)
- [Docker常用镜像：Nacos（单机版 + 使用MySQL数据源）](https://maxqiu.com/article/detail/105)

客户端官方教程：

- [4. Spring Cloud Alibaba Nacos Config](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_spring_cloud_alibaba_nacos_config)
- [Nacos Spring Cloud 快速开始](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

# 环境搭建

## POM

> 必须要添加`spring-cloud-starter-bootstrap`依赖

```xml
<!-- Nacos -->
<dependency>
    <groupId>com.alibaba.nacos</groupId>
    <artifactId>nacos-client</artifactId>
    <version>2.0.3</version>
</dependency>
<!-- 配置中心 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <exclusions>
        <exclusion>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-client</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- Bootstrap -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
<!-- Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Application

> 无需添加任何注解

## yml

> 使用`bootstrap.yml`，以下是示例

```yml
spring:
  application:
    name: config # 服务模块名称，根据模块名称填写
  profiles:
    active: dev # 当前开发环境
  cloud:
    nacos:
      server-addr: nacos:8848 # nacos服务端地址（注册中心 和 配置中心 地址）
      config:
        enabled: true # 启用配置中心。默认：true
        encode: UTF-8 # 配置文件的文件编码，默认：UTF-8
        refresh-enabled: false # 启用刷新配置。默认：true
        timeout: 3000 # 客户端获取配置的超时时间（毫秒）。默认：3000
        max-retry: 10 # nacos允许的最大服务器重新连接错误数。默认：无限次
        server-addr: ${spring.cloud.nacos.server-addr} # 配置中心地址，如果和注册中心地址不一样，可单独配置
        namespace: # 服务所在命名空间ID，一般用于隔离不同项目的服务。默认：
        group: CONFIG_GROUP # 服务所在分组，一般用于隔离不同环境的服务。默认：DEFAULT_GROUP
        file-extension: yml # 配置文件的文件后缀。默认：properties
        prefix: ${spring.application.name} # 配置文件对应的DataId前缀。默认：${spring.application.name}
        #name: ${spring.application.name} # 配置文件对应的DataId前缀，会覆盖prefix，一般使用prefix即可。默认：
        extension-configs: # 扩展配置
          - dataId: public.yml # 配置文件ID
            group: PUBLIC_GROUP # 配置文件所在分组。默认：DEFAULT_GROUP
            refresh: true # 是否启用刷新配置。默认：false
          - dataId: public-${spring.profiles.active}.yml # 配置文件ID，需要手动添加根据环境变量加载的文件
            group: PUBLIC_GROUP # 配置文件所在分组。默认：DEFAULT_GROUP
            refresh: true # 是否启用刷新配置。默认：false
        #shared-configs: # 共享配置，使用方式同扩展配置，优先级低于extension-configs
```

# 使用

## 配置文件建立

1. 打开`Nacos`服务端
2. 点击`配置管理` → `配置列表`
3. 选择`public`命令空间（也可以先建立命名空间并选中）
4. 点击右侧`+`新建配置。
5. 填写`Data ID`、`Group`，并选择`YAML`格式。以下为示例

Data Id | Group | 内容
---|---|---
config.yml | CONFIG_GROUP | `user: tom`
config-dev.yml | CONFIG_GROUP | `profile: 开发环境`
config-pro.yml | CONFIG_GROUP | `profile: 生产环境`
public.yml | PUBLIC_GROUP | `public: 测试共享字段`

> PS：语法格式遵循`yaml`语法

## 测试配置读取

```java
/**
 * 配置读取
 */
// 启用自动刷新（该注解仅能配置在类上）
@RefreshScope
@RestController
public class IndexController {
    @Value("${user}")
    private String user;

    @Value("${profile}")
    private String profile;

    @Value("${pub}")
    private String pub;

    @GetMapping("")
    public Map<String, String> index() {
        HashMap<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("profile", profile);
        map.put("pub", pub);
        return map;
    }
}
```

启动服务后浏览器访问项目根目录，即可看到如下内容

```json
{"profile":"开发环境","pub":"测试共享字段","user":"tom"}
```

## 配置文件加载规则

在上文的服务启动过程中，可以看到如下加载示例：

```
Located property source: [
BootstrapPropertySource {name='bootstrapProperties-config-dev.yml,CONFIG_GROUP'},
BootstrapPropertySource {name='bootstrapProperties-config.yml,CONFIG_GROUP'},
BootstrapPropertySource {name='bootstrapProperties-config,CONFIG_GROUP'},
BootstrapPropertySource {name='bootstrapProperties-public-dev.yml,PUBLIC_GROUP'},
BootstrapPropertySource {name='bootstrapProperties-public.yml,PUBLIC_GROUP'}
]
```

总结后

> 加载文件列表如下：

- `Config`仅能加载一个`namespace`命名空间下的配置文件，不能跨命名空间
- 默认情况下，会加载如下配置文件：
    - `group`+`prefix`：`config,CONFIG_GROUP`<br>不建议使用，因为阿里官网没说会加载这个文件，不知道是不是bug，会不会后面修复
    - `group`+`prefix`+`file-extension`：`config.yml,CONFIG_GROUP`<br>该文件一般用于**当前服务**在**所有环境**下通用配置
    - `group`+`prefix`+`active`+`file-extension`：`config-dev.yml,CONFIG_GROUP`<br>仅当配置了`active`时，才会加载，该文件一般用于**当前服务**在**指定环境**下的配置
- 此外，还会根据`extension-configs`和`shared-configs`加载指定的配置（一般只用`extension-configs`即可）。如：`public.yml,PUBLIC_GROUP`、`public-dev.yml,PUBLIC_GROUP`<br>一般用于加载所有服务通用的配置。例如日志文件配置、Redis连接地址配置等

> 加载文件配置优先级如下：

- `shared-dataids` < `extension-config` < `当前服务配置`
- `当前服务配置` < `当前服务指定环境配置`

PS：个人建议不要使用覆盖编写，防止混淆
