package com.xcx.dailynews.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;
import com.xcx.dailynews.util.ViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcx.dailynews.R.id.toolbar;

public class NewsDetailActivity extends AppCompatActivity {

    @Bind(R.id.fb_news_share)
    FloatingActionButton mFbShare;
    @Bind(R.id.iv_news_back)
    ImageView mIvNewsBack;
    @Bind(R.id.tv_news_detail_title)
    TextView mTvTitle;
    @Bind(toolbar)
    Toolbar mToolbar;
    @Bind(R.id.sd_news_detail_pic)
    SimpleDraweeView mSdNewsPic;
    @Bind(R.id.tv_news_detail_content)
    TextView mTvNewsDetail;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initView();
        initToolBar();
        initData();
        initListener();
    }

    private void initView() {
        ViewUtil.changeStatusBarColor(this);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
    }

    private void initData() {

    }

    private void initListener() {

    }

    @OnClick({R.id.fb_news_share, R.id.iv_news_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_news_share:
                break;
            case R.id.iv_news_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
