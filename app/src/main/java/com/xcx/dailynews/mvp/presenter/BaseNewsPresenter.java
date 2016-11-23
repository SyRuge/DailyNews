package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.mvp.model.BaseModel;
import com.xcx.dailynews.mvp.ui.view.OnGetDataListener;

import java.util.List;

/**
 * Created by xcx on 2016/11/3.
 */

public abstract class BaseNewsPresenter<T> implements NewsContract.Presenter,
        OnGetDataListener<List<T>> {

    protected NewsContract.View mView;
    protected BaseModel mModel;


    public BaseNewsPresenter(NewsContract.View view, BaseModel model) {
        mView = view;
        mModel = model;
    }

    public BaseNewsPresenter(BaseModel model) {
        mModel = model;
    }

    /**
     * 到底怎么获取数据，子类重写
     */
    protected abstract void howGetData(String type, String channelId, int loadType, int pageNum);

    @Override
    public void getData(String type, String channelId, int loadType, int pageNum) {
        mModel.setOnGetDataListener(this);
        howGetData(type, channelId, loadType, pageNum);
    }

    @Override
    public void attachView(NewsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView(String channelId) {

        mView = null;
    }


    @Override
    public void onDataSuccess(List<T> list, int loadType) {
        mView.setDataToView(list, loadType);
    }


    @Override
    public void onDataFailure(String message) {
        mView.showErrorMessage(message);
    }
}
