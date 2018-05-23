package com.bingstar.bingmall.order.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.main.bean.PayEvent;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;

import com.bingstar.bingmall.order.LogisticsSearch.LogisticsInfo;
import com.bingstar.bingmall.order.OrderApplyAfter.AfterSaleBeanInfo;
import com.bingstar.bingmall.order.OrderApplyAfter.OrderApplayEditBeanInfo;
import com.bingstar.bingmall.order.bean.CreateOrderBeanInfo;
import com.bingstar.bingmall.order.bean.CreateOrderInfo;
import com.bingstar.bingmall.order.bean.OrderList;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.bingstar.bingmall.order.bean.OrderState;
import com.bingstar.bingmall.order.bean.OrderStateList;
import com.bingstar.bingmall.order.bean.ZorderStatus;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by qianhechen on 17/2/13.
 */

public class OrderClient {

    //订单列表查询
    private static final String TITLE = "/orderListQuery.shtml";
    private static final String LIST_QUERY_METHOD = "order.list.query";
    //订单详情查询
    private static final String INFO_TITLE = "/orderInfoQuery.shtml";
    private static final String ORDERINFO_QUERY_METHOD = "order.info.query";
    //订单状态查询
    private static final String STATUS_TITLE = "/zorderStatusQuery.shtml";
    private static final String ORDERSTATUS_QUERY_METHOD = "zorder.status.query";
    //订单创建
    private static final String ORDER_CREATE_TITLE = "/orderCreate.shtml";
    private static final String ORDER_CREATE_METHOD = "order.create";
    //订单物流查询
    private static final String ORDER_LOGISTIC_TITLE = "/expressQuery.shtml";
    private static final String ORDER_LOGISTIC_METHOD = "express.query";
    //订单验证
    private static final String ORDER_CHECK_TITLE = "/orderCheck.shtml";
    private static final String ORDER_CHECK_METHOD = "order.check";
    //订单删除
    private static final String ORDER_DELETE = "/orderDelete.shtml";
    private static final String ORDER_DELETE_METHOD = "order.delete";
    //售后申请查询
    private static final String ORDER_APPLAY = "/afterSaleApplyQuery.shtml";
    private static final String ORDER_APPLAY_METHOD = "afterSale.apply.query";
    //售后申请
    private static final String ORDER_APPLAY_AFTER = "/afterSaleApply.shtml";
    private static final String ORDER_APPLAY_AFTER_METHOD = "afterSale.apply";

