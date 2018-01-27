package com.it.huaxia.springcloud.service.MergeRequest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * 测试请求合并
 *      硬编码实现
 * @author fengqigui
 * @Description
 * @Date 2018/01/26 21:20
 */
public class MergeRequestComand extends HystrixCommand<List<String>>{

    private List<Long> ids;
    private RestTemplate restTemplate;

    public MergeRequestComand(String groupKey, RestTemplate restTemplate, List<Long> ids) {
        super(HystrixCommandGroupKey.Factory.asKey(groupKey));
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected List<String> run() throws Exception {

        System.out.println("发送请求----参数为："+ids.toString()+"---"+Thread.currentThread().getName());
        String[] strings = restTemplate.getForEntity("http://EUREKA-CONSUMER/listUser?ids={1}", String[].class, StringUtils.join(ids, ",")).getBody();

        return Arrays.asList(strings);
    }
}
