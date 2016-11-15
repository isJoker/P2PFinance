package com.wjc.p2p.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：
 */

public abstract class MyBaseAdapter2<T> extends BaseAdapter {

    public List<T> list;
    public MyBaseAdapter2(List<T> list){
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
        return i;
    }

    //解决两个问题：①提供一个holder的实现，并在holder中关联convertView,同时提供item layout加载
    //②将集合中的具体position位置的数据装配到holder的convertView中
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseHolder holder;
        T t = list.get(i);
        if(view == null) {
            holder = getHolder();
        } else {
            holder = (BaseHolder) view.getTag();
        }

        //装配数据
        holder.setData(t);

        return holder.getRootView();
    }

    public abstract BaseHolder getHolder();

}
