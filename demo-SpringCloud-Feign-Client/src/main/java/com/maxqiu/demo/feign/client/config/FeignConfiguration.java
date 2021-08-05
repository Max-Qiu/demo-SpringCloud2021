package com.maxqiu.demo.feign.client.config;

/**
 * Feign全局配置
 *
 * 推荐直接在yml中配置
 *
 * @author Max_Qiu
 */
// 单独配置日志信息
// @Configuration
public class FeignConfiguration {
    // @Bean
    feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.FULL;
    }
}
