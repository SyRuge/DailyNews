package com.xcx.dailynews.util;

import android.content.Context;

import com.xcx.dailynews.MyApplication;

import retrofit2.Retrofit;

/**
 * 相关工具类的集合
 * Created by xcx on 2016/11/3.
 */

public class UiUtil {
    public static Context getAppContext() {
        return MyApplication.getAppContext();
    }

    public static MyApplication getApplication() {
        return MyApplication.getMyApplication();
    }

    public static Retrofit getRetrofit() {
        return UiUtil.getApplication().getAppComponent().getRetrofit();
    }


}
