package com.wjc.p2p.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wjc.p2p.R;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.uitls.LogUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/18.
 * WeChat：wjc398556712
 * Function：提现界面
 */

public class WithdrawActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.account_zhifubao)
    TextView accountZhifubao;
    @Bind(R.id.select_bank)
    RelativeLayout selectBank;
    @Bind(R.id.recharge_text)
    TextView rechargeText;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.input_money)
    EditText inputMoney;
    @Bind(R.id.tv_recharge)
    TextView tvRecharge;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.btn_withdraw)
    Button btnWithdraw;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("提现");

    }

    @OnClick(R.id.iv_top_back)
    public void back(View view){
        closeCurrentActivity();
    }

    @Override
    protected void initData() {

        //设置button的可点击性
        btnWithdraw.setClickable(false);

        //给页面中的EditText设置文本的监听
        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LogUtil.e("beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                LogUtil.e("onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String money = inputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    btnWithdraw.setBackgroundResource(R.drawable.btn_023);
                    //设置button的可点击性
                    btnWithdraw.setClickable(false);
                } else {
                    btnWithdraw.setBackgroundResource(R.drawable.btn_01);
                    //设置button的可点击性
                    btnWithdraw.setClickable(true);
                }

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }

}
