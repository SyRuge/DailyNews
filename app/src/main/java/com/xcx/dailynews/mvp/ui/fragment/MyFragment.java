package com.xcx.dailynews.mvp.ui.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.db.CollectBeanDao;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.CheckVresionPresenter;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.ui.activity.AboutActivity;
import com.xcx.dailynews.mvp.ui.activity.LoginActivity;
import com.xcx.dailynews.util.UiUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的界面Fragment
 */
public class MyFragment extends Fragment implements NewsContract.View<List<LoginBean>> {


    @Bind(R.id.sv_my_head)
    SimpleDraweeView mSvMyHead;
    @Bind(R.id.tv_myfragment_login)
    TextView mTvMyfragmentLogin;
    @Bind(R.id.rl_about_me)
    RelativeLayout mRlAboutMe;
    @Bind(R.id.rl_check_version)
    RelativeLayout mRlCheckVersion;
    @Bind(R.id.rl_logout)
    RelativeLayout mRlLogout;
    private ProgressDialog mProgressDialog;

    @Inject
    CheckVresionPresenter mPresenter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        initInject();
        return view;
    }

    private void initInject() {
        AppComponent appComponent = ((MyApplication) getActivity().getApplication())
                .getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .injectCheckFragment(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        checkLoginStatus();
        mSvMyHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //   getActivity().startActivity(intent);

                startActivityForResult(intent, Activity.RESULT_FIRST_USER);

            }
        });
    }

    private void checkLoginStatus() {
        SharedPreferences xcx = getActivity().getSharedPreferences("xcx", Context
                .MODE_PRIVATE);
        String isLogin = xcx.getString("isLogin", "no");
        if (isLogin.equals("yes")){
            GenericDraweeHierarchy hierarchy = mSvMyHead.getHierarchy();
            hierarchy.setPlaceholderImage(R.drawable.success_log_in);
            mTvMyfragmentLogin.setVisibility(View.INVISIBLE);
            mRlLogout.setVisibility(View.VISIBLE);
        }




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            //登陆成功
            case 1:
                GenericDraweeHierarchy hierarchy = mSvMyHead.getHierarchy();
                hierarchy.setPlaceholderImage(R.drawable.success_log_in);
                mTvMyfragmentLogin.setVisibility(View.INVISIBLE);
                SharedPreferences xcx = getActivity().getSharedPreferences("xcx", Context
                        .MODE_PRIVATE);
                SharedPreferences.Editor edit = xcx.edit();
                edit.putString("isLogin", "yes");
                edit.commit();
                mRlLogout.setVisibility(View.VISIBLE);
                break;
            //登陆出错
            case -1:
                mTvMyfragmentLogin.setVisibility(View.VISIBLE);
                mRlLogout.setVisibility(View.GONE);
                break;
            //登陆出错
            case -2:
                mTvMyfragmentLogin.setVisibility(View.VISIBLE);
                mRlLogout.setVisibility(View.GONE);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_about_me, R.id.rl_check_version,R.id.rl_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_about_me:
                getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.rl_check_version:
                mPresenter.getData("xcx", null, 6, 0);
                initPb();
                break;
            case R.id.rl_logout:
                loginOut();
                mRlLogout.setVisibility(View.GONE);
                break;
        }
    }

    private void loginOut() {

        GenericDraweeHierarchy hierarchy = mSvMyHead.getHierarchy();
        hierarchy.setPlaceholderImage(R.drawable.log_in_place);
        mTvMyfragmentLogin.setVisibility(View.VISIBLE);

        mRlLogout.setVisibility(View.GONE);

        SharedPreferences xcx = getActivity().getSharedPreferences("xcx", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = xcx.edit();
        edit.putString("isLogin", "no");
        edit.putString("isSetData", "no");
        edit.commit();
        CollectBeanDao dao = UiUtil.getApplication().getDaoSession().getCollectBeanDao();
        dao.deleteAll();
    }

    private void initPb() {
        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在检查版本...");
        mProgressDialog.show();
    }

    @Override
    public void showNetErrorMessage() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void setDataToView(List<LoginBean> list, int loadType) {

        if (list != null && list.size() != 0) {
            LoginBean b = list.get(0);

            if (b == null || TextUtils.isEmpty(b.status)) {
                Toast.makeText(getActivity(), "服务器错误", Toast.LENGTH_SHORT).show();
            } else if ("isNew".equals(b.status)) {
                Toast.makeText(getActivity(), "当前已经是最新版本", Toast.LENGTH_SHORT).show();
            } else if ("notNew".equals(b.status)) {
                Toast.makeText(getActivity(), "有新版本", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
        mProgressDialog.dismiss();
    }

}
