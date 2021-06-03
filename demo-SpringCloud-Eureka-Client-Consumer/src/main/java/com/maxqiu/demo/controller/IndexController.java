package com.maxqiu.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Max_Qiu
 */
@RestController
@RequestMapping("")
@Slf4j
public class IndexController {
    private static final String PROVIDER_URL = "http://EUREKA-CLIENT-PROVIDER";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("getProviderInfo")
    public String getProviderInfo() {
        return restTemplate.getForObject(PROVIDER_URL + "/info", String.class);
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
}
