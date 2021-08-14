官方教程：

- [Spring Cloud Sleuth](https://docs.spring.io/spring-cloud-sleuth/docs/3.0.3/reference/htmlsingle/)
- [Zipkin](https://zipkin.io/)

服务端安装教程

- [Zipkin服务端安装教程（Jar方式、Docker方式以及Elasticsearch数据持久化）](https://maxqiu.com/article/detail/115)

# 环境搭建

以下配置每个服务都需要添加，建议放在父pom中

## POM

```xml
<!-- 此处省略其他依赖如注册中心、配置中心 -->
<!-- 链路追踪 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```

## Application

> 无需添加任何注解

## yml

```yml
spring:
  application:
    name: sleuth # 服务名称（必须配置）
  sleuth:
    sampler:
      probability: 1 # 采样比例，0.0 - 1.0 之间
  zipkin:
    base-url: http://127.0.0.1:9411 # zipkin 服务端地址
```

# 使用

1. 启动模块后，访问一些`URL`，
2. 打开`zipkin`的控制面板，
    - 点击`RUN QUERY`即可看到效果，例如：<br>![](https://cdn.maxqiu.com/upload/c31247cd4f614e9fbc8418beefa285cd.jpg)
    - 点击左上角`依赖`，再点击搜索按钮，即可看到依赖关系图，例如：<br>![](https://cdn.maxqiu.com/upload/1d421c80d25442e4b555e760ce2e5233.jpg)
