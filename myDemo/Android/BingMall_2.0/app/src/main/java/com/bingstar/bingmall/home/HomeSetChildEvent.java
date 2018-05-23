package com.bingstar.bingmall.home;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/7 下午3:14
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/7 下午3:14
 * @modify by reason:{方法名}:{原因}
 */

public class HomeSetChildEvent {

    private int type;

    public HomeSetChildEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
