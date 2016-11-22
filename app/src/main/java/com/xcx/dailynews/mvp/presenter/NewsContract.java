package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.mvp.ui.view.BaseView;

/**
 * Created by xcx on 2016/11/3.
 */

public interface NewsContract {
    interface Presenter extends BasePresenter<View>{
        void getData(String type, String channelId);
    }
    interface View<T> extends BaseView{
        void setDataToView(T list,int loadType);

    }
}
