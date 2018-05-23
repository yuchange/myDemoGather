package com.zyf.bings.bingos.goods.bean;

/**
 * Created by zhangyifei on 17/9/15.
 */

public class OrderConfirmBean {
    MemberAddress memberAddress;

    CouponInfo coupons;

    public MemberAddress getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(MemberAddress memberAddress) {
        this.memberAddress = memberAddress;
    }

    public CouponInfo getCouponInfo() {
        return coupons;
    }

    public void setCouponInfo(CouponInfo couponInfo) {
        this.coupons = couponInfo;
    }

    public static class MemberAddress {
        public String addressid;
        public String member_id;
        public String name;
        public String idCard;
        public String mobile;
        public String region;
        public String detailed;
        public String used;
        public String remark;

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDetailed() {
            return detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class CouponInfo {

        public String couponId;
        public String couponMoney;
        public String couponName;
        public String couponDetail;
        public String couponValidity;
        public String couponRange;

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

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponDetail() {
            return couponDetail;
        }

        public void setCouponDetail(String couponDetail) {
            this.couponDetail = couponDetail;
        }

        public String getCouponValidity() {
            return couponValidity;
        }

        public void setCouponValidity(String couponValidity) {
            this.couponValidity = couponValidity;
        }

        public String getCouponRange() {
            return couponRange;
        }

        public void setCouponRange(String couponRange) {
            this.couponRange = couponRange;
        }
    }
}

