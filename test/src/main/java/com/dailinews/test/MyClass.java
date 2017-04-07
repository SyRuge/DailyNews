package com.dailinews.test;

import java.util.Stack;

public class MyClass {

    public static String json = "{\"data\":[{\"age\":42,\"id\":1,\"male\":true," +
            "\"name\":\"Sherlock " +
            "Holmes\",\"schoolId\":1},{\"age\":42,\"id\":2,\"male\":false,\"name\":\"John " +
            "Watson\",\"schoolId\":1},{\"age\":42,\"id\":2,\"male\":false,\"name\":\"\"," +
            "\"schoolId\":1}]}";


    public static void main(String[] args) {

        Stack<Integer> sData=new Stack<>();
        Stack<Integer> sMin=new Stack<>();

        

        /*JSONObject object = JSON.parseObject(json);

        JSONArray data = object.getJSONArray("data");

        List<Bean> b = JSON.parseArray(data.toJSONString(), Bean.class);


        String url = "http://3g.163.com/post/pic_xhtml.jsp?picurl=http%3A//s.cimg.163" +
                ".com/i/cms-bucket.nosdn.127.net/catchpic/e/e0/e077487ff7ad4f7d7105e5ff1752be1d" +
                ".jpg.320x320.auto.jpg";

        //   String subUrl = "http:" + url.substring(url.indexOf("%3A") + 3);

        String s = "http://3g.163.com/news/16/1128/15/C6VH29PD0001875P.html";
        String s1 = s.substring(0, s.lastIndexOf(".html")) + "_0.html";

        System.out.println(s1);*/
    }
}
