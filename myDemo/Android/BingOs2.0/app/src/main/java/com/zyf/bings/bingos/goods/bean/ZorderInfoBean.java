package com.zyf.bings.bingos.goods.bean;

import java.util.List;

/**
 * Created by wangshiqi on 2017/10/9.
 */

public class ZorderInfoBean {
    private  Zorderinfo zorderinfo;

    public Zorderinfo getZorderinfo() {
        return zorderinfo;
    }

    public void setZorderinfo(Zorderinfo zorderinfo) {
        this.zorderinfo = zorderinfo;
    }

    public  class Zorderinfo{
        private String zorder_total_money;
        private String couponId;
        private String orderSize;
        private String member_id;
        private String zorderinfo_id;
        private String goods_name;
        private String post_money;
        private String name;
        private String zstate;
        private String couponMoney;
        private String zorder_no;
        private List<Product> productList;

        public String getZorder_total_money() {
            return zorder_total_money;
        }

        public void setZorder_total_money(String zorder_total_money) {
            this.zorder_total_money = zorder_total_money;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getOrderSize() {
            return orderSize;
        }

        public void setOrderSize(String orderSize) {
            this.orderSize = orderSize;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getZorderinfo_id() {
            return zorderinfo_id;
        }

        public void setZorderinfo_id(String zorderinfo_id) {
            this.zorderinfo_id = zorderinfo_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPost_money() {
            return post_money;
        }

        public void setPost_money(String post_money) {
            this.post_money = post_money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getZstate() {
            return zstate;
        }

        public void setZstate(String zstate) {
            this.zstate = zstate;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getZorder_no() {
            return zorder_no;
        }

        public void setZorder_no(String zorder_no) {
            this.zorder_no = zorder_no;
        }

        public List<Product> getProductList() {
            return productList;
        }

        public void setProductList(List<Product> productList) {
            this.productList = productList;
        }

        public class Product{
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
