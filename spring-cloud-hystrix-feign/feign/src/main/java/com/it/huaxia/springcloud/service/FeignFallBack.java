package com.it.huaxia.springcloud.service;

import com.it.huaxia.springcloud.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务降级
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 16:32
 */
public class FeignFallBack implements FeignService {

    @Override
    public String hello() {
        return "error";
    }

    @Override
    public List<String> getUserList(String ids) {
        return new ArrayList<>(1);
    }

    @Override
    public String getUserSexByName(String name) {
        return "error2";
    }

    @Override
    public User getUserByName(String name, String pwd) {
        return new User(1,"demo","123456");
    }

    @Override
    public String addUser(User user) {
        return "failure";
    }




}
