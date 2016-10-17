package com.lengfeng.vegetablesshopping.utils;



import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.lengfeng.vegetablesshopping.common.MyApplication;

/**
 * Created by Administrator on 2016/3/18.
 */
public class UIUtils {

    /*public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }*/


    /*public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }*/


    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * 1dp:0.75px
     * 1dp:1px
     * 1dp:1.25px;
     * 1dp:....
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }


    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
        //Log.i("128px", density);
    }


    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }


    /**
     * 保证runnable在主线程当中执行的
     *
     * @param runnable
     */
    public static void runOnMainThread(Runnable runnable) {
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }
}
