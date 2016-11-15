package com.wjc.p2p.common;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：
 */

public abstract class BaseHolder<T> {

    private T data;//要装配的数据

    private View rootView;//要装配到的视图

    public BaseHolder(){
        rootView = initView();
        rootView.setTag(this);
        ButterKnife.bind(this, rootView);
    }

    public abstract View initView() ;

    public void setData(T t){
        this.data = t;
        //如何装配
        refreshData(t);
    }

    protected abstract void refreshData(T t);

    public View getRootView() {
        return rootView;
    }

}
