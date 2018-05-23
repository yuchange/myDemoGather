package com.bingstar.bingmall.user.addr.AddrManage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John on 2017/3/6.
 */

public class AddrManageInfoBean implements Serializable{
    private List<MemberAddress> memberAddressList;

    public List<MemberAddress> getMemberAddressList() {
        return memberAddressList;
    }

    public void setMemberAddressList(List<MemberAddress> memberAddressList) {
        this.memberAddressList = memberAddressList;
    }

    public MemberAddress getMemberAddress(){
        return new MemberAddress();
    }

    public class MemberAddress implements Serializable{

        private String addressid;

        private String member_id;

        private String name;

        private String idCard;

        private String mobile;

        private String region;

        private String detailed;

        private String used;

        private String remark;

        private String flug; // 判断：修改？新增？查看

        private  boolean flugBoolean;

        public boolean isFlugBoolean() {
            return flugBoolean;
        }

        public void setFlugBoolean(boolean flugBoolean) {
            this.flugBoolean = flugBoolean;
        }

        public String getFlug() {
            return flug;
        }

        public void setFlug(String flug) {
            this.flug = flug;
        }

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
}
