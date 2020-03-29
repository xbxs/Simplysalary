package com.example.atry.simplysalary.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 李维: TZZ on 2020-01-01 19:08
 * 邮箱: 3182430026@qq.com
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //是否拦截事件 false:不拦截事件 true:孩子无法处理事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
    //是否消费事件，不消费就传给父控件,事件的传递主要有 viewgroup传递给view,如果view不处理，则上一层布局将会处理
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
