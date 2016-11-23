package com.xcx.dailynews.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.bean.PhotoBean;
import com.xcx.dailynews.net.NewsService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcx on 2016/11/8.
 */

public class PhotoModel extends BaseModel {


    @Override
    public void removeMemeoryCache(String channelId) {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    protected void getDataFromModel(String type, String channelId, int loadType, int pageNum) {

    }

    @Override
    protected void getDataFromserver(String type, String channelId) {
        NewsService newsService = mRetrofit.create(NewsService.class);
        newsService.getPhotoService(ApiConstants.PHOTO_URL + Integer.valueOf(type) + "/" +
                Integer.valueOf(channelId))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new PhotoResultFun1())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<PhotoBean>(4));
    }

    @Override
    protected void getDataFromserver(String type, String channelId, int pageNum) {

    }

    class PhotoResultFun1 implements Func1<String, List<PhotoBean>> {

        @Override
        public List<PhotoBean> call(String s) {
            //解析Json
            //解析json对象
            JSONObject object = JSON.parseObject(s);
            //从json对象中获取json数组
            JSONArray array = object.getJSONArray("results");
            //解析json数组获取数据
            List<PhotoBean> list = JSON.parseArray(array.toJSONString(), PhotoBean.class);
            return list;

        }
    }

}
