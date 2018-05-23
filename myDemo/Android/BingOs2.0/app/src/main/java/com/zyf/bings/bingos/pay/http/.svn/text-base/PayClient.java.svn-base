package com.zyf.bings.bingos.pay.http;

import android.support.v4.util.ArrayMap;


import com.google.gson.JsonObject;
import com.zyf.bings.bingos.cart.http.CartStates;
import com.zyf.bings.bingos.pay.bean.HaierPayInfo;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 下午4:55
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 下午4:55
 * @modify by reason:{方法名}:{原因}
 */

public class PayClient {

    public static String ZORDER_NO = "zorder_no";

    private static final String HAIERPAY_URL = "/haierpayListQuery.shtml";
    private static final String HAIERPAY_METHOD = "haierpay.list.query";


    public static void getHaierPayMessage(String zorder_no) {
        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        params.put(CartStates.MEMBERID, memberId);
        params.put(ZORDER_NO, zorder_no);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, HAIERPAY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, HAIERPAY_URL, PayClient.class, null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        HaierPayInfo haierPayInfo = GsonFactory.fromJson(data, HaierPayInfo.class);
                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                });
    }
}
