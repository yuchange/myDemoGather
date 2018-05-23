package com.yunzhi.lib.view;

import android.view.View;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/22 上午9:23
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/22 上午9:23
 * @modify by reason:{方法名}:{原因}
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
