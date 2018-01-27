package com.it.huaxia.springcloud.service;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义实现HystrixCommond
 * @author fengqigui
 * @Description
 * @Date 2018/01/25 20:10
 */
public class HelloHystrixCommond extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    /**
     * 线程隔离，线程组
     * @param mydefined 自定义字符串,线程池就是根据这个划分了
     */
    public HelloHystrixCommond(String mydefined, RestTemplate restTemplate) {

        super(HystrixCommandGroupKey.Factory.asKey(mydefined));
        this.restTemplate = restTemplate;

    }

    @Override
    protected String run() throws Exception {

        System.out.println(Thread.currentThread().getName());
        return  restTemplate.getForEntity("http://EUREKA-CONSUMER/hello", String.class).getBody();
    }

    @Override
    protected String getFallback() {
        return "error112";
    }

    /**
     * hystrix请求缓存:
     *      他是在request域里面的缓存，使用的是类似于Map的形式。这种缓存很鸡肋
     * @author fengqigui
     * @method  getCacheKey
     * @date 2018年01月26日 20:56:13
     * @param
     * @return
     */
    @Override
    protected String getCacheKey() {

       return "helloGetCacheKey";// 这里是不能写死的
    }

}
