package com.it.springcloud.springcloud.entity;

/**
 * @author fengqigui
 * @Description
 * @Date 2018/01/22 20:59
 */
public class User {

    private Integer id;

    private String userName;

    private String pwd;

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