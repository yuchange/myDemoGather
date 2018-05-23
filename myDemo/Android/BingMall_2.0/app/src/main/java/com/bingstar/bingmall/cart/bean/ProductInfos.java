package com.bingstar.bingmall.cart.bean;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/2/21.
 */

public class ProductInfos {
    private String totalCount;
    private ArrayList<ProductInfoAdd> productInfoList;
    private ProductInfoAdd productInfo;

    public ProductInfoAdd getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoAdd productInfo) {
        this.productInfo = productInfo;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<ProductInfoAdd> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(ArrayList<ProductInfoAdd> productInfoList) {
        this.productInfoList = productInfoList;
    }





}

