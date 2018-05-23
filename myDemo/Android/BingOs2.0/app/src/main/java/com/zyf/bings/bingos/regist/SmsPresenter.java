package com.zyf.bings.bingos.regist;


import android.support.v4.util.ArrayMap;

import com.zyf.bings.bingos.utils.CommonUtils;

import java.util.Map;

/**
 * Created by wangshiqi on 2017/8/24.
 */

public class SmsPresenter implements ISmsContract.SmsPresenter {
    private ISmsContract.SmsView smsView;

    public SmsPresenter(ISmsContract.SmsView smsView) {
        this.smsView = smsView;
    }

    @Override
    public void unBind() {
        smsView = null;
    }

    @Override
    public void getSmsCode(String phoneNumber) {

    }

    @Override
    public void regist(String phoneNum) {
        // 验证跳转逻辑
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("member_id", "1");
//        jsonObject.put("mac_id",  CommonUtils.getWifiMac());
//        jsonObject.put("phone_tel", phoneNum);
        Map<String, String> map = new ArrayMap<>();
        map.put("member_id", "1");
        map.put("mac_id", CommonUtils.getWifiMac());
        map.put("phone_tel", phoneNum);
        GetSmsClient.getInfo(map, new SmsClientCallback() {
            @Override
            public void onSuccess() {
                smsView.registSuccess();
            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }
}
