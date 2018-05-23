package com.zyf.bings.bingos.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.cart.http.CartStates;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.event.OrderCountEvent;
import com.zyf.bings.bingos.order.bean.OrderCountBean;
import com.zyf.bings.bingos.order.http.OrderCountClient;
import com.zyf.bings.bingos.order.view.AllOrderdFragment;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshiqi on 2017/9/20.
 */

public class OrderFragment extends BaseFragment {

    private TabLayout orderTab;
    private ViewPager orderVp;
    private String[] titles;
    private int mOrderWaitPayCount; //待付款数量
    private int mOrderWaitSendCount; //待发货数量
    private int mOrderWaitReceiveCount; //待收货数量
    private int mOrderFinishCount; //已完成数量
    private int mOrderCancelCount; //已取消数量
    private int mOrderAfterSaleCount; //售后中数量
    public static final String WAIT_PAY = "wait_pay";
    public static final String WAIT_SEND = "wait_send";
    public static final String WAIT_RECEIVE = "wait_receive";
    public static final String ORDER_FINISH = "order_finish";
    public static final String ORDER_CANCEL = "order_cancel";
    public static final String AFTER_SALE = "after_sale";
    private OrderAdapter mOrderAdapter;
    private List<TabItemInfo> mTabItems;
    private int mPostion = 0;
    private ProgressFrameLayout mProgressFrameLayout;
    private OrderCountBean mOrderCountBean;

    @Override
    public void setPresenter() {

    }

