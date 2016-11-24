package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.mvp.model.BaseModel;
import com.xcx.dailynews.mvp.ui.view.OnGetDataListener;

/**
 * Created by xcx on 2016/11/24.
 */

public class NewsDetailPresenter implements NewsContract.Presenter,OnGetDataListener<String> {
    protected NewsContract.View mView;
    protected BaseModel mModel;

    @Override
    public void getData(String type, String channelId, int loadType, int pageNum) {
        mModel.setOnGetDataListener(this);
        mModel.getData(type, channelId, loadType, pageNum);
    }

    public NewsDetailPresenter(BaseModel model) {
        mModel = model;
    }

    @Override
    public void attachView(NewsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView(String channelId) {

    }

    @Override
    public void onDataSuccess(String list, int loadType) {
        mView.setDataToView(list, loadType);
        mModel.unSubscribe();
    }

    @Override
    public void onDataFailure(String message) {
        mView.showErrorMessage(message);
    }
}
