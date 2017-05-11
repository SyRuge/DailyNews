package com.xcx.dailynews.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.bean.CollectBean;
import com.xcx.dailynews.bean.NewsDetailBean;
import com.xcx.dailynews.net.NewsService;
import com.xcx.dailynews.util.OtherUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcx on 2017/4/9.
 */

public  class UpdateServerModel extends BaseModel{


    @Override
    public void removeMemeoryCache(String channelId) {

    }


    @Override
    public void unSubscribe() {

    }

    @Override
    protected void getDataFromModel(String type, String channelId, int loadType, int pageNum) {
        //查询操作
        if (loadType==10){
            NewsService newsService = mRetrofit.create(NewsService.class);
            newsService.getServerCollectService(ApiConstants.UPDATE_COLLECT_URL,type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(new UpdateResultFun1())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<CollectBean>(10));
        }else if (loadType==11){
            //删除操作
            NewsService newsService = mRetrofit.create(NewsService.class);
            newsService.delServerCollectService(ApiConstants.UPDATE_COLLECT_URL,type,channelId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(new UpdateResultFun1())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<CollectBean>(11));
        }
    }

    /**
     * 插入数据操作
     */
    @Override
    public void getData(String url, String type,CollectBean collectBean) {
        //拼接json
        String json = OtherUtil.getJson(collectBean);
        NewsService newsService = mRetrofit.create(NewsService.class);
        newsService.insertCollectService(ApiConstants.INSERT_COLLECT_URL,type,json)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new InsertResultFun1())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<NewsDetailBean>(12));
    }

    class UpdateResultFun1 implements Func1<String, List<CollectBean>> {

        @Override
        public List<CollectBean> call(String s) {
            //解析Json
            //解析json对象
            JSONObject object = JSON.parseObject(s);
            //解析json获取数据

            CollectBean bean = JSON.parseObject(s, CollectBean.class);
            List<CollectBean> list = new ArrayList<>();
            list.add(bean);
            return list;

        }
    }
    class InsertResultFun1 implements Func1<String, List<NewsDetailBean>> {

        @Override
        public List<NewsDetailBean> call(String s) {

            List<NewsDetailBean> l=new ArrayList<>();
            return l;

        }
    }

}
