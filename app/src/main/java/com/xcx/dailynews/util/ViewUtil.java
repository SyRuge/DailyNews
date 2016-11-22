package com.xcx.dailynews.util;


import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xcx.dailynews.R;

/**
 * Created by xcx on 2016/10/26.
 */

public class ViewUtil {

    /**
     * 修改状态栏和底部导航栏的颜色
     */
    public static void changeStatusBarColor(Activity context){
        // 设置顶部状态栏颜色（此功能仅19版本以上可用 4.4以及4.4以上）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //透明导航栏
         //   window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            SystemBarTintManager tintManager = new SystemBarTintManager(context);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
         //   tintManager.setNavigationBarTintEnabled(true);

            // 使用颜色资源
            tintManager.setStatusBarTintResource(R.color.colorPrimary);

            //因为使用此种方式会导致整个activity的位置向上移动了Systembar的高度，
            // 因此需要设置你activity中控件的padinntTop避免这个问题
          //  SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
         //   view.setPadding(0, config.getPixelInsetTop(true), 0, config.getPixelInsetBottom());
        }
    }
}
