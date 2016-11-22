package com.xcx.dailynews.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xcx.dailynews.mvp.ui.fragment.impl.NewsImplFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcx on 2016/10/27.
 */

public class BasePagerAdapter extends FragmentStatePagerAdapter {

    List<NewsImplFragment> mList=new ArrayList<>();

    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addPage(NewsImplFragment fragment){
        mList.add(fragment);
    }

   /* @Override
    public CharSequence getPageTitle(int position) {
        return "推荐";
    }*/

    @Override
    public NewsImplFragment getItem(int position) {
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
