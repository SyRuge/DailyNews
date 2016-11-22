package com.xcx.dailynews.data;

import com.xcx.dailynews.bean.BaseDataBean;

/**
 * Created by xcx on 2016/11/14.
 * 缓存加载类
 */

public class CacheLoader {

    public static int pageSize=0;//已经使用的缓存，默认等于-1表示这个键(Key)还没有对应的值(value)
    public static int loadPageNum=0;//已经加载的缓存页数

    public MemoryCache mMemoryCache;
    public NetworkCache<BaseDataBean> mNetworkCache;
    public DiskCache mDiskCache;


    private CacheLoader() {
        mMemoryCache = new MemoryCache();
        mNetworkCache = new NetworkCache<>();
        mDiskCache=new DiskCache();

    }

    public static class SingletonHolder {
        private static final CacheLoader INSTANCE = new CacheLoader();
    }

    public static final CacheLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
