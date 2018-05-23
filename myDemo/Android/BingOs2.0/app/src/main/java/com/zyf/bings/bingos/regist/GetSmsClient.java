package com.zyf.bings.bingos.regist;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;

import java.util.Map;


/**
 * Created by wangshiqi on 2017/8/24.
 */

public class GetSmsClient {
    private static final String MEMBER_UPDATE_METHOD = "member.update";
//    private static final String AD_INFO_QUERY_METHOD = "member.loginOut";
    private static final String MEMBER_UPDATE_URL = "http://192.168.1.138:8099/bingstar-mobile/memberUpdate.shtml";
//    private static final String AD_INFO_QUERY_URL = "/loginOut.shtml";
    public static void getInfo(Map mMap, final SmsClientCallback clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, MEMBER_UPDATE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, mMap.toString());
        OkGoUtils.doStringPostRequest(map, MEMBER_UPDATE_URL, "GetSmsClient", new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                Log.d("GetSmsClient", str);
                if (!TextUtils.isEmpty(str)) {
                    clientCallback.onSuccess();
                } else {
                    clientCallback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                clientCallback.onFail(code, error);
            }
        });

//                Log.d("GetSmsClient", str);
////                Bean Infos = JSON.parseObject(str, Bean.class);
//


//


    }
}
