package com.zyf.bings.bingos.base;

import android.view.View;

/**
 * Created wangshiqi on 2017/9/11.
 */

public abstract class OnSingleClickListener implements View.OnClickListener {

    private long lastTime = 0;

    @Override
    public void onClick(View v) {
        final int TIME_SPACE = 1000;
        long currentTime = System.currentTimeMillis();
        if (currentTime-lastTime> TIME_SPACE){
            lastTime = System.currentTimeMillis();
            onSingleClick(v);
        }
    }

    public abstract void onSingleClick(View view);
}
