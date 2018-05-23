package com.zyf.bings.bingos.event;

/**
 * Created by wangshiqi on 2017/11/3.
 */

public class OrderCouponEvent {
    String couponId;
    String couponMoney;

    public OrderCouponEvent(String couponId, String couponMoney) {
        this.couponId = couponId;
        this.couponMoney = couponMoney;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }
}
