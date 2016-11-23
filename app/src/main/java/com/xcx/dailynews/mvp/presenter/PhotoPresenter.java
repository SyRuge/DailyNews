package com.xcx.dailynews.mvp.presenter;

import com.xcx.dailynews.bean.PhotoBean;
import com.xcx.dailynews.mvp.model.BaseModel;

/**
 * Created by xcx on 2016/11/8.
 */

public class PhotoPresenter extends BaseNewsPresenter<PhotoBean>{


    public PhotoPresenter(BaseModel model) {
        super(model);
    }

    @Override
    protected void howGetData(String type, String channelId, int loadType, int pageNum) {
        //三级缓存
        //内存缓存
        //本地缓存
        //网络缓存
        mModel.getData(type,channelId,loadType,pageNum);
    }


}
