package com.dailinews.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class MyClass {

    public static String json = "{\"data\":[{\"age\":42,\"id\":1,\"male\":true," +
            "\"name\":\"Sherlock " +
            "Holmes\",\"schoolId\":1},{\"age\":42,\"id\":2,\"male\":false,\"name\":\"John " +
            "Watson\",\"schoolId\":1},{\"age\":42,\"id\":2,\"male\":false,\"name\":\"\"," +
            "\"schoolId\":1}]}";

    public static void main(String[] args) {

        JSONObject object = JSON.parseObject(json);

        JSONArray data = object.getJSONArray("data");

        List<Bean> b = JSON.parseArray(data.toJSONString(), Bean.class);

        String s="hi"+"_"+2;
        System.out.println(s);

    }


}
