package com.zyf.bings.bingos.order.presenter;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zyf.bings.bingos.order.IOrderContract;
import com.zyf.bings.bingos.order.bean.OrderListBean;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangshiqi on 2017/9/25.
 */

public class OrderPresenter implements IOrderContract.OrderPresenter {
    private IOrderContract.OrderListView orderListView;

    public OrderPresenter(IOrderContract.OrderListView orderListView) {
        this.orderListView = orderListView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        orderListView = null;
    }

    public static final String MEMBERID = "memberId";
    public static final String STATE = "state";
    public static final String PAGESIZE = "pageSize";
    public static final String CURRENTINDEX = "currentIndex";
    //订单列表查询
    private static final String TITLE = "/orderListQuery.shtml";
    private static final String LIST_QUERY_METHOD = "order.list.query";
    @Override
    public void getAllOrderList(String memberId, String state, int pageSize, int currentPage) {
        Map<String, String> params = new ArrayMap<>();
        params.put(MEMBERID, memberId);
        params.put(STATE, state);
        params.put(PAGESIZE, pageSize + "");
        params.put(CURRENTINDEX, currentPage + "");
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, TITLE, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    OrderListBean orderListBean = gson.fromJson(data, OrderListBean.class);
                    if (orderListBean != null) {
                        List<OrderListBean.ZorderListBean> zorderList = orderListBean.getZorderList();
                        orderListView.notifyOrderList(zorderList);
                    } else {
                        orderListView.listErr();
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    /**
     * 提醒发货
     *
     * @param zorderInfoId
     */
    private static final String NOTIFICATION_URL = Config.AFTER_SALE +"/remindShipment.shtml";
    private static final String NOTIFICCATION_USER_METHOD = "remind.shipment";
    private static final String ZORDERINFO_ID = "zorderinfo_id";
    @Override
    public void notificationUser(String zorderInfoId) {
        Map<String, String> params = new ArrayMap<>();
        params.put(ZORDERINFO_ID, zorderInfoId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, NOTIFICCATION_USER_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, NOTIFICATION_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                orderListView.remindSuccess();
            }

            @Override
            public void onFailed(int code, String msg) {
                orderListView.remindFailed(msg);
            }
        });
    }
}
