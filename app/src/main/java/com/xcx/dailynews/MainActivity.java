package com.xcx.dailynews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.xcx.dailynews.mvp.ui.activity.AddChannelActivity;
import com.xcx.dailynews.mvp.ui.fragment.BasePagerFragment;
import com.xcx.dailynews.mvp.ui.fragment.CollectFragment;
import com.xcx.dailynews.mvp.ui.fragment.MyFragment;
import com.xcx.dailynews.mvp.ui.fragment.PhotoFragment;
import com.xcx.dailynews.mvp.ui.view.MyFragmentTabHost;
import com.xcx.dailynews.util.ViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BasePagerFragment
        .OnBindPagerAndTabListener {

    @Bind(R.id.tl_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tl_tab)
    TabLayout mTabLayout;
    @Bind(R.id.dl_drawer)
    DrawerLayout mDlDrawer;
    @Bind(R.id.fl_th_content)
    FrameLayout mFlThContent;
    @Bind(R.id.th_tabhost)
    MyFragmentTabHost mFragmentTabHost;
    @Bind(R.id.rl_activity_tab)
    RelativeLayout mTablayoutParent;
    @Bind(R.id.cl_content)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.iv_add_channel)
    ImageView mIvAddChannel;
    @Bind(R.id.tv_drawer_news)
    TextView mTvDrawerNews;
    @Bind(R.id.tv_drawer_photo)
    TextView mTvDrawerPhoto;
    @Bind(R.id.tv_drawer_collect)
    TextView mTvDrawerCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initThridSDK();

        initView();
        initToolBarAndDrawerLayout();
        initTabLayout();
        initTabs();
        initData();
        initListener();
    }

    /**
     * 初始化第三方SDK
     */
    private void initThridSDK() {


    }


    private void initView() {
        ViewUtil.changeStatusBarColor(this);

    }

    /**
     * 初始化侧拉菜单
     */
    private void initToolBarAndDrawerLayout() {
        mToolbar.setTitle("Daily News");
        //把ActionBar替换成ToolBar
        setSupportActionBar(mToolbar);
        //设置小图标能够随侧滑菜单动态的改变样式和能够被点击
        //开启小图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建小图标
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDlDrawer,
                mToolbar, R.string.drawer_open, R.string.drawer_close);
        //同步侧滑状态
        toggle.syncState();
        //设置侧滑滑动监听
        mDlDrawer.addDrawerListener(toggle);
    }


    private void initTabLayout() {

        mTabLayout.addTab(mTabLayout.newTab().setText("头条"));
        mTabLayout.addTab(mTabLayout.newTab().setText("精选"));
        mTabLayout.addTab(mTabLayout.newTab().setText("科技"));
        mTabLayout.addTab(mTabLayout.newTab().setText("财经"));
        mTabLayout.addTab(mTabLayout.newTab().setText("军事"));
        mTabLayout.addTab(mTabLayout.newTab().setText("娱乐"));

    }

    private void initTabs() {

        //1. 让Tabhost和FrameLayout相关联
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.fl_th_content);
        if (Build.VERSION.SDK_INT > 10) {
            mFragmentTabHost.getTabWidget().setShowDividers(0);
        }
        Class<?>[] f = {BasePagerFragment.class, CollectFragment.class, PhotoFragment.class,
                MyFragment
                .class};
        int[] resId = {R.drawable.home, R.drawable.colect, R.drawable.image, R.drawable.person};
        String[] resName = {"主页", "收藏", "图片", "我的"};
        String[] tabId = {"home", "collect", "photo", "my"};
        for (int i = 0; i < 4; i++) {
            //2. 获取测量准则
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(tabId[i]);
            //3. 设置指示器
            View view = View.inflate(getApplicationContext(), R.layout.tab_indicator, null);

            //底部功能菜单
            TextView tvTabIndicator = (TextView) view.findViewById(R.id.tv_tab_indicator);
            //给TextView设置左上右下的四个图片，类似于xml的drawableLeft
            tvTabIndicator.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                    .getDrawable(resId[i]), null, null);
            tvTabIndicator.setText(resName[i]);


            tabSpec.setIndicator(view);
            //4. 添加相应的标签
            Bundle bundle = new Bundle();
            bundle.putString("text", i + "tab");
            mFragmentTabHost.addTab(tabSpec, f[i], bundle);
        }
    }

    private void initData() {

    }

    private void initListener() {

        mIvAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddChannelActivity.class));
            }
        });

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mTablayoutParent.setVisibility("home".equals(tabId) ? View.VISIBLE : View.GONE);
            }

        });

    }


    @Override
    public void bindTab(ViewPager viewPager) {
        /**
         * 下面的方法可以解决tab字体丢失的问题,不要用setupWithViewPager()方法
         */
        //   activity.getTabLayout().setupWithViewPager(mVpPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout
                .ViewPagerOnTabSelectedListener(viewPager));
    }

    @OnClick({R.id.tv_drawer_news, R.id.tv_drawer_photo, R.id.tv_drawer_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_drawer_news:
                mFragmentTabHost.setCurrentTabByTag("home");
                mDlDrawer.closeDrawers();
                break;
            case R.id.tv_drawer_photo:
                mFragmentTabHost.setCurrentTabByTag("photo");
                mDlDrawer.closeDrawers();
                break;
            case R.id.tv_drawer_collect:
                mFragmentTabHost.setCurrentTabByTag("collect");
                mDlDrawer.closeDrawers();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor edit = getSharedPreferences("xcx", Context
                .MODE_PRIVATE).edit();
        edit.putString("isSetData", "no");
        edit.commit();

    }
    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                SharedPreferences.Editor edit = getSharedPreferences("xcx", Context
                        .MODE_PRIVATE).edit();
                edit.putString("isSetData", "no");
                edit.commit();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
