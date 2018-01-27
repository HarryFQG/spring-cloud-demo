package com.it.springcloud.springcloud.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengqigui
 * @Description
 * @Date 2018/01/22 21:00
 */
@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String hello() throws InterruptedException {

        Thread.sleep(800);
        return "hello spring cloud";
    }

}
