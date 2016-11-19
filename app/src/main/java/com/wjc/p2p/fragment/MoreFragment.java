package com.wjc.p2p.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.wjc.p2p.R;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：
 */

public class MoreFragment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.tv_message)
    TextView tvMessage;

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
        //实现跑马灯效果的方式一：
//        tvMessage.setFocusable(true);
//        tvMessage.setFocusableInTouchMode(true);
//        tvMessage.requestFocus();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("更多");
    }

}
