package com.bingstar.bingmall.cart.view;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.video.lib.SynLinearLayout;


import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zhangyifei on 17/7/1.
 */

public class DiscountPresenter implements IDiscountCartContract.DiscountCartPresenter {
    IDiscountCartContract.DiscountCartView mDiscountCartView;

    public DiscountPresenter(IDiscountCartContract.DiscountCartView discountCartView) {
        mDiscountCartView = discountCartView;
    }

    @Override
    public void unBind() {
        if (mDiscountCartView != null)
            mDiscountCartView = null;
    }

    @Override
    public void getCouponShowList(final String type) {
        Map<String, String> map = new ArrayMap<>();
        map.put(CartStates.MEMBER_ID, User.getIntance().getMemberId());
        map.put(CartStates.STATE, type);
        mDiscountCartView.showView(SynLinearLayout.SHOW_LOAD, type);
        CartClient.getCouponShowList(map, new ClientCallback<Coupon>() {
            @Override
            public void onSuccess(Coupon coupon) {
                mDiscountCartView.showView(SynLinearLayout.SHOW_SUCCESS, type);
                if (coupon.getCouponInfoList().size() == 0) {
                    mDiscountCartView.showView(SynLinearLayout.SHOW_EMPTY, type);
                    return;
                }
                if (coupon.getCouponInfoList().size() > 0) {
                    mDiscountCartView.notifyCouponList(coupon.getCouponInfoList(), type);
                    ArrayList<Coupon.CouponInfo> couponInfoList = coupon.getCouponInfoList();
                    int totalCount = 0;
                    Integer i1 = 0;
                    for (int i = 0; i < couponInfoList.size(); i++) {
                        Coupon.CouponInfo couponInfo = couponInfoList.get(i);
                        try {
                            i1 = Integer.parseInt(couponInfo.getCouponNum());
                        } catch (NumberFormatException e) {
                            i1 = 0;
                        }
                        totalCount += i1;
                    }
                    mDiscountCartView.setNum(type, coupon.getCouponInfoList().size(), totalCount);
                }


            }

            @Override
            public void onFail(int code, String error) {
                mDiscountCartView.showView(SynLinearLayout.SHOW_ERROR, type);
            }
        });
    }
}
