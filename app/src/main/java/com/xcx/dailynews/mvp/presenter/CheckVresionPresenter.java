package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.mvp.model.BaseModel;

/**
 * Created by xcx on 2017/4/12.
 */

public class CheckVresionPresenter extends BaseNewsPresenter<LoginBean>{

    public CheckVresionPresenter(BaseModel model) {
        super(model);
    }

    @Override
    protected void howGetData(String type, String channelId, int loadType, int pageNum) {
        mModel.getData(type, channelId, loadType, pageNum);
    }
}
