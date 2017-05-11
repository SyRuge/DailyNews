package com.xcx.dailynews.di.module;

import com.xcx.dailynews.mvp.model.CheckVersionModel;
import com.xcx.dailynews.mvp.model.LoginModel;
import com.xcx.dailynews.mvp.model.NewsDetailModel;
import com.xcx.dailynews.mvp.model.NewsModel;
import com.xcx.dailynews.mvp.model.PhotoModel;
import com.xcx.dailynews.mvp.model.SignUpModel;
import com.xcx.dailynews.mvp.model.UpdateServerModel;
import com.xcx.dailynews.mvp.presenter.CheckVresionPresenter;
import com.xcx.dailynews.mvp.presenter.LoginPresenter;
import com.xcx.dailynews.mvp.presenter.NewsDetailPresenter;
import com.xcx.dailynews.mvp.presenter.NewsPresenter;
import com.xcx.dailynews.mvp.presenter.PhotoPresenter;
import com.xcx.dailynews.mvp.presenter.SignUpPresenter;
import com.xcx.dailynews.mvp.presenter.UpdateServerPresenter;
import com.xcx.dailynews.mvp.ui.activity.LoginActivity;
import com.xcx.dailynews.mvp.ui.activity.SignUpActivity;
import com.xcx.dailynews.mvp.ui.fragment.BaseNewsFragment;
import com.xcx.dailynews.mvp.ui.fragment.MyFragment;
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

    private MyFragment mMyFragment;

    private LoginActivity mLoginActivity;
    private SignUpActivity mSignUpActivity;

    public ActivityModule(MyFragment myFragment) {
        mMyFragment = myFragment;
    }

    public ActivityModule(SignUpActivity signUpActivity) {
        mSignUpActivity = signUpActivity;
    }

    public ActivityModule(){}

    public ActivityModule(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
    }

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
    NewsDetailModel provideNewsDetailModel(){
        return new NewsDetailModel();
    }

    @Provides
    LoginModel provideLoginModel(){
        return new LoginModel();
    }

    @Provides
    SignUpModel provideSignUpModel(){
        return new SignUpModel();
    }

    @Provides
    CheckVersionModel provideCheckVersionModel(){
        return new CheckVersionModel();
    }

    @Provides
    UpdateServerModel provideUpdateServerModel(){
        return new UpdateServerModel();
    }



    /**
     * 提供Presenter依赖
     */
    @Provides
    NewsPresenter provideNewsPresenter(NewsModel model){
        return new NewsPresenter(model);
    }

    @Provides
    NewsDetailPresenter provideNewsDetailPresenter(NewsDetailModel model){
        return new NewsDetailPresenter(model);
    }

    @Provides
    LoginPresenter provideLoginPresenter(LoginModel model){
        return new LoginPresenter(model);
    }

    @Provides
    SignUpPresenter provideSignUpPresenter(SignUpModel model){
        return new SignUpPresenter(model);
    }

    @Provides
    PhotoPresenter providePhotoPresenter(PhotoModel model){
        return new PhotoPresenter(model);
    }

    @Provides
    CheckVresionPresenter provideCheckVresionPresenter(CheckVersionModel model){
        return new CheckVresionPresenter(model);
    }

    @Provides
    UpdateServerPresenter provideUpdateServerPresenter(UpdateServerModel model){
        return new UpdateServerPresenter(model);
    }

}
