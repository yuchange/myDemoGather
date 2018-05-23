package com.bingstar.bingmall.goods.bean;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/21 下午3:03
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/21 下午3:03
 * @modify by reason:{方法名}:{原因}
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

        private String totalCount;

        public List<ProductInfoDetail> getProductInfoList() {
            return productInfoList;
        }

        public void setProductInfoList(List<ProductInfoDetail> productInfoList) {
            this.productInfoList = productInfoList;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
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
