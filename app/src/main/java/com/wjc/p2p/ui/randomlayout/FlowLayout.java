package com.wjc.p2p.ui.randomlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wjc.p2p.uitls.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${万嘉诚} on 2016/11/16.
 * WeChat：wjc398556712
 * Function：自定义流式布局
 */

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取宽度和高度的模式和数值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //声明当前视图的宽高，如果是至多模式，需要计算出此两个变量的值
        int width = 0;
        int height = 0;

        //声明一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        //获取子视图的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //获取每一个子视图的宽度和高度，边距值
            View childView = getChildAt(i);
            //需要调用如下的方法之后，才可以获取子视图的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //获取测量的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //获取边距(要想获取边距，必须重写当前类的方法generateLayoutParams())
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= widthSize) {//不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.bottomMargin + mp.topMargin);

            } else {//换行
                width = Math.max(width, lineWidth);
                height += lineHeight;

                //重置
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }

            //单独的考虑一下最后一个！
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }

        LogUtil.e("widthSize-----" + widthSize + "  heightSize-----" + heightSize);
        LogUtil.e("width-----" + width + "  height------" + heightSize);

        //设置当前布局的宽高
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);

    }

    private List<Integer> allHeights = new ArrayList<>();//每一行的高度构成的集合
    private List<List<View>> allViews = new ArrayList<>();//每一行view集合构成的集合

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getWidth();//获取布局的宽度

        //每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        List<View> lineViews = new ArrayList<>();//用于保存一行的所有的View
        //目的：给allHeights 和 allViews 赋值
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            //获取子视图测量的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= width) {//不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight,childHeight + mp.topMargin + mp.bottomMargin);

                lineViews.add(childView);
            } else {//换行
                allHeights.add(lineHeight);
                allViews.add(lineViews);

                lineViews = new ArrayList<>();
                lineViews.add(childView);
                //重新获取宽高
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if(i == childCount - 1) {
                allHeights.add(lineHeight);
                allViews.add(lineViews);
            }

        }

        int lineNumber = allViews.size();

        int x = 0;//每行里面子视图距离这一行第一个子视图的X轴偏移量
        int y = 0;//当前视图距离第一行视图的Y轴偏移量

        for(int i = 0; i < lineNumber; i++) {
            List<View> singleLineViews = allViews.get(i);//获取一行中元素构成的集合
            int singleLineHeight = allHeights.get(i);//获取一行的高度
            for (View singleLineView : singleLineViews) {//遍历一行元素
                MarginLayoutParams mp = (MarginLayoutParams) singleLineView.getLayoutParams();
                int left = x + mp.leftMargin;
                int top = y + mp.topMargin;
                int right = left + singleLineView.getMeasuredWidth();
                int bottom = top + singleLineView.getMeasuredHeight();

                singleLineView.layout(left,top,right,bottom);

                x += singleLineView.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;

            }
            //换行
            x = 0;
            y += singleLineHeight;
        }


    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }
}
