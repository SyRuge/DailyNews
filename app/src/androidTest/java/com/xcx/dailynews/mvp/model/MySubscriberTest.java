package com.xcx.dailynews.mvp.model;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xcx on 2016/11/4.
 */
public class MySubscriberTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onNext() throws Exception {
        String url="http://news.163.com/16/1123/07/C6HRUNE9000189FH.html";
        //从一个URL加载一个Document对象。
        Document doc = Jsoup.connect(url).get();
        //选择“美食天下”所在节点
        Elements elements = doc.select("post_text");
        //打印 <a>标签里面的title
        Log.e("TAG",elements.select("p").attr("otitle"));
        System.out.println(elements.select("p").attr("otitle"));
    }

}