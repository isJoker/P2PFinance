package com.wjc.p2p.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wjc.p2p.R;
import com.wjc.p2p.activity.AccountSaveActivity;
import com.wjc.p2p.activity.BarChartActivity;
import com.wjc.p2p.activity.LineChartActivity;
import com.wjc.p2p.activity.LoginActivity;
import com.wjc.p2p.activity.PieChartActivity;
import com.wjc.p2p.activity.RechargeActivity;
import com.wjc.p2p.activity.UserInfoActivity;
import com.wjc.p2p.activity.WithdrawActivity;
import com.wjc.p2p.activity.gesturelock.GestureVerifyActivity;
import com.wjc.p2p.bean.User;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.uitls.BitmapUtils;
import com.wjc.p2p.uitls.UIUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：w我的资产界面
 */

public class MeFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.icon_time)
    FrameLayout iconTime;
    @Bind(R.id.textView11)
    TextView textView11;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.recharge)
    ImageView recharge;
    @Bind(R.id.withdraw)
    ImageView withdraw;
    @Bind(R.id.ll_invest_manager)
    TextView llInvestManager;
    @Bind(R.id.ll_invest_manager_ocular)
    TextView llInvestManagerOcular;
    @Bind(R.id.ll_assets_manager)
    TextView llAssetsManager;
    @Bind(R.id.ll_account_security)
    TextView llAccountSecurity;


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        //验证用户是否已经登录
        isLogin();
    }

    @OnClick(R.id.iv_top_settings)
    public void showSettings(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(UserInfoActivity.class, null);
    }

    private void isLogin() {
        //1.读取本地保存登录信息位置的文件，判断是否已经登录。
        SharedPreferences sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uf_acc = sp.getString("UF_ACC", "");
        if (TextUtils.isEmpty(uf_acc)) {
            //2.1如果没有找到文件中的登录信息：提供用户：必须登录
            login();
        } else {
            //2.2如果找到了登录信息，加载登录用户的信息
            loadUserInfo();
        }

    }

    //如果在本地找到了用户登录的信息，则将此信息读入内存中
    private void loadUserInfo() {
        User user = ((BaseActivity) this.getActivity()).readLogin();
        //设置用户名的显示
        textView11.setText(user.UF_ACC);

        //判断本地用户是否开启了手势密码，如果开启，需要进入
        boolean isSecretOpen = getActivity().getSharedPreferences("secret_protect",Context.MODE_PRIVATE).getBoolean("isSecretOpen", false);
        if(isSecretOpen) {
            Intent intent = new Intent(getActivity(), GestureVerifyActivity.class);
            startActivity(intent);
        }

        //如果在本地存储了用户头像，则优先从本地获取
        String filePath = getActivity().getCacheDir() + "/tx.png";
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }

        //设置用户头像--联网获取
        Picasso.with(getActivity()).load(user.UF_AVATAR_URL).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                //对Bitmap进行压缩处理
                Bitmap zoom = BitmapUtils.zoom(source, UIUtils.dp2Px(62), UIUtils.dp2Px(62));
                //对Bitmap进行圆形处理
                Bitmap circleBitmap = BitmapUtils.circleBitmap(zoom);

                //回收
                source.recycle();
                return circleBitmap;
            }

            @Override
            public String key() {
                return "";//此方法不能返回null.否则报异常
            }
        }).into(imageView1);

    }

    //提供用户登录的方法
    private void login() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("登录")
                .setMessage("用户必须先登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //启动登录操作的activity
                        ((BaseActivity) MeFragment.this.getActivity()).goToActivity(LoginActivity.class, null);
                    }
                })
                .setCancelable(false)//设置AlertDia其他区域点击无效
                .show();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.VISIBLE);
        tvTopTitle.setText("我的资产");

    }

    @Override
    public void onResume() {//从本地获取
        super.onResume();
        String filePath = getActivity().getCacheDir() + "/tx.png";
        File file = new File(filePath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageView1.setImageBitmap(bitmap);
        }
    }

    @OnClick(R.id.recharge)
    public void reCharge(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(RechargeActivity.class, null);
    }

    @OnClick(R.id.withdraw)
    public void withdraw(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(WithdrawActivity.class, null);
    }

    @OnClick(R.id.ll_invest_manager)
    public void showLineChart(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(LineChartActivity.class, null);
    }

    @OnClick(R.id.ll_invest_manager_ocular)
    public void showBarChart(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(BarChartActivity.class, null);
    }

    @OnClick(R.id.ll_assets_manager)
    public void showPieChart(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(PieChartActivity.class, null);
    }

    @OnClick(R.id.ll_account_security)
    public void showToggleButton(View view) {
        ((BaseActivity) this.getActivity()).goToActivity(AccountSaveActivity.class, null);
    }


}
