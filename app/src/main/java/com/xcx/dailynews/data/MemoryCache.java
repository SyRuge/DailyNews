package com.xcx.dailynews.data;

import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xcx on 2016/11/14.
 * 内存缓存
 */

public class MemoryCache {
  //  public LruCache<String, String> mCache;

    public LruCache<String,String> mCache;


  /*  public MemoryCache() {
        int maxMemorySize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemorySize / 8;
        mCache=new LruCache<String,List<BaseDataBean>>(cacheSize){
            @Override
            protected int sizeOf(String key, List<BaseDataBean> value) {
                return value.toString().getBytes().length;
            }

        };
    }*/

    public MemoryCache() {
        int maxMemorySize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemorySize / 8;
        mCache = new LruCache<String, String>(cacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                return value.getBytes().length;
            }
        };
    }


    /**
     * 根据传入的Key读取缓存
     */
   /* public  Observable<List<BaseDataBean>> get(final String key){
        return Observable.create(new Observable.OnSubscribe<List<BaseDataBean>>() {
            @Override
            public void call(Subscriber<? super List<BaseDataBean>> subscriber) {
                List<BaseDataBean> list=mCache.get(key);
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                if (list==null||list.size()==0) {
                    subscriber.onNext(null);
                }else {
                    subscriber.onNext(list);
                }
                subscriber.onCompleted();
            }
        });

    }*/

    /**
     * 根据传入的Key读取缓存
     */
    public <T> Observable<List<T>> get(final String key, final Class<T> clz){
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                String result=mCache.get(key);
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                if (TextUtils.isEmpty(result)) {
                    Log.e("TAG", "call: " );
                    subscriber.onNext(null);
                }else {
                    //解析Json
                    //解析json对象
                    JSONObject object = JSON.parseObject(result);
                    //从json对象中获取json数组
                    JSONArray array = object.getJSONArray(key);
                    //解析json数组获取数据
                    List<T> list = JSON.parseArray(array.toJSONString(), clz);
                    subscriber.onNext(list);
                }
                subscriber.onCompleted();
            }
        });

    }


    /**
     * 将请求的数据存入内存缓存
     */
    public <T> void put(String key, T t) {
        if (t != null) {
            mCache.put(key, toString());
        }

    }

    public String getMemoryCache(String key){
        if (mCache.get(key)==null){
            return null;
        }else {
            return mCache.get(key);
        }
    }

    public void removeMemoryCache(String key){
        mCache.remove(key);
    }

}
