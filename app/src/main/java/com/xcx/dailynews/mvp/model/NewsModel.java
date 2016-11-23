package com.xcx.dailynews.mvp.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.bean.BaseDataBean;
import com.xcx.dailynews.data.helper.MyOpenHelper;
import com.xcx.dailynews.net.NewsService;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xcx on 2016/11/3.
 */

public class NewsModel extends BaseModel {


    protected String mType;
    protected String mChannelId;
    protected Subscription mSubscribe;

    @Override
    public void removeMemeoryCache(String channelId) {
        mCacheLoader.mMemoryCache.removeMemoryCache(channelId + "_" + 0);
    }

    @Override
    public void unSubscribe() {
        if (mSubscribe!=null){
            if (!mSubscribe.isUnsubscribed()){
                mSubscribe.unsubscribe();
            }
        }
    }

    @Override
    public void getDataFromModel(String type, final String channelId, int loadType, final int
            pageNum) {
        this.mType = type;
        this.mChannelId = channelId;


        Observable<List<BaseDataBean>> memoryCache = mCacheLoader.mMemoryCache.get(channelId +
                "_" + pageNum, BaseDataBean.class);
        Observable<List<BaseDataBean>> diskCache = mCacheLoader.mDiskCache.get(channelId + "_" +
                pageNum, channelId, BaseDataBean.class);

        NewsService newsService = mRetrofit.create(NewsService.class);
        Observable<List<BaseDataBean>> networkCache = newsService.getPastNewsService(type,
                channelId, pageNum * 20 + "")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Schedulers.io().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                                //非阻塞IO线程
                                //1存入内存
                                mCacheLoader.mMemoryCache.put(channelId + "_" + pageNum, s);
                                mCacheLoader.mDiskCache.put(channelId + "_" + pageNum, s);
                            }
                        });
                    }
                })
                .observeOn(Schedulers.io())
                .map(new ResultFun1());

       mSubscribe=Observable.concat(memoryCache, diskCache, networkCache)
                .first(new CacheFun1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseDataBean>(loadType));

    }


    class ResultFun1 implements Func1<String, List<BaseDataBean>> {

        @Override
        public List<BaseDataBean> call(String s) {
            //解析Json
            //解析json对象
            JSONObject object = JSON.parseObject(s);
            //从json对象中获取json数组
            JSONArray array = object.getJSONArray(mChannelId);
            //解析json数组获取数据
            List<BaseDataBean> list = JSON.parseArray(array.toJSONString(), BaseDataBean.class);
            return list;
        }
    }

    class CacheFun1 implements Func1<List<BaseDataBean>, Boolean> {

        @Override
        public Boolean call(List<BaseDataBean> baseDataBeen) {
            return baseDataBeen != null;
        }
    }

    @Override
    protected void initDataBase() {
        SQLiteDatabase db = MyOpenHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        for (int i = 0; i < 6; i++) {
            ContentValues v = new ContentValues();
            v.put("id", i);
            v.put("name", ApiConstants.allChannel[i]);
            v.put("orderId", i);
            v.put("selected", 1);
            db.insert("channel", null, v);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
}
