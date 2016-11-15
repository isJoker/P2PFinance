package com.wjc.p2p.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：提供通用的baseAdapter
 */

public abstract class MyBaseAdapter1<T> extends BaseAdapter {

    public List<T> list;

    public MyBaseAdapter1(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return myGetView(i,view,viewGroup);
    }

    public abstract View myGetView(int i, View view, ViewGroup viewGroup);
}
