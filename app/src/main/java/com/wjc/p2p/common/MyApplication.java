package com.wjc.p2p.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：
 */

public class MyApplication extends Application {
    //提供4个需要用到的属性
    public static Context mContext;//在整个应用中用到context的位置，都可以使用此变量
    public static Handler mHandler;//在整个应用中，发送消息使用
    public static Thread mThread;//获取当前应用的线程：主线程
    public static int currentThreadId;//获取主线程id

    @Override
    public void onCreate() {
        super.onCreate();
        //属性的初始化
        mContext =this.getApplicationContext();
        mHandler = new Handler();
        mThread = Thread.currentThread();
        currentThreadId = android.os.Process.myTid();
    }
}
