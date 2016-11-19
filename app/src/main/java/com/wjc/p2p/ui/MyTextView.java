package com.wjc.p2p.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 文字跑马灯实现二，自定义TextView,重写isFocused()，保证获取焦点
 */
public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写isFocused()，保证获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
