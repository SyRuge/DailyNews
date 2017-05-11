package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.mvp.model.BaseModel;

/**
 * Created by xcx on 2017/4/9.
 */

public class LoginPresenter extends BaseNewsPresenter<LoginBean>{


    public LoginPresenter(BaseModel model) {
        super(model);
    }

    @Override
    protected void howGetData(String type, String channelId, int loadType, int pageNum) {
        mModel.getData(type, channelId, loadType, pageNum);
    }
}
