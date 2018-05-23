package com.bingstar.bingmall.cart.bean;

import java.util.List;

/**
 * Created by zhangyifei on 17/7/12.
 */

public class OneKeyCartOrder {
    /**
     * productInfo : {"productType":"1","totalPrice":"100.0","countryName":"中国","goodsTitle":"纯棉亲肤","stock":"66","agencyName":"杭州经销商","count":"1","isDeleted":"3","tax":"0.00","bsjProductCode":"6523256300","productPicList":[{"picId":"255","picUrl":"/uploadImage/2017/5/3/1493777321822.png","bsjProductId":"127"},{"picId":"256","picUrl":"/uploadImage/2017/5/3/1493777405149.png","bsjProductId":"127"},{"picId":"257","picUrl":"/uploadImage/2017/5/3/1493777409899.png","bsjProductId":"127"},{"picId":"258","picUrl":"/uploadImage/2017/5/3/1493777414946.png","bsjProductId":"127"},{"picId":"259","picUrl":"/uploadImage/2017/5/3/1493777420024.png","bsjProductId":"127"}],"weight":"1.00","limitPrice":"100.00","QRcode":"/QRcode/1501060693374.jpg","freight":"00","unit":"条","bsjProductId":"127","categoryId":"38","activity_type":"0","bsjProductname":"婴儿浴巾","productVideoList":[],"price":"100.00","sellerName":"杭州商家001","brandName":"无品牌用","categoryName":"婴儿用品"}
     */

    private ProductInfoBean productInfo;

    public ProductInfoBean getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoBean productInfo) {
        this.productInfo = productInfo;
    }


//        public void decreaseNum(ProductInfo productInfoAdd, final OneKeyCartOrder.UpdateNumCallback callback) {
//            int num = Integer.parseInt(getCount());
//            if (num != 0) {
//                setCount(String.valueOf(--num));
//                productInfoAdd.setCount(String.valueOf(num));
//                if (num != 0) {
//                    callback.update();
//                }
//            }
//        }
//
//
//        public void increaseNum(ProductInfo productInfoAdd, final OneKeyCartOrder.UpdateNumCallback callback) {
//            int num = Integer.parseInt(getCount());
//            if (num != 0) {
//                setCount(String.valueOf(++num));
//                productInfoAdd.setCount(String.valueOf(num));
//                callback.update();
//            }
//        }


    public interface UpdateNumCallback {
        void update();

        void rollback();
    }


    public static class ProductInfoBean {
        /**
         * productType : 1
         * totalPrice : 100.0
         * countryName : 中国
         * goodsTitle : 纯棉亲肤
         * stock : 66
         * agencyName : 杭州经销商
         * count : 1
         * isDeleted : 3
         * tax : 0.00
         * bsjProductCode : 6523256300
         * productPicList : [{"picId":"255","picUrl":"/uploadImage/2017/5/3/1493777321822.png","bsjProductId":"127"},{"picId":"256","picUrl":"/uploadImage/2017/5/3/1493777405149.png","bsjProductId":"127"},{"picId":"257","picUrl":"/uploadImage/2017/5/3/1493777409899.png","bsjProductId":"127"},{"picId":"258","picUrl":"/uploadImage/2017/5/3/1493777414946.png","bsjProductId":"127"},{"picId":"259","picUrl":"/uploadImage/2017/5/3/1493777420024.png","bsjProductId":"127"}]
         * weight : 1.00
         * limitPrice : 100.00
         * QRcode : /QRcode/1501060693374.jpg
         * freight : 00
         * unit : 条
         * bsjProductId : 127
         * categoryId : 38
         * activity_type : 0
         * bsjProductname : 婴儿浴巾
         * productVideoList : []
         * price : 100.00
         * sellerName : 杭州商家001
         * brandName : 无品牌用
         * categoryName : 婴儿用品
         */

        private String productType;
        private String totalPrice;
        private String countryName;
        private String goodsTitle;
        private String stock;
        private String agencyName;
        private String count;
        private String isDeleted;
        private String tax;
        private String bsjProductCode;
        private String weight;
        private String limitPrice;
        private String QRcode;
        private String freight;
        private String unit;
        private String bsjProductId;
        private String categoryId;
        private String endTime;
        private String grossWeight;
        private String discountExplain;

        public String getDiscountExplain() {
            return discountExplain;
        }

        public void setDiscountExplain(String discountExplain) {
            this.discountExplain = discountExplain;
        }

        public String getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(String grossWeight) {
            this.grossWeight = grossWeight;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        private String activity_type;
        private String bsjProductname;
        private String price;
        private String activity;
        private String sellerName;
        private String brandName;
        private String categoryName;
        private List<ProductPicListBean> productPicList;
        private List<ProductVideoList> productVideoList;

        public String getProductType() {
            return productType;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getAgencyName() {
            return agencyName;
        }

        public void setAgencyName(String agencyName) {
            this.agencyName = agencyName;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getBsjProductCode() {
            return bsjProductCode;
        }

        public void setBsjProductCode(String bsjProductCode) {
            this.bsjProductCode = bsjProductCode;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getLimitPrice() {
            return limitPrice;
        }

        public void setLimitPrice(String limitPrice) {
            this.limitPrice = limitPrice;
        }

        public String getQRcode() {
            return QRcode;
        }

        public void setQRcode(String QRcode) {
            this.QRcode = QRcode;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getBsjProductId() {
            return bsjProductId;
        }

        public void setBsjProductId(String bsjProductId) {
            this.bsjProductId = bsjProductId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(String activity_type) {
            this.activity_type = activity_type;
        }

        public String getBsjProductname() {
            return bsjProductname;
        }

        public void setBsjProductname(String bsjProductname) {
            this.bsjProductname = bsjProductname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<ProductPicListBean> getProductPicList() {
            return productPicList;
        }

        public void setProductPicList(List<ProductPicListBean> productPicList) {
            this.productPicList = productPicList;
        }

        public List<ProductVideoList> getProductVideoList() {
            return productVideoList;
        }

        public void setProductVideoList(List<ProductVideoList> productVideoList) {
            this.productVideoList = productVideoList;
        }

        public static class ProductVideoList {

            private String bsjProductId;
            private String videoId;
            private String picUrl;
            private String videoUrl;

            public String getBsjProductId() {
                return bsjProductId;
            }

            public void setBsjProductId(String bsjProductId) {
                this.bsjProductId = bsjProductId;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }
        }


        public static class ProductPicListBean {
            /**
             * picId : 255
             * picUrl : /uploadImage/2017/5/3/1493777321822.png
             * bsjProductId : 127
             */

            private String picId;
            private String picUrl;
            private String bsjProductId;
            private String isDefault;
            private String picType;

            public String getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(String isDefault) {
                this.isDefault = isDefault;
            }

            public String getPicType() {
                return picType;
            }

            public void setPicType(String picType) {
                this.picType = picType;
            }

            public String getPicId() {
                return picId;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getBsjProductId() {
                return bsjProductId;
            }

            public void setBsjProductId(String bsjProductId) {
                this.bsjProductId = bsjProductId;
            }
        }
    }
}
