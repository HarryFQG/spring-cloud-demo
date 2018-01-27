package com.it.huaxia.basic_consumer2.server;

import com.it.huaxia.basic_consumer2.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 重点feign
 * @author fengqigui
 * @Description
 * @Date 2018/01/22 21:00
 */
@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String hello(){

        System.out.println("请求进入了consumer2----");
        return "hello spring cloud";
    }


    /**************************/
    /***
     * 请求合并
     * @param ids
     * @return
     * @RequestMapping("/listUser/{id}")
     */
    @RequestMapping("/listUser")
    public List<String> getUserList(String ids){

        List<String> list = new ArrayList<>();
        list.add("aaa---"+ids);
        list.add("bbb");
        list.add("cccc");
        return list;

    }
/**********************feign**************************************/
    /**
     * 测试feign,上面的是测试hystrix
     * @author fengqigui
     * @method  getUserName
     * @date 2018年01月27日 15:10:37
     * @param
     * @return
     */

    @RequestMapping(value = "/getUserSex", method = RequestMethod.GET)
    public String getUserSexByName(@RequestParam String name){
        return "Hello "+name;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUserByName(@RequestHeader  String name, @RequestHeader String pwd){

        return new User(1,name,pwd);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody User user){

        return "OK--"+user.getUserName();

    }

    /**
     * 测试service
     * @param name
     * @param pwd
     * @return
     */
    public User getUser(String name, String pwd){

        return new User(2, name, pwd);
    }


}
