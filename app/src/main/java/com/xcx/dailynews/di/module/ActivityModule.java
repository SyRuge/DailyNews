package com.xcx.dailynews.di.module;

import com.xcx.dailynews.mvp.model.NewsModel;
import com.xcx.dailynews.mvp.model.PhotoModel;
import com.xcx.dailynews.mvp.presenter.NewsPresenter;
import com.xcx.dailynews.mvp.presenter.PhotoPresenter;
import com.xcx.dailynews.mvp.ui.fragment.BaseNewsFragment;
import com.xcx.dailynews.mvp.ui.fragment.PhotoFragment;
import com.xcx.dailynews.mvp.ui.fragment.impl.NewsImplFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xcx on 2016/11/1.
 */
@Module
public class ActivityModule {

    private BaseNewsFragment mFragment;
    private PhotoFragment mPhotoFragment;

    public ActivityModule(PhotoFragment photoFragment) {
        mPhotoFragment = photoFragment;
    }


    public ActivityModule(BaseNewsFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    NewsImplFragment provideNewsImplFragment(){
        return new NewsImplFragment();
    }

    /**
     * 提供Model依赖的方法，当注入Model时会从这方法初始化Model
     */
    @Provides
    NewsModel provideNewsModel(){
        return new NewsModel();
    }

    @Provides
    PhotoModel providePhotoModel(){
        return new PhotoModel();
    }

    @Provides
    PhotoPresenter providePhotoPresenter(PhotoModel model){
        return new PhotoPresenter(model);
    }

    /**
     * 提供Presenter依赖
     */
    @Provides
    NewsPresenter provideNewsPresenter(NewsModel model){
        return new NewsPresenter(model);
    }
}
