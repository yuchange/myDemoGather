package com.zyf.bings.bingos.coupon.presenter;

import android.support.v4.util.ArrayMap;

import com.zyf.bings.bingos.coupon.bean.CouponCountBean;
import com.zyf.bings.bingos.coupon.bean.CouponListBean;
import com.zyf.bings.bingos.coupon.view.ICouponContract;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.MainBuildConfigure;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.EncryptUtils;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author wangshiqi
 * @date 2017/10/27
 */

public class CouponListPresenter implements ICouponContract.CouponPresenter {
    private ICouponContract.CouponListView mCouponListView;
    private String mCodeStr;

    public CouponListPresenter(ICouponContract.CouponListView couponListView) {
        this.mCouponListView = couponListView;
    }



    /**
     * 优惠券删除
     */
    private static final String COUPON_DELETE_METHOD = "memeber.coupon.delete";
    private static final String COUPON_DELETE_URL = "/member/couponDelete.shtml";

    @Override
    public void deleteCoupon(String couponId, int position) {
        Map<String, String> params = new ArrayMap<>();
        params.put("memberId", SPUtil.getStringFromSp(Config.MEMBER_ID, ""));
        params.put("couponId", couponId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_DELETE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, COUPON_DELETE_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                mCouponListView.deleteSuccess(position);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }
    /**
     * 用户优惠券列表查询
     *
     * @param state
     */
    private static final String COUPON_QUERY_METHOD = "coupon.list.query";
    private static final String COUPON_QUERY_URL = "/couponListQuery.shtml";

    @Override
    public void getCouponList(String state) {
        Map<String, String> params = new ArrayMap<>();
        params.put("member_id", SPUtil.getStringFromSp(Config.MEMBER_ID, ""));
        params.put("state", state);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, COUPON_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, COUPON_QUERY_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                CouponListBean couponListBean = GsonFactory.fromJson(data, CouponListBean.class);
                if (couponListBean.getCouponInfoList() != null) {
                    mCouponListView.notifyListData(couponListBean.getCouponInfoList());
                } else {
                    mCouponListView.listEmpty();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    /**
     * 用户优惠券数量
     */
    private static final String COUPON_QUERY_COUNT_METHOD = "memeber.coupon.count";
    private static final String COUPON_QUERY_COUNT_URL = "/member/getCouponCount.shtml";

    @Override
    public void getCouponTitleData() {
        String timeBIZService = TimeUtil.getTimeBIZService();
        Map<String, String> params = new ArrayMap<>();
        params.put("memberId", SPUtil.getStringFromSp(Config.MEMBER_ID, ""));
        try {
            mCodeStr = URLEncoder.encode(GsonFactory.map2Json(params), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> mapSign = new ArrayMap<>();
        mapSign.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        mapSign.put(BingNetStates.BIZ_SOURCE, "123456");
        mapSign.put(BingNetStates.TIMESTAMP, timeBIZService);//时间戳
        mapSign.put(BingNetStates.METHOD, COUPON_QUERY_COUNT_METHOD);
        mapSign.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        map.put(BingNetStates.BIZ_SOURCE, "123456");
        map.put(BingNetStates.TIMESTAMP, timeBIZService);//时间戳
        map.put(BingNetStates.METHOD, COUPON_QUERY_COUNT_METHOD);
        map.put(BingNetStates.REQUEST_DATA, mCodeStr);
        map.put(BingNetStates.SIGN, getSign(mapSign));
        OkGoUtils.doStringGetRequest(MainBuildConfigure.HOST_URL + COUPON_QUERY_COUNT_URL, map, getClass().getSimpleName(), new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                CouponCountBean couponCountBean = GsonFactory.fromJson(str,CouponCountBean.class);
                if (couponCountBean != null) {

                    mCouponListView.notifyListTitleData(couponCountBean);
                }
            }

            @Override
            public void onFail(int code, String error) {
                ToastUtils.showToast(error);
            }
        });

    }


    public static String getSign(Map<String, String> map) {
        String customCode = map.get(BingNetStates.CUSTOME_CODE);
        String bizSource = map.get(BingNetStates.BIZ_SOURCE);
        String timestamp = map.get(BingNetStates.TIMESTAMP);
        String method = map.get(BingNetStates.METHOD);
        String requestBizData = map.get(BingNetStates.REQUEST_DATA);
        if (customCode == null || bizSource == null || method == null) {
            return "";
        }
        if (requestBizData == null) {
            requestBizData = "";
        } else {
            requestBizData = "," + BingNetStates.REQUEST_DATA + ":" + requestBizData;
        }

        return EncryptUtils.getMd5(BingNetStates.CUSTOME_CODE + ":" + customCode + "," + BingNetStates.BIZ_SOURCE + ":" + bizSource + "," + BingNetStates.TIMESTAMP + ":" + timestamp
                + "," + BingNetStates.METHOD + ":" + method + requestBizData.trim());
    }
}
