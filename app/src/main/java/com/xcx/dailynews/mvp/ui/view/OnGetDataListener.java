package com.xcx.dailynews.mvp.ui.view;

/**
 * 从网络加载数据成功或者失败的监听接口
 * Created by xcx on 2016/11/1.
 */

public interface OnGetDataListener<T> {
    void onDataSuccess(T list,int loadType);
    void onDataFailure(String message);
}
