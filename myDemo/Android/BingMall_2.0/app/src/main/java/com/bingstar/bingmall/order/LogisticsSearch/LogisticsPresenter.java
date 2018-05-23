package com.bingstar.bingmall.order.LogisticsSearch;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderLogisticInfo;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.order.view.IOrderInfoContract;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * Created by John on 2017/3/20.
 */

public class LogisticsPresenter implements IOrderInfoContract.LogisticsPresenter {
    private IOrderInfoContract.LogisticsView logisticsView;

    public LogisticsPresenter(IOrderInfoContract.LogisticsView logisticsView) {
        this.logisticsView = logisticsView;
    }

    @Override
    public void getLogisticsList(final String logistics_no, String orderinfo_id) {
        OrderClient.getOrderLogistic(BeanTranslater.getLogistictList(logistics_no, orderinfo_id), new ClientCallback<LogisticsInfo>() {
            @Override
            public void onSuccess(LogisticsInfo logisticsInfo) {
                if (logisticsView!=null) {
                    logisticsView.setnotifyDate(logisticsInfo);
                }
            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.OrderInfo_logistics_final);
            }
        });
    }

    @Override
    public void unBind() {
        if(logisticsView != null){
            logisticsView = null;
        }
    }
}