    /**
     * 获取订单列表数据
     *
     * @param params
     * @param callback
     */
    public static void getOrderList(Map<String, String> params, final ClientCallback<OrderList> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost2(TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                OrderList orderList = JSON.parseObject(str, OrderList.class);
                if (orderList == null) {
                    /**
                     * 数据转换错误 data_error处理
                     */
                    callback.onFail(BingstarErrorParser.DATA_ERROR, "data_error");
                    return;
                }
                if (orderList != null && !"0".equals(orderList.getOrderCount())) {
                    LogUtils.Debug_E(OrderClient.class, "获取订单列表参数");
                    callback.onSuccess(orderList);
                } else {
                    callback.onSuccess(orderList);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    /**
     * 获取订单详情
     *
     * @param params
     * @param callback
     */
    public static void getOrderInfo(Map<String, String> params, final ClientCallback<OrderListInfo> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDERINFO_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(INFO_TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                OrderListInfo orderListInfo = JSON.parseObject(str, OrderListInfo.class);
                if (orderListInfo != null) {
                    LogUtils.Debug_E(OrderClient.class, "通过");
                    callback.onSuccess(orderListInfo);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void getOrderStatus(String zorderNo, final ClientCallback<OrderState> callback) {

        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDERSTATUS_QUERY_METHOD);
        Map<String, String> map1 = new ArrayMap<>();
        map1.put(OrderStates.ZORDER_NO, zorderNo);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(map1));
        BingstarNet.doPost(STATUS_TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ZorderStatus zorderstatus = JSONObject.parseObject(str, ZorderStatus.class);
                if (zorderstatus!=null) {
                    callback.onSuccess(zorderstatus.getZorderstatus());
                }else {
                    callback.onFail(BingNetStates.DATA_ERROR,BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    /**
     * 创建订单
     *
     * @param createOrderInfo
     * @param callback
     */
    public static void createOrder(final CreateOrderInfo createOrderInfo, final ClientCallback<CreateOrderBeanInfo> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_CREATE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(createOrderInfo));
        BingstarNet.doPost(ORDER_CREATE_TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(OrderClient.class, "创建订单成功：" + str);
                CreateOrderBeanInfo createOrderInfo1 = JSON.parseObject(str, CreateOrderBeanInfo.class);
                if (createOrderInfo1 != null) {
                    callback.onSuccess(createOrderInfo1);
                } else {
                    callback.onFail(BingstarErrorParser.DATA_ERROR, "data_error");
                }

            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    /**
     * 订单物流查询
     *
     * @param params
     * @param callback
     */
    public static void getOrderLogistic(Map<String, String> params, final ClientCallback<LogisticsInfo> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_LOGISTIC_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(ORDER_LOGISTIC_TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(OrderClient.class, "物流信息：" + str);
                LogisticsInfo logisticsInfo = JSON.parseObject(str, LogisticsInfo.class);
                if (logisticsInfo != null) {
                    callback.onSuccess(logisticsInfo);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, "date error!");
                }
//                OrderLogisticInfo orderLogisticInfo = JSON.parseObject(str, OrderLogisticInfo.class);
//                if( null != orderLogisticInfo && orderLogisticInfo.getLogisticsInfoList() != null ){
//                    callback.onSuccess(orderLogisticInfo);
//                }else{
//                    callback.onFail(BingNetStates.DATA_ERROR, "code error!");
//                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void checkOrder(Map<String, String> params, ClientCallback callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_CHECK_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(ORDER_CHECK_TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    /**
     * 删除订单
     */
    public static void getOrderDelete(Map<String, String> params, final ClientCallback<Object> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_DELETE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(ORDER_DELETE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(OrderClient.class, "删除订单成功：" + str);
                callback.onSuccess(new Object());
            }

            @Override
            public void onFail(int code, String error) {
                LogUtils.Debug_E(OrderClient.class, "删除订单失败" + code + " error: " + error);
                callback.onFail(code, error);
            }
        });
    }

    /**
     * 售后申请查询
     */
    public static void getOrderApplaySearch(Map<String, String> params, final ClientCallback<AfterSaleBeanInfo> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_APPLAY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(ORDER_APPLAY, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(OrderClient.class, "售后申请查询成功：" + str);
                AfterSaleBeanInfo afterSaleBeanInfo = JSON.parseObject(str, AfterSaleBeanInfo.class);
                if (afterSaleBeanInfo != null && afterSaleBeanInfo.getAfterSaleInfo() != null) {
                    callback.onSuccess(afterSaleBeanInfo);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, "售后申请查询失败");
                }
            }

            @Override
            public void onFail(int code, String error) {
                LogUtils.Debug_E(OrderClient.class, "售后申请查询失败" + code + " error: " + error);
                callback.onFail(code, error);
            }
        });
    }

    /**
     * 售后申请
     */
    public static void getApplayAfter(final OrderApplayEditBeanInfo afterSaleInfo, final ClientCallback clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_APPLAY_AFTER_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(afterSaleInfo));
        BingstarNet.doPost(ORDER_APPLAY_AFTER, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(OrderClient.class, "售后申请成功：" + str);
                clientCallback.onSuccess(new Object());
            }

            @Override
            public void onFail(int code, String error) {
                LogUtils.Debug_E(OrderClient.class, "售后申请失败：code = " + code + " error = " + error);
                clientCallback.onFail(code, error);
            }
        });
    }
}
