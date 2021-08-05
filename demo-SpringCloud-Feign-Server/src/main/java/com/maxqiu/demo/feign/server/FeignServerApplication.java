package com.maxqiu.demo.feign.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author Max_Qiu
 */
// 启用注册中心
@EnableDiscoveryClient
@SpringBootApplication
public class FeignServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignServerApplication.class, args);
    }
}
