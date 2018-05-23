package com.zyf.bings.bingos.cart;

import android.support.v4.util.ArrayMap;

import com.zyf.bings.bingos.cart.bean.ShopCartGoodsBean;
import com.zyf.bings.bingos.cart.bean.ShopCartUpdateBean;
import com.zyf.bings.bingos.cart.http.CartClient;
import com.zyf.bings.bingos.cart.http.CartStates;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zhangyifei on 17/9/3.
 */

public class CartPresenter implements ICartContract.CartPresenter {

    ICartContract.CartView mCartListView;

    public CartPresenter(ICartContract.CartView cartView) {
        mCartListView = cartView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCartListView = null;
    }


    //购物车列表
    private static final String CART_QUERY_METHOD = "shoppingCart.list.query";
    private static final String CART_QUERY_URL = "/shoppingCartListQuery.shtml";


    //购物车批量删除

    public static final String CART_BATCH_DELE_METHOD = "delete.good";
    public static final String CART_BATCH_DELE_URL = "/deleteGood.shtml";


    public static final String PARAMS_GOODSID = "goodIdList";
    public static final String PARAMS_MEMBER_ID = "memberId";

    @Override
    public void getCartList(String category, String pageSize, String currentIndex) {

        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        params.put(CartStates.MEMBERID, memberId);
        params.put(CartStates.PAGESIZE, pageSize);
        params.put(CartStates.CURRENTINDEX, currentIndex);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, CART_QUERY_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                ShopCartGoodsBean shopCartGoodsBean = GsonFactory.fromJson(data, ShopCartGoodsBean.class);
                if (mCartListView != null) mCartListView.notifyCartList(shopCartGoodsBean);
            }

            @Override
            public void onFailed(int code, String msg) {
                if (mCartListView != null) mCartListView.listErr(msg);
            }
        });


    }

    @Override
    public void batchDeleteCart(ArrayList<String> goodsId) {
        Map<String, Object> params = new ArrayMap<>();

        String string = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        params.put(PARAMS_GOODSID, goodsId);
        params.put(PARAMS_MEMBER_ID, string);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_BATCH_DELE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(params));
        RxOkClient.doPost(map, CART_BATCH_DELE_URL, getClass().getSimpleName(), PBUtil.getPD(mCartListView.getViewContext()))
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mCartListView != null) mCartListView.batchDeleteCartSuccess();
                        CartCountClient.getCartCount();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mCartListView != null) mCartListView.batchDeleteCartError(msg);
                    }
                });
    }

    @Override
    public void deleteItemCart(ShopCartGoodsBean.ProductInfoListBean infoListBean) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CartClient.CART_UPDATE_METHOD);
        ShopCartUpdateBean productInfoUpdate = new ShopCartUpdateBean();
        infoListBean.setCount("0");
        productInfoUpdate.setMemberId(infoListBean.getMemberId());
        productInfoUpdate.setProductInfo(infoListBean);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(productInfoUpdate));
        RxOkClient.doPost(map, CartClient.CART_UPDATE_URL, getClass().getSimpleName(), PBUtil.getPD(mCartListView.getViewContext()))
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mCartListView != null) mCartListView.deleteItemCartSuccess();
                        CartCountClient.getCartCount();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mCartListView != null) mCartListView.deleteItemCartError(msg);

                    }
                });
    }

    @Override
    public void getCoupon() {

    }

    @Override
    public void cancelRequest() {
        RxOkClient.cancelRequest(getClass().getSimpleName());
    }
}
