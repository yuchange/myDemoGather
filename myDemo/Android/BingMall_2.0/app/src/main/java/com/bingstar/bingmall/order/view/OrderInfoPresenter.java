package com.bingstar.bingmall.order.view;

import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.main.bean.PayEvent;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.sdk.pay.PayClient;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianhechen on 17/2/21.
 */

public class OrderInfoPresenter implements IOrderInfoContract.OrderInfoPresenter {

    private IOrderInfoContract.OrderInfoView orderInfoView;

    public OrderInfoPresenter(IOrderInfoContract.OrderInfoView orderInfoView) {
        this.orderInfoView = orderInfoView;
    }

    @Override
    public void getOrderInfo(String zorderinfo_id, String orderinfo_no, final OrderListInfo.OrderMoreInfo orderMoreInfo) {
//        访问网络获取数据
        OrderClient.getOrderInfo(BeanTranslater.getOrderInfoList(zorderinfo_id, orderinfo_no), new ClientCallback<OrderListInfo>() {
            @Override
            public void onSuccess(OrderListInfo orderListInfo) {
                if (orderInfoView != null) {
                    orderInfoView.setView(orderListInfo);
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (code > 1000) {
                    if (orderInfoView != null) {
                        orderInfoView.showToast(error);
                    }
                }
                if (orderInfoView != null) {
                    orderInfoView.loadListError();
                }

            }
        });
    }

    /**
     * 支付宝支付
     *
     * @param zorder_no
     */
    @Override
    public void getPayMoney(String zorder_no) {
        EventBus.getDefault().post(new PayEvent(zorder_no));
    }

    @Override
    public void unBind() {
        orderInfoView = null;
    }
}
