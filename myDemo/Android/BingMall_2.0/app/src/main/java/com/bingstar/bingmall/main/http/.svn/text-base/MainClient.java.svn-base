package com.bingstar.bingmall.main.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.main.bean.ActivePage;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;

import java.util.Map;

/**
 * Created by zhangyifei on 17/6/28.
 */

public class MainClient {

    //优惠卷领取 coupon.receive
    private static final String COUPON_ACQUIRE = "/couponReceive.shtml";
    private static final String COUPON_ACQUIRE_METHOD = "coupon.receive";

    //优惠卷过期提醒
    private static final String COUPON_REMIND = "/couponOverdueRemind.shtml";
    private static final String COUPON_REMIND_METHOD = "coupon.overdue.remind";

    private static final String COUPON_ACTIVEPAGE_METHOD = "active.page.get";
    private static final String COUPON_ACTIVEPAGE = "/activePageGet.shtml";


    public static void acquireCoupon(Map<String, String> params, final ClientCallback<String> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_ACQUIRE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(COUPON_ACQUIRE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                //0：领取成功；1: 该优惠券已领取 2：该优惠券已抢光
                callback.onSuccess(str);
            }


            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }


    public static void getCouponActive(Map<String, String> params, final ClientCallback<ActivePage> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_ACTIVEPAGE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(COUPON_ACTIVEPAGE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                //0：领取成功；1: 该优惠券已领取 2：该优惠券已抢光
                ActivePage activePage = JSON.parseObject(str, ActivePage.class);
                if (activePage != null) {
                    callback.onSuccess(activePage);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }


            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }


    public static void remindCoupon(Map<String, String> params, final ClientCallback<String> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_REMIND_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(COUPON_REMIND, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                try {
                    JSONObject jsonObject = JSON.parseObject(str);
                    String flag = jsonObject.getString("flag");
                    callback.onSuccess(flag);
                } catch (Exception e) {

                }
            }


            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }
}
