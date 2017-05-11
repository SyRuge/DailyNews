package com.xcx.dailynews.mvp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.adapter.CollectAdapter;
import com.xcx.dailynews.bean.CollectBean;
import com.xcx.dailynews.db.CollectBeanDao;
import com.xcx.dailynews.db.CollectContentProvider;
import com.xcx.dailynews.db.DaoSession;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.presenter.UpdateServerPresenter;
import com.xcx.dailynews.mvp.ui.activity.NewsDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcx.dailynews.util.UiUtil.getApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends Fragment implements NewsContract.View<List<CollectBean>> {


    @Bind(R.id.rl_collect_news)
    RecyclerView mRecyclerView;
    @Bind(R.id.sl_collect_refresh)
    SwipeRefreshLayout mSlCollectRefresh;
    @Bind(R.id.rl_collect_no_data)
    RelativeLayout mRlCollectNoData;
    @Bind(R.id.tv_collect_no_login)
    TextView mTvCollectNoLogin;
    @Bind(R.id.rl_collect_no_login)
    RelativeLayout mRlCollectNoLogin;

    @Inject
    UpdateServerPresenter mPresenter;

    private LinearLayoutManager mManager;
    private CollectAdapter mAdapter;
    private CollectObserver mCollectObserver;
    private SharedPreferences mSharedPreferences;

    protected boolean isVisible;//当前页面是否可见的标志位
    protected boolean isPrepared;//是否已经准备好加载数据


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ButterKnife.bind(this, view);
        initInject();
        mSharedPreferences = getActivity().getSharedPreferences("xcx", Context.MODE_PRIVATE);
        initRecylerView();
        mPresenter.attachView(this);
        isPrepared = true;
        return view;

    }


    private void initInject() {
        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule())
                .build()
                .injectUpdateServerFragment(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /*if (getUserVisibleHint()) {
            //界面可见
            isVisible = true;
            onVisible();
            Log.e("TAG", "onVisible: ");
            Toast.makeText(getActivity(), "onVisible", Toast.LENGTH_SHORT).show();
        } else {
            isVisible = false;
            inVisible();
            Log.e("TAG", "inVisible: ");
            Toast.makeText(getActivity(), "inVisible", Toast.LENGTH_SHORT).show();
        }*/

    }

    private void inVisible() {

    }

    private void onVisible() {
        if (isPrepared) {
            String isLogin = mSharedPreferences.getString("isLogin", "no");
            String isSetData = mSharedPreferences.getString("isSetData", "no");
            //   updateViewByLoginStatus(isLogin, isSetData);
            if (isSetData.equals("no")) {
                initData();
            } else {
                updateViewByLoginStatus(isLogin, isSetData);
            }


        } else {

        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onVisible();
        mSlCollectRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSlCollectRefresh.setRefreshing(false);
                CollectBeanDao dao = MyApplication.getMyApplication().getDaoSession()
                        .getCollectBeanDao();
                List<CollectBean> list = dao.loadAll();

                if (list == null || list.size() == 0) {
                    //本地数据库没有数据，查询服务器数据
                    mPresenter.getData("select", "", 10, -1);
                } else {
                    updateViewShowByData(list);
                }
            }
        });


        mCollectObserver = new CollectObserver(new Handler());
        getActivity().getContentResolver().registerContentObserver(CollectContentProvider
                .CONTENT_URI, true, mCollectObserver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (isHidden()) {
            isVisible = false;
            inVisible();
        } else {
            //界面可见
        //    Toast.makeText(getActivity(), "isPrepared=" + isPrepared, Toast.LENGTH_SHORT).show();
            isVisible = true;
            onVisible();
        }
        super.onHiddenChanged(hidden);
    }

    private void initData() {
        String isLogin = mSharedPreferences.getString("isLogin", "no");
        if (isLogin.equals("yes")) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mRlCollectNoLogin.setVisibility(View.GONE);
            CollectBeanDao dao = MyApplication.getMyApplication().getDaoSession()
                    .getCollectBeanDao();

            List<CollectBean> list = dao.loadAll();

            if (list == null || list.size() == 0) {
                //本地数据库没有数据，查询服务器数据
                mPresenter.getData("select", "", 10, -1);
            } else {
                updateViewShowByData(list);
            }

            if (mAdapter != null) {
                mAdapter.setOnItemClickLitener(new CollectAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position, CollectBean bean) {
                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                        Bundle b = new Bundle();
                        b.putString("url", bean.getUrl());
                        b.putString("id", bean.getChannelId());
                        b.putInt("pos", bean.getPosition());
                        intent.putExtra("value", b);
                        getActivity().startActivity(intent);
                    }

                    @Override
                    public void onUncollectClick(View view, int position, CollectBean bean) {
                        getActivity().getContentResolver().delete(CollectContentProvider
                                        .CONTENT_URI,
                                null, new String[]{bean.getUrl()});
                        mPresenter.getData("delete", bean.getUrl(), 11, -1);

                    }
                });
            }
        } else {
            mRlCollectNoLogin.setVisibility(View.VISIBLE);
            mRlCollectNoData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    private void updateViewByLoginStatus(String isLogin, String isSetData) {
        if (isLogin.equals("yes")) {
            mRlCollectNoLogin.setVisibility(View.GONE);
            if (isSetData.equals("yes")) {
                mRlCollectNoData.setVisibility(View.GONE);
                //   mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mRlCollectNoData.setVisibility(View.VISIBLE);
                //  mRecyclerView.setVisibility(View.GONE);
            }


        } else {
            mRlCollectNoLogin.setVisibility(View.VISIBLE);
            mRlCollectNoData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    /**
     * 根据list更新界面
     */
    private void updateViewShowByData(List<CollectBean> list) {

        SharedPreferences.Editor edit = getActivity().getSharedPreferences("xcx", Context
                .MODE_PRIVATE).edit();

        if (list == null || list.size() == 0) {
            mRlCollectNoData.setVisibility(View.VISIBLE);
            //   mRecyclerView.setVisibility(View.GONE);
            edit.putString("isSetData", "no");
            edit.commit();
        } else {
            mRlCollectNoData.setVisibility(View.GONE);
            //  mRecyclerView.setVisibility(View.VISIBLE);

            edit.putString("isSetData", "yes");
            edit.commit();

            if (mRecyclerView.getAdapter() == null) {
                mAdapter = new CollectAdapter(list);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                if (!list.containsAll(mAdapter.mList)) {
                    mAdapter.notifyDataSetChanged();
                }
            }

        }


    }

    private void initRecylerView() {
        //1 创建布局管理器
        mManager = new LinearLayoutManager(getActivity());
        //2 设置布局管理器
        mRecyclerView.setLayoutManager(mManager);
        //3 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //4 设置Item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.tv_collect_no_login)
    public void onViewClicked() {

    }

    @Override
    public void setDataToView(List<CollectBean> list, int loadType) {
        //查询操作
        if (loadType == 10) {

            if (list == null || list.size() == 0 || "failure".equals(list.get(0).getUrl())) {
                list.clear();
                updateViewShowByData(list);
            } else {
                SharedPreferences.Editor edit = getActivity().getSharedPreferences("xcx", Context
                        .MODE_PRIVATE).edit();
                edit.putString("isSetData", "yes");
                edit.commit();
                updateViewShowByData(list);

                DaoSession session = MyApplication.getMyApplication().getDaoSession();
                CollectBeanDao dao = session.getCollectBeanDao();
                List<CollectBean> l = dao.loadAll();

                if (l == null || l.size() == 0) {
                    dao.insertInTx(list);
                }
            }
        } else if (loadType == 11) {
            //删除操作
            Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showNetErrorMessage() {

    }

    @Override
    public void showErrorMessage(String message) {

    }


    class CollectObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public CollectObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {

            SharedPreferences.Editor edit = mSharedPreferences.edit();

            edit.putString("isSetData", "no");
            edit.commit();

            CollectBeanDao dao = MyApplication.getMyApplication().getDaoSession()
                    .getCollectBeanDao();
            List<CollectBean> list = dao.loadAll();

            if (list == null || list.size() == 0) {
                mRlCollectNoData.setVisibility(View.VISIBLE);
              //  mRecyclerView.setVisibility(View.GONE);
                edit.putString("isSetData", "no");
                edit.commit();
            } else {
                mRlCollectNoData.setVisibility(View.GONE);
              //  mRecyclerView.setVisibility(View.VISIBLE);
                if (mRecyclerView.getAdapter() == null) {
                    mAdapter = new CollectAdapter(list);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.mList.clear();
                    mAdapter.mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().getContentResolver().unregisterContentObserver(mCollectObserver);
        SharedPreferences.Editor edit = getActivity().getSharedPreferences("xcx", Context
                .MODE_PRIVATE).edit();
        edit.putString("isSetData", "no");
        edit.commit();
    }

}
