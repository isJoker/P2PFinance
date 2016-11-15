package com.wjc.p2p.common;

import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：
 */

public class ProductListFAdapter2 extends MyBaseAdapter2 {

    public ProductListFAdapter2(List list) {
        super(list);
    }

    @Override
    public BaseHolder getHolder() {
        return new MyHolder();
    }
}
