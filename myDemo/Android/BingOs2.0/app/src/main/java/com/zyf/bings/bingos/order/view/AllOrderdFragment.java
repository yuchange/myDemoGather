package com.zyf.bings.bingos.order.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.event.NotifyAllOrderRvEvent;
import com.zyf.bings.bingos.goods.OrderConfirmFragment;
import com.zyf.bings.bingos.order.NotificationDialogFragment;
import com.zyf.bings.bingos.order.adapter.AllOrdersAdapter;
import com.zyf.bings.bingos.order.adapter.AllOrdersItemDecoration;
import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;
import com.zyf.bings.bingos.order.bean.OrderListBean;
import com.zyf.bings.bingos.order.http.OrderClient;
import com.zyf.bings.bingos.pay.PayFragment;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 * 全部订单的fragment
 */

public class AllOrderdFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRvAllOrders;
    private int pageSize = 10;//每页显示的条目数
    private int currentIndex = 0;//当前第几页

    private final String TAG = "AllOrderFragment";
    private AllOrdersAdapter mAllOrdersAdapter;
    private List<OrderListBean.ZorderListBean> mZorderList;
    private boolean isVisible;

    private List<ConfirmOrderBean.ItemBean> mItemBeanArrayList;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private ProgressFrameLayout mProgressFrameLayout;
    private TextView mTvOrderEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        isViewCreated = true;
        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.fragment_all_orders, container, false);
        initView(mProgressFrameLayout);


        initData();

        Log.i(TAG, "onCreateView" + "执行了");
        return mProgressFrameLayout;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "setUserVisi执行了" + isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
        } else {
            isUIVisible = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume" + "执行了");

    }

    @Subscribe(sticky = true)
    public void notifyRvAllOrder(NotifyAllOrderRvEvent notifyAllOrderRvEvent) {
        String orderId = notifyAllOrderRvEvent.getOrderId();
        Log.i(TAG, "post执行了");
        if (mZorderList != null && orderId != null) {

            for (int i = 0; i < mZorderList.size(); i++) {

                if (orderId.equals(mZorderList.get(i).getZorderinfo_id())) {
                    mZorderList.remove(i);
                    mAllOrdersAdapter.notifyDataSetChanged();
                    break;
                }

            }

        }


    }


    private void initData() {
        mProgressFrameLayout.showLoading();
        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", SPUtil.getString(getActivity(), Config.MEMBER_ID));
            jsonObject.put("state", "");
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("currentIndex", currentIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());

        OkGoUtils.doStringPostRequest(map, OrderClient.TITLE, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                mProgressFrameLayout.showContent();
                isViewCreated = false;
                OrderListBean orderListBean = GsonFactory.fromJson(str, OrderListBean.class);
                mZorderList = orderListBean.getZorderList();
                List<OrderListBean.StateListBean> stateList = orderListBean.getStateList();//订单数量的集合

                if ("0".equals(orderListBean.getOrderCount())) {
                    mTvOrderEmpty.setVisibility(View.VISIBLE);

                }
                initAllOrdersAdapter(mZorderList);


            }

            @Override
            public void onFail(int code, String error) {
                mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "加载失败...", error, "点击重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }
        });


    }

    private void initAllOrdersAdapter(List<OrderListBean.ZorderListBean> zorderList) {

        mAllOrdersAdapter = new AllOrdersAdapter(R.layout.item_all_orders, zorderList, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvAllOrders.setLayoutManager(linearLayoutManager);
        AllOrdersItemDecoration allOrdersItemDecoration = new AllOrdersItemDecoration(26);
        mRvAllOrders.addItemDecoration(allOrdersItemDecoration);
        mRvAllOrders.setAdapter(mAllOrdersAdapter);
        mAllOrdersAdapter.disableLoadMoreIfNotFullPage(mRvAllOrders);

        mAllOrdersAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentIndex = currentIndex + 1;
                getMoreOrderData(currentIndex, pageSize);


            }
        },mRvAllOrders);

        if (zorderList.size() < 10) {
            mAllOrdersAdapter.setEnableLoadMore(false);

        } else if (zorderList.size() >= 10) {
            mAllOrdersAdapter.setEnableLoadMore(true);
        }




        mAllOrdersAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //去订单详情页
                String zorderinfo_id = mZorderList.get(position).getZorderinfo_id();
                String zorder_no = mZorderList.get(position).getZorder_no();


                OrderDetailFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), zorderinfo_id, zorder_no);
            }
        });
        mAllOrdersAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (mZorderList.get(position).getZstate()) {
                    case "0"://立即付款

                        PayFragment.start(getFragmentManager(), mZorderList.get(position).getZorder_no(), mZorderList.get(position).getZorderinfo_id());
                        break;
                    case "1"://待发货 提醒发货
                        if (getResources().getString(R.string.has_remind_the_delivery).equals(((TextView) view).getText().toString())) {
                            return;
                        }
                        NotificationDialogFragment.newInstance(getString(R.string.shipment_request)).show(getActivity().getSupportFragmentManager(), "");
                        ((TextView) view).setText("已提醒发货");
                        break;
                    case "2"://待收货 查看物流
                        SuborderLogisticsFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), null, mZorderList.get(position).getZorderinfo_id(), mZorderList.get(position).getZorder_no(), "AllOrderdFragment");


                        break;
                    case "5"://已收货 再次购买      //去确认支付
                        toOrderConfirmFragment(position, mZorderList);

                        break;
                    case "6"://已取消  再次购买
                        toOrderConfirmFragment(position, mZorderList);

                        break;
                    case "7"://售后中  再次购买
                        toOrderConfirmFragment(position, mZorderList);

                        break;
                    default:
                        break;


                }


            }
        });


    }

    private void toOrderConfirmFragment(int position, List<OrderListBean.ZorderListBean> zorderList) {

        ConfirmOrderBean confirmOrderBean = new ConfirmOrderBean();
        mItemBeanArrayList = new ArrayList<>();
        List<OrderListBean.ZorderListBean.ProductListBean> productList = mZorderList.get(position).getProductList();

        for (int i = 0; i < productList.size(); i++) {
            ConfirmOrderBean.ItemBean itemBean = new ConfirmOrderBean.ItemBean();
            itemBean.setCount(Integer.parseInt(productList.get(i).getNumber()));
            itemBean.setGrossWeight(productList.get(i).getGrossWeight());
            itemBean.setTitle(productList.get(i).getBsjProductName());
            itemBean.setSpec(productList.get(i).getSpecification());
            itemBean.setImgUrl(productList.get(i).getImage_url());
            itemBean.setSpecificationId(productList.get(i).getSpecificationId());
            itemBean.setProductCode(productList.get(i).getBsjProductCode());
            itemBean.setProductId(productList.get(i).getBsjProductId());
            itemBean.setPrice(productList.get(i).getPrice());
            mItemBeanArrayList.add(itemBean);
        }
        confirmOrderBean.setItemBeanList(mItemBeanArrayList);
        OrderConfirmFragment.start(getActivity().getSupportFragmentManager(), R.id.fl_container, null, "", mItemBeanArrayList, mZorderList.get(position).getZorder_total_money(), null, "00");


    }

    private void getMoreOrderData(int currentsize, int pageSize) {

        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("memberId", SPUtil.getString(getActivity(), Config.MEMBER_ID));
            jsonObject.put("state", "");
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("currentIndex", currentIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());

        OkGoUtils.doStringPostRequest(map, OrderClient.TITLE, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                OrderListBean orderListBean = GsonFactory.fromJson(str, OrderListBean.class);
                if (orderListBean.getZorderList().size() > 0) {
                    mZorderList.addAll(orderListBean.getZorderList());
                    mAllOrdersAdapter.notifyDataSetChanged();
                } else if (orderListBean.getZorderList().size() == 0) {
                    ToastUtils.showToast("没有更多订单了");
                }
                if (orderListBean.getZorderList().size() < 10) {
                    mAllOrdersAdapter.setEnableLoadMore(false);
                } else if (orderListBean.getZorderList().size() >= 10) {
                    mAllOrdersAdapter.setEnableLoadMore(true);
                }
                mAllOrdersAdapter.loadMoreComplete();

            }

            @Override
            public void onFail(int code, String error) {
                mAllOrdersAdapter.loadMoreFail();
                ToastUtils.showToast("加载失败");
            }
        });


    }

    private void initView(View view) {
        mRvAllOrders = (RecyclerView) view.findViewById(R.id.rv_all_orders);
        mTvOrderEmpty = (TextView) view.findViewById(R.id.tv_order_empty);
        // ListView lv= (ListView) view.findViewById(R.id.lv);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        currentIndex = 0;
        EventBus.getDefault().unregister(this);
    }
}
