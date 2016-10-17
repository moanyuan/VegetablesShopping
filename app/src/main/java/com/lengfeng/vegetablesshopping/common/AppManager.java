package com.lengfeng.vegetablesshopping.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2016/3/18.
 * <p/>
 * 添加、删除、指定删除、清空、判断栈的大小
 * <p/>
 * AppManager进行单例
 */
public class AppManager {

    public static AppManager appManager = null;

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    private static Stack<Activity> activityStack = new Stack<Activity>();


    public static void addActivity(Activity activity) {
        activityStack.add(activity);
    }


    public static void removeCurrent() {
        Activity lastElement = activityStack.lastElement();
        lastElement.finish();
        activityStack.remove(lastElement);
    }


    /**
     * 删除指定的activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (activityStack.get(i).getClass().equals(activity.getClass())) {
                activity.finish();
                activityStack.remove(activity);
                break;
            }
        }
    }

    public static void clear() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }


    public static int getSize() {
        return activityStack.size();
    }
}
