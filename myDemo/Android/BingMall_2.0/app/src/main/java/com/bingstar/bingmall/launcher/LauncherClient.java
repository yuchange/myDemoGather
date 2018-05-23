package com.bingstar.bingmall.launcher;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.user.bean.UserStates;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/31 下午6:20
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/31 下午6:20
 * @modify by reason:{方法名}:{原因}
 */

public class LauncherClient {
    private static final String ZORDER_NUMBER_QUERY_METHOD = "zorder.number.query";
    private static final String ZORDER_NUMBER_QUERY_URL = "/zorderNumberQuery.shtml";

    public static void getOrderNum(String memberId, final ClientCallback<OrderNumBean> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UserStates.MEMBER_ID,memberId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ZORDER_NUMBER_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(jsonObject));
        BingstarNet.doPost(ZORDER_NUMBER_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                OrderNumInfo orderNumInfo = JSON.parseObject(str, OrderNumInfo.class);
                OrderNumBean orderNumBean = BeanTranslater.infoToBean(orderNumInfo);
                if (orderNumInfo != null) {
                    callback.onSuccess(orderNumBean);
                }else {
                    callback.onFail(0, null);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }
}
