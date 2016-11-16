package com.wjc.p2p.adapter;

import com.wjc.p2p.common.BaseHolder;
import com.wjc.p2p.common.MyHolder;

import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：
 */

public class PListFAdapter2 extends MyBaseAdapter2 {

    public PListFAdapter2(List list) {
        super(list);
    }

    @Override
    public BaseHolder getHolder() {
        return new MyHolder();
    }
}
