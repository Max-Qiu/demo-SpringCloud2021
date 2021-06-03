package com.maxqiu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Max_Qiu
 */
@SpringBootApplication
@EnableFeignClients
public class EurekaOpenFeignClientConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaOpenFeignClientConsumerApplication.class, args);
    }
}
