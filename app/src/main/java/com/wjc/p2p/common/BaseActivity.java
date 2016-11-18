package com.wjc.p2p.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.wjc.p2p.bean.User;

import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/16.
 * WeChat：wjc398556712
 * Function：通用的Activity的类
 */

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        //将当前的activity添加到自己的栈空间中
        MyActivityManager.getInstance().add(this);

        //初始化的操作
        initTitle();
        initData();
    }

    protected abstract void initTitle();

    protected abstract void initData();
    
    public abstract int getLayoutId();

    //启动新的activity
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this,activity);
        if(bundle != null && bundle.size() != 0) {
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
    }

    //联网操作需要使用AsyncHttpClient
    public AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    //保存用户登录信息
    public void saveLogin(User user) {
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UF_ACC", user.UF_ACC);
        editor.putString("UF_AVATAR_URL", user.UF_AVATAR_URL);
        editor.putString("UF_IS_CERT", user.UF_IS_CERT);
        editor.putString("UF_PHONE", user.UF_PHONE);
        editor.commit();
    }

    //读取用户的登录信息
    public User readLogin() {
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        user.UF_ACC = sp.getString("UF_ACC", "");
        user.UF_AVATAR_URL = sp.getString("UF_AVATAR_URL", "");
        user.UF_IS_CERT = sp.getString("UF_IS_CERT", "");
        user.UF_PHONE = sp.getString("UF_PHONE", "");
        return user;
    }

    //关闭当前activity的显示
    public void closeCurrentActivity(){
        MyActivityManager.getInstance().removeCurrent();
    }
}
