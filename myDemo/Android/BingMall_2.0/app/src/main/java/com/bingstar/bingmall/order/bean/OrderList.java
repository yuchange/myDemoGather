package com.bingstar.bingmall.order.bean;

import java.util.List;

/**
 * Created by qianhechen on 17/2/20.
 */

public class OrderList {

    private List<Zorder> zorderList;
    private String orderCount;

    public List<Zorder> getZorderList() {
        return zorderList;
    }

    public void setZorderList(List<Zorder> zorderList) {
        this.zorderList = zorderList;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public class Zorder{

        private String zorderinfo_id;

        private String zorder_no;

        private String zstate;

        private String member_id;

        private String zorder_total_money;

        private String orderSize;

        private String name;

        private List<ZorderProduct> productList;

        private String afterSaleState;

        private String post_money;

        private String couponMoney;

        private String payTime;

        private String pay_type;

        private String addTime;

        @Override
        public String toString() {
            return "Zorder{" +
                    "zorderinfo_id='" + zorderinfo_id + '\'' +
                    ", zorder_no='" + zorder_no + '\'' +
                    ", zstate='" + zstate + '\'' +
                    ", member_id='" + member_id + '\'' +
                    ", zorder_total_money='" + zorder_total_money + '\'' +
                    ", orderSize='" + orderSize + '\'' +
                    ", name='" + name + '\'' +
                    ", productList=" + productList +
                    ", afterSaleState='" + afterSaleState + '\'' +
                    ", post_money='" + post_money + '\'' +
                    ", couponMoney='" + couponMoney + '\'' +
                    ", payTime='" + payTime + '\'' +
                    ", pay_type='" + pay_type + '\'' +
                    ", addTime='" + addTime + '\'' +
                    '}';
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPost_money() {
            return post_money;
        }

        public void setPost_money(String post_money) {
            this.post_money = post_money;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getAfterSaleState() {
            return afterSaleState;
        }

        public void setAfterSaleState(String afterSaleState) {
            this.afterSaleState = afterSaleState;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getZorderinfo_id() {
            return zorderinfo_id;
        }

        public void setZorderinfo_id(String zorderinfo_id) {
            this.zorderinfo_id = zorderinfo_id;
        }

        public String getZorder_no() {
            return zorder_no;
        }

        public void setZorder_no(String zorder_no) {
            this.zorder_no = zorder_no;
        }

        public String getZstate() {
            return zstate;
        }

        public void setZstate(String zstate) {
            this.zstate = zstate;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getZorder_total_money() {
            return zorder_total_money;
        }

        public void setZorder_total_money(String zorder_total_money) {
            this.zorder_total_money = zorder_total_money;
        }

        public String getOrderSize() {
            return orderSize;
        }

        public void setOrderSize(String orderSize) {
            this.orderSize = orderSize;
        }

        public List<ZorderProduct> getProductList() {
            return productList;
        }

        public void setProductList(List<ZorderProduct> productList) {
            this.productList = productList;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public class ZorderProduct{

            private String bsjProductName;
            private String image_url;

            public String getBsjProductName() {
                return bsjProductName;
            }

            public void setBsjProductName(String bsjProductName) {
                this.bsjProductName = bsjProductName;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }
    }
}
