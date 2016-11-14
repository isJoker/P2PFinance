package com.wjc.p2p.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.p2p.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：
 */

public class InvestFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
//    @Bind(R.id.tab_page_indicator)
//    com.viewpagerindicator.TabPageIndicator tabPageIndicator;
    @Bind(R.id.vp_invest)
    ViewPager vpInvest;

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    public void initData() {
        initFragments();
    }

    /**
     * 将三个要显示的fragment添加到集合中
     */
    private void initFragments() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();

        fragments.add(productListFragment);
        fragments.add(productRecommondFragment);
        fragments.add(productHotFragment);
    }

    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("投资");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