    public static void start(int resId, FragmentManager manager, int orderWaitPayCount, int orderWaitSendCount,
                             int orderWaitReceiveCount, int orderFinishCount, int orderCancelCount, int orderAfterSaleCount) {

        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "OrderFragment";
        OrderFragment fragment = (OrderFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new OrderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            bundle.putInt(WAIT_PAY, orderWaitPayCount);
            bundle.putInt(WAIT_SEND, orderWaitSendCount);
            bundle.putInt(WAIT_RECEIVE, orderWaitReceiveCount);
            bundle.putInt(ORDER_FINISH, orderFinishCount);
            bundle.putInt(ORDER_CANCEL, orderCancelCount);
            bundle.putInt(AFTER_SALE, orderAfterSaleCount);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            if (!fragment.isHidden()) {
                transaction.commit();
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof OrderFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        Bundle bundle = getArguments();
        mOrderWaitPayCount = bundle.getInt(WAIT_PAY);
        mOrderAfterSaleCount = bundle.getInt(AFTER_SALE);
        mOrderCancelCount = bundle.getInt(ORDER_CANCEL);
        mOrderFinishCount = bundle.getInt(ORDER_FINISH);
        mOrderWaitReceiveCount = bundle.getInt(WAIT_RECEIVE);
        mOrderWaitSendCount = bundle.getInt(WAIT_SEND);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    private void initView(View view) {
        orderTab = (TabLayout) view.findViewById(R.id.order_tab);
        orderVp = (ViewPager) view.findViewById(R.id.order_vp);
        mProgressFrameLayout = (ProgressFrameLayout) view.findViewById(R.id.order_progress);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        titles = getResources().getStringArray(R.array.order_tab);
        mTabItems = new ArrayList<>();
        mTabItems.add(new TabItemInfo(new AllOrderdFragment(), titles[0], 0));
        mTabItems.add(new TabItemInfo(WaitPayOrderFragment.newInstance(1), titles[1], mOrderWaitSendCount));
        mTabItems.add(new TabItemInfo(WaitPayOrderFragment.newInstance(2), titles[2], mOrderWaitPayCount));
        mTabItems.add(new TabItemInfo(WaitPayOrderFragment.newInstance(3), titles[3], mOrderWaitReceiveCount));
        mTabItems.add(new TabItemInfo(WaitPayOrderFragment.newInstance(4), titles[4], mOrderFinishCount));
        mTabItems.add(new TabItemInfo(WaitPayOrderFragment.newInstance(5), titles[5], mOrderAfterSaleCount));
        mTabItems.add(new TabItemInfo(WaitPayOrderFragment.newInstance(6), titles[6], mOrderCancelCount));
        mOrderAdapter = new OrderAdapter(getContext(), getChildFragmentManager(), mTabItems);
        orderVp.setAdapter(mOrderAdapter);
        orderTab.setupWithViewPager(orderVp);
        for (int i = 0; i < orderTab.getTabCount(); i++) {
            TabLayout.Tab tab = orderTab.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mOrderAdapter.getTabView(i));
            }
        }
        orderVp.setCurrentItem(0);
        setTabLine(orderTab, 70 / 3, 70 / 3);
        orderVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPostion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 处理标签下划线长度
     */
    public void setTabLine(TabLayout tab, int left, int right) {
        try {
            Class<?> tablayout = tab.getClass();
            Field tabStrip = tablayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(orderTab);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                if (i == 0) {
                    child.setPadding(0, 0, 0, 0);
                } else {
                    child.setPadding(10, 0, 0, 0);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                //修改两个tab的间距
                params.setMarginStart(DensityUtil.dp2px(getContext(), left));
                params.setMarginEnd(DensityUtil.dp2px(getContext(), right));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("OrderFragment", "hidden" + hidden);
        if (!hidden) {
            EventBus.getDefault().postSticky(new MainTitleEvent("我的订单"));
            Log.i("OrderFragment", "Count" + mOrderAdapter.getCount());
            mOrderAdapter.getCount();
            TextView viewById = (TextView) mOrderAdapter.getTabView(1).findViewById(R.id.order_tab_num);
            Log.i("OrderFragment", "textView" + viewById);
            orderCountClient();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().postSticky(new MainTitleEvent("我的订单"));
        Log.i("OrderFragment", "onResume执行了");
        orderCountClient();
    }

    @Subscribe(sticky = true)
    public void changeOrderCount(OrderCountEvent countEvent) {
        if (!TextUtils.isEmpty(countEvent.getString())) {
            Log.d("sss", "zzzz");
            orderCountClient();
        }
    }

    /**
     * 订单数量
     */
    private static final String METHOD = "zorder.number.query";
    private static final String URL = "/zorderNumberQuery.shtml";

    private void orderCountClient() {
        mProgressFrameLayout.showLoading();
        Map<String, String> params = new ArrayMap<>();
        String memberId = SPUtil.getStringFromSp(Config.MEMBER_ID, "");
        if ("".equals(memberId) || TextUtils.isEmpty(memberId)) {
            return;
        }
        params.put(CartStates.MEMBER_ID, memberId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, URL, OrderCountClient.class, null).subscribe(new WebAction() {


            @Override
            public void onSuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    mOrderCountBean = gson.fromJson(data, OrderCountBean.class);
                    for (OrderCountBean.ZorderNumberListBean bean : mOrderCountBean.getZorderNumberList()) {
                        switch (bean.getZstate()) {
                            case "0":
                                if (bean.getNumber().equals("")) {
                                    mOrderWaitPayCount = 0;
                                    ((TextView) orderTab.getTabAt(2).getCustomView().findViewById(R.id.order_tab_num)).setVisibility(View.GONE);
                                } else {
                                    mOrderWaitPayCount = Integer.parseInt(bean.getNumber());
                                    ((TextView) orderTab.getTabAt(2).getCustomView().findViewById(R.id.order_tab_num)).setText(mOrderWaitPayCount + "");
                                }
                                break;
                            case "1":
                                if (bean.getNumber().equals("")) {
                                    mOrderWaitSendCount = 0;
                                    ((TextView) orderTab.getTabAt(1).getCustomView().findViewById(R.id.order_tab_num)).setVisibility(View.GONE);
                                } else {
                                    mOrderWaitSendCount = Integer.parseInt(bean.getNumber());
                                    ((TextView) orderTab.getTabAt(1).getCustomView().findViewById(R.id.order_tab_num)).setText(mOrderWaitSendCount + "");
                                }
                                break;
                            case "2":
                                if (bean.getNumber().equals("")) {
                                    mOrderWaitReceiveCount = 0;
                                    ((TextView) orderTab.getTabAt(3).getCustomView().findViewById(R.id.order_tab_num)).setVisibility(View.GONE);
                                } else {
                                    mOrderWaitReceiveCount = Integer.parseInt(bean.getNumber());
                                    ((TextView) orderTab.getTabAt(3).getCustomView().findViewById(R.id.order_tab_num)).setText(mOrderWaitReceiveCount + "");
                                }
                                break;
                            case "5":
                                if (bean.getNumber().equals("")) {
                                    mOrderFinishCount = 0;
                                    ((TextView) orderTab.getTabAt(4).getCustomView().findViewById(R.id.order_tab_num)).setVisibility(View.GONE);
                                } else {
                                    mOrderFinishCount = Integer.parseInt(bean.getNumber());
                                    ((TextView) orderTab.getTabAt(4).getCustomView().findViewById(R.id.order_tab_num)).setText(mOrderFinishCount + "");
                                }
                                break;
                            case "6":
                                if (bean.getNumber().equals("")) {
                                    mOrderCancelCount = 0;
                                    ((TextView) orderTab.getTabAt(6).getCustomView().findViewById(R.id.order_tab_num)).setVisibility(View.GONE);
                                } else {

                                    mOrderCancelCount = Integer.parseInt(bean.getNumber());
                                    ((TextView) orderTab.getTabAt(6).getCustomView().findViewById(R.id.order_tab_num)).setText(mOrderCancelCount + "");
                                }
                                break;
                            case "7":
                                if (bean.getNumber().equals("")) {
                                    mOrderAfterSaleCount = 0;
                                    ((TextView) orderTab.getTabAt(5).getCustomView().findViewById(R.id.order_tab_num)).setVisibility(View.GONE);
                                } else {

                                    mOrderAfterSaleCount = Integer.parseInt(bean.getNumber());
                                    ((TextView) orderTab.getTabAt(5).getCustomView().findViewById(R.id.order_tab_num)).setText(mOrderAfterSaleCount + "");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    mProgressFrameLayout.showContent();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("OrderFragment", "setUser执行了");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }
}
