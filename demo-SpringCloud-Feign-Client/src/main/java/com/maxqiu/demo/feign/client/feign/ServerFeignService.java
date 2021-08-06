package com.maxqiu.demo.feign.client.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 服务调用
 *
 * @author Max_Qiu
 */
@FeignClient(
    // 服务名称
    name = "feign-server",
    // Bean 名称，默认使用name，若有多个Feign访问同一个服务，则需要定制不同的contextId
    contextId = "serverFeignService",
    // 所有方法的前缀。默认：空
    path = "")
public interface ServerFeignService {
    @GetMapping("index")
    Map<String, String> index();

    @GetMapping("timeout")
    Map<String, String> timeout();
}
