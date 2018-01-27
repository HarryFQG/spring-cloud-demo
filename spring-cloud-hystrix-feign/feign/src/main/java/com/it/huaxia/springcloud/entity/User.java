package com.it.huaxia.springcloud.entity;

import java.io.Serializable;

/**
 * 重点在feign的使用
 * @author fengqigui
 * @Description
 * @Date 2018/01/27 15:30
 */
public class User implements Serializable{

    private static final  long serialVersionID = 1L;
    private Integer id;

    private String userName;

    private String pwd;

    public User() {
    }

    public User(Integer id, String userName, String pwd) {
        this.id = id;
        this.userName = userName;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}
