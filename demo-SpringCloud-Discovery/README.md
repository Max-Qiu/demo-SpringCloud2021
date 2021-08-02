Nacos服务端安装教程：

- [Nacos2.x安装教程（Windows/Linux单机版 + Linux集群版 + 使用MySQL数据源）](https://maxqiu.com/article/detail/103)
- [Docker常用镜像：Nacos（单机版 + 使用MySQL数据源）](https://maxqiu.com/article/detail/105)

客户端官方教程：

- [3. Spring Cloud Alibaba Nacos Discovery](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_spring_cloud_alibaba_nacos_discovery)
- [Nacos Spring Cloud 快速开始](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

# 环境搭建

## POM

```xml
<!-- Nacos -->
<dependency>
    <groupId>com.alibaba.nacos</groupId>
    <artifactId>nacos-client</artifactId>
    <version>2.0.2</version>
</dependency>
<!-- 注册中心 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <exclusions>
        <exclusion>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-client</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Application

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 启用注册中心
@EnableDiscoveryClient
@SpringBootApplication
public class DiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class, args);
    }
}
```

PS：`@EnableDiscoveryClient`即使不写也可以完成注册，推荐写一下，防止后面版本更新后失效

## yml

```yml
spring:
  application:
    name: discovery # 服务模块名称，根据模块名称填写
  cloud:
    nacos:
      server-addr: nacos:8848 # nacos服务端地址（注册中心 和 配置中心 地址）
      discovery:
        enabled: true # 是否启用注册中心。默认：true
        server-addr: ${spring.cloud.nacos.server-addr} # 注册中心地址，如果和配置中心地址不一样，可单独配置
        namespace: # 服务所在命名空间ID，一般用于隔离不同项目的服务。默认：
        group: DEFAULT_GROUP # 服务所在分组，一般用于隔离不同环境的服务。默认：DEFAULT_GROUP
        service: ${spring.application.name} # 注册的服务名称。默认：${spring.application.name}
        weight: 1 # 权重
```

# 使用

1. 启动服务
2. （可选）使用`--server.port=xxx`启动多个相同服务
3. 打开`Nacos`服务端 → 服务管理 → 服务列表，点击某个服务的`详细`，即可看到服务集群
