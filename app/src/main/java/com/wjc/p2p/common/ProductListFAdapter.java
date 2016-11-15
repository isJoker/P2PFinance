package com.wjc.p2p.common;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wjc.p2p.R;
import com.wjc.p2p.bean.Product;
import com.wjc.p2p.ui.RoundProgress;
import com.wjc.p2p.uitls.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/15.
 * WeChat：wjc398556712
 * Function：一般的自定义Adapter
 */

public class ProductListFAdapter extends BaseAdapter {

    private List<Product> products;

    public ProductListFAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getCount() {
        return products != null ? products.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return products != null ? products.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int itemViewType = getItemViewType(position);

        if(itemViewType == 0) {//显示不同的item

            TextView tv = new TextView(parent.getContext());
            tv.setText("不一样的烟火");
            tv.setTextColor(Color.RED);
            tv.setTextSize(UIUtils.dp2Px(20));
            tv.setGravity(Gravity.CENTER);
            return tv;

        } else { //在这里新加入了一条item，但原来的item数目不变

            if(position > 3) {
                position--;//保证原来的数据接着正常显示
            }

            ViewHolder holder;
            //装配数据
            Product product = products.get(position);
            if (convertView == null) {
                //parent是listview,parent.getContext() 是 listview 外层 Activity
                convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.pMinnum.setText(product.memberNum);
            holder.pMinzouzi.setText(product.minTouMoney);
            holder.pMoney.setText(product.money);
            holder.pName.setText(product.name);
            holder.pSuodingdays.setText(product.suodingDays);
            holder.pYearlv.setText(product.yearRate);
            holder.pProgresss.setProgress(Integer.parseInt(product.progress));

            return convertView;
        }
    }

    //获取不同position位置上的类型
    @Override
    public int getItemViewType(int position) {
        if(position == 3) {//改变position为3位置的视图类型
            return 0;
        } else{
            return 1;
        }
    }

    //获取不同item类型的个数
    @Override
    public int getViewTypeCount() {
        return 2;//在这里是两种类型
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
