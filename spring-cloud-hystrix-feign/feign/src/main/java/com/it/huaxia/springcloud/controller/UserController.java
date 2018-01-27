package com.it.huaxia.springcloud.controller;

import com.it.huaxia.springcloud.entity.User;
import com.it.huaxia.springcloud.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 15:32
 */
@RestController
public class UserController {

    @Autowired
    FeignService feignService;

    @RequestMapping("/consumer1")
    public String helloConsumer(){
        // 映射到远程的hello方法
        return feignService.hello();
    }

    @RequestMapping(value = "/getUserSexMe", method = RequestMethod.GET)
    public String getUserSexByName(){

        String sex = feignService.getUserSexByName("lisi");
        return sex;

    }


    @RequestMapping(value = "/getUserMe", method = RequestMethod.GET)
    public User getUserByName(){

        User name = feignService.getUserByName("wangwu", "123456");
        String lisi = feignService.addUser(new User(2, "lisi", "123456"));
        return name;

    }

    @RequestMapping(value = "/addUserMe", method = RequestMethod.GET)
    public String addUser(){

        String lisi = feignService.addUser(new User(2, "lisi", "123456"));
        return  lisi;
    }

    /**
     * 错误的映射
     * @return
     */
    @RequestMapping(value = "/getUserMes", method = RequestMethod.GET)
    public User getUser(){

//        User lisi = feignService.getUser("王五", "123456");
        return  null;
    }
}
