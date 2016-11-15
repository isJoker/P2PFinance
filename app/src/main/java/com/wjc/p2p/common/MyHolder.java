package com.wjc.p2p.common;

import android.view.View;
import android.widget.TextView;

import com.wjc.p2p.R;
import com.wjc.p2p.bean.Product;
import com.wjc.p2p.ui.RoundProgress;
import com.wjc.p2p.uitls.UIUtils;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：
 */

public class MyHolder extends BaseHolder {

    @Bind(R.id.p_name)
    TextView pName;
    @Bind(R.id.p_money)
    TextView pMoney;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.p_suodingdays)
    TextView pSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView pMinzouzi;
    @Bind(R.id.p_minnum)
    TextView pMinnum;
    @Bind(R.id.p_progresss)
    RoundProgress pProgresss;

    @Override
    public View initView() {
        return UIUtils.getXmlView(R.layout.item_product_list);
    }

    @Override
    protected void refreshData(Object o) {
        Product product = (Product) o;
        //装配数据
        pMinnum.setText(product.memberNum);
        pMinzouzi.setText(product.minTouMoney);
        pMoney.setText(product.money);
        pName.setText(product.name);
        pSuodingdays.setText(product.suodingDays);
        pYearlv.setText(product.yearRate);
        pProgresss.setProgress(Integer.parseInt(product.progress));
    }
}
