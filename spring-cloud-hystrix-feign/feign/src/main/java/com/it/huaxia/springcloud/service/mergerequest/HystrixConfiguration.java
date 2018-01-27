package com.it.huaxia.springcloud.service.mergerequest;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配合FeignServiceAsync类使用，
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 17:12
 */
@Configuration
public class HystrixConfiguration {

    @Bean
    public HystrixCommandAspect hystrixCommandAspect(){

        return new HystrixCommandAspect();
    }

}
