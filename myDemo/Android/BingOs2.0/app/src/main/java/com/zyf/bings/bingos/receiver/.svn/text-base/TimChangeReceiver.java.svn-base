package com.zyf.bings.bingos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zyf.bings.bingos.goods.events.TimeChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2016/12/23.
 * 时间变化的广播
 */
public class TimChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String time = getCurrentOsTime();
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

            EventBus.getDefault().post(new TimeChangeEvent());


        }

    }




    private String getCurrentOsTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }



}
