package com.zyf.bings.bingos.regist;

/**
 * Created by wangshiqi on 2017/8/30.
 */

public interface SmsClientCallback {
    void onSuccess();

    void onFail(int code, String error);
}
