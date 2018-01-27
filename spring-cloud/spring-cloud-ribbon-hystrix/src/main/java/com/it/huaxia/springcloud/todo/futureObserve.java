package com.it.huaxia.springcloud.todo;

import rx.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengqigui
 * @Description
 * @Date 2018/01/25 21:18
 */
public class futureObserve<T> implements Observer<T> {
    List<T> listResult = new ArrayList<>();

    public futureObserve(List<T> listResult) {
        this.listResult = listResult;
    }

    public Object getResult(){
        return listResult;
    }
    @Override
    public void onCompleted() {
        System.out.println("聚合完成所有的请求查询");
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onNext(T result) {
        listResult.add(result);
    }
}
