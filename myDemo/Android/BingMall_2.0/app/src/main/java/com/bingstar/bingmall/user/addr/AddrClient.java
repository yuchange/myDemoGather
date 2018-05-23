package com.bingstar.bingmall.user.addr;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * Created by liumengqiang on 2017/2/27.
 */

public class AddrClient {
    /**
     * 配送城市区域查询
     */
    private static final String ADDR_TITLE = "/deliveryCityQuery.shtml";
    private static final String ADDR_METHOD = "delivery.city.query";
    public static void getCityId(final ClientCallback<AddrInfo> callback){
        Map<String , String > map = new ArrayMap<String , String >();
        map.put(BingNetStates.METHOD, ADDR_METHOD);
        BingstarNet.doPost(ADDR_TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                AddrInfo addrInfo = JSON.parseObject(str, AddrInfo.class);
                if(addrInfo != null && addrInfo.getProcList() != null){
                    callback.onSuccess(addrInfo);
                }else{
                    callback.onFail(BingNetStates.DATA_ERROR,"date_error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code , error);
            }
        });
    }
}
