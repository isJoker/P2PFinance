package com.wjc.p2p.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;
import com.wjc.p2p.R;
import com.wjc.p2p.uitls.UIUtils;

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
    @Bind(R.id.tab_page_indicator)
    TabPageIndicator tabPageIndicator;
    @Bind(R.id.vp_invest)
    ViewPager vpInvest;

    private List<Fragment> fragments = new ArrayList<>();


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
        initFragments();
        MyAdapter adapter = new MyAdapter(getFragmentManager());
        vpInvest.setAdapter(adapter);
        //给ViewPager设置标题
        tabPageIndicator.setViewPager(vpInvest);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invest;
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

    @Override
    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("投资");

    }

    /**
     * FragmentStatePagerAdapter:适用于Fragment较多的情况，会适时的销毁已经创建的Fragment
     * FragmentPagerAdapter:适用于Fragment不多的情况，不会销毁
     */
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments != null ? fragments.get(position) : null;
        }

        @Override
        public int getCount() {
            return fragments != null ? fragments.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //从strings.xml中读取String构成的数组
            return UIUtils.getStringArray(R.array.invest_tab)[position];
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
