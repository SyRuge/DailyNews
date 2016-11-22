package com.xcx.dailynews.di.module;

import com.xcx.dailynews.ApiConstants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xcx on 2016/10/27.
 * 提供全局类的Module
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(10*1000, TimeUnit.MILLISECONDS)
                .readTimeout(10*1000,TimeUnit.MILLISECONDS)
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client){
        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConstants.NEWS_DETAIL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
