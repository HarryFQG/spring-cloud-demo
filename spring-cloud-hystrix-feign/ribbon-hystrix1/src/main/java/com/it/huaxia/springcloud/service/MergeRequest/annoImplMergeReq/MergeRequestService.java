package com.it.huaxia.springcloud.service.MergeRequest.annoImplMergeReq;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 注解实现请求合并
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 12:01
 */
@Service
public class MergeRequestService {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 这个注解能实现大部分场景，ID递增的
     *  getUserList:下面的方法名
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "getUserList", collapserProperties = {@HystrixProperty(
            name = "timerDelayInMilliseconds", value = "200"
    )} )
    public Future<String> batchRequest(long id){
        return null;
    }

    @HystrixCommand
    public List<String> getUserList(List<Long> ids){

        System.out.println("发送请求...参数为："+ids.toString()+"---线程--"+Thread.currentThread().getName());
        String[] result = restTemplate.getForEntity("http://EUREKA-CONSUMER/listUser?ids={1}", String[].class, StringUtils.join(ids, ",")).getBody();
        return Arrays.asList(result);

    }


}
