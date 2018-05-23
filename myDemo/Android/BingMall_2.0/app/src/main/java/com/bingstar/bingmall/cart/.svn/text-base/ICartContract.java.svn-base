package com.bingstar.bingmall.cart;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.bean.ProductInfos;

import java.util.List;

/**
 * Created by qianhechen on 17/2/13.
 */

public interface ICartContract {

    interface CartListView {
        void setPresenter();

        void notifyCartList();

        void notifyTitle();

        void notifyTitleRefresh();

        void listErr();

        void notifyCard();

        void notifyOneCart(OneKeyCartOrder oneKeyCartOrder);

        void notifyPrice();
    }

    interface CartPresenter extends IBasePresenter {
        void getCartList(List<ProductInfoAdd> productInfosList, String category, String currentPage);

        void getCartTitle(List<CartTitle.CartCategoryInfo> cartTitleList, String memberId);

        void getCouponList(List<Coupon.CouponInfo> couponList, List<String> goodsId, List<String> totalPrice, String couponState);

        void getOneKeyCartOrder(String goodsId, String count);
    }
}
