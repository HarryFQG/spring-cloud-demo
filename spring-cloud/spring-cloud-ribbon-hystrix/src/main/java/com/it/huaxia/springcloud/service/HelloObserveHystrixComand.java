package com.it.huaxia.springcloud.service;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * 对于多个一个controller中调用多个service
 * 观察者模式，基于响应式编程。也就是RX编程
 * @author fengqigui
 * @Description
 * @Date 2018/01/25 20:56
 */
public class HelloObserveHystrixComand extends HystrixObservableCommand {

    private RestTemplate restTemplate;
    public HelloObserveHystrixComand(String mydefined, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey(mydefined));
        this.restTemplate = restTemplate;
    }

    /**
     * 固定写法
     * @return
     */
    @Override
    protected Observable construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {


            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println(Thread.currentThread().getName());

                // 只要是订阅了这个就执行下面的逻辑
                try {
                    if(!subscriber.isUnsubscribed()){
                        System.out.println("方法执行----");
                        String result = restTemplate.getForEntity("http://EUREKA-CONSUMER/hello", String.class).getBody();
                        // 向下传递结果
                        subscriber.onNext(result);
                        String result1 = restTemplate.getForEntity("http://EUREKA-CONSUMER/hello", String.class).getBody();
                        // 向下传递结果
                        subscriber.onNext(result1);
                        subscriber.onCompleted();// 这个之后不会执行
                    }
                } catch (RestClientException e) {
                    // 如果有错误则返回，调用者
                    subscriber.onError(e);
                }


            }
        });
    }

    /**
     * 服务降级
     * @return
     */
    @Override
    protected Observable<String> resumeWithFallback() {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext("error");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
