package com.zyf.bings.bingos.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.cart.ShopCartFragment;
import com.zyf.bings.bingos.coupon.bean.CouponCountBean;
import com.zyf.bings.bingos.coupon.view.CouponFragment;
import com.zyf.bings.bingos.event.InitOrderCountEvent;
import com.zyf.bings.bingos.event.LogOutEvent;
import com.zyf.bings.bingos.event.MainCartCount;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.order.OrderFragment;
import com.zyf.bings.bingos.order.bean.OrderCountBean;
import com.zyf.bings.bingos.order.http.OrderCountClient;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.MainBuildConfigure;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.EncryptUtils;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by wangshiqi on 2017/9/4.
 */

public class AccountFragment extends Fragment implements View.OnClickListener {
    private String mCodeStr;
    private LinearLayout basicInfo;
    private TextView mCartCount;
    private LinearLayout managerCart;
    private LinearLayout orderList;
    private TextView mTvOrderCount;
    private int mOrderTotalCount; //总订单数量
    private int mOrderWaitPayCount; //待付款数量
    private int mOrderWaitSendCount; //待发货数量
    private int mOrderWaitReceiveCount; //待收货数量
    private int mOrderFinishCount; //已完成数量
    private int mOrderCancelCount; //已取消数量
    private int mOrderAfterSaleCount; //售后中数量
    private LinearLayout mManagerCoupon;
    private int mCouponCoutn;
    private TextView mTvCouponCount;

    public static AccountFragment newInstance() {


        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initView(view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    private void initView(View view) {
        mTvOrderCount = (TextView) view.findViewById(R.id.tv_order_count);
        basicInfo = (LinearLayout) view.findViewById(R.id.manager_profile);
        mCartCount = (TextView) view.findViewById(R.id.account_cart_count);
        managerCart = (LinearLayout) view.findViewById(R.id.manager_shop_cart);
        orderList = (LinearLayout) view.findViewById(R.id.manager_order_list);
        mManagerCoupon = (LinearLayout) view.findViewById(R.id.manager_coupon);
        mTvCouponCount = (TextView) view.findViewById(R.id.tv_coupon_count);
        mManagerCoupon.setOnClickListener(this);
        orderList.setOnClickListener(this);
        managerCart.setOnClickListener(this);
        basicInfo.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        CartCountClient.getCartCount();
        OrderCountClient.getOrderCount();
        getCouponCount();
    }

    /**
     * 获取优惠券数量
     */
    private static final String COUPON_QUERY_COUNT_METHOD = "memeber.coupon.count";
    private static final String COUPON_QUERY_COUNT_URL = "/member/getCouponCount.shtml";
    private void getCouponCount() {
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
                    mCouponCoutn = couponCountBean.getAlreadyUsed() + couponCountBean.getExpired() + couponCountBean.getNoUsed() + couponCountBean.getExpiring();
                    if (mCouponCoutn == 0) {
                        mTvCouponCount.setVisibility(View.GONE);
                    } else {
                        mTvCouponCount.setText(mCouponCoutn + "");
                    }
                }
            }

            @Override
            public void onFail(int code, String error) {
                ToastUtils.showToast(error);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            OrderCountClient.getOrderCount();
            getCouponCount();
        }
    }

    /**
     * 修改购物车角标数量
     *
     * @param mainCartCount
     */
    @Subscribe(sticky = true)
    public void changeCartCount(MainCartCount mainCartCount) {
        if (mCartCount != null) {
            String cartCount = mainCartCount.getCartCount();
            if (!TextUtils.isEmpty(cartCount) && cartCount.equals("0")) {
                mCartCount.setVisibility(View.GONE);
            } else {
                mCartCount.setVisibility(View.VISIBLE);
                mCartCount.setText(mainCartCount.getCartCount());
            }
        }
    }

    /**
     * 退出登录事件
     */
    @Subscribe
    public void logoutEvent(LogOutEvent logOutEvent) {
        if (logOutEvent.getLogOut().equals("logout")) {
            mCartCount.setVisibility(View.GONE);
            mTvOrderCount.setVisibility(View.GONE);
        }
    }

    /**
     * 订单数量
     *
     * @param
     */

    @Subscribe(sticky = true)
    public void changeOrderCount(InitOrderCountEvent initOrderCountEvent) {
        for (OrderCountBean.ZorderNumberListBean bean : initOrderCountEvent.getList()) {
            switch (bean.getZstate()) {
                case "0":
                    if (bean.getNumber().equals("")) {
                        mOrderWaitPayCount = 0;
                    } else {
                        mOrderWaitPayCount = Integer.parseInt(bean.getNumber());
                    }
                    break;
                case "1":
                    if (bean.getNumber().equals("")) {
                        mOrderWaitSendCount = 0;
                    } else {
                        mOrderWaitSendCount = Integer.parseInt(bean.getNumber());
                    }
                    break;
                case "2":
                    if (bean.getNumber().equals("")) {
                        mOrderWaitReceiveCount = 0;
                    } else {
                        mOrderWaitReceiveCount = Integer.parseInt(bean.getNumber());
                    }
                    break;
                case "5":
                    if (bean.getNumber().equals("")) {
                        mOrderFinishCount = 0;
                    } else {
                        mOrderFinishCount = Integer.parseInt(bean.getNumber());
                    }
                    break;
                case "6":
                    if (bean.getNumber().equals("")) {
                        mOrderCancelCount = 0;
                    } else {
                        mOrderCancelCount = Integer.parseInt(bean.getNumber());
                    }
                    break;
                case "7":
                    if (bean.getNumber().equals("")) {
                        mOrderAfterSaleCount = 0;
                    } else {
                        mOrderAfterSaleCount = Integer.parseInt(bean.getNumber());
                    }
                    break;
                default:
            }
        }
        mOrderTotalCount = mOrderWaitPayCount + mOrderWaitSendCount + mOrderWaitReceiveCount + mOrderFinishCount + mOrderCancelCount + mOrderAfterSaleCount;
        if (mOrderTotalCount == 0) {
            mTvOrderCount.setVisibility(View.GONE);
        } else {
            mTvOrderCount.setText(mOrderTotalCount + "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.manager_coupon:
                if (TextUtils.isEmpty(SPUtil.getString(getContext(), Config.MEMBER_ID))) {
                    ToastUtils.showToast("您尚未登录,请先登录");
                    return;
                }
                CouponFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                break;
            case R.id.manager_shop_cart:
                if (TextUtils.isEmpty(SPUtil.getString(getContext(), Config.MEMBER_ID))) {
                    ToastUtils.showToast("您尚未登录,请先登录");
                    return;
                }
                ShopCartFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                break;
            case R.id.manager_profile:
                // 去基础信息页面
                if (TextUtils.isEmpty(SPUtil.getString(getContext(), Config.MEMBER_ID))) {
                    ToastUtils.showToast("您尚未登录,请先登录");
                    return;
                }
                BasicInfoFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                break;
            case R.id.manager_order_list:
                if (TextUtils.isEmpty(SPUtil.getString(getContext(), Config.MEMBER_ID))) {
                    ToastUtils.showToast("您尚未登录,请先登录");
                    return;
                } else {
                    OrderFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), mOrderWaitPayCount, mOrderWaitSendCount,
                            mOrderWaitReceiveCount, mOrderFinishCount, mOrderCancelCount, mOrderAfterSaleCount);
                }
                break;
            default:
                break;
        }
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
