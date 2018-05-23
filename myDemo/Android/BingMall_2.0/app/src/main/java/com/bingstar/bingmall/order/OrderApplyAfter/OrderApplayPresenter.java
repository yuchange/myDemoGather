package com.bingstar.bingmall.order.OrderApplyAfter;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.order.view.IOrderInfoContract;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * 售后申请查询presenter
 * Created by liumengqiang on 2017/3/21.
 */

public class OrderApplayPresenter implements IOrderInfoContract.ApplaySearchPresenter{
    /**
     * 售后申请查询
     */
    private IOrderInfoContract.ApplaySearchView applaySearchView;

    public OrderApplayPresenter(IOrderInfoContract.ApplaySearchView applaySearchView) {
        this.applaySearchView = applaySearchView;
    }

    @Override
    public void getHttpApplayList(String zorderinfo_id) {
        OrderClient.getOrderApplaySearch(BeanTranslater.getDeleteOrder(zorderinfo_id), new ClientCallback<AfterSaleBeanInfo>() {
            @Override
            public void onSuccess(AfterSaleBeanInfo afterSaleBeanInfo) {
                if (applaySearchView!=null) {
                    applaySearchView.setnotifyDate(afterSaleBeanInfo);
                }
            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.OrderApplyDialog_final);
            }
        });
    }


    /**
     * 售后申请
     */
    @Override
    public void getHttpApplayAfter(List<OrderListInfo.OrderMoreInfo> orderMoreInfoList, String reason, String zstate) {
        OrderClient.getApplayAfter(BeanTranslater.getAfterSaleBeanInfo(orderMoreInfoList, reason, zstate), new ClientCallback() {
            @Override
            public void onSuccess(Object o) {
                if (applaySearchView!=null) {
                    applaySearchView.setDismissView();
                }
            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.OrderApplyEditDialog_final);
            }
        });
    }

    @Override
    public void unBind() {
        if(applaySearchView != null){
            applaySearchView = null;
        }
    }

}
