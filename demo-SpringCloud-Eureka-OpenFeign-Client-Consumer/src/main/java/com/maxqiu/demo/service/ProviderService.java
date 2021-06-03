package com.maxqiu.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Max_Qiu
 */
@Service
@FeignClient(value = "EUREKA-CLIENT-PROVIDER")
public interface ProviderService {
    @GetMapping("info")
    String info();

    @GetMapping("timeout")
    String timeout();
}
