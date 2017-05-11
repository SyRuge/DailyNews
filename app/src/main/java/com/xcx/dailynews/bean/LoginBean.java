package com.xcx.dailynews.bean;

/**
 * Created by xcx on 2017/4/9.
 */

public class LoginBean {
    public LoginBean() {

    }

    public String status;
    public String token;

    @Override
    public String toString() {
        return "LoginBean{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
