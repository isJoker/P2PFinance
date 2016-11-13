package com.wjc.p2p.uitls;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.wjc.p2p.common.MyApplication;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：专门提供为处理一些UI相关的问题而创建的工具类，
 * 提供资源获取的通用方法，避免每次都写重复的代码获取结果。
 */

public class UIUtils {

    /**
     * 返回当前应用的context实例 （返回的就是myApplication实例）
     * @return
     */
    public static Context getContext(){
        return MyApplication.mContext;
    }

    /**
     * 返回一个可以发送消息的handler的实例
     * @return
     */
    public static Handler getHandler(){
        return MyApplication.mHandler;
    }

    /**
     * 返回资源文件中指定colorId对应的颜色值
     * @param colorId
     * @return
     */
    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    /**
     * 加载指定layoutId的布局，并返回
     * @param layoutId
     * @return
     */
    public static View getXmlView(int layoutId){
        return View.inflate(getContext(), layoutId, null);
    }


    /**
     * 返回对应的strings.xml中<string-array>对应的string数组资源
     * @param stringArrayId
     * @return
     */
    public static String[] getStringArray(int stringArrayId){
        String[] stringArray = getContext().getResources().getStringArray(stringArrayId);
        return stringArray;
    }

    /**
     * 将dp转换为px
     * @param dp
     * @return
     */
    public static int dp2Px(int dp){// px = dp * density  (160dp手机看成density = 1)
        //获取当前手机的密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density + 0.5);//通过四舍五入，获取最接近的整数

    }

    public static int px2dp(int px){
        //获取当前手机的密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(px / density + 0.5);//通过四舍五入，获取最接近的整数
    }

    /**
     * 保证runnable中run()中的操作是在主线程中执行的
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable){
        //先判断当前线程是否是主线程
        if(isMainThread()){
            runnable.run();
        }else{
            getHandler().post(runnable);//发送消息到主线程中执行
        }
    }

    /**
     * 判断当前线程是否是主线程
     * @return
     */
    private static boolean isMainThread() {
        int currentThreadId = android.os.Process.myTid();//获取当前线程的id
        return currentThreadId == MyApplication.currentThreadId;

    }
    //显示Toast
    public static void toast(String message,boolean isLongShow){
        Toast.makeText(UIUtils.getContext(),message, isLongShow == true? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

}
