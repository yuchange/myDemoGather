package com.bingstar.bingmall.order.OrderDelete;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.order.view.IOrderInfoContract;

import java.util.Map;

/**
 * 删除订单presenter
 * Created by liumengqiang on 2017/3/21.
 */

public class OrderDetailDeletePresenter implements IOrderInfoContract.OrderDeletePresenter {
    private IOrderInfoContract.OrderDeleteView orderDeleteView;

    public OrderDetailDeletePresenter(IOrderInfoContract.OrderDeleteView orderDeleteView) {
        this.orderDeleteView = orderDeleteView;
    }

    @Override
    public void getHttpDelete(String zorderinfo_id) {
        OrderClient.getOrderDelete(BeanTranslater.getDeleteOrder(zorderinfo_id), new ClientCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                if(orderDeleteView != null){
                    orderDeleteView.setEventBus();
                }
            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.OrderInfo_delete_final);
            }
        });
    }

    @Override
    public void unBind() {
        if(orderDeleteView != null){
            orderDeleteView = null;
        }
    }
}
