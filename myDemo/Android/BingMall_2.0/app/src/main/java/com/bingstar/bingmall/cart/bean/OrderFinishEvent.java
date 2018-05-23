package com.bingstar.bingmall.cart.bean;

/**
 * Created by zhangyifei on 17/7/13.
 */

public class OrderFinishEvent {

    public String flag;  //1 下单成功  2 下单失败

    public OrderFinishEvent(String flag) {
        this.flag = flag;
    }
}
