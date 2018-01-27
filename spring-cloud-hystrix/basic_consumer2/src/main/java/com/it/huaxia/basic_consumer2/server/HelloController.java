package com.it.huaxia.basic_consumer2.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
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


}
