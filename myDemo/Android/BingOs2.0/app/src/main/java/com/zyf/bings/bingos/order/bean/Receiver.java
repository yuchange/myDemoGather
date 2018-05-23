package com.zyf.bings.bingos.order.bean;

import java.io.Serializable;

/**
 * Created by wangshiqi on 2017/9/30.
 */

public class Receiver implements Serializable {
    private String id;
    private String memberId;
    private String name;
    private String address;
    private String mobile;
    private String phone;
    private String region;
    private String buyer_IDcard;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBuyer_IDcard() {
        return buyer_IDcard;
    }

    public void setBuyer_IDcard(String buyer_IDcard) {
        this.buyer_IDcard = buyer_IDcard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", region='" + region + '\'' +
                ", buyer_IDcard='" + buyer_IDcard + '\'' +
                '}';
    }
}
