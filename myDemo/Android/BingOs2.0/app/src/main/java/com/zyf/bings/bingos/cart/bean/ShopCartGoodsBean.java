package com.zyf.bings.bingos.cart.bean;

import java.util.List;

/**
 * Created by zhangyifei on 17/8/29.
 */

public class ShopCartGoodsBean {


    /**
     * productInfoList : [{"productType":"1","totalPrice":"250.00","if_show":"3","shopCartsId":"1459","stock":"50","memberId":"15869153840","categoryId":"38","count":"5","weight":"1","price":"50.00","productCode":"6523652300","productName":"婴儿被子","picUrl":"/uploadImage/2017/5/3/1493777088106.png","productId":"125","unit":"条","gross_Weight":"0.00","discountExplain":""}]
     * totalCount : 1
     */

    private String totalCount;
    private List<ProductInfoListBean> productInfoList;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<ProductInfoListBean> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfoListBean> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public interface UpdateNumCallback {
        void update();

        void rollback();
    }

    public static class ProductInfoListBean {
        /**
         * productType : 1
         * totalPrice : 250.00
         * if_show : 3
         * shopCartsId : 1459
         * stock : 50
         * memberId : 15869153840
         * categoryId : 38
         * count : 5
         * weight : 1
         * price : 50.00
         * productCode : 6523652300
         * productName : 婴儿被子
         * picUrl : /uploadImage/2017/5/3/1493777088106.png
         * productId : 125
         * unit : 条
         * gross_Weight : 0.00
         * discountExplain :
         */

        private String shopCartsId;
        private String memberId;//会员id
        private String categoryId;//分类id
        private String productId;//商品id
        private String productName;//商品名称
        private String picUrl;//商品图片
        private String price;//商品价格
        private String count;//商品数量
        private String totalPrice;//总价
        //    private String grossWeight;//商品重量
        private String unit;//单位
        private String productCode;//商品编号
        private String weight;
        private String post_money;
        private String gross_Weight;
        private String productType; // 1：完税商品 (境内商品) 2：保税商品 (跨境商品)
        private String stock;//商品库存
        private String specification;
        private String specificationId; // 规格ID
        private String screenrePertory;

        public String getScreenrePertory() {
            return screenrePertory;
        }

        public void setScreenrePertory(String screenrePertory) {
            this.screenrePertory = screenrePertory;
        }

        public String getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(String specificationId) {
            this.specificationId = specificationId;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        private String discountExplain;//优惠说明

        private int isSelected = 2; //是否选择 1选择 2未选择


        //0：未发布 11：已发布  1:审核通过 2: 审核未通过   3:上架 4：下架   5：删除
        private String if_show; //是否下架

        private String limitPrice; //折扣价格


        private String virtualCount;


        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPost_money() {
            return post_money;
        }

        public void setPost_money(String post_money) {
            this.post_money = post_money;
        }

        public String getWeight() {
            if (weight == null) {
                return "";
            }
            return weight;
        }

        public String getDiscountExplain() {
            return discountExplain;
        }

        public void setDiscountExplain(String discountExplain) {
            this.discountExplain = discountExplain;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getUnit() {
            if (unit == null) {
                return "";
            }
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getShopCartsId() {
            if (shopCartsId == null) {
                return "";
            }
            return shopCartsId;
        }

        public String getVirtualCount() {
            return virtualCount;
        }

        public void setVirtualCount(String virtualCount) {
            this.virtualCount = virtualCount;
        }


        public void setShopCartsId(String shopCartsId) {
            this.shopCartsId = shopCartsId;
        }

        public String getMemberId() {
            if (memberId == null) {
                return "";
            }
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getCategoryId() {
            if (categoryId == null) {
                return "";
            }
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getProductId() {
            if (productId == null) {
                return "";
            }
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            if (productName == null) {
                return "";
            }
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPicUrl() {
            if (picUrl == null) {
                return "";
            }
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPrice() {
            if (price == null) {
                return "0";
            }
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }


        public String getIf_show() {
            return if_show;
        }

        public void setIf_show(String if_show) {
            this.if_show = if_show;
        }

        public String getCount() {
            if (count == null) {
                return "0";
            }
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getProductCode() {
            if (productCode == null) {
                return "";
            }
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getTotalPrice() {
            if (totalPrice == null) {
                return "";
            }
            return totalPrice;
        }

        public String getGross_Weight() {
            return gross_Weight;
        }

        public void setGross_Weight(String gross_Weight) {
            this.gross_Weight = gross_Weight;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }


        public int getIsSelected() {
            return isSelected;
        }

        public void setSelected(int selected) {
            this.isSelected = selected;
        }


        public String getLimitPrice() {
            return limitPrice;
        }

        public void setLimitPrice(String limitPrice) {
            this.limitPrice = limitPrice;
        }
    }
}
