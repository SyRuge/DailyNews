package com.xcx.dailynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xcx.dailynews.mvp.ui.fragment.impl.NewsImplFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcx on 2016/11/2.
 */

public class BaseAdapter extends FragmentStatePagerAdapter{

    List<NewsImplFragment> mList=new ArrayList<>();


    public void addPage(NewsImplFragment fragment){
        mList.add(fragment);
    }

    public BaseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mList.size()!=0){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mList.size()!=0){
            return mList.size();
        }
        return 0;
    }
}
