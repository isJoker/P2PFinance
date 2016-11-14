package com.wjc.p2p.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.wjc.p2p.uitls.LogUtil;

/**
 * Created by ${万嘉诚} on 2016/11/13.
 * WeChat：wjc398556712
 * Function：自定义滚动视图
 */

public class MyScrollView extends ScrollView {
    private View childView;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //获取内部的子视图
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    private int lastY;//记录上一次y坐标的位置
    private Rect normal = new Rect();//用于记录临界状态时的left,top,right,bottom的坐标
    private boolean isFinishAnimation = true;//判断是否结束了动画

    private int lastX;//记录上一次x坐标的位置
    private int downX,downY;//记录down事件时，x轴和y轴的坐标位置

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercept = false;
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = downX = eventX;
                lastY = downY = eventY;

                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = Math.abs(eventX - downX);
                int distanceY = Math.abs(eventY - downY);

                if (distanceY > distanceX && distanceY > 10) {
                    isIntercept = true;
                }
                lastX = eventX;
                lastY = eventY;

                break;
        }

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView == null || !isFinishAnimation) {
            return super.onTouchEvent(ev);
        }

        int eventY = (int) ev.getY();//获取相对于当前视图的y轴坐标

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = eventY;

                break;
            case MotionEvent.ACTION_MOVE:
                int distanceY = eventY - lastY;//获取移动的距离

                if (isNeedMove()) {
                    //记录在临界位置时的childView的坐标
                    if (normal.isEmpty()) {//未被赋值
                        normal.set(childView.getLeft(), childView.getTop(),
                                childView.getRight(), childView.getBottom());
                    }
                }

                //给ChildView重新布局
                childView.layout(childView.getLeft(), childView.getTop() + distanceY / 2,
                        childView.getRight(), childView.getBottom() + distanceY / 2);

                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    TranslateAnimation animation = new TranslateAnimation(0, 0, 0, normal.bottom - childView.getBottom());
                    animation.setDuration(300);
//                    animation.setFillAfter(true);//不能使用这种方式保证childView的最终位置。
                    childView.startAnimation((animation));

                    //设置动画的监听
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //清除动画
                            childView.clearAnimation();
                            //childView进行重新布局
                            childView.layout(normal.left, normal.top, normal.right, normal.bottom);
                            //置空normal
                            normal.setEmpty();

                            isFinishAnimation = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }


                break;
        }
        return super.onTouchEvent(ev);
    }

    //判断在up时，何时需要执行平移动画
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    //判断在何种情况下才需要按照我们的方式，给自定的ScrollView重新布局
    private boolean isNeedMove() {
        //获取子视图的测量高度
        int mesuredHeight = childView.getMeasuredHeight();
        int height = this.getHeight();//得到ScrollView在屏幕上占据的空间的高度
        int distanceHeight = mesuredHeight - height;
        LogUtil.e("distanceHeight=========" + distanceHeight);

        int scrollY = this.getScrollY();//获取子视图在y轴上的滚动量.特点：上加下减
        LogUtil.e("scrollY========" + scrollY);

        if (scrollY <= 0 || scrollY >= distanceHeight) {
            return true;
        }
        return false;
    }
}
