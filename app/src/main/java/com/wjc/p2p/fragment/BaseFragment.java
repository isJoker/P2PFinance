package com.wjc.p2p.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjc.p2p.uitls.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：Fragment基类
 */

public abstract class BaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(getLayoutId());
        ButterKnife.bind(this,view);

        initTitleBar();
        initData();
        return view;
    }

    protected abstract int getLayoutId() ;


    protected abstract void initTitleBar();


    //用于子类初始化数据
    public void initData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
