package com.zyf.bings.bingos.cart;


import android.content.Context;

import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.cart.bean.Coupon;
import com.zyf.bings.bingos.cart.bean.ShopCartGoodsBean;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/2/13.
 */

public interface ICartContract {

    interface CartView extends IBaseView {
        void notifyCartList(ShopCartGoodsBean shopCartGoods);

        void batchDeleteCartError(String msg);

        void batchDeleteCartSuccess();


        void deleteItemCartError(String msg);

        void deleteItemCartSuccess();

        void listErr(String errMsg);

        void getCouponSuccess(Coupon coupon);

        Context getViewContext();

    }

    interface CartPresenter extends IBasePresenter {
        void getCartList(String category, String pageSize, String currentIndex);

        void batchDeleteCart(ArrayList<String> goodsId);

        void deleteItemCart(ShopCartGoodsBean.ProductInfoListBean infoListBean);

        void getCoupon();

        void cancelRequest();
    }
}
