package com.xcx.dailynews.mvp.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.R;
import com.xcx.dailynews.adapter.BasePagerAdapter;
import com.xcx.dailynews.mvp.ui.fragment.impl.NewsImplFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasePagerFragment extends Fragment {


    @Bind(R.id.vp_pager)
    ViewPager mVpPager;

    public interface OnBindPagerAndTabListener {
        void bindTab(ViewPager viewPager);
    }

    private OnBindPagerAndTabListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_basepager, null);
        ButterKnife.bind(this, v);
        mVpPager.setOffscreenPageLimit(1);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBindPagerAndTabListener) {
            mListener = (OnBindPagerAndTabListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implements " +
                    "OnBindPagerAndTabListener");
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
           BasePagerAdapter adapter = new BasePagerAdapter(this.getChildFragmentManager());

        //"头条", "精选", "科技", "财经", "军事", "体育",
        String[] ids = {ApiConstants.HEADLINE_ID, ApiConstants.CHOICE_ID, ApiConstants.TECH_ID,
                ApiConstants.FINANCE_ID, ApiConstants.MILITARY_ID, ApiConstants.SPORTS_ID};

        for (int i = 0; i < 6; i++) {

            if (i == 0) {
                NewsImplFragment newsImplFragment = new NewsImplFragment();
                newsImplFragment.setTypeAndChannelId("headline", ids[i]);
                adapter.addPage(newsImplFragment);
            } else {
                NewsImplFragment newsImplFragment = new NewsImplFragment();
                newsImplFragment.setTypeAndChannelId("list", ids[i]);
                adapter.addPage(newsImplFragment);
            }


        }

        mVpPager.setAdapter(adapter);

        mListener.bindTab(mVpPager);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
