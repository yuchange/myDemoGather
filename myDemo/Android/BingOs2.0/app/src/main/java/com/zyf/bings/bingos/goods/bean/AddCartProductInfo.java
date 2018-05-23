package com.zyf.bings.bingos.goods.bean;

/**
 * Created by zhangyifei on 17/9/8.
 */

public class AddCartProductInfo {

    ProductInfo productInfo;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public static class ProductInfo {

        public String memberId;
        public String categoryId;
        public String productId;
        public String productCode;
        public String productName;
        public String picUrl;
        public String gross_Weight;
        public String weight;
        public String unit;
        public String price;
        public String count;
        public String totalPrice;
        public String specificationId;

        public String getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(String specificationId) {
            this.specificationId = specificationId;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getGross_Weight() {
            return gross_Weight;
        }

        public void setGross_Weight(String gross_Weight) {
            this.gross_Weight = gross_Weight;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "ProductInfo{" +
                    "memberId='" + memberId + '\'' +
                    ", categoryId='" + categoryId + '\'' +
                    ", productId='" + productId + '\'' +
                    ", productCode='" + productCode + '\'' +
                    ", productName='" + productName + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", gross_Weight='" + gross_Weight + '\'' +
                    ", weight='" + weight + '\'' +
                    ", unit='" + unit + '\'' +
                    ", price='" + price + '\'' +
                    ", count='" + count + '\'' +
                    ", totalPrice='" + totalPrice + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "AddCartProductInfo{" +
                "mProductInfo=" + productInfo +
                '}';
    }

}
