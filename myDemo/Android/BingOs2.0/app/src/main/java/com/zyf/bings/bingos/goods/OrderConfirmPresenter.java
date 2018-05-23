package com.zyf.bings.bingos.goods;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zyf.bings.bingos.goods.bean.OrderConfirmBean;
import com.zyf.bings.bingos.goods.bean.ZorderInfoBean;
import com.zyf.bings.bingos.order.bean.CreateOrderInfo;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import java.util.Map;

/**
 * Created by zhangyifei on 17/9/15.
 */

public class OrderConfirmPresenter implements IOrderConfirmContract.OrderConfirmPresenter {

    IOrderConfirmContract.OrderConfirmView mOrderConfirmView;

    public OrderConfirmPresenter(IOrderConfirmContract.OrderConfirmView orderConfirmView) {
        mOrderConfirmView = orderConfirmView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mOrderConfirmView != null) {
            mOrderConfirmView = null;
        }
    }

    //确认下单页面
    private static final String CART_ADD_METHOD = "buy.now";
    private static final String CART_ADD_URL = "/buyNow.shtml";

    public static final String GOODS_ID_PARAMS = "goodsId";
    public static final String GOODS_COUNT_PARAMS = "goodsCount";

    @Override
    public void getOrderConfirmData(String goodsId, String goodsCount) {

        Map<String, Object> requestMap = new ArrayMap<>();
        String memberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        requestMap.put(Config.MEMBER_ID, memberId);
        requestMap.put(GOODS_ID_PARAMS, goodsId);
        requestMap.put(GOODS_COUNT_PARAMS, goodsCount);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(requestMap));
        RxOkClient.doPost(map, CART_ADD_URL, this, null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        OrderConfirmBean orderConfirmBean = GsonFactory.fromJson(data, OrderConfirmBean.class);
                        if (mOrderConfirmView != null) {
                            mOrderConfirmView.notifyOrderConfirmRefresh(orderConfirmBean);
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mOrderConfirmView != null) {
                            mOrderConfirmView.listErr(msg);
                        }
                    }
                });

    }

    //订单创建
    private static final String ORDER_CREATE_TITLE = "/orderCreate.shtml";
    private static final String ORDER_CREATE_METHOD = "order.create";
    @Override
    public void creatOrder(CreateOrderInfo createOrderInfo) {
        Gson gson = new Gson();
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDER_CREATE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, gson.toJson(createOrderInfo));
        RxOkClient.doPost(map, ORDER_CREATE_TITLE, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                ZorderInfoBean zorderInfoBean = GsonFactory.fromJson(data, ZorderInfoBean.class);
                if (!TextUtils.isEmpty(zorderInfoBean.getZorderinfo().getZorder_no())) {
                    mOrderConfirmView.createOrderSuccess(zorderInfoBean.getZorderinfo().getZorder_no(),zorderInfoBean.getZorderinfo().getZorderinfo_id());
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                mOrderConfirmView.createOrderFailed(msg);
            }
        });
    }
}
