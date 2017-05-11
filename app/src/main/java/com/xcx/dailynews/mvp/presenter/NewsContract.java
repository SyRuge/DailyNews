package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.bean.CollectBean;
import com.xcx.dailynews.mvp.ui.view.BaseView;

/**
 * Created by xcx on 2016/11/3.
 */

public interface NewsContract {
    interface Presenter extends BasePresenter<View>{
        void getData(String type, String channelId,int loadType,int pageNum);
        void getData(String url, String type,CollectBean collectBean);
    }
    interface View<T> extends BaseView{
        void setDataToView(T list,int loadType);

    }
}
