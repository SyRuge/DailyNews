package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.mvp.ui.view.BaseView;

/**
 * Created by xcx on 2016/11/1.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView(String channelId);
}
