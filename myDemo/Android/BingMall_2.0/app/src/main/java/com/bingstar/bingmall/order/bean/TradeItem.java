package com.bingstar.bingmall.order.bean;

import java.io.Serializable;

/**
 * Created by John on 2017/3/7.
 */

public class TradeItem implements Serializable{
    private String bsjProductId;
    private String bsjProductCode;
    private String bsjProductName;
    private String number;
    private String totalFee;
    private String discountFee;
    private String price;
    private String grossWeight;

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBsjProductId() {
        return bsjProductId;
    }

    public void setBsjProductId(String bsjProductId) {
        this.bsjProductId = bsjProductId;
    }

    public String getBsjProductCode() {
        return bsjProductCode;
    }

    public void setBsjProductCode(String bsjProductCode) {
        this.bsjProductCode = bsjProductCode;
    }

    public String getBsjProductName() {
        return bsjProductName;
    }

    public void setBsjProductName(String bsjProductName) {
        this.bsjProductName = bsjProductName;
    }


    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

}