package com.wjc.p2p.fragment;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.wjc.p2p.R;
import com.wjc.p2p.ui.randomlayout.StellarMap;
import com.wjc.p2p.uitls.UIUtils;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by ${万嘉诚} on 2016/11/12.
 * WeChat：wjc398556712
 * Function：
 */

public class ProductRecommondFragment extends BaseFragment {

    @Bind(R.id.stellar_map)
    StellarMap stellarMap;

    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };

    //将上述的数据分为两组
    private String[] ones = new String[datas.length / 2];
    private String[] twos = new String[datas.length / 2];
    private Random random;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        //初始化ones和twos数组
        for (int i = 0; i < datas.length / 2; i++) {
            ones[i] = datas[i];
        }

        for (int i = 0; i < datas.length / 2; i++) {
            twos[i] = datas[i + 8];
        }

        random = new Random();
        MyAdapter adapter = new MyAdapter();
        stellarMap.setAdapter(adapter);

        //如下两个方法如果不调用，不会在界面显示随机数据的效果
        stellarMap.setRegularity(8, 8);//设置XY轴上的稀疏度
        stellarMap.setGroup(0, true);//首先显示第一组

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_productrecommend;
    }

    @Override
    protected void initTitleBar() {

    }

    class MyAdapter implements StellarMap.Adapter {

        //返回组数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回指定组组的元素个数
        @Override
        public int getCount(int group) {
            if (group == 0) {
                return datas.length / 2;
            } else {
                return datas.length - datas.length / 2;
            }
//            return 8;
        }

        /**
         * 返回指定组指定位置上的View
         *
         * @param group
         * @param position    对于每组数据position都从0开始
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView textView = new TextView(getActivity());

            //设置属性
            if (group == 0) {
                textView.setText(ones[position]);//设置显示内容
            } else {
                textView.setText(twos[position]);
            }

            textView.setTextSize(UIUtils.dp2Px(8) + random.nextInt(10));//设置字体大小
            int red = random.nextInt(211);//red:[0,255]  00~ff   211随机写的数
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            textView.setTextColor(Color.rgb(red, green, blue));//设置随机颜色

            //设置每一个textView的点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductRecommondFragment.this.getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            return textView;
        }

        //下一组显示平移动画组别
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        //下一一组显示缩放动画的组别
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            //两组交替
            if (group == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
