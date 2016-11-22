package com.xcx.dailynews.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

/**
 * Created by xcx on 2016/11/14.
 */

public class FileUtil {
    /**
     * 获取应用缓存路径
     */
    public static File getDiskCacheDir(String path){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = UiUtil.getAppContext().getExternalCacheDir().getPath();
        } else {
            cachePath = UiUtil.getAppContext().getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + path);
    }

    /**
     * 获取应用当前版本
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
