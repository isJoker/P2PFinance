package com.wjc.p2p.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjc.p2p.R;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：Fragment基类
 */

public abstract class BaseFragment extends Fragment {

    private View titlebar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        titlebar = View.inflate(getActivity(), R.layout.titlebar, null);
        initTitleBar();
        return initView();
    }


    protected abstract void initTitleBar();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //用于子类初始化数据
    public void initData() {

    }

    //用于子类初始化视图
    public abstract View initView();


}
