package com.maxqiu.demo.controller;

import javax.annotation.Resource;

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
    private static final String PROVIDER_URL = "http://consul-client-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("getProviderInfo")
    public String getProviderInfo() {
        return restTemplate.getForObject(PROVIDER_URL + "/info", String.class);
    }
}
