package com.zyf.bings.bingos.goods;

import android.content.Context;

import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.goods.bean.OrderConfirmBean;
import com.zyf.bings.bingos.order.bean.CreateOrderInfo;

/**
 * Created by zhangyifei on 17/9/15.
 */

public interface IOrderConfirmContract {

    interface OrderConfirmView extends IBaseView {
        void notifyOrderConfirmRefresh(OrderConfirmBean orderConfirmBean);

        void listErr(String errMsg);


        Context getViewContext();

        void createOrderSuccess(String zorder_no,String zorder_id);

        void createOrderFailed(String errMsg);

    }

    interface OrderConfirmPresenter extends IBasePresenter {
        void getOrderConfirmData(String goodsId, String goodsCount);

        void creatOrder(CreateOrderInfo createOrderInfo);

    }

}
