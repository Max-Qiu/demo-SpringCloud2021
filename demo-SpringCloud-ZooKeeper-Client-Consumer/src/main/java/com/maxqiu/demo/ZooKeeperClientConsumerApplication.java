package com.maxqiu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Max_Qiu
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZooKeeperClientConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZooKeeperClientConsumerApplication.class, args);
    }
}
