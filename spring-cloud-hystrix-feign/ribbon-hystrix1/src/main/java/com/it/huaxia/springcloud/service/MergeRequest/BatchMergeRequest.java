package com.it.huaxia.springcloud.service.MergeRequest;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求合并的控制方法，对MergeRequestCommand进行包装,不执行
 *  HystrixCollapser<List<String>,String, Long>：
 *      三个参数：List<String> 返回类型
 *              String ：单个请求结果
 *              Long  ：请求参数类型
 * @author fengqigui
 * @Description
 * @Date 2018/01/26 21:28
 */
public class BatchMergeRequest extends HystrixCollapser<List<String>,String, Long> {

    /**
     * 单个调用的ID，来自调用Command方法初始化传进来的
     */
    private Long id;

    /**
     * 构造器
     */
    private RestTemplate restTemplate;

    public BatchMergeRequest(Long id, RestTemplate restTemplate) {
        /**
         * 200:两个请求的间隔200ms内就合并。比较关键
         */
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("mergeReq"))
                        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                                .withTimerDelayInMilliseconds(200)));
        this.id = id;
        this.restTemplate = restTemplate;
    }

    /**
     * 获得单个的请求参数
     * @return
     */
    @Override
    public Long getRequestArgument() {
        return id;
    }

    /**
     * 将单个命令合并
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Long>> collection) {

        List<Long> ids = new ArrayList<>(collection.size());
        ids.addAll(collection.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        MergeRequestComand comand =new MergeRequestComand("mergeReqKey", restTemplate, ids);
        return comand;

    }

    /**
     * 合并的结果，将合并的结果进行对各个请求分发
     * @param results : 执行的结果，注意顺序
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<String> results, Collection<CollapsedRequest<String, Long> > collection) {
        System.out.println("分配批量请求结果....");

        int count = 0;
        for (CollapsedRequest<String, Long> collapsedRequest:collection) {
            String result = results.get(count++);
            collapsedRequest.setResponse(result);

        }
        
        
        
    }





}
