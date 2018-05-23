package com.bingstar.bingmall.user.bean;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.sdk.map.LatLng;
import com.bingstar.bingmall.user.addr.AddrStates;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/30 下午1:43
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/30 下午1:43
 * @modify by reason:{方法名}:{原因}
 */

public class UserClient {

    private static final String MEMBER_ADD_METHOD = "member.add";
    private static final String MEMBER_ADD_URL = "/memberAdd.shtml";
    private static final String ONLINE_COUNT_METHOD = "online.count";
    private static final String ONLINE_COUNT_URL = "/onlineCount.shtml";

    public static void setMember(String mac, String memberId, final ClientCallback<String> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UserStates.MAC_ID, mac);
        jsonObject.put(UserStates.MEMBER_ID, memberId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, MEMBER_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(jsonObject));
        BingstarNet.doPost(MEMBER_ADD_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                callback.onSuccess("");
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void setFridge(String mac, String memberId, String type, LatLng latLng, final ClientCallback<String> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UserStates.MAC_ID, mac);
        jsonObject.put(UserStates.MEMBER_ID, memberId);
        jsonObject.put(UserStates.TYPE, type);
        if (latLng != null) {
            jsonObject.put(AddrStates.LATITUDE, latLng.getLatitude() + "");
            jsonObject.put(AddrStates.LONGITUDE, latLng.getLongitude() + "");
        } else {
            jsonObject.put(AddrStates.LATITUDE, "");
            jsonObject.put(AddrStates.LONGITUDE, "");
        }
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ONLINE_COUNT_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(jsonObject));
        BingstarNet.doPost(ONLINE_COUNT_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                callback.onSuccess("");
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }
}
