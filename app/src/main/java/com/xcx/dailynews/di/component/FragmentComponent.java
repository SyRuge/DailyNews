package com.xcx.dailynews.di.component;

import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.di.qualifiter.PreFragment;
import com.xcx.dailynews.mvp.ui.fragment.BaseNewsFragment;
import com.xcx.dailynews.mvp.ui.fragment.PhotoFragment;

import dagger.Component;

/**
 * Created by xcx on 2016/11/1.
 */
@PreFragment
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface FragmentComponent {
    void injectFragment(BaseNewsFragment fragment);
    void injectPhotoFragment(PhotoFragment fragment);

}
