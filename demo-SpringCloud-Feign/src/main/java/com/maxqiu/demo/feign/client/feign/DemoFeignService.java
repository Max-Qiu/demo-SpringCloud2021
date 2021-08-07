package com.maxqiu.demo.feign.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.maxqiu.demo.common.pojo.ParamsVO;

/**
 * 服务调用
 *
 * 不同参数时的调用方法
 *
 * @author Max_Qiu
 */
@FeignClient(name = "server", contextId = "demoFeignService", path = "demo")
public interface DemoFeignService {
    /**
     * 无参请求
     */
    @GetMapping("normal")
    String normal();

    /**
     * 单个参数
     */
    @GetMapping("singleParam")
    String singleParam(@RequestParam String param);

    /**
     * 多个参数
     */
    @GetMapping("multiParam")
    String multiParam(@RequestParam String param1, @RequestParam String param2);

    /**
     * 实体参数
     *
     * 注意：实体需要使用@SpringQueryMap注解
     */
    @GetMapping("objParam")
    ParamsVO objParam(@SpringQueryMap ParamsVO vo);

    /**
     * json参数
     */
    @PostMapping("jsonParam")
    ParamsVO jsonParam(@RequestBody ParamsVO vo);
}
