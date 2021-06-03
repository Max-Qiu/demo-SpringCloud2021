package com.maxqiu.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Max_Qiu
 */
@RestController
@RequestMapping("")
public class IndexController {
    @Value("${spring.application.name}:${server.port}")
    String info;

    @GetMapping("info")
    public String info() {
        return info;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "discovery")
    public DiscoveryClient discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
            List<ServiceInstance> instances = discoveryClient.getInstances(element);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort()
                    + "\t" + instance.getUri());
            }
            System.out.println();
        }
        return discoveryClient;
    }

    @GetMapping("timeout")
    public String timeout() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return info;
    }
}
