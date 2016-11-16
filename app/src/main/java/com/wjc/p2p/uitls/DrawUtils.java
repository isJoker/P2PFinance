package com.wjc.p2p.uitls;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by shkstart on 2016/9/24 0024.
 */
public class DrawUtils {

    public static GradientDrawable getDrawable(int rgb,float radius){
        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setColor(rgb);//设置颜色
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);//设置显示的样式
        gradientDrawable.setCornerRadius(radius);//设置圆角的半径
        gradientDrawable.setStroke(UIUtils.dp2Px(1),rgb);//描边

        return gradientDrawable;
    }

    public static StateListDrawable getSelector(Drawable normalDrawable,Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

}
