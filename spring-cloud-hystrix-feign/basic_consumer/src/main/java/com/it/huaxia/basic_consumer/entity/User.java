package com.it.huaxia.basic_consumer.entity;

import java.io.Serializable;

/**
 * @author fengqigui
 * @Description
 * @Date 2018/01/22 20:59
 */
public class User  implements Serializable{
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
