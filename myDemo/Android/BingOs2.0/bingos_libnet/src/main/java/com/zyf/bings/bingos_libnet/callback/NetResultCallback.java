package com.zyf.bings.bingos_libnet.callback;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/7 下午12:54
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/7 下午12:54
 * @modify by reason:{方法名}:{原因}
 */
public interface NetResultCallback {
    void onSuccess(String str);
    void onFail(int code, String error);
}
