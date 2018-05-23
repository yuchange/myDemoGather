package com.zyf.bings.bingos.brand.bean;

import java.util.List;

/**
 * Created by sd on 2017/9/12.
 */

public class HotListBean {

    /**
     * brandList : [{"brand_url":"/homeImge/2017041414/1492152028805.png","brand_id":"13","brand_name":"帮宝适"},{"brand_url":"/homeImge/2017041414/1492152065664.png","brand_id":"14","brand_name":"可口可乐"},{"brand_url":"/homeImge/2017041414/1492152103774.png","brand_id":"15","brand_name":"统一"},{"brand_url":"/homeImge/2017030620/1488802018799.jpg","brand_id":"16","brand_name":"乔丹"},{"brand_url":"/homeImge/2017041414/1492152183634.png","brand_id":"17","brand_name":"无品牌用"},{"brand_url":"/homeImge/2017041313/1492061133016.png","brand_id":"19","brand_name":"伊利"}]
     * count : 6
     */

    private int count;
    private List<BrandListBean> brandList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BrandListBean> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandListBean> brandList) {
        this.brandList = brandList;
    }

    public static class BrandListBean {
        /**
         * brand_url : /homeImge/2017041414/1492152028805.png
         * brand_id : 13
         * brand_name : 帮宝适
         */

        private String brand_url;
        private String brand_id;
        private String brand_name;

        public String getBrand_url() {
            return brand_url;
        }

        public void setBrand_url(String brand_url) {
            this.brand_url = brand_url;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }
    }
}
