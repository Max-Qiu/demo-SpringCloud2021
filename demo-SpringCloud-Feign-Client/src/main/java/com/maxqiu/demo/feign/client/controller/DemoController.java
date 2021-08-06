package com.maxqiu.demo.feign.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxqiu.demo.common.pojo.ParamsVO;
import com.maxqiu.demo.feign.client.feign.DemoFeignService;

/**
 * 客户端示例Controller
 *
 * @author Max_Qiu
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private DemoFeignService demoFeignService;

    /**
     * 无参请求
     */
    @GetMapping("normal")
    public String normal() {
        return demoFeignService.normal();
    }

    /**
     * 单个参数
     */
    @GetMapping("singleParam")
    public String singleParam(String param) {
        return demoFeignService.singleParam(param);
    }

    /**
     * 多个参数
     */
    @GetMapping("multiParam")
    public String multiParam(String param1, String param2) {
        return demoFeignService.multiParam(param1, param2);
    }

    /**
     * 实体参数
     */
    @GetMapping("objParam")
    public ParamsVO objParam(ParamsVO vo) {
        return demoFeignService.objParam(vo);
    }

    /**
     * json参数
     */
    @PostMapping("jsonParam")
    public ParamsVO jsonParam(@RequestBody ParamsVO vo) {
        return demoFeignService.jsonParam(vo);
    }
}
