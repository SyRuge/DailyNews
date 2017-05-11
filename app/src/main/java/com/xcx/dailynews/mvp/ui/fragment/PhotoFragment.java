package com.xcx.dailynews.mvp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xcx.dailynews.Constants;
import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.adapter.StagggerAdapter;
import com.xcx.dailynews.bean.PhotoBean;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.presenter.PhotoPresenter;
import com.xcx.dailynews.mvp.ui.activity.LargePhotoActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment implements NewsContract.View<List<PhotoBean>>,
        StagggerAdapter.OnGetMoreListener {


    @Inject
    protected PhotoPresenter mPresenter;
    @Bind(R.id.rl_photo_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.sl_photo_refresh)
    SwipeRefreshLayout mSlPhotoRefresh;
    private StaggeredGridLayoutManager mManager;
    int page = 1;
    private StagggerAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);
        initInject();
        initRecyclerView();
        initListener();
        return view;
    }

    private void initRecyclerView() {
        //1 创建布局管理器
        mManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        //2 设置布局管理器
        mRecyclerView.setLayoutManager(mManager);
        //3 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //4 设置Item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initListener() {


        mSlPhotoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               mPresenter.getData("20","1", Constants.PHOTO_TYPE,Constants.COMMON_NUM);
            }
        });

    }

    private void initInject() {
        AppComponent appComponent = ((MyApplication) getActivity().getApplication())
                .getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .injectPhotoFragment(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.attachView(this);
        mPresenter.getData("20", page + "", Constants.PHOTO_TYPE,Constants.COMMON_NUM);
        page++;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void setDataToView(List<PhotoBean> list,int loadType) {
        if (mSlPhotoRefresh.isRefreshing()){
            mSlPhotoRefresh.setRefreshing(false);
        }
        if (mAdapter==null) {
            mAdapter = new StagggerAdapter(list);
            //  PhotoAdapter adapter = new PhotoAdapter(bean.results,mManager );
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.mList.addAll(list);
            for (int i = 0; i < list.size(); i++) {
                mAdapter.mHeight.add((int) (500 + Math.random() * 800));
            }
            mAdapter.notifyDataSetChanged();
        }

        mAdapter.setOnGetMoreListener(this);

        mAdapter.setOnItemClickLitener(new StagggerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, Bundle bundle) {
                Intent intent = new Intent(getActivity(), LargePhotoActivity.class);
                intent.putExtra("bundle",bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void showNetErrorMessage() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void onGetMore() {
        mPresenter.getData("20", page + "", Constants.PHOTO_TYPE,Constants.COMMON_NUM);
        page++;
    }

}
