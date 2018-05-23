package com.zyf.bings.bingos.coupon.bean;

import java.util.List;

/**
 *
 * @author wangshiqi
 * @date 2017/10/27
 */

public class CouponListBean {


    private List<CouponInfoListBean> couponInfoList;

    public List<CouponInfoListBean> getCouponInfoList() {
        return couponInfoList;
    }

    public void setCouponInfoList(List<CouponInfoListBean> couponInfoList) {
        this.couponInfoList = couponInfoList;
    }

    public static class CouponInfoListBean {
        /**
         * couponName : 王世琦
         * couponValidity : 2017.10.27 13:00-2017.10.28 13:00
         * couponType : 1
         * couponMoney : 5
         * state : null
         * residual : null
         * couponId : 318
         * couponNum : 2
         * conditions : null
         * id : null
         * limitAcount : null
         * circulation : null
         * usageAmount : null
         * couponDetail : 满20元使用
         * couponRange : 全场使用
         */

        private String couponName;
        private String couponValidity;
        private String couponType;
        private String couponMoney;
        private Object state;
        private Object residual;
        private String couponId;
        private String couponNum;
        private Object conditions;
        private Object id;
        private Object limitAcount;
        private Object circulation;
        private Object usageAmount;
        private String couponDetail;
        private String couponRange;
        private String useDateString;

        public String getUseDateString() {
            return useDateString;
        }

        public void setUseDateString(String useDateString) {
            this.useDateString = useDateString;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponValidity() {
            return couponValidity;
        }

        public void setCouponValidity(String couponValidity) {
            this.couponValidity = couponValidity;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getResidual() {
            return residual;
        }

        public void setResidual(Object residual) {
            this.residual = residual;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(String couponNum) {
            this.couponNum = couponNum;
        }

        public Object getConditions() {
            return conditions;
        }

        public void setConditions(Object conditions) {
            this.conditions = conditions;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getLimitAcount() {
            return limitAcount;
        }

        public void setLimitAcount(Object limitAcount) {
            this.limitAcount = limitAcount;
        }

        public Object getCirculation() {
            return circulation;
        }

        public void setCirculation(Object circulation) {
            this.circulation = circulation;
        }

        public Object getUsageAmount() {
            return usageAmount;
        }

        public void setUsageAmount(Object usageAmount) {
            this.usageAmount = usageAmount;
        }

        public String getCouponDetail() {
            return couponDetail;
        }

        public void setCouponDetail(String couponDetail) {
            this.couponDetail = couponDetail;
        }

        public String getCouponRange() {
            return couponRange;
        }

        public void setCouponRange(String couponRange) {
            this.couponRange = couponRange;
        }
    }
}
