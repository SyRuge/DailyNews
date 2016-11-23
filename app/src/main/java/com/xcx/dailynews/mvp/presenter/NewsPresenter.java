package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.bean.BaseDataBean;
import com.xcx.dailynews.mvp.model.BaseModel;

/**
 * Created by xcx on 2016/11/3.
 */

public class NewsPresenter extends BaseNewsPresenter<BaseDataBean> {


    public NewsPresenter(BaseModel model) {
        super(model);
    }

    @Override
    protected void howGetData(String type, String channelId, int loadType, int pageNum) {
        //三级缓存
        //内存缓存
        //本地缓存
        //网络缓存
    //    mModel.getData(type, channelId);
        mModel.getData(type, channelId, loadType, pageNum);

    }
    public void getMoreData(String type, String channelId){
        mModel.getMoreData(type, channelId);
    }

    public void getPastData(String type,  String channelId,int pageNum){
        mModel.getPastData(type,channelId,pageNum);
    }

    @Override
    public void detachView(String channelId) {
        super.detachView(channelId);
        mModel.removeMemeoryCache(channelId);
    }
}
