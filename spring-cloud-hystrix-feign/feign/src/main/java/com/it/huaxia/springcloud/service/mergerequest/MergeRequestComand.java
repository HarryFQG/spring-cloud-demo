package com.it.huaxia.springcloud.service.mergerequest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * 对于请求合并，这里可有使用这个或者注解的形式，不适用feign的请求转发。得自己手动写
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 16:46
 */
public class MergeRequestComand extends HystrixCommand<List<String>> {

    public MergeRequestComand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected List<String> run() throws Exception {
        return null;
    }
}
