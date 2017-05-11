package com.xcx.dailynews.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.net.NewsService;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcx on 2017/4/9.
 */

public  class CheckVersionModel extends BaseModel{


    @Override
    public void removeMemeoryCache(String channelId) {

    }


    @Override
    public void unSubscribe() {

    }

    @Override
    protected void getDataFromModel(String type, String channelId, int loadType, int pageNum) {
        NewsService newsService = mRetrofit.create(NewsService.class);
        newsService.CheckVersionService(ApiConstants.CHECK_VERSION_URL,type)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new CheckResultFun1())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<LoginBean>(5));
    }

    class CheckResultFun1 implements Func1<String, List<LoginBean>> {

        @Override
        public List<LoginBean> call(String s) {
            //解析Json
            //解析json对象
            JSONObject object = JSON.parseObject(s);
            //解析json获取数据

            LoginBean bean = JSON.parseObject(s, LoginBean.class);
            List<LoginBean> list = new ArrayList<>();
            list.add(bean);
            return list;

        }
    }

}
