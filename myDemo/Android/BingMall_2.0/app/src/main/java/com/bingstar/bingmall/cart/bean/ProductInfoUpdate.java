package com.bingstar.bingmall.cart.bean;

/**
 * Created by qianhechen on 17/3/5.
 */

public class ProductInfoUpdate {
    private String memberId;
    private ProductInfoAdd productInfo;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public ProductInfoAdd getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoAdd productInfo) {
        this.productInfo = productInfo;
    }
}
