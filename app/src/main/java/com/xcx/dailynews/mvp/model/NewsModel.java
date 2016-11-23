package com.xcx.dailynews.mvp.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.bean.BaseDataBean;
import com.xcx.dailynews.data.CacheLoader;
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
    private Subscription mSubscribe;

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
        Observable<List<BaseDataBean>> pastNetCache = newsService.getPastNewsService(type,
                channelId, pageNum * 20 + "")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Schedulers.io().createWorker().schedule(new Action0() {
                            @Override
                            public void call() {
                                Log.e("TAG", "doOnNext: "+s );
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

       mSubscribe=Observable.concat(memoryCache, diskCache, pastNetCache)
                .first(new CacheFun1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseDataBean>(loadType));

    }

    @Override
    protected void getDataFromserver(String type, final String channelId) {

        this.mType = type;
        this.mChannelId = channelId;


        Observable<List<BaseDataBean>> memoryCache = mCacheLoader.mMemoryCache.get(channelId,
                BaseDataBean.class);
        Observable<List<BaseDataBean>> diskCache = mCacheLoader.mDiskCache.get(channelId,
                BaseDataBean.class);

        NewsService newsService = mRetrofit.create(NewsService.class);
        Observable<List<BaseDataBean>> networkCache = newsService.getNewsService(type, channelId)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        mCacheLoader.mMemoryCache.put(channelId, s);
                        mCacheLoader.mDiskCache.put(channelId, s);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new ResultFun1());


        Observable observable = Observable.concat(memoryCache, diskCache, networkCache)
                .first(new CacheFun1());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseDataBean>(1));


    }

    @Override
    protected void getDataFromserver(String type, final String channelId, final int pageNum) {

        this.mType = type;
        this.mChannelId = channelId;

        Observable<List<BaseDataBean>> diskCache = mCacheLoader.mDiskCache.get(channelId + pageNum,
                BaseDataBean.class, CacheLoader.loadPageNum);

        NewsService newsService = mRetrofit.create(NewsService.class);
        Observable<List<BaseDataBean>> networkCache = newsService.getPastNewsService(type,
                channelId, pageNum * 20 + "")
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        //  mCacheLoader.mMemoryCache.put(channelId, s);
                        //  mCacheLoader.mDiskCache.put(channelId, s, 0);

                        mCacheLoader.mDiskCache.put(channelId + pageNum, s);


                    }
                })
                .observeOn(Schedulers.io())
                .map(new ResultFun1());


        Observable observable = Observable.concat(diskCache, networkCache)
                .first(new CacheFun1());

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseDataBean>(1));


    }

    @Override
    public void getPastData(String type, final String channelId, final int pageNum) {

        Observable<List<BaseDataBean>> memoryCache = mCacheLoader.mMemoryCache.get(channelId +
                "_" + pageNum, BaseDataBean.class);
        Observable<List<BaseDataBean>> diskCache = mCacheLoader.mDiskCache.get(channelId + "_" +
                pageNum, channelId, BaseDataBean.class);

        NewsService newsService = mRetrofit.create(NewsService.class);
        Observable<List<BaseDataBean>> pastNetCache = newsService.getPastNewsService(type,
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

        Observable.concat(memoryCache, diskCache, pastNetCache)
                .first(new CacheFun1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseDataBean>(3));
    }

    @Override
    public void getMoreData(String type, final String channelId) {
        NewsService newsService = mRetrofit.create(NewsService.class);
        newsService.getNewsService(type, channelId)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                       /* mCacheLoader.mMemoryCache.put(channelId, s);

                      *//*  if (CacheLoader.pageSize<5){
                            mCacheLoader.mDiskCache.put(channelId, s,CacheLoader.pageSize++);
                        }else {
                            mCacheLoader.mDiskCache.put(channelId,s,CacheLoader.pageSize);
                        }*//*

                        //  mCacheLoader.mDiskCache.removeDiskCache(channelId);

                        mCacheLoader.mDiskCache.put(channelId, s, 0);
*/

                    }
                })
                .observeOn(Schedulers.io())
                .map(new ResultFun1())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseDataBean>(2));
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
