package com.zyf.bings.bingos.cart.http;

import android.support.v4.util.ArrayMap;
import android.util.Log;


import com.google.gson.Gson;
import com.zyf.bings.bingos.cart.bean.Coupon;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import java.util.Map;


/**
 * Created by qianhechen on 17/2/13.
 */

public class CartClient {

    public static final String ACTION_NAME = "添加购物车成功";
    //购物车列表
    public static final String CART_QUERY_METHOD = "shoppingCart.list.query";
    public static final String CART_QUERY_URL = "/shoppingCartListQuery.shtml";
    //添加购物车
    public static final String CART_ADD_METHOD = "shoppingCart.add";
    public static final String CART_ADD_URL = "/shoppingCartAdd.shtml";
    //购物车更新
    public static final String CART_UPDATE_METHOD = "shoppingCart.update";
    public static final String CART_UPDATE_URL = "/shoppingCartUpdate.shtml";
    //购物车类目
    public static final String CART_TITLE_METHOD = "shoppingCart.categoryInfo.query";
    public static final String CART_TITLE_URL = "/shoppingCartCategoryInfoQuery.shtml";
    //优惠券列表
    public static final String COUPON_LIST_METHOD = "coupon.list.get";
    public static final String COUPON_LIST_URL = "/couponListGet.shtml";
    //优惠券展示列表
    public static final String COUPON_LISTSHOW_METHOD = "coupon.list.query";
    public static final String COUPON_LISTSHOW_URL = "/couponListQuery.shtml";

    public static final String ONEKEY_ORDER_URL = "/orderGoodGet.shtml";
    public static final String ONEKEY_ORDER_METHOD = "orderGood.get";

/*

    public static void updateCart(ProductInfoUpdate productInfoUpdate, final ProductInfoAdd.UpdateNumCallback updateNum) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_UPDATE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(productInfoUpdate));
        BingstarNet.doPost2(CART_UPDATE_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                updateNum.update();
            }

            @Override
            public void onFail(int code, String error) {
                updateNum.rollback();
                EventBus.getDefault().post(new EventMsg(CartClient.class, String.valueOf(ErrorFragment.CartFragment)));
            }
        });
    }
*/


    public static void getCouponList(Map<String, String> params, Object tag) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_LIST_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, COUPON_LIST_URL, tag, null);
    }


}
