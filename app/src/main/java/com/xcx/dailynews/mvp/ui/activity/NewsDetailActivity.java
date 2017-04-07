package com.xcx.dailynews.mvp.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    @Bind(R.id.wv_news_detail)
    WebView mWebView;

    @Inject
    NewsDetailPresenter mPresenter;
    private String mUrl;
    private String mChannelId;
    private int mPosition;
    private String mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        String base_url = getIntent().getStringExtra("url");
        mUrl = base_url.substring(0, base_url.lastIndexOf(".html")) + "_0.html";
        mChannelId = getIntent().getStringExtra("id");
        mPosition = getIntent().getIntExtra("pos", 0);
        mTitle = getIntent().getStringExtra("title");
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
                showShare();
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
        /*Document doc = Jsoup.parse(list);
        Elements es = doc.select("div.content");
        String html = es.html();
        mTvNewsDetail.setText(Html.fromHtml(html));*/


        Document doc = Jsoup.parse(list);
        Elements es = doc.select("div.content");
        Elements div = doc.select("div.content");
        Elements text = es.select("p");

        Elements pic = text.select("p[align]");
        if (pic != null && pic.size() != 0) {
            Element img = pic.select("a").first();
            String picUrl = img.attr("href");

            if (!TextUtils.isEmpty(picUrl)) {
                String subUrl = "http:" + picUrl.substring(picUrl.indexOf("%3A") + 3);
                mSdNewsPic.setImageURI(subUrl);
            }
        }

        boolean isFirstAppend = true;
        //   div.html("<p> &nbsp; </p>");

        for (Element e : text) {
            if (!e.hasAttr("align")) {
                if (isFirstAppend) {
                    isFirstAppend = false;
                    div.html(e.toString());
                } else {
                    div.append(e.toString());
                }
                //  div.append(e.toString());
            }
        }

        mTvTitle.setText(mTitle);

        Document d = Jsoup.parseBodyFragment(es.toString());
      //  mTvNewsDetail.setText(Html.fromHtml(d.html()));
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.loadDataWithBaseURL(null,d.html(),"text/html","utf-8",null);

    }


    @Override
    public void showNetErrorMessage() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    /**
     * 第三方分享
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(mTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(mUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    class MyImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            return null;
        }
    }

}
