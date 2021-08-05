package com.maxqiu.demo.feign.client.config;

/**
 * 自定义Feign配置
 *
 * @author Max_Qiu
 */
// 注意，此处不能添加@Configuration注解
public class CustomFeignConfiguration {
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }
}
