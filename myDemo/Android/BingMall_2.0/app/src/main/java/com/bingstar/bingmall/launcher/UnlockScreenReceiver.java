package com.bingstar.bingmall.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 功能：
 * Created by yaoyafeng on 17/6/20 下午2:33
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/6/20 下午2:33
 * @modify by reason:{方法名}:{原因}
 */

public class UnlockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
            //Log.e("asdad", "screen_on");
            Log.e("zuoXiaoTang2323", "onReceive: " + "收到广播");
        }
    }
}
