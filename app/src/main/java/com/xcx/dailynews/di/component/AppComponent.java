package com.xcx.dailynews.di.component;

import com.xcx.dailynews.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by xcx on 2016/10/27.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    OkHttpClient getOkHttpClient();
    Retrofit getRetrofit();
}
