package com.maxqiu.demo.feign.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.demo.common.pojo.ParamsVO;

/**
 * @author Max_Qiu
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    /**
     * 无参请求
     */
    @GetMapping("normal")
    public String normal() {
        return "normal";
    }

    /**
     * 单个参数
     */
    @GetMapping("singleParam")
    public String singleParam(String param) {
        return param;
    }

    /**
     * 多个参数
     */
    @GetMapping("multiParam")
    public String multiParam(String param1, String param2) {
        return param1 + " " + param2;
    }

    /**
     * 实体参数
     */
    @GetMapping("objParam")
    public ParamsVO objParam(ParamsVO vo) {
        return vo;
    }

    /**
     * json参数
     */
    @PostMapping("jsonParam")
    public ParamsVO jsonParam(@RequestBody ParamsVO vo) {
        return vo;
    }
}
