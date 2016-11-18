package com.wjc.p2p.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wjc.p2p.MainActivity;
import com.wjc.p2p.R;
import com.wjc.p2p.bean.User;
import com.wjc.p2p.common.MyActivityManager;
import com.wjc.p2p.common.AppNetConfig;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.uitls.MD5Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/16.
 * WeChat：wjc398556712
 * Function：
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.login_user_name)
    EditText loginUserName;
    @Bind(R.id.about_com)
    RelativeLayout aboutCom;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.log_log_btn)
    Button logLogBtn;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("用户登录");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.log_log_btn)
    public void login(View view){
        //1.获取用户手机号和密码
        String number = loginUserName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        //判断用户名和密码是否为空
        if(TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "手机号码和密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //2.联网发送请求，将手机号和密码作为请求参数发送给服务器
            RequestParams requestParams = new RequestParams();
            requestParams.put("username",number);//此处的key要和服务器的key保持一致
            requestParams.put("password", MD5Utils.MD5(password));
            asyncHttpClient.post(AppNetConfig.LOGIN,requestParams,new AsyncHttpResponseHandler(){

                @Override
                public void onSuccess(String content) {
                    JSONObject jsonObject = JSON.parseObject(content);
                    Boolean success = jsonObject.getBoolean("success");
                    if(success) {//3.如果返回success = true，表示登录成功
                        String data = jsonObject.getString("data");
                        User user = JSON.parseObject(data, User.class);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //保存用户登录的信息
                        saveLogin(user);
                        //返回操作
                        MyActivityManager.getInstance().removeAll();
                        goToActivity(MainActivity.class,null);//显示首页

                    } else {//4.如果返回successs = false,表示用户名或密码不匹配
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Toast.makeText(LoginActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        MyActivityManager.getInstance().removeAll();//移除所有栈空间的activity
        goToActivity(MainActivity.class, null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

}
