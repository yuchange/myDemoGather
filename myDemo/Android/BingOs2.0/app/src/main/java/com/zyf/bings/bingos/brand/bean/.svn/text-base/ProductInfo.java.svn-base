package com.zyf.bings.bingos.brand.bean;

import java.util.List;

/**
 * 功能：
 * Created by wangshiqi on 17/11/9
 *
 */

public class ProductInfo {
    private ProductInfors productInfo;

    public ProductInfors getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfors productInfo) {
        this.productInfo = productInfo;
    }

    public class ProductInfors{
        private List<ProductInfoDetail> productInfoList;

        private int totalCount;

        public List<ProductInfoDetail> getProductInfoList() {
            return productInfoList;
        }

        public void setProductInfoList(List<ProductInfoDetail> productInfoList) {
            this.productInfoList = productInfoList;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        @Override
        public String toString() {
            return "ProductInfors{" +
                    "productInfoList=" + productInfoList +
                    ", totalCount='" + totalCount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productInfo=" + productInfo +
                '}';
    }
}
