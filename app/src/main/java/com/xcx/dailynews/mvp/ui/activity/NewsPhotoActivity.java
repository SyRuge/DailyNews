package com.xcx.dailynews.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.xcx.dailynews.R;
import com.xcx.dailynews.adapter.PhotoPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsPhotoActivity extends AppCompatActivity {

    @Bind(R.id.iv_photo_back)
    ImageView mIvPhotoBack;
    @Bind(R.id.vp_photo_detail)
    ViewPager mVpPhotoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_photo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<String> list=new ArrayList<>();
        Bundle b = getIntent().getBundleExtra("value");
        String title = b.getString("title");
        list.add(b.getString("imgUrl1"));
        list.add(b.getString("imgUrl2"));
        mVpPhotoDetail.setAdapter(new PhotoPagerAdapter(list,title));
    }

    @OnClick(R.id.iv_photo_back)
    public void onViewClicked() {
        finish();
    }
}
