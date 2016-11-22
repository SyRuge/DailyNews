package com.xcx.dailynews.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xcx.dailynews.util.UiUtil;

/**
 * Created by xcx on 2016/11/3.
 */

public class MyOpenHelper extends SQLiteOpenHelper {



    private MyOpenHelper(Context context) {
        super(context, "news.db", null, 1);
    }

    public static class singletonHolder{
        private static MyOpenHelper sMyOpenHelper = new MyOpenHelper(UiUtil.getAppContext());
    }

    public static MyOpenHelper getInstance(){
        return singletonHolder.sMyOpenHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
         String sql = "create table channel(_id integer primary key autoincrement,id integer," +
                "name text,orderId integer,selected selected)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
