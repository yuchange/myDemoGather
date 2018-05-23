package com.zyf.bings.bingos.pay;

import android.support.v4.util.ArrayMap;

import com.lzy.okgo.OkGo;
import com.zyf.bings.bingos.pay.bean.HaierPayInfo;
import com.zyf.bings.bingos.pay.bean.OrderState;
import com.zyf.bings.bingos.pay.bean.ZorderStatus;
import com.zyf.bings.bingos.pay.http.PayClient;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import java.util.Map;

/**
 * Created by zhangyifei on 17/9/20.
 */

public class PayPresenter implements IPayContract.PayPresenter {
    IPayContract.PayView mPayView;


    public static String ZORDER_NO = "zorder_no";


    //支付信息查询
    private static final String HAIERPAY_URL = "/haierpayListQuery.shtml";
    private static final String HAIERPAY_METHOD = "haierpay.list.query";

    //订单状态查询
    private static final String STATUS_TITLE = "/zorderStatusQuery.shtml";
    private static final String ORDERSTATUS_QUERY_METHOD = "zorder.status.query";

    public PayPresenter(IPayContract.PayView payView) {
        this.mPayView = payView;
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void unsubscribe() {

        if (mPayView != null) {
            mPayView = null;
        }
        OkGo.getInstance().cancelTag(PayClient.class);
    }

    @Override
    public void getPayInfo(String zorder_no) {
        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        params.put(Config.MEMBER_ID, "13840933985");
        params.put(ZORDER_NO, zorder_no);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, HAIERPAY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, HAIERPAY_URL, PayClient.class, null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        HaierPayInfo haierPayInfo = GsonFactory.fromJson(data, HaierPayInfo.class);
                        if (mPayView != null) mPayView.notifyPayInfo(haierPayInfo);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mPayView != null) mPayView.getPayInfoError(msg);
                    }
                });
    }

    @Override
    public void getOrderStatus(String zorder_no) {
        Map<String, String> map1 = new ArrayMap<>();
        map1.put(ZORDER_NO, zorder_no);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ORDERSTATUS_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(map1));
        RxOkClient.doPost(map, STATUS_TITLE, PayPresenter.this, null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        ZorderStatus zorderStatus = GsonFactory.fromJson(data, ZorderStatus.class);
                        OrderState orderState = zorderStatus.getZorderstatus();
                        String state = orderState.getOrderStatus();
                        if (state != null) {
                            if ("1".equals(state)) {
                                if (mPayView != null) mPayView.paySuccess("成功");
                            } else if ("99".equals(state)) {
                                if (mPayView != null)
                                    mPayView.payError(orderState.getFailure_reason(),1);
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mPayView != null) mPayView.getPayInfoError(msg);
                    }
                })
        ;
    }
}
