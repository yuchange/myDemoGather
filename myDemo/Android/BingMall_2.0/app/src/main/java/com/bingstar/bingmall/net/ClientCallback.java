package com.bingstar.bingmall.net;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 上午9:54
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 上午9:54
 * @modify by reason:{方法名}:{原因}
 */
public interface ClientCallback<T>{
    void onSuccess(T t);

    void onFail(int code ,String error);
}
