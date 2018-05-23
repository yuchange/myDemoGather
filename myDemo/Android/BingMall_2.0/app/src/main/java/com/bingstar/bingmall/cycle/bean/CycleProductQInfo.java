package com.bingstar.bingmall.cycle.bean;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianhechen on 17/2/7.
 */

public class CycleProductQInfo implements Serializable {

    private ArrayList<CycleProductQ> cycleProductQList;

    public ArrayList<CycleProductQ> getCycleProductQList() {
        return cycleProductQList;
    }

    public void setCycleProductQList(ArrayList<CycleProductQ> cycleProductQList) {
        this.cycleProductQList = cycleProductQList;
    }

    public CycleProductQ getInfo() {
        return new CycleProductQ();
    }

    public class CycleProductQ {
        private String categoryId;
        private List<CycleProductInfoQDetail> cycleProductQInfoList;
        private String cycleProductQCount;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<CycleProductInfoQDetail> getCycleProductQInfoList() {
            return cycleProductQInfoList;
        }

        public void setCycleProductQInfoList(List<CycleProductInfoQDetail> cycleProductQInfoList) {
            this.cycleProductQInfoList = cycleProductQInfoList;
        }

        public String getCycleProductQCount() {
            return cycleProductQCount;
        }

        public void setCycleProductQCount(String cycleProductQCount) {
            this.cycleProductQCount = cycleProductQCount;
        }

        public CycleProductInfoQDetail getInfo() {
            return new CycleProductInfoQDetail();
        }

        public class CycleProductInfoQDetail implements Serializable {

            private String productId;
            private String picId;
            private String productName;
            private String picUrl;
            private String picType;
            private String price;
            private String totalPrice;
            private String grossWeight;
            private String goodsCat;
            private String productType;
            private String number;
            private String postFee;
            private String taxFee;

            private String productCode;

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getProductId() {
                return productId;
            }

            public String getPicId() {
                return picId;
            }

            public String getProductName() {
                return productName;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public String getPicType() {
                return picType;
            }

            public String getPrice() {
                return price;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public String getGrossWeight() {
                return grossWeight;
            }

            public String getGoodsCat() {
                return goodsCat;
            }

            public String getProductType() {
                return productType;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPostFee() {
                return postFee;
            }

            public String getTaxFee() {
                return taxFee;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public void setPicType(String picType) {
                this.picType = picType;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public void setGrossWeight(String grossWeight) {
                this.grossWeight = grossWeight;
            }

            public void setGoodsCat(String goodsCat) {
                this.goodsCat = goodsCat;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public void setPostFee(String postFee) {
                this.postFee = postFee;
            }

            public void setTaxFee(String taxFee) {
                this.taxFee = taxFee;
            }
        }
    }
}
