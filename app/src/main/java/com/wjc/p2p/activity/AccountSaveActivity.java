package com.wjc.p2p.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wjc.p2p.R;
import com.wjc.p2p.activity.gesturelock.GestureEditActivity;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.uitls.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/19.
 * WeChat：wjc398556712
 * Function：
 */

public class AccountSaveActivity extends BaseActivity {
    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.toggle_me)
    ToggleButton toggleMe;
    @Bind(R.id.btn_reset_passwprd)
    Button btnResetPasswprd;

    private SharedPreferences sp;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("账户安全");
    }

    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        closeCurrentActivity();
    }

    @Override
    protected void initData() {
        sp = this.getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        //获取当前togglebutton的状态
        boolean isSecretOpen = sp.getBoolean("isSecretOpen", false);
        toggleMe.setChecked(isSecretOpen);//设置当前toggleButton的状态

        toggleMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked) {//按钮被选择
                    //获取是否已经保存了手势密码
                    String inputCode = sp.getString("inputCode", "");
                    if ("".equals(inputCode)) {//本地没有设置过
                        new AlertDialog.Builder(AccountSaveActivity.this)
                                .setTitle("安全设置")
                                .setMessage("是否现在开启手势解锁")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sp.edit().putBoolean("isSecretOpen", true).commit();
                                        toggleMe.setChecked(true);
                                        startActivity(new Intent(AccountSaveActivity.this, GestureEditActivity.class));
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //页面提示
                                        UIUtils.toast("关闭密码保护", false);
                                        //保存关闭状态
                                        sp.edit().putBoolean("isSecretOpen", false).commit();
                                        //设置togglebutton的关闭
                                        toggleMe.setChecked(false);
                                        //设置重置解锁密码按钮不可点击
                                        btnResetPasswprd.setEnabled(false);
                                    }
                                })
                                .show();


                    } else {//本地设置过
                        UIUtils.toast("开启密码保护", false);
                        sp.edit().putBoolean("isSecretOpen", true).commit();
                        toggleMe.setChecked(true);
                        btnResetPasswprd.setEnabled(true);
                    }

                } else {//按钮没有被选择
                    //页面提示
                    UIUtils.toast("关闭密码保护", false);
                    //保存关闭状态
                    sp.edit().putBoolean("isSecretOpen", false).commit();
                    //设置togglebutton的关闭
                    toggleMe.setChecked(false);
                    btnResetPasswprd.setEnabled(false);
                }
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_accountsave;
    }

    @OnClick(R.id.btn_reset_passwprd)
    public void resetGesture(View view) {
        Intent intent = new Intent(this, GestureEditActivity.class);
        startActivity(intent);
    }

}
