package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.bean.CollectBean;
import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.mvp.model.BaseModel;

/**
 * Created by xcx on 2017/4/9.
 */

public class UpdateServerPresenter extends BaseNewsPresenter<LoginBean>{


    public UpdateServerPresenter(BaseModel model) {
        super(model);
    }

    @Override
    protected void howGetData(String type, String channelId, int loadType, int pageNum) {
        mModel.getData(type, channelId, loadType, pageNum);
    }

    @Override
    public void getData(String url, String type,CollectBean collectBean) {
        super.getData(url, type,collectBean);
        mModel.getData(url, type,collectBean);
    }
}
