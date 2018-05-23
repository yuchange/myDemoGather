package com.bingstar.bingmall.cart.http;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.bean.ProductInfoUpdate;
import com.bingstar.bingmall.cart.bean.ProductInfos;
import com.bingstar.bingmall.cart.bean.ShopCar;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.user.bean.User;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by qianhechen on 17/2/13.
 */

public class CartClient {

    private static final String ACTION_NAME = "添加购物车成功";
    //购物车列表
    private static final String CART_QUERY_METHOD = "shoppingCart.list.query";
    private static final String CART_QUERY_URL = "/shoppingCartListQuery.shtml";
    //添加购物车
    private static final String CART_ADD_METHOD = "shoppingCart.add";
    private static final String CART_ADD_URL = "/shoppingCartAdd.shtml";
    //购物车更新
    private static final String CART_UPDATE_METHOD = "shoppingCart.update";
    private static final String CART_UPDATE_URL = "/shoppingCartUpdate.shtml";
    //购物车类目
    private static final String CART_TITLE_METHOD = "shoppingCart.categoryInfo.query";
    private static final String CART_TITLE_URL = "/shoppingCartCategoryInfoQuery.shtml";
    //优惠券列表
    private static final String COUPON_LIST_METHOD = "coupon.list.get";
    private static final String COUPON_LIST_URL = "/couponListGet.shtml";
    //优惠券展示列表
    private static final String COUPON_LISTSHOW_METHOD = "coupon.list.query";
    private static final String COUPON_LISTSHOW_URL = "/couponListQuery.shtml";

    private static final String ONEKEY_ORDER_URL = "/orderGoodGet.shtml";
    private static final String ONEKEY_ORDER_METHOD = "orderGood.get";


    public static void getCartList(Map<String, String> params, final ClientCallback<ProductInfos> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(CART_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ProductInfos productInfos = JSON.parseObject(str, ProductInfos.class);
                if (productInfos != null) {
                    callback.onSuccess(productInfos);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }


    public static void addCart(final ProductInfos productInfo, final ClientCallback<ShopCar> callback) {
        Map<String, String> map = new ArrayMap<>();
        String memberId = User.getIntance().getMemberId();
        if (memberId == null || memberId.equals("")) {
            callback.onFail(BingNetStates.NEED_LOGIN, BingNetStates.NEED_LOGIN_MSG);
        }
        map.put(BingNetStates.METHOD, CART_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(productInfo));
        BingstarNet.doPost(CART_ADD_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ShopCar shopCar = JSON.parseObject(str, ShopCar.class);
                callback.onSuccess(shopCar);
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }


    //连续请求
    public static void addCart2(final ProductInfos productInfo, final ClientCallback<ShopCar> callback) {
        Map<String, String> map = new ArrayMap<>();
        String memberId = User.getIntance().getMemberId();
        if (memberId == null || memberId.equals("")) {
            callback.onFail(BingNetStates.NEED_LOGIN, BingNetStates.NEED_LOGIN_MSG);
        }
        map.put(BingNetStates.METHOD, CART_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(productInfo));
        BingstarNet.doPost2(CART_ADD_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ShopCar shopCar = JSON.parseObject(str, ShopCar.class);
                callback.onSuccess(shopCar);
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

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

    public static void getCartTitle(Map<String, String> params, final ClientCallback<CartTitle> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_TITLE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(CART_TITLE_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                CartTitle cartTitle = JSON.parseObject(str, CartTitle.class);
                if (cartTitle != null && cartTitle.getCategoryList() != null) {
                    callback.onSuccess(cartTitle);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void getCouponList(Map<String, Object> params, final ClientCallback<Coupon> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_LIST_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        Log.i("Cart",JSON.toJSONString(params));
        BingstarNet.doPost(COUPON_LIST_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {

                Log.i("Cart",str);
                Coupon coupon = JSON.parseObject(str, Coupon.class);
                if (coupon != null) {
                    callback.onSuccess(coupon);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void getCouponShowList(Map<String, String> params, final ClientCallback<Coupon> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_LISTSHOW_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost2(COUPON_LISTSHOW_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                Coupon coupon = JSON.parseObject(str, Coupon.class);
                if (coupon != null) {
                    callback.onSuccess(coupon);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }


    public static void getOneKeyOrder(Map<String, String> params, final ClientCallback<OneKeyCartOrder> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, ONEKEY_ORDER_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(ONEKEY_ORDER_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                Log.i("ONe",str);
                OneKeyCartOrder oneKeyCartOrder = JSON.parseObject(str, OneKeyCartOrder.class);
                if (oneKeyCartOrder != null) {
                    callback.onSuccess(oneKeyCartOrder);
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                Log.i("ONe",error);
                callback.onFail(code, error);
            }
        });
    }
}
