package com.bingstar.bingmall.base.http;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */

public class WebActivityCliet {

    public static final String CUPONS_ID = "cuponsId";
    public static final String CUPONS_METHOD = "banner.coupon";
    private static final String CUPONS_URL = "/bannerCoupon.shtml";

    public static void getCuponsState(String data, final ClientCallback<String> clinentCallback) {
        Map<String, String> map = new ArrayMap<String, String>();
        map.put(BingNetStates.METHOD, CUPONS_METHOD);
        map.put(BingNetStates.REQUEST_DATA, data);

        BingstarNet.doPost(CUPONS_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                if (str != null) {
                    clinentCallback.onSuccess(str);
                } else {
                    clinentCallback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);

                }


            }

            @Override
            public void onFail(int code, String error) {
                clinentCallback.onFail(code, error);
            }
        });


    }


}
