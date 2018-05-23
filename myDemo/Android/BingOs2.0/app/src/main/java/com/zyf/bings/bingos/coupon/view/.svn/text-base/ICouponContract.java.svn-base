package com.zyf.bings.bingos.coupon.view;

import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.coupon.bean.CouponCountBean;
import com.zyf.bings.bingos.coupon.bean.CouponListBean;
import com.zyf.bings.bingos.coupon.bean.OrderCouponBean;

import java.util.List;

/**
 * Created by wangshiqi on 2017/10/27.
 */

public interface ICouponContract {
    interface CartCouponListView extends IBaseView {
        void notifyListData(List<OrderCouponBean.CouponInfoListBean> couponInfoListBeen);
    }

    interface CartCouponPresener {
        void getCartCouponList(List<String> goodsIdList, List<String> goodCountList, String state);
    }
    interface CouponListView extends IBaseView {
        void notifyListData(List<CouponListBean.CouponInfoListBean> couponInfoListBeen);

        void notifyListTitleData(CouponCountBean couponCountBean);

        void deleteSuccess(int position);

        void listEmpty();
    }

    interface CouponPresenter {
        void getCouponList(String state);

        void getCouponTitleData();

        void deleteCoupon(String couponId, int position);
    }
}
