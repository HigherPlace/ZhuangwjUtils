package com.zwj.zwjutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Activity整个栈的管理类
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈中
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束当前界面下的所有界面
     */
    public void finishPreAllActivity() {
        int size = activityStack.size();
        if(size > 1) {
            Activity firstActivity = null;
            for (int i = 0; i < size; i++) {
                Activity activity = activityStack.get(i);
                if (null != activity) {
                    if(i == size - 1) {
                        firstActivity = activity;
                    }else {
                        activity.finish();
                    }
                }
            }

            activityStack.clear();
            if(firstActivity != null) {
                activityStack.add(firstActivity);
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {

        //友盟统计,如果开发者调用Process.kill或者System.exit之类的方法杀死进程，
        //请务必在此之前调用MobclickAgent.onKillProcess(Context context)方法，用来保存统计数据
//		MobclickAgent.onKillProcess(context);

        finishAllActivity();
        ActivityManager activityMgr = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses(context.getPackageName());

        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}