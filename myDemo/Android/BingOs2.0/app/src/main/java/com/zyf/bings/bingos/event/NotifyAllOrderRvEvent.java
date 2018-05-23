package com.zyf.bings.bingos.event;

/**
 * Created by Administrator on 2017/10/17.
 */

public class NotifyAllOrderRvEvent {
    private String orderId;

    public NotifyAllOrderRvEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
