package com.zyf.bings.bingos.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/10/31.
 */


@SuppressLint("AppCompatCustomView")
public class NoClickTextView extends TextView {


    public NoClickTextView(Context context) {
        super(context);
    }

    public NoClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
