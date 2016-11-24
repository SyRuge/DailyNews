package com.xcx.dailynews.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.Constants;
import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.presenter.NewsDetailPresenter;
import com.xcx.dailynews.util.ViewUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xcx.dailynews.R.id.toolbar;

public class NewsDetailActivity extends AppCompatActivity implements NewsContract.View<String> {

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

    @Inject
    NewsDetailPresenter mPresenter;
    private String mUrl;
    private String mChannelId;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mUrl = getIntent().getStringExtra("url");
        mChannelId = getIntent().getStringExtra("id");
        mPosition = getIntent().getIntExtra("pos", 0);
        ButterKnife.bind(this);
        initView();
        initInject();
        initToolBar();
        mPresenter.attachView(this);
        initData();
        initListener();
    }

    private void initView() {
        ViewUtil.changeStatusBarColor(this);
    }

    private void initInject() {
        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule())
                .build()
                .injectActivity(this);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
    }

    private void initData() {
        if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mChannelId)) {

            mPresenter.getData(mUrl, mChannelId, Constants.NEWS_DETAIL, mPosition);
        }
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

    @Override
    public void setDataToView(String list, int loadType) {
        Document doc = Jsoup.parse(list);
        Elements es = doc.select("div.content");
        String html = es.html();
        mTvNewsDetail.setText(Html.fromHtml(html));
    }

    @Override
    public void showNetErrorMessage() {

    }

    @Override
    public void showErrorMessage(String message) {

    }
}
