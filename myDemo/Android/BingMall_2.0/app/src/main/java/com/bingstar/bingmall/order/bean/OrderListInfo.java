package com.bingstar.bingmall.order.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.bingstar.bingmall.order.OrderApplyAfter.AfterSaleBeanInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianhechen on 17/2/21.
 */



public class OrderListInfo implements Serializable{

    private List<OrderMoreInfo> orderList;

    public List<OrderMoreInfo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderMoreInfo> orderList) {
        this.orderList = orderList;
    }

    public  class OrderMoreInfo implements Serializable{
        private String orderinfo_id;
        private String zorderinfo_id;
        private String zorder_no;
        private String state;
        private String flag;
        private String cycleflag;
        private String member_id;
        private String order_no;
        private String pay_transNo;
        private String pay_type;
        private String post_money;
        private String order_total_money;
        private String order_discount_money;
        private String logistics_no;
        private String shipping_name;
        private String couponId;
        private String payTime;
        private String applyrefundTime;
        private String refundTime;
        private String deliveryTime;
        private String receiveTime;
        private String applyreGoodsReturnTime;
        private String addTime;
        private String buyer_msg;
        private String receiver_name;
        private String region;
        private String detailed_address;
        private String contact_phone;
        private String contact_mobile;
        private String buyer_IDcard;
        private String order_remark;
        private String postCode;
        private String shipper;
        private String pay_amt;
        private String pay_time;
        private String pay_no;
        private List<OrderGoodsList> ordergoodsList;

        public String getZorder_no() {
            return zorder_no;
        }

        public void setZorder_no(String zorder_no) {
            this.zorder_no = zorder_no;
        }

        public String getOrderinfo_id() {
            return orderinfo_id;
        }

        public void setOrderinfo_id(String orderinfo_id) {
            this.orderinfo_id = orderinfo_id;
        }

        public String getZorderinfo_id() {
            return zorderinfo_id;
        }

        public void setZorderinfo_id(String zorderinfo_id) {
            this.zorderinfo_id = zorderinfo_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getCycleflag() {
            return cycleflag;
        }

        public void setCycleflag(String cycleflag) {
            this.cycleflag = cycleflag;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getPay_transNo() {
            return pay_transNo;
        }

        public void setPay_transNo(String pay_transNo) {
            this.pay_transNo = pay_transNo;
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

        public String getOrder_total_money() {
            return order_total_money;
        }

        public void setOrder_total_money(String order_total_money) {
            this.order_total_money = order_total_money;
        }

        @Override
        public boolean equals(Object obj) {
            return orderinfo_id.equals(((AfterSaleBeanInfo.AfterSaleInfo.AfterSaleOrder)obj).getOrderinfo_id());
        }

        @Override
        public int hashCode() {
            return orderinfo_id.hashCode();
        }

        public String getOrder_discount_money() {
            return order_discount_money;
        }

        public void setOrder_discount_money(String order_discount_money) {
            this.order_discount_money = order_discount_money;
        }

        public String getLogistics_no() {
            return logistics_no;
        }

        public void setLogistics_no(String logistics_no) {
            this.logistics_no = logistics_no;
        }

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getApplyrefundTime() {
            return applyrefundTime;
        }

        public void setApplyrefundTime(String applyrefundTime) {
            this.applyrefundTime = applyrefundTime;
        }

        public String getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(String refundTime) {
            this.refundTime = refundTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getApplyreGoodsReturnTime() {
            return applyreGoodsReturnTime;
        }

        public void setApplyreGoodsReturnTime(String applyreGoodsReturnTime) {
            this.applyreGoodsReturnTime = applyreGoodsReturnTime;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getBuyer_msg() {
            return buyer_msg;
        }

        public void setBuyer_msg(String buyer_msg) {
            this.buyer_msg = buyer_msg;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public String getContact_mobile() {
            return contact_mobile;
        }

        public void setContact_mobile(String contact_mobile) {
            this.contact_mobile = contact_mobile;
        }

        public String getBuyer_IDcard() {
            return buyer_IDcard;
        }

        public void setBuyer_IDcard(String buyer_IDcard) {
            this.buyer_IDcard = buyer_IDcard;
        }

        public String getOrder_remark() {
            return order_remark;
        }

        public void setOrder_remark(String order_remark) {
            this.order_remark = order_remark;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getShipper() {
            return shipper;
        }

        public void setShipper(String shipper) {
            this.shipper = shipper;
        }

        public String getPay_amt() {
            return pay_amt;
        }

        public void setPay_amt(String pay_amt) {
            this.pay_amt = pay_amt;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPay_no() {
            return pay_no;
        }

        public void setPay_no(String pay_no) {
            this.pay_no = pay_no;
        }

        public List<OrderGoodsList> getOrdergoodsList() {
            return ordergoodsList;
        }

        public void setOrdergoodsList(List<OrderGoodsList> ordergoodsList) {
            this.ordergoodsList = ordergoodsList;
        }
        public class OrderGoodsList implements Serializable{
            private String orderinfo_id;
            private String goods_name;
            private String goods_code;
            private String goods_sku;
            private String price;
            private String goodsTotal_amount;
            private String total_money;
            private String number;
            private String grossWeight;
            private String weight;
            private String unit;
            private String picUrl;
            private String categoryId;
            private String goods_id;
            private String sellerName;
            private String isDeleted;

            public String getSellerName() {
                return sellerName;
            }

            public void setSellerName(String sellerName) {
                this.sellerName = sellerName;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            /**
             * 1：在orderListInfo中添加两个字段 一个是ifClick是否可点击，一个是selectState；
             */
            private  String selectState ;// OrderApplayAdapter图标是否显示 -1：不可点击状态 ?  1：绿色按钮(选中状态) ？ 2：灰色按钮(为选中状态)?

            public String getSelectState() {
                return "0";
            }

            public void setSelectState(String selectState) {
//                this.selectState = selectState;
            }

//

            public String getOrderinfo_id() {
                return orderinfo_id;
            }

            public void setOrderinfo_id(String orderinfo_id) {
                this.orderinfo_id = orderinfo_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_code() {
                return goods_code;
            }

            public void setGoods_code(String goods_code) {
                this.goods_code = goods_code;
            }

            public String getGoods_sku() {
                return goods_sku;
            }

            public void setGoods_sku(String goods_sku) {
                this.goods_sku = goods_sku;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGoodsTotal_amount() {
                return goodsTotal_amount;
            }

            public void setGoodsTotal_amount(String goodsTotal_amount) {
                this.goodsTotal_amount = goodsTotal_amount;
            }

            public String getTotal_money() {
                return total_money;
            }

            public void setTotal_money(String total_money) {
                this.total_money = total_money;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getGrossWeight() {
                return grossWeight;
            }

            public void setGrossWeight(String grossWeight) {
                this.grossWeight = grossWeight;
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

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }
        }
    }
}
