package com.bingstar.bingmall.cart.view;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.goods.bean.GoodsEntity;

import java.util.ArrayList;

/**
 * Created by zhangyifei on 17/7/1.
 */

public interface IDiscountCartContract {


    interface DiscountCartView extends IBaseView {

        void notifyCouponList(ArrayList<Coupon.CouponInfo> couponInfoLists, String type);


        void showView(int kind, String type);

        void setNum(String type, int num,int totalCount);
    }

    interface DiscountCartPresenter extends IBasePresenter {
        void getCouponShowList(String type);
    }
}
