package com.wjc.p2p.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.p2p.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：
 */

public class MeFragment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }


    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("我的资产");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
