package com.dailinews.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyClass {


    public static void main(String[] args) {

        String s = "{\"url\":\"success\",\"channelid\":\"xyjk\"}";
        JSONObject object = JSON.parseObject(s);
        //解析json获取数据

        OtherBean bean = JSON.parseObject(s, OtherBean.class);
        List<OtherBean> list = new ArrayList<>();
        list.add(bean);

        System.out.println(list.get(0).toString());
    }
}
