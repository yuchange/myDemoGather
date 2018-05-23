package com.zyf.bings.bingos.order.http;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zyf.bings.bingos.cart.http.CartStates;
import com.zyf.bings.bingos.event.InitOrderCountEvent;
import com.zyf.bings.bingos.order.bean.OrderCountBean;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 *
 * @author wangshiqi
 * @date 2017/10/19
 */

public class OrderCountClient {
    /**
     * 订单数量
     */
    private static final String METHOD = "zorder.number.query";
    private static final String URL = "/zorderNumberQuery.shtml";

    public static void getOrderCount() {
        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getStringFromSp(Config.MEMBER_ID, "");
        if ("".equals(memberId) || TextUtils.isEmpty(memberId)) {
            return;
        }
        params.put(CartStates.MEMBER_ID, memberId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, URL, OrderCountClient.class, null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    OrderCountBean orderCountBean = gson.fromJson(data,OrderCountBean.class);
                    EventBus.getDefault().postSticky(new InitOrderCountEvent(orderCountBean.getZorderNumberList()));
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }
}
