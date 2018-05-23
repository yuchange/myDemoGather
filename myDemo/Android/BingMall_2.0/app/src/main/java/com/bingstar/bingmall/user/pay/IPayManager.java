package com.bingstar.bingmall.user.pay;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 下午5:46
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 下午5:46
 * @modify by reason:{方法名}:{原因}
 */

public interface IPayManager {
    void pay(String orderInfo, PayCallback payCallback);
    interface PayCallback{
        void onSuccess();
        void onFail();
    }
}
