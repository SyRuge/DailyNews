package com.xcx.dailynews;

import android.app.Application;
import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerAppComponent;
import com.xcx.dailynews.di.module.AppModule;

/**
 * Created by xcx on 2016/10/27.
 */

public class MyApplication extends Application {

    AppComponent mAppComponent;
    private static Context mContext;
    private static MyApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig diskCacheConfig=DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(this.getExternalCacheDir())
                .setBaseDirectoryName("/image")
                .build();
        ImagePipelineConfig config=ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
        mContext = this.getApplicationContext();
        sApplication = this;
        Fresco.initialize(this,config);
    }



    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static MyApplication getApplication() {
        return sApplication;
    }
}
