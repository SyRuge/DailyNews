package com.xcx.dailynews.mvp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;
import com.xcx.dailynews.mvp.ui.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的界面Fragment
 */
public class MyFragment extends Fragment {


    @Bind(R.id.sv_my_head)
    SimpleDraweeView mSvMyHead;
    @Bind(R.id.tv_my_collection_num)
    TextView mTvMyCollectionNum;
    @Bind(R.id.tv_my_collection)
    TextView mTvMyCollection;
    @Bind(R.id.tv_my_attention_num)
    TextView mTvMyAttentionNum;
    @Bind(R.id.tv_my_attention)
    TextView mTvMyAttention;
    @Bind(R.id.tv_my_fans_num)
    TextView mTvMyFansNum;
    @Bind(R.id.tv_my_fans)
    TextView mTvMyFans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSvMyHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
