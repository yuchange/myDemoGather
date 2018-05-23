package com.zyf.bings.bingos.manager;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.zyf.bings.bingos.cart.http.CartStates;
import com.zyf.bings.bingos.event.LogOutEvent;
import com.zyf.bings.bingos.order.http.OrderCountClient;
import com.zyf.bings.bingos.utils.CommonUtils;
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
 * @date 2017/10/25
 */

public class LogOutClient {

    private static final String METHOD = "member.loginOut";
    private static final String LOGOUT_URL = "/loginOut.shtml";
    public static void logOut() {
        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getStringFromSp(Config.MEMBER_ID, "");
        String macId = CommonUtils.getWifiMac();
        if ("".equals(memberId) || TextUtils.isEmpty(memberId)) {
            return;
        }
        params.put(CartStates.MEMBER_ID, memberId);
        params.put("mac_id", macId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, LOGOUT_URL, OrderCountClient.class, null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                SPUtil.clear();
                ToastUtils.showToast("退出成功");
                EventBus.getDefault().post(new LogOutEvent("logout"));
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

}
