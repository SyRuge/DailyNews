package com.xcx.dailynews.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcx.dailynews.util.FileUtil;
import com.xcx.dailynews.util.UiUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xcx on 2016/11/14.
 */

public class DiskCache {
    private DiskLruCache mDiskLruCache;


    public DiskCache() {
        File cacheDir = FileUtil.getDiskCacheDir("list");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, FileUtil.getAppVersion(UiUtil
                    .getAppContext()
            ), 1, 50 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地缓存的Observable
     */
    public <T> Observable<List<T>> get(final String key, final Class<T> clz) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                String result=getFromDisk(key);
                if (subscriber.isUnsubscribed()){
                    return;
                }
                if (TextUtils.isEmpty(result)){
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
     * 读取本地缓存的Observable
     */
    public <T> Observable<List<T>> get(final String key, final Class<T> clz, final int page) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                String result=getFromDisk(key,page);
                if (subscriber.isUnsubscribed()){
                    return;
                }
                if (TextUtils.isEmpty(result)){
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
     * 从本地缓存中读取
     */
    public String  getFromDisk(String key) {
        DiskLruCache.Snapshot snapshot;
        String result=null;
        try {
            snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                result = snapshot.getString(0);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 从本地缓存中读取指定页数
     */
    public String  getFromDisk(String key,int page) {
        DiskLruCache.Snapshot snapshot;
        String result=null;
        try {
            snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                result = snapshot.getString(page);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public <T> void put(String key, T t) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(0);
                os.write(t.toString().getBytes());
                editor.commit();
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public <T> void put(String key, T t,int page) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(page);
                os.write(t.toString().getBytes());
                editor.commit();
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeDiskCache(String key){
        try {
            mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* private <T> boolean isWriteSuccess(T t, OutputStream out) {
        BufferedOutputStream bs = null;
        try {
            bs = new BufferedOutputStream(out, 8 * 1024);
            bs.write(t.toString().getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/



}
