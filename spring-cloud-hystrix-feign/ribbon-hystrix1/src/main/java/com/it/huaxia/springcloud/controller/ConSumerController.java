package com.it.huaxia.springcloud.controller;

import com.it.huaxia.springcloud.service.HelloHystrixCommond;
import com.it.huaxia.springcloud.service.HelloObserveHystrixComand;
import com.it.huaxia.springcloud.service.HelloService;
import com.it.huaxia.springcloud.service.MergeRequest.BatchMergeRequest;
import com.it.huaxia.springcloud.service.MergeRequest.MergeRequestComand;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

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
/***************************请求缓存*********************************/
    /**
     * 请求缓存
     * @author fengqigui
     * @method  hello8
     * @date 2018年01月26日 20:49:00
     * @param
     * @return
     */

    @RequestMapping("/consumer8")
    public String hello8(){


        HystrixRequestContext.initializeContext();
        HelloHystrixCommond commond = new HelloHystrixCommond("hello", restTemplate);
        String execute = commond.execute();
        // 注意这种情况下走缓存
        HelloHystrixCommond commond1 = new HelloHystrixCommond("hello", restTemplate);
        String execute1 = commond1.execute();

        // 清理缓存
        // HystrixRequestCache.getInstance("helloGetCacheKey").clear();
        return execute;

    }

    /****************************请求合并*****************************/
    /**
     * 代码实现
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("/consumer9")
    public String helloConsumer() throws ExecutionException, InterruptedException {

        // request 域中操作，必须初始化
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        BatchMergeRequest comand = new BatchMergeRequest(1L,restTemplate);
        BatchMergeRequest comand1 = new BatchMergeRequest(2L,restTemplate);
        BatchMergeRequest comand2 = new BatchMergeRequest(3L,restTemplate);

        // 只能使用future,也就是异步的，同步的会阻塞
        Future<String> future = comand.queue();
        Future<String> future1 = comand1.queue();

        String result = future.get();
        String result1 = future1.get();

        Thread.sleep(2000);

        // 注意这个请求不会合并
        Future<String> future2 = comand2.queue();
        String result2 = future2.get();

        System.out.println("rst1---:"+result+"rst2---:"+result1+"rst2---:"+result2);

        context.close();
        return result2;

    }






}
