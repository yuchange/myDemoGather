package com.zyf.bings.bingos.order;

/**
 * Created by qianhechen on 17/2/13.
 */

public class OrderClient {

    //订单列表查询
    public static final String TITLE = "/orderListQuery.shtml";
    public static final String LIST_QUERY_METHOD = "order.list.query";
    //订单详情查询
    public static final String INFO_TITLE = "/orderInfoQuery.shtml";
    public static final String ORDERINFO_QUERY_METHOD = "order.info.query";
    //订单状态查询
    public static final String STATUS_TITLE = "/zorderStatusQuery.shtml";
    public static final String ORDERSTATUS_QUERY_METHOD = "zorder.status.query";
    //订单创建
    public static final String ORDER_CREATE_TITLE = "/orderCreate.shtml";
    public static final String ORDER_CREATE_METHOD = "order.create";
    //订单物流查询
    public static final String ORDER_LOGISTIC_TITLE = "/expressQuery.shtml";
    public static final String ORDER_LOGISTIC_METHOD = "express.query";
    //订单验证
    public static final String ORDER_CHECK_TITLE = "/orderCheck.shtml";
    public static final String ORDER_CHECK_METHOD = "order.check";
    //订单删除
    public static final String ORDER_DELETE = "/orderDelete.shtml";
    public static final String ORDER_DELETE_METHOD = "order.delete";
    //售后申请查询
    public static final String ORDER_APPLAY = "/afterSaleApplyQuery.shtml";
    public static final String ORDER_APPLAY_METHOD = "afterSale.apply.query";
    //售后申请
    public static final String ORDER_APPLAY_AFTER = "/afterSaleApply.shtml";
    public static final String ORDER_APPLAY_AFTER_METHOD = "afterSale.apply";
    //提醒发货
    public static final String ORDER_REMAIN_DELIVERY="/remindShipment.shtml";
    public static final String ORDER_REMAIN_DELIVERY_METHOD="remind.shipment";

}
