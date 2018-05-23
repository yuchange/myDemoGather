package com.zyf.bings.bingos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/9/27.
 */

public class ScrollBottomScrollView extends ScrollView {


    private ScrollBottomListener scrollBottomListener;

    public ScrollBottomScrollView(Context context) {
        super(context);
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("Scroo", "l  " + l);
        Log.i("Scroo", "oldl   " + l);
        Log.i("Scroo", "t   " + t);
        Log.i("Scroo", "oldt    " + oldt);
//        if (t + getHeight() >= computeVerticalScrollRange()) {
//            //ScrollView滑动到底部了
//            scrollBottomListener.scrollBottom(0, 0);
       // }

            scrollBottomListener.scrollBottom(t, oldt);

    }


    public void setScrollBottomListener(ScrollBottomListener scrollBottomListener) {
        this.scrollBottomListener = scrollBottomListener;
    }

    public interface ScrollBottomListener {
        public void scrollBottom(int t, int oldt);
    }
}
