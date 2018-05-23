package com.zyf.bings.bingos.coupon.presenter;

import android.support.v4.util.ArrayMap;

import com.zyf.bings.bingos.coupon.bean.OrderCouponBean;
import com.zyf.bings.bingos.coupon.view.ICouponContract;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangshiqi on 2017/11/3.
 */

public class CouponCartPresenter implements ICouponContract.CartCouponPresener {
    private ICouponContract.CartCouponListView mCartCouponListView;
    private static final String COUPON_LIST_GET_METHOD = "coupon.list.get";
    private static final String COUPON_LIST_GET_URL = "/couponListGet.shtml";
    public CouponCartPresenter(ICouponContract.CartCouponListView cartCouponListView) {
        this.mCartCouponListView = cartCouponListView;
    }


    /**
     * 购物车获取优惠券
     */

    @Override
    public void getCartCouponList(List<String> goodsIdList, List<String> goodCountList, String state) {
        Map<String, Object> params = new ArrayMap<>();
        params.put("member_id", SPUtil.getStringFromSp(Config.MEMBER_ID, ""));
        params.put("state", state);
        params.put("goodsIdList", goodsIdList);
        params.put("goodCountList", goodCountList);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_LIST_GET_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.mapToJson(params));
        OkGoUtils.doStringPostRequest(map, COUPON_LIST_GET_URL, getClass().getSimpleName(), new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                OrderCouponBean orderCouponBean = GsonFactory.fromJson(str, OrderCouponBean.class);
                if (orderCouponBean.getCouponInfoList().size() > 0) {
                    mCartCouponListView.notifyListData(orderCouponBean.getCouponInfoList());
                } else {
                    ToastUtils.showToast("无可用优惠券");
                }
            }

            @Override
            public void onFail(int code, String error) {
                ToastUtils.showToast(error);
            }
        });
    }
}
