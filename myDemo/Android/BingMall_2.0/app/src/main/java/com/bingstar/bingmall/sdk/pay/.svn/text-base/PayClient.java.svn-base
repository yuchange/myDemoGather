package com.bingstar.bingmall.sdk.pay;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.main.bean.PayEvent;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.sdk.pay.alipay.PayState;
import com.bingstar.bingmall.sdk.pay.alipay.PayInfo;
import com.bingstar.bingmall.sdk.pay.haier.HaierPayInfo;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.bean.UserStates;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 下午4:55
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 下午4:55
 * @modify by reason:{方法名}:{原因}
 */

public class PayClient {

    private static final String ALIPAY_URL = "/alipayInfoQuery.shtml";
    private static final String ALIPAY_METHOD = "alipay.info.query";

    private static final String HAIERPAY_URL = "/haierpayListQuery.shtml";
    private static final String HAIERPAY_METHOD = "haierpay.list.query";


    public static void getAliPayMessage(String zorder_no, final ClientCallback<String> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PayState.ZORDER_NO, zorder_no);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ALIPAY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        BingstarNet.doPost(ALIPAY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                PayInfo payInfo = JSON.parseObject(str,PayInfo.class);
                if (payInfo!=null) {
                    callback.onSuccess(payInfo.getSignReturn());
                }else {
                    callback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }


            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }

    public static void getHaierPayMessage(String zorder_no,final ClientCallback<HaierPayInfo> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PayState.ZORDER_NO, zorder_no);
        jsonObject.put(UserStates.MEMBER_ID, User.getIntance().getMemberId());
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, HAIERPAY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        BingstarNet.doPost(HAIERPAY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                HaierPayInfo payInfo = JSON.parseObject(str,HaierPayInfo.class);
                if (payInfo!=null) {
                    callback.onSuccess(payInfo);
                }else {
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }


            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }
}
