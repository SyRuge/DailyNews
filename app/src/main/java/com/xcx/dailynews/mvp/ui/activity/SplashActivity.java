package com.xcx.dailynews.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.xcx.dailynews.MainActivity;
import com.xcx.dailynews.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_splash);
     //   SystemClock.sleep(2000);
     //   startActivity(new Intent(this, MainActivity.class));
     //   finish();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                  finish();
            }
        }).start();
    }

}
