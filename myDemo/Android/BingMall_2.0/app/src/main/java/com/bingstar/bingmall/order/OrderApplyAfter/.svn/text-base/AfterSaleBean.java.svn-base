package com.bingstar.bingmall.order.OrderApplyAfter;

import java.io.Serializable;
import java.util.List;

/**
 * 售后申请输入参数Bean
 * Created by liumengqiang on 2017/3/20.
 */
public class AfterSaleBean implements Serializable{
    private String afterSaleType;//售后类型(1：退款;2:退货)
    private String zorderinfo_id;//总订单ID
    private String afterSaleReason;//售后申请原因
    private List<AfterSaleOrderInfo> afterSaleOrderList;//售后子订单列表信息

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

    public String getAfterSaleReason() {
        return afterSaleReason;
    }

    public void setAfterSaleReason(String afterSaleReason) {
        this.afterSaleReason = afterSaleReason;
    }

    public List<AfterSaleOrderInfo> getAfterSaleOrderList() {
        return afterSaleOrderList;
    }

    public void setAfterSaleOrderList(List<AfterSaleOrderInfo> afterSaleOrderList) {
        this.afterSaleOrderList = afterSaleOrderList;
    }
//    获得AfterSaleOrder类
    public AfterSaleOrderInfo getAfterSaleOrder(){
        return new AfterSaleOrderInfo();
    }
}
class AfterSaleOrder implements Serializable{
    private String orderinfo_id;//售后子订单ID
    private List<AfterSaleGoodsInfo> afterSaleGoodsList;//售后商品列表

    public String getOrderinfo_id() {
        return orderinfo_id;
    }

    public void setOrderinfo_id(String orderinfo_id) {
        this.orderinfo_id = orderinfo_id;
    }

    public List<AfterSaleGoodsInfo> getAfterSaleGoodsList() {
        return afterSaleGoodsList;
    }

    public void setAfterSaleGoodsList(List<AfterSaleGoodsInfo> afterSaleGoodsList) {
        this.afterSaleGoodsList = afterSaleGoodsList;
    }
    //获得AfterSaleGoods类
    public AfterSaleGoodsInfo getAfterSaleGoods(){
        return new AfterSaleGoodsInfo();
    }
}
class AfterSaleGoods implements Serializable{
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
