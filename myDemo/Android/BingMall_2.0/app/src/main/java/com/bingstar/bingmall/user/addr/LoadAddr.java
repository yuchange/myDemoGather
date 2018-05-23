package com.bingstar.bingmall.user.addr;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.base.DateUtils;
import com.bingstar.bingmall.base.SharedPreferencesUtils;
import com.bingstar.bingmall.net.ClientCallback;

/**
 * Created by John on 2017/3/2.
 */

public class LoadAddr {

    public static  void getValue(Context context){
        /**
         * 初始化城市地址
         */
        String cityInfo = SharedPreferencesUtils.getCITYString(context);
        if(cityInfo == null || cityInfo.equals("")){
//            数据为空(第一次登录，或者清空缓存)
            String beginTime = DateUtils.getTime();
            SharedPreferencesUtils.setCITYDate(context,beginTime);
            setCityData(context);
        }
        // 有数据 判断日期是否超过三十天
        String endTime = SharedPreferencesUtils.getCITYDate(context);
        if(30 < DateUtils.DateTime(Long.parseLong(endTime)+"")){
            setCityData(context);
        }
        String beginTime = DateUtils.getTime();
        SharedPreferencesUtils.setCITYDate(context,beginTime);
    }
    public static void setCityData( final Context context){
        AddrClient.getCityId(new ClientCallback<AddrInfo>() {
            @Override
            public void onSuccess(AddrInfo addrInfo) {
                String jsonString = JSON.toJSONString(addrInfo);
                SharedPreferencesUtils.setCITYString(context,jsonString);
            }

            @Override
            public void onFail(int code, String error) {
            }
        });
    }
}
