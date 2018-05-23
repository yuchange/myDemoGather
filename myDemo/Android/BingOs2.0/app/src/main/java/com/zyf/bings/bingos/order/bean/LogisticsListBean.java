package com.zyf.bings.bingos.order.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 * 物流信息列表
 */

public class LogisticsListBean {


    private List<OrderInfoListBean> OrderInfoList;

    public List<OrderInfoListBean> getOrderInfoList() {
        return OrderInfoList;
    }

    public void setOrderInfoList(List<OrderInfoListBean> OrderInfoList) {
        this.OrderInfoList = OrderInfoList;
    }

    public static class OrderInfoListBean {
        /**
         * logistics_no : null
         * goodsList : [{"image_url":"/uploadImage/2017092511/1506309323703.png","goods_id":"171","goods_name":"巧克力俄罗斯巧克力QIAOKEL"},{"image_url":"/uploadImage/2017092511/1506309325908.png","goods_id":"171","goods_name":"巧克力俄罗斯巧克力QIAOKEL"},{"image_url":"/uploadImage/2017092511/1506309328386.png","goods_id":"171","goods_name":"巧克力俄罗斯巧克力QIAOKEL"},{"image_url":"/uploadImage/2017092511/1506309331034.png","goods_id":"171","goods_name":"巧克力俄罗斯巧克力QIAOKEL"},{"image_url":"/uploadImage/2017093013/1506749461008.png","goods_id":"171","goods_name":"巧克力俄罗斯巧克力QIAOKEL"}]
         * orderinfo_id : 2171
         */

        private String logistics_no;
        private String orderinfo_id;

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        private String shipping_name;
        private List<GoodsListBean> goodsList;

        public String getLogistics_no() {
            return logistics_no;
        }

        public void setLogistics_no(String logistics_no) {
            this.logistics_no = logistics_no;
        }

        public String getOrderinfo_id() {
            return orderinfo_id;
        }

        public void setOrderinfo_id(String orderinfo_id) {
            this.orderinfo_id = orderinfo_id;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * image_url : /uploadImage/2017092511/1506309323703.png
             * goods_id : 171
             * goods_name : 巧克力俄罗斯巧克力QIAOKEL
             */

            private String image_url;
            private String goods_id;
            private String goods_name;

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }
        }
    }
}
