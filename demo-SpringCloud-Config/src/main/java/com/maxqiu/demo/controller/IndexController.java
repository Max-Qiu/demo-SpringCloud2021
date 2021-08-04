package com.maxqiu.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置读取
 *
 * @author Max_Qiu
 */
// 启用自动刷新（该注解仅能配置在类上）
@RefreshScope
@RestController
public class IndexController {
    @Value("${user}")
    private String user;

    @Value("${profile}")
    private String profile;

    @Value("${pub}")
    private String pub;

    @GetMapping("")
    public Map<String, String> index() {
        HashMap<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("profile", profile);
        map.put("pub", pub);
        return map;
    }
}
