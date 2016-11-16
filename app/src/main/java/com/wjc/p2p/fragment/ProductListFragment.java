package com.wjc.p2p.fragment;


import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.wjc.p2p.R;
import com.wjc.p2p.bean.Product;
import com.wjc.p2p.common.AppNetConfig;
import com.wjc.p2p.adapter.PListFAdapter2;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/12.
 * WeChat：wjc398556712
 * Function：
 */

public class ProductListFragment extends BaseFragment {

    @Bind(R.id.lv_product_list)
    ListView lvProductList;
    private List<Product> productList;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {
        //用FastJson解析json数据
        JSONObject jsonObject = JSON.parseObject(content);

        Boolean sucess = jsonObject.getBoolean("success");
        if(sucess) {
            String data = jsonObject.getString("data");//得到json字符串
            productList = JSON.parseArray(data, Product.class);//得到了所有产品构成的集合

        }

        //方式一：
//        PListFAdapter adapter = new PListFAdapter(productList);

        //方式二：抽取方式一以后 （可以使用，但是getView()优化有限）
//        PListFAdapter1 adapter = new PListFAdapter1(productList);

        //方式三（推荐,既对getView()抽取了，同时使用了ViewHolder）
        PListFAdapter2 adapter = new PListFAdapter2(productList);

        lvProductList.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_productlist;
    }

    @Override
    protected void initTitleBar() {

    }

}
