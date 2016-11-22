package com.xcx.dailynews;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.xcx.dailynews.mvp.ui.fragment.BasePagerFragment;
import com.xcx.dailynews.mvp.ui.fragment.Fragment2;
import com.xcx.dailynews.mvp.ui.fragment.MyFragment;
import com.xcx.dailynews.mvp.ui.fragment.PhotoFragment;
import com.xcx.dailynews.mvp.ui.view.MyFragmentTabHost;
import com.xcx.dailynews.util.ViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initToolBarAndDrawerLayout();
        initTabLayout();
        initTabs();
        initData();
        initListener();
    }


    private void initView() {
        ViewUtil.changeStatusBarColor(this);

    }

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
        Class<?>[] f = {BasePagerFragment.class, Fragment2.class, PhotoFragment.class, MyFragment
                .class};
        int[] resId = {R.drawable.home, R.drawable.video, R.drawable.image, R.drawable.person};
        String[] resName = {"主页", "视频", "图片", "我的"};
        String[] tabId = {"home", "movie", "photo", "my"};
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
}
