package com.maxqiu.demo.feign.client.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.maxqiu.demo.common.pojo.ParamsVO;

/**
 * @author Max_Qiu
 */
@FeignClient(
    // 服务名称
    name = "feign-server",
    // Bean 名称，默认使用name，若有多个Feign访问同一个服务，则需要定制不同的contextId
    contextId = "serverFeignService",
    // 自定义Feign配置
    // configuration = {CustomFeignConfiguration.class},
    // 所有方法的前缀。默认：空
    path = "")
public interface ServerFeignService {
    @GetMapping("")
    Map<String, String> index();

    @GetMapping("timeout")
    Map<String, String> timeout();

    @GetMapping("queryMap")
    String queryMap(@SpringQueryMap ParamsVO vo);
}
