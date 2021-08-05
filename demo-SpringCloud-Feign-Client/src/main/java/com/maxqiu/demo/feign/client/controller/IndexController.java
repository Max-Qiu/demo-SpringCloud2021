package com.maxqiu.demo.feign.client.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.demo.common.pojo.ParamsVO;
import com.maxqiu.demo.feign.client.feign.ServerFeignService;

/**
 * @author Max_Qiu
 */
@RestController
public class IndexController {
    @Autowired
    private ServerFeignService serverFeignService;

    @GetMapping("")
    public Map<String, String> index() {
        return serverFeignService.index();
    }

    @GetMapping("timeout")
    public Map<String, String> timeout() {
        return serverFeignService.timeout();
    }

    @GetMapping("queryMap")
    public String queryMap(ParamsVO vo) {
        return serverFeignService.queryMap(vo);
    }
}
