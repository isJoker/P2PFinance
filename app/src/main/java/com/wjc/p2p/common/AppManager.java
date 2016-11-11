package com.wjc.p2p.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：统一应用程序中所有的Activity的栈管理（单例）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 */

public class AppManager {

    //在整个应用程序中，只需要维护当前类的一个实例即可（单例）
    private AppManager(){}

    private static AppManager appManager = new AppManager();

    public static AppManager getInstance(){
        return appManager;
    }

    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 添加指定的activity到栈中
     * @param activity
     */
    public void add(Activity activity){
        if(activity != null) {
            activityStack.add(activity);
        }
    }


    /**
     * 删除指定的activity
     * @param activity
     */
    public void remove(Activity activity){
        if(activity != null) {
            //方式一 ？？？
//            for(int i = 0; i < activityStack.size(); i++) {
//                //在内存中只加载一次当前类。所以可以使用==进行判断。
//                if(activity.getClass() == activityStack.get(i).getClass()){
//                    //从栈空间移除当前的activity
//                    activityStack.remove(i);
//                    i--;
//                }
//            }
            //方式二
            for (int i = activityStack.size() - 1 ; i >= 0; i--){
                //在内存中只加载一次当前类。所以可以使用==进行判断。
                if(activity.getClass() == activityStack.get(i).getClass()) {
                    //从栈空间移除当前的activity
                    activityStack.get(i).finish();
                    activityStack.remove(i);
                }
            }

        }
    }

    /**
     * 删除栈顶的activity
     */
    public void removeCurrent(){
        //方式一：
        //移除当前activity的显示
//        activityStack.get(activityStack.size() - 1).finish();
        //从当前栈中删除
//        activityStack.remove(activityStack.size() - 1);
        //方式二：
        activityStack.lastElement().finish();
        activityStack.remove(activityStack.lastElement());
    }

    /**
     * 删除所有的activity
     *
     */
    public void removeAll(){
        for (int i = activityStack.size() - 1;i >= 0;i--){
            activityStack.get(i).finish();
            activityStack.remove(i);
        }
    }

    /**
     * 返回当前stack的大小
     */
    public int getSize(){
        return activityStack.size();
    }

}
