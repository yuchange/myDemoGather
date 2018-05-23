package com.bingstar.bingmall.user.pay;

import android.app.Activity;

import com.bingstar.bingmall.sdk.pay.haier.HaierPayActivity;
import com.bingstar.bingmall.user.pay.IPayManager;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/11 上午10:51
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/11 上午10:51
 * @modify by reason:{方法名}:{原因}
 */

public class HaierPayManager implements IPayManager {

    private Activity activity;

    public HaierPayManager(Activity activity){
        this.activity = activity;
    }

    @Override
    public void pay(String orderInfo, PayCallback payCallback) {
        HaierPayActivity.start(activity,orderInfo);
    }

}
