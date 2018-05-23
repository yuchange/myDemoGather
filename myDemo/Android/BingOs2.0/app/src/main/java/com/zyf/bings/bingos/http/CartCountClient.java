package com.zyf.bings.bingos.http;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zyf.bings.bingos.cart.http.CartStates;
import com.zyf.bings.bingos.event.MainCartCount;
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
 * Created by zhangyifei on 17/9/19.
 */

public class CartCountClient {


    //购物车数量
    private static final String CART_COUNT_QUERY_METHOD = "shoppingCart.count";
    private static final String CART_COUNT_QUERY_URL = "/shoppingCartCount.shtml";


    public static void getCartCount() {
        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        if(TextUtils.isEmpty(memberId)){
            return;
        }
        params.put(CartStates.MEMBERID, memberId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_COUNT_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, CART_COUNT_QUERY_URL, CartCountClient.class, null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {

                JsonParser parser = new JsonParser();
                JsonObject asJsonObject = parser.parse(data).getAsJsonObject();
                JsonElement count = asJsonObject.get("count");
                String asString = count.getAsString();
                EventBus.getDefault().postSticky(new MainCartCount(asString));
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });


    }
}
