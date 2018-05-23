package com.bingstar.bingmall.cart;

import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.Utils.ACache;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.bean.ProductInfos;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by qianhechen on 17/2/13.
 */

public class CartPresenter implements ICartContract.CartPresenter {

    private ICartContract.CartListView cartListView;


    public CartPresenter(ICartContract.CartListView cartListView) {
        this.cartListView = cartListView;
    }

    @Override
    public void unBind() {
        cartListView = null;
    }


    @Override
    public void getCartList(final List<ProductInfoAdd> productInfosList, String category, String currentPage) {
        boolean isAll = false;
        Map<String, String> map = new ArrayMap<>();
        map.put(CartStates.MEMBERID, User.getIntance().getMemberId());
//        if(category!=null){
//            map.put(CartStates.CATEGORYID,category);
//        }
        map.put(CartStates.PAGESIZE, "100000");
        map.put(CartStates.CURRENTINDEX, "0");

        CartClient.getCartList(map, new ClientCallback<ProductInfos>() {
            @Override
            public void onSuccess(ProductInfos productInfos) {
                productInfosList.clear();
                productInfosList.addAll(productInfos.getProductInfoList());
                Collections.reverse(productInfosList); //购物车倒序显示
                if (cartListView != null) {
                    cartListView.notifyCartList();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (cartListView != null) {
                    cartListView.listErr();
                }
            }
        });

    }


    @Override
    public void getCartTitle(final List<CartTitle.CartCategoryInfo> cartTitleList, String memberId) {
        Map<String, String> map = new ArrayMap<>();
        map.put(CartStates.MEMBERID, User.getIntance().getMemberId());
        map.put(CartStates.SOURCE, "0");
        CartClient.getCartTitle(map, new ClientCallback<CartTitle>() {
            @Override
            public void onSuccess(CartTitle cartTitle) {
                cartTitleList.clear();
                CartTitle cart = new CartTitle();
                CartTitle.CartCategoryInfo cartCategoryInfo = cart.new CartCategoryInfo();
                cartCategoryInfo.setCategoryId("");
                cartCategoryInfo.setCategoryName("全部");
                cartCategoryInfo.setCategoryOrder("");
                cartTitleList.add(cartCategoryInfo);
                for (int i = 0; i < cartTitle.getCategoryList().size(); i++) {
                    cartTitleList.add(cartTitle.getCategoryList().get(i));
                }
                if (cartListView != null) {
                    cartListView.notifyTitle();
                }
                if (cartListView != null) {
                    cartListView.notifyTitleRefresh();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (cartListView != null) {
                    cartListView.listErr();
                }
            }
        });
    }

    @Override
    public void getCouponList(final List<Coupon.CouponInfo> couponList, List<String> goodsId, List<String> totalPrice, String couponState) {
        Map<String, Object> map = new ArrayMap<>();
        map.put(CartStates.MEMBER_ID, User.getIntance().getMemberId());
        map.put(CartStates.GOODS_ID, goodsId);
        map.put(CartStates.TOTAL_PRICE, totalPrice);
        map.put(CartStates.COUPON_STATE, couponState);
        CartClient.getCouponList(map, new ClientCallback<Coupon>() {
            @Override
            public void onSuccess(Coupon coupon) {
                couponList.clear();
                couponList.addAll(coupon.getCouponInfoList());
                for (int i = 0; i < couponList.size(); i++) {
                    couponList.get(i).setSelected(false);
                }
                if (cartListView != null) {
                    cartListView.notifyCard();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (cartListView != null) {
                    cartListView.listErr();
                }
            }
        });
    }

    public static final String COUPON = "{\n" +
            "  \"couponInfoList\": [\n" +
            "    {\n" +
            "      \"couponRange\": \"部分商品使用\",\n" +
            "      \"couponName\": \"测试2\",\n" +
            "      \"remindText\": \"8小时后过期\",\n" +
            "      \"couponMoney\": \"4\",\n" +
            "      \"couponId\": \"238\",\n" +
            "      \"remindFlag\": \"1\",\n" +
            "      \"couponDetail\": \"无门槛使用\",\n" +
            "      \"couponValidity\": \"2017.08.24 18:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"couponRange\": \"部分商品使用\",\n" +
            "      \"couponName\": \"测试2\",\n" +
            "      \"remindText\": \"8小时后过期\",\n" +
            "      \"couponMoney\": \"4\",\n" +
            "      \"couponId\": \"238\",\n" +
            "      \"remindFlag\": \"1\",\n" +
            "      \"couponDetail\": \"无门槛使用\",\n" +
            "      \"couponValidity\": \"2017.08.24 18:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"couponRange\": \"部分商品使用\",\n" +
            "      \"couponName\": \"测试2\",\n" +
            "      \"remindText\": \"8小时后过期\",\n" +
            "      \"couponMoney\": \"4\",\n" +
            "      \"couponId\": \"238\",\n" +
            "      \"remindFlag\": \"1\",\n" +
            "      \"couponDetail\": \"无门槛使用\",\n" +
            "      \"couponValidity\": \"2017.08.24 18:00\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public Coupon mockData() {
        Coupon coupon = JSON.parseObject(COUPON, Coupon.class);
        return coupon;
    }

    @Override
    public void getOneKeyCartOrder(String goodsId, String count) {
        Map<String, String> map = new ArrayMap<>();
        map.put(CartStates.MEMBER_ID, User.getIntance().getMemberId());
        map.put(CartStates.ONE_GOODS_ID, goodsId);
        map.put(CartStates.ONE_GOODS_COUNT, count);
        CartClient.getOneKeyOrder(map, new ClientCallback<OneKeyCartOrder>() {
            @Override
            public void onSuccess(OneKeyCartOrder oneKeyCartOrder) {
                if (cartListView != null) {
                    cartListView.notifyOneCart(oneKeyCartOrder);
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (cartListView != null) {
                    cartListView.listErr();
                }
            }
        });

    }
}
