package com.zyf.bings.bingos.order.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class LogisticsComanyInfo {


    private List<ShippingListBean> shippingList;

    public List<ShippingListBean> getShippingList() {
        return shippingList;
    }

    public void setShippingList(List<ShippingListBean> shippingList) {
        this.shippingList = shippingList;
    }

    public static class ShippingListBean {
        /**
         * shipping_name : 圆通
         * shipping_egname : yto
         */

        private String shipping_name;
        private String shipping_egname;

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getShipping_egname() {
            return shipping_egname;
        }

        public void setShipping_egname(String shipping_egname) {
            this.shipping_egname = shipping_egname;
        }
    }
}

