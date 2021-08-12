官方教程：

- [Spring Cloud OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
- [Spring Cloud LoadBalancer](https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer)

# 环境搭建

> 说明

- 一般`Feign`用于微服务之间的`A`服务调用`B`服务。下文中`A`服务称为客户端、`B`服务称为服务端
- 以下配置仅需要在客户端添加依赖。

## POM

```xml
<!-- 此处省略其他依赖如注册中心、配置中心、Web -->
<!-- OpenFeign -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
<!-- LoadBalancer -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
<!-- 负载均衡缓存 -->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
</dependency>
```

## Application

1. 启动类需要添加`@EnableFeignClients`注解
2. 若`Feign`接口不在当前启动类下，使用该注解的`basePackages`属性配置包扫描

```java
// 启用Feign服务调用
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }
}
```

## yml

以下示例配置了常用选项和默认选项，具体可根据注释进行修改

```yml
# Feign配置
feign:
  #  compression: # 压缩设置（暂时不要开启，目前版本还有bug）
  #    request: # 请求压缩
  #      enabled: false # 是否启用。默认：false
  #      mime-types: # 支持的mime类型。示例即默认配置
  #        - "text/xml"
  #        - "application/xml"
  #        - "application/json"
  #      min-request-size: 2048 # 启用压缩的最小内容大小。
  #    response: # 响应压缩
  #      enabled: false # 是否启用。默认：false
  #      useGzipDecoder: false # 启用gzip解码器来解码gzip响应。默认false
  client:
    config:
      default: # feign客户端全局默认配置
        loggerLevel: FULL # 显示全部请求与返回信息，详见：feign.Logger.Level
        connectTimeout: 1000 # 连接超时时间，单位毫秒。默认：10000
        readTimeout: 3000 # 读取超时时间，单位毫秒。默认：60000
  autoconfiguration:
    jackson:
      enabled: true # Spring Data Support。默认：false
# 日志配置
logging:
  level:
    com.maxqiu.demo.**.feign.**: debug # 指定Feign接口日志级别为debug（建议仅开发环境启用）
```

# 使用

## 简单使用

> 服务端Controller

```java
@RestController
public class IndexController {
    @Value("${server.port}")
    private String port;

    @GetMapping("index")
    public Map<String, String> index() {
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "index");
        map.put("port", port);
        return map;
    }

    @GetMapping("timeout")
    public Map<String, String> timeout() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "timeout");
        map.put("port", port);
        return map;
    }
}
```

> 客户端`Feign`接口

```java
@FeignClient(
    // 服务名称
    name = "server",
    // Bean 名称，默认使用name，若有多个Feign访问同一个服务，则需要定制不同的contextId
    contextId = "serverFeignService",
    // 所有方法的前缀。默认：空
    path = "")
public interface ServerFeignService {
    @GetMapping("index")
    Map<String, String> index();

    @GetMapping("timeout")
    Map<String, String> timeout();
}
```

> 客户端Controller

```java
@RestController
public class IndexController {
    @Autowired
    private ServerFeignService serverFeignService;

    @GetMapping("index")
    public Map<String, String> index() {
        return serverFeignService.index();
    }

    @GetMapping("timeout")
    public Map<String, String> timeout() {
        return serverFeignService.timeout();
    }
}
```

> 结果

- 访问客户端的`/index`，看到返回结果`{"method":"index","port":"40401"}`
- 多次访问客户端的`/index`，看到负载均衡的轮询已经可用
- 访问客户端的`/timeout`，3秒后看到报错，因为已经设置了`Feign`的读取超时时间
- 如果将客户端配置文件中的`readTimeout`调成大于5000并重启客户端，访问正常

## `Feign`接口传参示例

> 服务端Controller

```java
@RestController
@RequestMapping("demo")
public class DemoController {
    /**
     * 无参请求
     */
    @GetMapping("normal")
    public String normal() {
        return "normal";
    }

    /**
     * 单个参数
     */
    @GetMapping("singleParam")
    public String singleParam(String param) {
        return param;
    }

    /**
     * 多个参数
     */
    @GetMapping("multiParam")
    public String multiParam(String param1, String param2) {
        return param1 + " " + param2;
    }

    /**
     * 实体参数
     */
    @GetMapping("objParam")
    public ParamsVO objParam(ParamsVO vo) {
        return vo;
    }

    /**
     * json参数
     */
    @PostMapping("jsonParam")
    public ParamsVO jsonParam(@RequestBody ParamsVO vo) {
        return vo;
    }
}
```

> 客户端`Feign`接口

```java
@FeignClient(name = "server", contextId = "demoFeignService", path = "demo")
public interface DemoFeignService {
    /**
     * 无参请求
     */
    @GetMapping("normal")
    String normal();

    /**
     * 单个参数
     */
    @GetMapping("singleParam")
    String singleParam(@RequestParam String param);

    /**
     * 多个参数
     */
    @GetMapping("multiParam")
    String multiParam(@RequestParam String param1, @RequestParam String param2);

    /**
     * 实体参数
     *
     * 注意：实体需要使用@SpringQueryMap注解
     */
    @GetMapping("objParam")
    ParamsVO objParam(@SpringQueryMap ParamsVO vo);

    /**
     * json参数
     */
    @PostMapping("jsonParam")
    ParamsVO jsonParam(@RequestBody ParamsVO vo);
}
```

> 客户端Controller

```java
@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private DemoFeignService demoFeignService;

    /**
     * 无参请求
     */
    @GetMapping("normal")
    public String normal() {
        return demoFeignService.normal();
    }

    /**
     * 单个参数
     */
    @GetMapping("singleParam")
    public String singleParam(String param) {
        return demoFeignService.singleParam(param);
    }

    /**
     * 多个参数
     */
    @GetMapping("multiParam")
    public String multiParam(String param1, String param2) {
        return demoFeignService.multiParam(param1, param2);
    }

    /**
     * 实体参数
     */
    @GetMapping("objParam")
    public ParamsVO objParam(ParamsVO vo) {
        return demoFeignService.objParam(vo);
    }

    /**
     * json参数
     */
    @PostMapping("jsonParam")
    public ParamsVO jsonParam(@RequestBody ParamsVO vo) {
        return demoFeignService.jsonParam(vo);
    }
}
```

> 结果

结果可以使用`Postman`导入源码的`feign.postman_collection.json`，然后运行每一个请求并查看结果

> 注意事项

- `Feign`接口中传参和`Controller`基本一样，`@RequestParam`、`@RequestBody`等注解均可用
- 每个参数必须要写明注解
- 如果传递实体时，则只能使用`@SpringQueryMap`
