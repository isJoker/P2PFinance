package com.wjc.p2p.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.wjc.p2p.R;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.uitls.LogUtil;
import com.wjc.p2p.uitls.pay.PayKeys;
import com.wjc.p2p.uitls.pay.PayResult;
import com.wjc.p2p.uitls.pay.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

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

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    //提供商户的信息
    private static final String PARTNER = PayKeys.DEFAULT_PARTNER;
    private static final String SELLER = PayKeys.DEFAULT_SELLER;
    private static final String RSA_PRIVATE = PayKeys.PRIVATE;


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


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(RechargeActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(RechargeActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(RechargeActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(RechargeActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };



    //点击“充值”按钮实现的充值操作
    @OnClick(R.id.recharge_btn)
    public void recharge(View view){
        // 订单
        String orderInfo = getOrderInfo("iphone7 plus", "apple史上最强大的手机", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RechargeActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }


    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order.
     * 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
