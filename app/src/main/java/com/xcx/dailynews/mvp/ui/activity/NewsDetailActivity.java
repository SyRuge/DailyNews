package com.xcx.dailynews.mvp.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.ApiConstants;
import com.xcx.dailynews.Constants;
import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.CollectBean;
import com.xcx.dailynews.bean.NewsDetailBean;
import com.xcx.dailynews.db.CollectBeanDao;
import com.xcx.dailynews.db.CollectContentProvider;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.presenter.NewsDetailPresenter;
import com.xcx.dailynews.mvp.presenter.UpdateServerPresenter;
import com.xcx.dailynews.util.ViewUtil;

import org.greenrobot.greendao.query.QueryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

import static com.xcx.dailynews.R.id.toolbar;

public class NewsDetailActivity extends AppCompatActivity implements NewsContract
        .View<NewsDetailBean>{

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
    @Inject
    UpdateServerPresenter mUpdateServerPresenter;

    private String mChannelId;
    private int mPosition;
    private String mTitle;
    private String mSource;
    private String mDigest;
    private String mLmodify;
    private CollectBeanDao mCollectBeanDao;
    private String mUrl_3w;
    private String mPostid;
    private String mTrueUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getDataFromIntent();

        ButterKnife.bind(this);
        mCollectBeanDao = MyApplication.getMyApplication().getDaoSession().getCollectBeanDao();
        initView();
        initInject();
        initToolBar();
        mPresenter.attachView(this);
        mUpdateServerPresenter.attachView(this);
        initData();
        initListener();
    }

    private void getDataFromIntent() {
        Bundle b = getIntent().getBundleExtra("value");
        mChannelId = b.getString("id");
        mPosition = b.getInt("pos", 0);
        mTitle = b.getString("title");
        mSource = b.getString("source", "未知来源");
        mDigest = b.getString("digest");
        mLmodify = b.getString("lmodify");
        mUrl_3w = b.getString("url_3w");
        mPostid = b.getString("postid");
        mTrueUrl = ApiConstants.TRUE_NEWS_DETAIL_URL + mPostid + ApiConstants.ENDDETAIL_URL;
    }

    private void initView() {
        ShareSDK.initSDK(this);
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

        Log.e("TAG", mTrueUrl);

        if (!TextUtils.isEmpty(mTrueUrl) && !TextUtils.isEmpty(mChannelId)) {
            mPresenter.getData(mTrueUrl, mChannelId, Constants.NEWS_DETAIL, mPosition);
        }

        /*if (!TextUtils.isEmpty(mUrl_3w)) {
            initWebView(mUrl_3w);
        }*/
    }

    private void initListener() {


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_settings:
                        insertIntoDataBase();
                        break;
                }

                return true;
            }
        });
    }

    private void insertIntoDataBase() {
        SharedPreferences xcx = getSharedPreferences("xcx", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = xcx.edit();
        String isLogin = xcx.getString("isLogin", "no");
        String isSetData = xcx.getString("isSetData", "no");
        if (isLogin.equals("yes")) {
            QueryBuilder<CollectBean> qb = mCollectBeanDao.queryBuilder();
            qb.where(CollectBeanDao.Properties.Url.eq(mTrueUrl));
            List<CollectBean> list = qb.list();
            if (list == null || list.size() == 0) {
                ContentValues cv = new ContentValues();
                cv.put("url", mTrueUrl);
                cv.put("channelid", mChannelId);
                cv.put("position", mPosition);
                cv.put("title", mTitle);
                cv.put("source", mSource);
                cv.put("digest", mDigest);
                cv.put("lmodify", mLmodify);
                getContentResolver().insert(CollectContentProvider.CONTENT_URI, cv);
                edit.putString("isSetData","yes");
                edit.commit();
                /**
                 * 同时将数据插入到服务器数据库中
                 */
                CollectBean cb = new CollectBean(null, mTrueUrl, mChannelId,
                        mPosition, mSource, mTitle, mDigest, mLmodify);
                mUpdateServerPresenter.getData(mTrueUrl, "insert", cb);
                Toast.makeText(getApplicationContext(), "收藏成功!", Toast.LENGTH_SHORT)
                        .show();

            } else {
                Toast.makeText(getApplicationContext(), "已经收藏过啦", Toast.LENGTH_SHORT)
                        .show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "还没有登录呢", Toast.LENGTH_SHORT)
                    .show();
        }
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


    /**
     * 数据获取成功会回调此方法，然后更新界面
     */
    @Override
    public void setDataToView(NewsDetailBean bean, int loadType) {

        //不是插入操作的回调
       if (loadType!=12){
           if (bean.newsdetail.img != null && bean.newsdetail.img.size() != 0) {
               NewsDetailBean.NewsDetail.ImgBean imgUrl = bean.newsdetail.img.get(0);
               if (imgUrl != null && !TextUtils.isEmpty(imgUrl.src)) {
                   mSdNewsPic.setImageURI(imgUrl.src);
               }
           }
           mTvTitle.setText(bean.newsdetail.title);
           mWebView.getSettings().setBlockNetworkImage(false);
           mWebView.getSettings().setLoadsImagesAutomatically(false);
           mWebView.loadDataWithBaseURL(null, bean.newsdetail.html, "text/html", "utf-8", null);
       }else if (loadType==12){
           Toast.makeText(getApplicationContext(), "收藏成功!", Toast.LENGTH_SHORT)
                   .show();
       }

    }

    @Override
    public void showNetErrorMessage() {

    }

    @Override
    public void showErrorMessage(String message) {

    }


    private void showShare() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "来自DailyNews");
        intent.putExtra(Intent.EXTRA_TEXT, mUrl_3w);
        intent=Intent.createChooser(intent,"分享");
        startActivity(intent);

    }


   /* private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(mTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(mTrueUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mTrueUrl);
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
    }*/

    private void paseHtml(String list, int type) {
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
        mWebView.loadDataWithBaseURL(null, d.html(), "text/html", "utf-8", null);

        //  initWebView(mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
