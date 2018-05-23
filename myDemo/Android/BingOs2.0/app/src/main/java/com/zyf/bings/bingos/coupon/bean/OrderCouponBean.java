package com.zyf.bings.bingos.coupon.bean;

import java.util.List;

/**
 * Created by wangshiqi on 2017/11/3.
 */

public class OrderCouponBean {

    private List<CouponInfoListBean> couponInfoList;

    public List<CouponInfoListBean> getCouponInfoList() {
        return couponInfoList;
    }

    public void setCouponInfoList(List<CouponInfoListBean> couponInfoList) {
        this.couponInfoList = couponInfoList;
    }

    public static class CouponInfoListBean {
        /**
         * couponValidity : 2017.11.24 00:00
         * couponName : 新人礼包
         * couponMoney : 50
         * remindText : 20天后过期
         * couponId : 238
         * remindFlag : 0
         * couponRange : 部分商品使用
         * couponDetail : 无门槛使用
         */

        private String couponValidity;
        private String couponName;
        private String couponMoney;
        private String remindText;
        private String couponId;
        private String remindFlag;
        private String couponRange;
        private String couponDetail;

        public String getCouponValidity() {
            return couponValidity;
        }

        public void setCouponValidity(String couponValidity) {
            this.couponValidity = couponValidity;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getRemindText() {
            return remindText;
        }

        public void setRemindText(String remindText) {
            this.remindText = remindText;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getRemindFlag() {
            return remindFlag;
        }

        public void setRemindFlag(String remindFlag) {
            this.remindFlag = remindFlag;
        }

        public String getCouponRange() {
            return couponRange;
        }

        public void setCouponRange(String couponRange) {
            this.couponRange = couponRange;
        }

        public String getCouponDetail() {
            return couponDetail;
        }

        public void setCouponDetail(String couponDetail) {
            this.couponDetail = couponDetail;
        }
    }
}
