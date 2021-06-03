package com.maxqiu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.demo.service.ProviderService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Max_Qiu
 */
@RestController
@RequestMapping("")
@Slf4j
public class IndexController {
    @Autowired
    private ProviderService providerService;

    @GetMapping("getProviderInfo")
    public String getProviderInfo() {
        return providerService.info();
    }

    @GetMapping("testProviderTimeout")
    public String testProviderTimeout() {
        return providerService.timeout();
    }
}
