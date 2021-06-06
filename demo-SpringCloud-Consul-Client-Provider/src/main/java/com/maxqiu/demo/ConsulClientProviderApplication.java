package com.maxqiu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Max_Qiu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulClientProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulClientProviderApplication.class, args);
    }
}
