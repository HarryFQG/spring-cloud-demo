package com.it.huaxia.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * 测试对服务进行负载均衡，客户的请求先经过。ribbon，ribbon使用负载均衡的算法进行转发
 * @author fengqigui
 * @Description
 * @Date 2018/01/24 19:35
 */
@RestController
public class ConSumerController {


    /**
     * 实现负载均衡方式一：
     * ribbon进行负载均衡的客户端，他是使用负载均衡的算法选出生产者的位置
     *  默认是：轮询算法
     **/
    //@Autowired
    //private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumer")
    public String getHello(){

        // 获得生产者的访问位置，
        // ServiceInstance instance = loadBalancerClient.choose("stores");

        // 拼成连接
        // URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));

        //return uri.toString();
        return null;
    }

    @RequestMapping("/consumer1")
    public String hello(){
        /**
         * http://EUREKA-SERVER/hello:
         *  EUREKA-SERVER:生产者的名字
         *  hello：消费之的RequestMapping
         * String.class:对应方法返回值得类型
         */
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://EUREKA-CONSUMER/hello", String.class);
        String body = forEntity.getBody();
        return body;

    }



















}
