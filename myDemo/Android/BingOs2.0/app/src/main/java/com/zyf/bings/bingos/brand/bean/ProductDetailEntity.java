package com.zyf.bings.bingos.brand.bean;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/21 下午10:20
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/21 下午10:20
 * @modify by reason:{方法名}:{原因}
 */

public class ProductDetailEntity {

    private ProductInfoDetail productInfoDetail;

    public ProductInfoDetail getProductInfoDetail() {
        return productInfoDetail;
    }

    public void setProductInfoDetail(ProductInfoDetail productInfoDetail) {
        this.productInfoDetail = productInfoDetail;
    }


    public class ProductInfoDetail {
        private List<ProductPic> productPicList;
        private List<ProductVideo> productVideoList;
        private String isDeleted;  //1上架，2下架，
        private String bsjProductId; //产品id
        private String price;       //商品价格
        private String limitPrice;  //商品折后价格
        private String bsjProductName; //商品名称
        private String goodsTitle;  //商品标题
        private String bsjProductCode; //商品编码
        private String weight; //商品重量
        private String norms;       //商品规格
        private String goodsCat;    //商品类别1：自营商品 2：入住商家商品
        private String productType; //产品类型1：完税商 2：保税商品
        private String CountryName; //进口国
        private String agencyName; //所属供应商
        private String sellerName; //所属卖家
        private String freight; //是否包邮  00:不包邮 01：包邮
        private String tax; //税率
        private String unit;//单位
        private String stock; //库存
        private String brandName; //商品品牌
        private String categoryId; //所属分类
        private String categoryName; //所属分类名称
        private String chooseNum;
        private String activity;//返回指定的优惠类型
        private String endTime;// 结束时间
        private String activity_type; //商品类型

        private String discountExplain;//优惠说明


        public String getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(String activity_type) {
            this.activity_type = activity_type;
        }

        public String getChooseNum() {
            return chooseNum;
        }

        public void setChooseNum(String chooseNum) {
            this.chooseNum = chooseNum;
        }

        public List<ProductPic> getProductPicList() {
            return productPicList;
        }

        public void setProductPicList(List<ProductPic> productPicList) {
            this.productPicList = productPicList;
        }


        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }


        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getBsjProductId() {
            return bsjProductId;
        }

        public void setBsjProductId(String bsjProductId) {
            this.bsjProductId = bsjProductId;
        }

        public String getBsjProductName() {
            return bsjProductName;
        }

        public void setBsjProductName(String bsjProductName) {
            this.bsjProductName = bsjProductName;
        }

        public String getBsjProductCode() {
            return bsjProductCode;
        }

        public void setBsjProductCode(String bsjProductCode) {
            this.bsjProductCode = bsjProductCode;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLimitPrice() {
            return limitPrice;
        }

        public void setLimitPrice(String limitPrice) {
            this.limitPrice = limitPrice;
        }


        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getNorms() {
            return norms;
        }

        public void setNorms(String norms) {
            this.norms = norms;
        }

        public String getGoodsCat() {
            return goodsCat;
        }

        public void setGoodsCat(String goodsCat) {
            this.goodsCat = goodsCat;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }

        public String getAgencyName() {
            return agencyName;
        }

        public void setAgencyName(String agencyName) {
            this.agencyName = agencyName;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<ProductVideo> getProductVideoList() {
            return productVideoList;
        }

        public void setProductVideoList(List<ProductVideo> productVideoList) {
            this.productVideoList = productVideoList;
        }


        public String getDiscountExplain() {
            return discountExplain;
        }

        public void setDiscountExplain(String discountExplain) {
            this.discountExplain = discountExplain;
        }
    }
}
