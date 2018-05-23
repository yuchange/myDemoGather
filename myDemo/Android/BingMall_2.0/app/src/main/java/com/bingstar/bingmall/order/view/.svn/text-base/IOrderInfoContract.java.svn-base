package com.bingstar.bingmall.order.view;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;
import com.bingstar.bingmall.order.LogisticsSearch.LogisticsInfo;
import com.bingstar.bingmall.order.OrderApplyAfter.AfterSaleBeanInfo;
import com.bingstar.bingmall.order.bean.OrderListInfo;

import java.util.List;

/**
 * Created by qianhechen on 17/2/21.
 */

public interface IOrderInfoContract {

    interface  OrderInfoView extends IBaseView{
        void setPresenter();

        void setView(OrderListInfo orderListInfo);

        void loadListError();
    }

    interface OrderInfoPresenter extends IBasePresenter{

        void getOrderInfo(String zorderinfo_id, String orderinfo_no,OrderListInfo.OrderMoreInfo orderMoreInfo);

        void getPayMoney(String zorder_no);//支付宝支付
    }

    /**
     * 订单删除
     */
    interface OrderDeleteView extends IBaseView {
        void setEventBus();
    }
    interface OrderDeletePresenter extends IBasePresenter{
        void getHttpDelete(String zorderinfo_id);
    }
    /**
     * 物流查询
     */
    interface LogisticsView extends IBaseView{
        void setnotifyDate(LogisticsInfo logisticsInfo);
    };
    interface LogisticsPresenter extends IBasePresenter{
        void getLogisticsList(String logistics_no, String orderinfo_id);
    }
    /**
     * 售后申请查询和售后申请
     */
    interface  ApplaySearchView extends  IBaseView{
        void  setnotifyDate(AfterSaleBeanInfo afterSaleBeanInfo);
        void setDismissView();
    }
    interface  ApplaySearchPresenter extends IBasePresenter{
        void getHttpApplayList(String zorderinfo_id);
        void getHttpApplayAfter(List<OrderListInfo.OrderMoreInfo> orderMoreInfoList, String reason, String state);
    }
//    /**
//     * 售后申请
//     */
//    interface  ApplayAfterView extends  IBaseView{
//        void setDismissView();
//    }
//    interface  ApplayAfterPresenter extends  IBasePresenter{
//        void getHttpApplayAfter(List<OrderListInfo.OrderMoreInfo> orderMoreInfoList, String reason, String state);
//    }
}
