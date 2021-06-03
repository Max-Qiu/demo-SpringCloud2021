package com.maxqiu.demo.controller;

import org.springframework.beans.factory.annotation.Value;
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
}
