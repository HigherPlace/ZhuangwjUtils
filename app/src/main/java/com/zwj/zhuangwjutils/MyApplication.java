package com.zwj.zhuangwjutils;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication gApp;

    @Override
    public void onCreate() {
        super.onCreate();


        gApp = (MyApplication) getApplicationContext();
        // 初始化Xutils
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
    }



    public static MyApplication getGlobalContext() {
        return gApp;
    }

}
