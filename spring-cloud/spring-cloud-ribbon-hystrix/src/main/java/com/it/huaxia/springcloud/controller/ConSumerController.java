package com.it.huaxia.springcloud.controller;

import com.it.huaxia.springcloud.service.HelloHystrixCommond;
import com.it.huaxia.springcloud.service.HelloObserveHystrixComand;
import com.it.huaxia.springcloud.service.HelloService;
import com.it.huaxia.springcloud.todo.futureObserve;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    @Autowired
    private HelloService helloService;

    @RequestMapping("/consumer")
    public String getHello(){

        // 获得生产者的访问位置，
        // ServiceInstance instance = loadBalancerClient.choose("stores");

        // 拼成连接
        // URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));

        //return uri.toString();
        return null;
    }

    /**
     * 阻塞式IO
     * @return
     */
    @RequestMapping("/consumer1")
    public String hello(){
        HelloHystrixCommond commond = new HelloHystrixCommond("hello", restTemplate);
        String result = commond.execute();
        return result;

    }
    /**
     * 阻塞式IO
     * @return
     */
    @RequestMapping("/consumer2")
    public String hello2(){
        return helloService.helloService();

    }

    /**
     * 非阻塞式IO
     *  自定义Command
     * @return
     */
    @RequestMapping("/consumer3")
    public String hello3() throws ExecutionException, InterruptedException {
        HelloHystrixCommond commond = new HelloHystrixCommond("hello", restTemplate);
        long now  = System.currentTimeMillis();
        Future<String> queue = commond.queue();
        System.out.println("-----1-----"+now);
        long end = System.currentTimeMillis();
        System.out.println("----- -----:"+(end-now));
        String result = queue.get();
        long last = System.currentTimeMillis();
        System.out.println("--last-----:"+(last-end));
        return result;
    }
    /**
     * 非阻塞式IO
     *  注解Command
     * @return
     */
    @RequestMapping("/consumer4")
    public String hello4(){

        AsyncResult<String> future = new AsyncResult<String>() {
            @Override
            public String invoke() {
                return helloService.helloService();
            }
        };

        return future.get();

    }

    /**
     * 调用多个服务,
     *  热执行：是指多个服务不会等待，直接执行
     *  冷执行：从上往下有顺序的执行，也就是HelloObserveHystrixComand.construct两个服务一次执行
     *  这里可封装这个Observer<String>(){...}方法，使用泛型
     * @return
     */
    @RequestMapping("/consumer5")
    public String hello5() throws InterruptedException {

        List<String> listResult = new ArrayList<>();

        HelloObserveHystrixComand comand = new HelloObserveHystrixComand("hello", restTemplate);
        Observable observe = comand.observe();//热执行

        /*observe.subscribe(new futureObserve<String>(listResult));*/
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("聚合完成所有的请求查询");
            }

            @Override
            public void onError(Throwable throwable) {
               throwable.printStackTrace();
            }

            @Override
            public void onNext(String result) {
                System.out.println("result-----结果来了--");
                listResult.add(result);
            }
        });

        return listResult.toString();


    }



    /**
     * 调用多个服务,
     *  这里可封装这个Observer<String>(){...}方法，使用泛型
     *  冷执行：可用于初始化，上下有次序
     * @return
     */
    @RequestMapping(value = "/consumer6")
    public String hello6() throws InterruptedException {

        List<String> listResult = new ArrayList<>();

        HelloObserveHystrixComand comand = new HelloObserveHystrixComand("hello", restTemplate);
        // 冷执行
        Observable observe = comand.toObservable();
        Thread.sleep(1000);
        /*observe.subscribe(new futureObserve<String>(listResult));*/
        //热执行
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("聚合完成所有的请求查询");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String result) {
                listResult.add(result);
            }
        });

        return listResult.toString();


    }

    @RequestMapping("/consumer7")
    public String hello7(){

        Observable<String> stringObservable = helloService.helloService1();

        return "";

    }






}
