package com.bingstar.bingmall.cycle.bean;

import com.bingstar.bingmall.ads.bean.AdsInfoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by qianhechen on 17/2/7.
 */

public class CycleProductInfo implements Serializable {

    private ArrayList<CycleProduct> cycleProductList;

    public ArrayList<CycleProduct> getCycleProductList() {
        return cycleProductList;
    }

    public void setCycleProductList(ArrayList<CycleProduct> cycleProductList) {
        this.cycleProductList = cycleProductList;
    }

    public class CycleProduct implements Serializable {
        private String categoryId;
        private List<CycleProductInfoDetail> cycleProductInfoList;
        private String cycleProductCount;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<CycleProductInfoDetail> getCycleProductInfoList() {
            return cycleProductInfoList;
        }

        public void setCycleProductInfoList(List<CycleProductInfoDetail> cycleProductInfoList) {
            this.cycleProductInfoList = cycleProductInfoList;
        }

        public String getCycleProductCount() {
            return cycleProductCount;
        }

        public void setCycleProductCount(String cycleProductCount) {
            this.cycleProductCount = cycleProductCount;
        }

        public void sortList(){
            Comparator<CycleProductInfoDetail> comp = new SortComparator();
            Collections.sort(cycleProductInfoList,comp);
        }
        protected class SortComparator implements Comparator<CycleProductInfoDetail> {
            @Override
            public int compare(CycleProductInfoDetail o1, CycleProductInfoDetail o2) {
                try {
                    return -(Integer.parseInt(o1.getCount()) - Integer.parseInt(o2.getCount()));
                }catch (Exception e){
                    return 0;
                }
            }
        }

        public class CycleProductInfoDetail implements Serializable {
            private String productId;
            private String picId;
            private String productName;
            private String picUrl;
            private String picType;
            private String price;
            private String grossWeight;
            private String weight;
            private String goodsCat;
            private String productType;
            private String count;
            private String number;
            private String productCode;
            private String unit;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

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

            public String getGrossWeight() {
                return grossWeight;
            }

            public String getGoodsCat() {
                return goodsCat;
            }

            public String getProductType() {
                return productType;
            }

            public String getCount() {
                return count;
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

            public void setGrossWeight(String grossWeight) {
                this.grossWeight = grossWeight;
            }

            public void setGoodsCat(String goodsCat) {
                this.goodsCat = goodsCat;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

        }
    }
}
