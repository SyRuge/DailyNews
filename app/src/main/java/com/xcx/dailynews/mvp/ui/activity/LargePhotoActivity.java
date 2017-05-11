package com.xcx.dailynews.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LargePhotoActivity extends AppCompatActivity {

    @Bind(R.id.sd_large_photo)
    SimpleDraweeView mSdLargePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_photo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Bundle b = getIntent().getBundleExtra("bundle");
        mSdLargePhoto.setImageURI(b.getString("url"));
    }
}
