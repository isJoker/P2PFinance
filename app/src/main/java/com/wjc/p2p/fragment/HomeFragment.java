package com.wjc.p2p.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.wjc.p2p.R;
import com.wjc.p2p.bean.Image;
import com.wjc.p2p.bean.Index;
import com.wjc.p2p.bean.Product;
import com.wjc.p2p.common.AppNetConfig;
import com.wjc.p2p.ui.RoundProgress;
import com.wjc.p2p.uitls.LogUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;


/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：首页
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tv_home_product)
    TextView tvHomeProduct;
    @Bind(R.id.rp_home_progress)
    RoundProgress rpHomeProgress;
    @Bind(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @Bind(R.id.btn_home_join)
    Button btnHomeJoin;
    private Index index;
    private int currentProgress;

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected void initData(String content) {
        index = new Index();
        if(!TextUtils.isEmpty(content)) {
            //1、使用FastJson解析得到json数据，并封装数据到bean对象中
            JSONObject jsonObject = JSON.parseObject(content);
            //解析得到Product对象

            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);

            //2.解析得到image构成的集合
            String imageArr = jsonObject.getString("imageArr");
            List<Image> images = JSON.parseArray(imageArr, Image.class);
            LogUtil.e("images---->" + images);

            //2、设置ViewPager,加载显示数据
            index = new Index();
            index.product = product;
            index.images = images;

//                //3、根据产品数据，在界面显示
//                adapter = new MyAdapter();
//                //使用ViewPager加载显示数据
//                viewPager.setAdapter(adapter);
            //显示对应的“小圆圈”
//                circlePageIndicator.setViewPager(viewPager);
            //修改显示的产品的年利率

            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片uri集合
            List<String> imageUris = new ArrayList<String>();
            for (int i = 0; i < images.size(); i++) {
                imageUris.add(images.get(i).IMAURL);
            }

            banner.setImages(imageUris);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.CubeOut);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = {"硅谷金融金秋加息2%", "乐享活180天计划", "超级新手计划升级版", "FASHION安心钱包计划"};
            banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            banner.start();


            tvHomeYearrate.setText(index.product.yearRate + "%");
            //初始化当前的进度
            currentProgress = Integer.parseInt(index.product.progress);
            LogUtil.e("currentProgress==" + currentProgress);

//                rpHomeProgress.postInvalidate();
            //让当前的圆形进度条的进度动态的加载显示
            new Thread() {
                public void run() {
                    rpHomeProgress.setMax(100);//设置最大进度
                    rpHomeProgress.setProgress(0);//设置为进度为0
                    for(int i = 0; i < currentProgress; i++) {
                        rpHomeProgress.setProgress(rpHomeProgress.getProgress() + 1);
                        SystemClock.sleep(30);
                        rpHomeProgress.postInvalidate();//强制重绘
                    }
                }
            }.start();

        }

    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //联网加载图片
            Picasso.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("首页");
    }
}
