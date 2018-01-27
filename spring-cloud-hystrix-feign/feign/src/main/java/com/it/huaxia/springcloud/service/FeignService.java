package com.it.huaxia.springcloud.service;

import com.it.huaxia.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 重点feign
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 15:40
 * 注解中：
 *  eureka-consumer：是远程消费者提供的服务
 *  fallback:降级类
 *  configuration：精细的控制，hystrix，ribbon的配置这里可以使用
 */

@FeignClient(value = "eureka-service", fallback = FeignFallBack.class)
public interface FeignService {

    /**
     * 映射到远程的hello()
     * @return
     */
    @RequestMapping("/hello")
    String hello();
    @RequestMapping("/listUser")
    List<String> getUserList(@RequestParam("ids") String ids);
    @RequestMapping(value = "/getUserSex", method = RequestMethod.GET)
    String getUserSexByName(@RequestParam("name") String name);


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    User getUserByName(@RequestHeader("name") String name, @RequestHeader("pwd") String pwd);

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    String addUser(@RequestBody User user);

    // 错误的不行非得映射
    //User getUser(String name, String pwd);

}
