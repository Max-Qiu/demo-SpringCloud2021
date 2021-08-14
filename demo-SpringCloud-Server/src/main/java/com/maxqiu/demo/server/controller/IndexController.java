package com.maxqiu.demo.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Max_Qiu
 */
@RestController
public class IndexController {
    @Value("${server.port}")
    private String port;

    @GetMapping("index")
    public Map<String, String> index() {
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "index");
        map.put("port", port);
        return map;
    }

    @GetMapping("timeout")
    public Map<String, String> timeout() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "timeout");
        map.put("port", port);
        return map;
    }
}
