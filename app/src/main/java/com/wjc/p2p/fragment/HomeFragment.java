package com.wjc.p2p.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.wjc.p2p.R;
import com.wjc.p2p.bean.Image;
import com.wjc.p2p.bean.Index;
import com.wjc.p2p.bean.Product;
import com.wjc.p2p.common.AppNetConfig;
import com.wjc.p2p.uitls.LogUtil;
import com.wjc.p2p.uitls.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.wjc.p2p.R.id.tv_home_yearrate;

;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：
 */

public class HomeFragment extends Fragment {

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
    @Bind(tv_home_yearrate)
    TextView tvHomeYearrate;
    @Bind(R.id.btn_home_join)
    Button btnHomeJoin;

//
//    @Bind(R.id.iv_top_back)
//    ImageView ivTopBack;
//    @Bind(R.id.tv_top_title)
//    TextView tvTopTitle;
//    @Bind(R.id.iv_top_settings)
//    ImageView ivTopSettings;
//    @Bind(R.id.vp_home)
//    ViewPager viewPager;
//    @Bind(R.id.tv_home_yearrate)
//    TextView tv_home_yearrate;
//    @Bind(R.id.circle_home_progress)
//    CirclePageIndicator circlePageIndicator;

    private Index index;
//    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = UIUtils.getXmlView(R.layout.fragment_home);
        ButterKnife.bind(this, view);

        initTitleBar();

        initData();

        return view;
    }

    private void initData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(AppNetConfig.INDEX, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                LogUtil.e("联网成功---" + content);
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
                for(int i = 0; i < images.size(); i++) {
                    imageUris.add(images.get(i).IMAURL);
                }

                banner.setImages(imageUris);
                //设置banner动画效果
                banner.setBannerAnimation(Transformer.CubeOut);
                //设置标题集合（当banner样式有显示title时）
                String[] titles = {"硅谷金融金秋加息2%","乐享活180天计划","超级新手计划升级版","FASHION安心钱包计划"};
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

            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                LogUtil.e("联网失败---" + content);
            }
        });
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //联网加载图片
            Picasso.with(context).load((String)path).into(imageView);
        }
    }

    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("首页");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

//    class MyAdapter extends PagerAdapter{
//
//        @Override
//        public int getCount() {//获取集合数据的大小
//            return index.images != null ? index.images.size() : 0;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        //装配图片数据，并返回
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//
//            ImageView imageView = new ImageView(getActivity());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            //根据图片的url，联网获取图片数据，装载到imageView对象上
//            //使用Picasso实现图片数据的下载
//            String imaurl = index.images.get(position).IMAURL;
//            Picasso.with(getActivity()).load(imaurl).into(imageView);
//            //把imageview添加到容器中
//            container.addView(imageView);
//
//            LogUtil.e("imageView---->" + imageView);
//
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }
}
