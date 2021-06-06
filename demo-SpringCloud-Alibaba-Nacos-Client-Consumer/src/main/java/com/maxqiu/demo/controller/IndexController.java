package com.maxqiu.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Max_Qiu
 */
@RestController
@RequestMapping("")
public class IndexController {
    private static final String PROVIDER_URL = "http://nacos-client-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("getProviderInfo")
    public String getProviderInfo() {
        return restTemplate.getForObject(PROVIDER_URL + "/info", String.class);
    }
}
