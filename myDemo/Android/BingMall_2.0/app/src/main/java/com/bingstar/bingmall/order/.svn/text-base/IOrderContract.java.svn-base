package com.bingstar.bingmall.order;


import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.order.bean.OrderList;
import com.bingstar.bingmall.order.bean.OrderListInfo;

import java.util.List;

/**
 * Created by qianhechen on 17/2/13.
 */

public interface IOrderContract {

    interface OrderListView {
        void setPresenter();

        void notifyOrderList(int flug, String state);

        void showView(int kind, String type);

//        void error();
    }

    interface OrderPresenter extends IBasePresenter {

        void getOrderList(List<OrderList.Zorder> orderInfoList);

        /**
         * @param orderInfoList 数据
         * @param memberId      会员ID
         * @param state         通过改变state的值来切换数据
         * @param pageSize      　每个页面显示数据的个数
         * @param currentPage   　当前页数
         * @param flug          1：刷新 2：加载
         */
        void getAllOrderList(List<OrderList.Zorder> orderInfoList, String memberId, String state, int pageSize, int currentPage, int flug);

//        void getUnpaidOrderList(List<OrderList.OrderInfo> orderInfoList, int pageSize, int currentPage);
//
//        void getUndeliverOrderList(List<OrderList.OrderInfo> orderInfoList, int pageSize, int currentPage);
//
//        void getUnreceiveOrderList(List<OrderList.OrderInfo> orderInfoList, int pageSize, int currentPage);
//
//        void getUnevaluateOrderList(List<OrderList.OrderInfo> orderInfoList, int pageSize, int currentPage);

        /**
         * 删除订单
         *
         * @param zordersList
         */
        void getDeleteOrder(List<OrderList.Zorder> zordersList);

    }


}
