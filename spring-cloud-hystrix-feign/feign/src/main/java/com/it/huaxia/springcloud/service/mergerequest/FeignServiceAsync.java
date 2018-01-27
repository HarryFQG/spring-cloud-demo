package com.it.huaxia.springcloud.service.mergerequest;

import com.it.huaxia.springcloud.entity.User;
import com.it.huaxia.springcloud.service.FeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * 使用feign实现异步调用,注意还要写一个切面类
 *
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 17:05
 */
@Service
public class FeignServiceAsync {

    @Autowired
    private FeignService feignService;

    /**
     * 异步的形式调用
     * @return
     */
    @HystrixCommand
    public Future<String> getEmployeeAsync(){

        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return feignService.getUserSexByName("lisi");
            }
        };
    }
    @HystrixCommand
    public Future<User> getUser(){
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return feignService.getUserByName("lisi","123456");
            }
        };
    }

    /**
     * 同步的形式
     * @return
     */
    @HystrixCommand
    public String getUserSexByName(){

        return feignService.getUserSexByName("123");
    }

}
