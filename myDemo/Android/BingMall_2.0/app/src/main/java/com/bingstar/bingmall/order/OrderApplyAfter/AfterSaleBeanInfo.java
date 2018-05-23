package com.bingstar.bingmall.order.OrderApplyAfter;

import java.io.Serializable;
import java.util.List;

/**
 * 售后申请详情查询
 * Created by liumengqiang on 2017/3/20.
 */

public class AfterSaleBeanInfo implements Serializable{
    private AfterSaleInfo afterSaleInfo;//售后信息

    public AfterSaleInfo getAfterSaleInfo() {
        return afterSaleInfo;
    }

    public void setAfterSaleInfo(AfterSaleInfo afterSaleInfo) {
        this.afterSaleInfo = afterSaleInfo;
    }

    public AfterSaleInfo getSaleInfo(){
        return new AfterSaleInfo();
    }

    public class AfterSaleInfo implements Serializable{

        private String afterSaleType;//售后类型
        private String zorderinfo_id;//售后总订单ID
        private String afterSaleState;//售后状态
        private String afterSaleReason;//售后申请原因
        private List<AfterSaleOrder> afterSaleOrderList;//售后子订单列表

        public String getAfterSaleType() {
            return afterSaleType;
        }

        public void setAfterSaleType(String afterSaleType) {
            this.afterSaleType = afterSaleType;
        }

        public String getZorderinfo_id() {
            return zorderinfo_id;
        }

        public void setZorderinfo_id(String zorderinfo_id) {
            this.zorderinfo_id = zorderinfo_id;
        }

        public String getAfterSaleState() {
            return afterSaleState;
        }

        public void setAfterSaleState(String afterSaleState) {
            this.afterSaleState = afterSaleState;
        }

        public String getAfterSaleReason() {
            return afterSaleReason;
        }

        public void setAfterSaleReason(String afterSaleReason) {
            this.afterSaleReason = afterSaleReason;
        }

        public List<AfterSaleOrder> getAfterSaleOrderList() {
            return afterSaleOrderList;
        }

        public void setAfterSaleOrderList(List<AfterSaleOrder> afterSaleOrderList) {
            this.afterSaleOrderList = afterSaleOrderList;
        }

        public AfterSaleOrder getSaleOrder(){
            return new AfterSaleOrder();
        }

        public class AfterSaleOrder implements Serializable{
            private String orderinfo_id;//售后子订单ID
            private List<AfterSaleGoods> afterSaleGoodsList;//售后商品列表

            public String getOrderinfo_id() {
                return orderinfo_id;
            }

            public void setOrderinfo_id(String orderinfo_id) {
                this.orderinfo_id = orderinfo_id;
            }

            public List<AfterSaleGoods> getAfterSaleGoodsList() {
                return afterSaleGoodsList;
            }

            public void setAfterSaleGoodsList(List<AfterSaleGoods> afterSaleGoodsList) {
                this.afterSaleGoodsList = afterSaleGoodsList;
            }

            public AfterSaleGoods getSaleGoods(){
                return new AfterSaleGoods();
            }

            public class AfterSaleGoods implements Serializable{
                private String goods_code;//售后商品编号
                private String selectState;//售后商品选择状态

                public String getGoods_code() {
                    return goods_code;
                }

                public void setGoods_code(String goods_code) {
                    this.goods_code = goods_code;
                }

                public String getSelectState() {
                    return selectState;
                }

                public void setSelectState(String selectState) {
                    this.selectState = selectState;
                }
            }
        }
    }
}
