官方教程：

- [Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)
- [Spring Cloud LoadBalancer](https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer)

> 本文是一篇水文章，所以推荐以下教程！

- `尚硅谷`视频教程：[尚硅谷_SpringCloud
Hoxton版 + SpringCloud alibaba](http://www.atguigu.com/download_detail.shtml?v=281)
- `纯洁的微笑`教程：
    - [springcloud(十五)：服务网关 Spring Cloud GateWay 入门](http://www.ityouknow.com/springcloud/2018/12/12/spring-cloud-gateway-start.html)
    - [springcloud(十六)：服务网关 Spring Cloud GateWay 服务化和过滤器](http://www.ityouknow.com/springcloud/2019/01/19/spring-cloud-gateway-service.html)
    - [springcloud(十七)：服务网关 Spring Cloud GateWay 熔断、限流、重试](http://www.ityouknow.com/springcloud/2019/01/26/spring-cloud-gateway-limit.html)

# 环境搭建

## POM

```xml
<!-- 此处省略其他依赖如注册中心、配置中心。注意：不要添加web -->
<!-- 网关 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<!-- 负载均衡 -->
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

> 无需添加任何注解

## yml

以下示例配置了常用选项和默认选项，具体可根据注释进行修改

```yml
spring:
  cloud:
    gateway:
      default-filters:
        - PreserveHostHeader # 转发 host
      routes:
        - id: server
          uri: lb://server
          predicates:
            - Path=/**
```
