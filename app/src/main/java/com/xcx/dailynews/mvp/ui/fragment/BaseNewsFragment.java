package com.xcx.dailynews.mvp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xcx.dailynews.Constants;
import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.adapter.NewsAdapter;
import com.xcx.dailynews.adapter.NewsAdapter.OnGetPastNewsListener;
import com.xcx.dailynews.bean.BaseDataBean;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.presenter.NewsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻列表的基类
 */
public abstract class BaseNewsFragment extends Fragment implements NewsContract
        .View<List<BaseDataBean>>, OnGetPastNewsListener {

    @Bind(R.id.rl_base_news)
    protected RecyclerView mRecyclerView;
    @Bind(R.id.sl_refresh)
    protected SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.btn_error_refresh)
    Button mBtnErrorRefresh;
    @Bind(R.id.iv_error)
    ImageView mIvError;

    @Inject
    protected NewsPresenter mPresenter;

    protected boolean isVisible;//当前页面是否可见的标志位
    protected boolean isPrepared;//是否已经准备好加载数据
    protected boolean isFirstLoad = true;//是否是第一次加载
    protected boolean isOnErrorPage;//现在是不是错误界面
    protected boolean isLoadMore;
    private int pageNum = 1;
    @Bind(R.id.rl_error_refresh)
    RelativeLayout mRlErrorRefresh;
    private NewsAdapter mAdapter;
    private LinearLayoutManager mManager;
    protected View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_basenews, null);
        ButterKnife.bind(this, fragmentView);
        initInject();
        mPresenter.attachView(this);
        isPrepared = true;
        initRecyclerView();
        return fragmentView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            //界面可见
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            inVisible();
        }

    }

    private void inVisible() {

    }

    private void onVisible() {
        if (!isVisible || !isPrepared) {

        } else {
            if (isFirstLoad) {
                isFirstLoad = false;
                mPresenter.getData(getType(), getChannelId(), Constants.COMMON_TYPE, Constants
                        .COMMON_NUM);
            } else {

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initRefreshListener();
        onVisible();
    }

    @Override
    public void getPastData() {
        if (pageNum < 5) {
            //  mPresenter.getPastData(getType(), getChannelId(), pageNum);
            mPresenter.getData(getType(), getChannelId(), Constants.BOTTOM_REFRESH, pageNum);
            pageNum++;
        }

    }

    @Override
    public void setDataToView(List<BaseDataBean> list, int loadType) {
        if (isOnErrorPage) {
            isOnErrorPage = false;
            //     mRefreshLayout.setVisibility(View.VISIBLE);
            mRlErrorRefresh.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        //1 普通加载
        //2 下拉刷新
        //3 底部加载更多
        //4 图片的加载更多

        if (loadType == Constants.COMMON_TYPE || loadType == Constants.TOP_REFRESH) {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(false);
            }

            if (mRecyclerView.getAdapter() == null) {
                mAdapter = new NewsAdapter(list);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                NewsAdapter a = (NewsAdapter) mRecyclerView.getAdapter();
                a.mList.clear();
                a.mList.addAll(list);
                a.notifyDataSetChanged();

            }
        } else if (loadType == Constants.BOTTOM_REFRESH) {
            if (mRecyclerView.getAdapter() == null) {
                mAdapter = new NewsAdapter(list);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                NewsAdapter a = (NewsAdapter) mRecyclerView.getAdapter();
                a.mList.addAll(list);
                a.notifyDataSetChanged();

            }
        }
        mAdapter.setOnGetPastNewsListener(this);
    }


    /**
     * 初始化Dagger注入
     */
    private void initInject() {
        AppComponent appComponent = ((MyApplication) getActivity().getApplication())
                .getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .injectFragment(this);
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        //1 创建布局管理器
        mManager = new LinearLayoutManager(getActivity());
        //2 设置布局管理器
        mRecyclerView.setLayoutManager(mManager);
        //3 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //4 设置Item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initRefreshListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreData();
            }
        });
    }


    @Override
    public void showNetErrorMessage() {

    }

    protected abstract void show(String message);

    @Override
    public void showErrorMessage(String message) {

        show(message);

        Log.e("TAG", "showErrorMessage: " + message);

        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }

        isOnErrorPage = true;
        //  mRefreshLayout.setVisibility(View.GONE);
        mRlErrorRefresh.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

    }

    /**
     * 下拉刷新，获取更多的数据
     */
    protected abstract void getMoreData();

    /**
     * 新闻频道类型
     */
    protected abstract String getType();

    /**
     * 新闻频道Id
     */
    protected abstract String getChannelId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.detachView(getChannelId());
        isFirstLoad = true;
    }

}
