package com.bingstar.bingmall.order;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.bean.OrderList;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.order.http.OrderStates;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qianhechen on 17/2/13.
 */

public class OrderPresenter implements IOrderContract.OrderPresenter {

    private IOrderContract.OrderListView orderListView;

    public ArrayList<OrderList.Zorder> orderList;


    public String statePresneter;

    public OrderPresenter(IOrderContract.OrderListView orderListView) {
        this.orderListView = orderListView;
    }

    @Override
    public void unBind() {
        orderListView = null;
    }

    @Override
    public void getOrderList(final List<OrderList.Zorder> orderInfoList) {
        Map<String, String> map = new ArrayMap<>();
        map.put(OrderStates.MEMBERID, "1");
        map.put(OrderStates.STATE, "1");
        map.put(OrderStates.PAGESIZE, "10");
        map.put(OrderStates.CURRENTINDEX, "0");
        OrderClient.getOrderList(map, new ClientCallback<OrderList>() {
            @Override
            public void onSuccess(OrderList orderList) {
                orderInfoList.clear();
                orderInfoList.addAll(orderList.getZorderList());
            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    /**
     * @param orderInfoList 数据集合
     * @param memberId      会员ID
     * @param state         通过改变state的值来切换数据
     * @param pageSize      页面显示数据个数
     * @param currentPage   当前页
     */
    @Override
    public void getAllOrderList(final List<OrderList.Zorder> orderInfoList, final String memberId, final String state, final int pageSize, final int currentPage, final int flug) {
        Map<String, String> map = new ArrayMap<>();
        map.put(OrderStates.MEMBERID, memberId);
        if (!"".equals(state)) {
            map.put(OrderStates.STATE, state);
        }
        map.put(OrderStates.PAGESIZE, pageSize + "");
        map.put(OrderStates.CURRENTINDEX, currentPage + "");
        OrderClient.getOrderList(map, new ClientCallback<OrderList>() {
            @Override
            public void onSuccess(OrderList orderList) {
                if (flug == 1) {//刷新 清空数据
                    orderInfoList.clear();
                }
                orderInfoList.addAll(orderList.getZorderList());
                /**
                 *  判断是否加载数据完全 orderList.size()小于10 或者等于0  就表明加载完毕
                 */
                if (currentPage == 0) {
                    if (orderInfoList.size() == 0) {
                        orderListView.notifyOrderList(OrderStates.ORDERDATE_NONO, state);
                    }
                }
                if (orderList.getZorderList().size() == 0 || orderList.getZorderList().size() < 10) {
                    if (orderListView != null) {
                        orderListView.notifyOrderList(OrderStates.ORDERDATE_NO, state);//没有数据了
                    }
                } else {
                    if (orderListView != null) {
                        orderListView.notifyOrderList(OrderStates.ORDERDATE_YES, state);//有数据
                    }
                }
            }

            @Override
            public void onFail(int code, String error) {
//                orderListView.error();
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.OrderFragment_final);
            }
        });
    }

    /**
     * 删除订单
     *
     * @param zordersList
     */
    @Override
    public void getDeleteOrder(List<OrderList.Zorder> zordersList) {

    }


}
