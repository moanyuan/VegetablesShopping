package com.lengfeng.vegetablesshopping.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class MyApplication extends Application {

	public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
    }
}
