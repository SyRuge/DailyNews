package com.xcx.dailynews.mvp.ui.fragment.impl;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.xcx.dailynews.mvp.ui.fragment.BaseNewsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsImplFragment extends BaseNewsFragment {

    public String type;
    public String channelId;

    public void setTypeAndChannelId(String type,String channelId) {
        this.type = type;
        this.channelId = channelId;
    }


    @Override
    protected String getType() {
        return type;
    }

    @Override
    protected String getChannelId() {
        return channelId;
    }



    @Override
    protected void getMoreData() {
        mPresenter.getMoreData(type,channelId);
    }

    @Override
    protected void show(String message) {
        Toast.makeText(getActivity(), "message"+message, Toast.LENGTH_SHORT).show();
    }


}
