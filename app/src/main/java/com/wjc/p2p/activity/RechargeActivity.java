package com.wjc.p2p.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.p2p.R;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.uitls.LogUtil;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/18.
 * WeChat：wjc398556712
 * Function：充值界面
 */

public class RechargeActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.recharge_text_input)
    TextView rechargeTextInput;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.recharge_et)
    EditText rechargeEt;
    @Bind(R.id.recharge_text)
    TextView rechargeText;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.recharge_btn)
    Button rechargeBtn;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("充值");
    }

    @Override
    protected void initData() {
        //设置button的可点击性
        rechargeBtn.setClickable(false);
        //给页面中的EditText设置文本的监听
        rechargeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtil.e("beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.e("onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TAG", "after");

                String money = rechargeEt.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    rechargeBtn.setBackgroundResource(R.drawable.btn_023);
                    //设置button的可点击性
                    rechargeBtn.setClickable(false);
                } else {
                    rechargeBtn.setBackgroundResource(R.drawable.btn_01);
                    //设置button的可点击性
                    rechargeBtn.setClickable(true);
                }

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

}
