package com.bingstar.bingmall.main.bean;

import java.util.List;

/**
 * Created by zhangyifei on 17/6/28.
 */

public class CouponInfo {

    private List<CouponInfoItem> couponInfos;

    public List<CouponInfoItem> getCouponInfo() {
        return couponInfos;
    }

    public void setCouponInfo(List<CouponInfoItem> couponInfos) {
        this.couponInfos = couponInfos;
    }

    public class CouponInfoItem {
        private String memberId;

        private String couponId;

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getMemberId() {

            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
    }
}
