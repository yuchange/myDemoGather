package com.zyf.bings.bingos.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.event.NotifyAllOrderRvEvent;
import com.zyf.bings.bingos.event.OrderCountEvent;
import com.zyf.bings.bingos.goods.OrderConfirmFragment;
import com.zyf.bings.bingos.order.adapter.OrderListAdapter;
import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;
import com.zyf.bings.bingos.order.bean.OrderListBean;
import com.zyf.bings.bingos.order.presenter.OrderPresenter;
import com.zyf.bings.bingos.order.view.OrderDetailFragment;
import com.zyf.bings.bingos.pay.PayFragment;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshiqi on 2017/9/21.
 */

public class WaitPayOrderFragment extends Fragment implements IOrderContract.OrderListView {

    private RecyclerView orderListView;
    private OrderListAdapter orderListAdapter;
    private IOrderContract.OrderPresenter presenter;
    private List<OrderListBean.ZorderListBean> zorderList = new ArrayList<>();
    private int currentState;
    private List<ConfirmOrderBean.ItemBean> mBeanList;
    private int pageSize = 10;//每页显示的条目数

    private int currentIndex;//当前第几页
    private TextView mTvListEmpty;
    private ProgressFrameLayout mFrameLayout;

    public static WaitPayOrderFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        WaitPayOrderFragment fragment = new WaitPayOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.fragment_unpay_order, container, false);
        initView(mFrameLayout);
        setPresenter();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getArguments();
        currentState = bundle.getInt("position");
        return mFrameLayout;
    }

    private void initView(View view) {
        orderListView = (RecyclerView) view.findViewById(R.id.unpay_orderlist);
        mTvListEmpty = (TextView) view.findViewById(R.id.tv_order_empty);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.d("WaitPayOrderFragment", "显示");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("WaitPayOrderFragment", "onResume");
        mFrameLayout.showLoading();
        currentIndex = 0;
        zorderList = new ArrayList<>();
        switch (currentState) {
            case 1:// 待发货
                presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "1", pageSize, currentIndex);
                break;
            case 2: //待付款
                presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "0", pageSize, currentIndex);
                break;
            case 3: // 待收货
                presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "2", pageSize, currentIndex);
                break;
            case 4: // 已完成
                presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "5", pageSize, currentIndex);
                break;
            case 5:// 售后中
                presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "7", pageSize, currentIndex);
                break;
            case 6: // 已取消
                presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "6", pageSize, currentIndex);
                break;
            default:
        }

        initOrderListAdapter();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("WaitPayOrderFragment", "setUserVisivile");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void initOrderListAdapter() {
        orderListAdapter = new OrderListAdapter(R.layout.item_unpay, getActivity().getSupportFragmentManager());
        LinearLayoutManager wrapLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        orderListView.setLayoutManager(wrapLinearLayoutManager);
        orderListView.setAdapter(orderListAdapter);
        orderListAdapter.disableLoadMoreIfNotFullPage(orderListView);
        orderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDetailFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(),
                        zorderList.get(position).getZorderinfo_id(), zorderList.get(position).getZorder_no());
            }
        });
        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (((TextView) view).getText().equals("再次购买")) {
                    buyAgain(position);
                } else if (((TextView) view).getText().equals("立即付款")) {
                    PayFragment.start(getActivity().getSupportFragmentManager(), zorderList.get(position).getZorder_no(), zorderList.get(position).getZorderinfo_id());
                } else {
                    presenter.notificationUser(zorderList.get(position).getZorderinfo_id());
                }
            }
        });
        orderListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentIndex++;
                switch (currentState) {
                    case 1:// 待发货
                        presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "1", pageSize, currentIndex);
                        break;
                    case 2: //待付款
                        presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "0", pageSize, currentIndex);
                        break;
                    case 3: // 待收货
                        presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "2", pageSize, currentIndex);
                        break;
                    case 4: // 已完成
                        presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "5", pageSize, currentIndex);
                        break;
                    case 5:// 售后中
                        presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "7", pageSize, currentIndex);
                        break;
                    case 6: // 已取消
                        presenter.getAllOrderList(SPUtil.getString(getContext(), Config.MEMBER_ID), "6", pageSize, currentIndex);
                        break;
                    default:
                }
            }
        }, orderListView);

    }

    /**
     * 刷新列表
     *
     * @param
     */
    @Subscribe(sticky = true)
    public void update(NotifyAllOrderRvEvent notifyAllOrderRvEvent) {
        if (!TextUtils.isEmpty(notifyAllOrderRvEvent.getOrderId())) {
            for (int i = 0; i < zorderList.size(); i++) {
                if (zorderList.get(i).getZorderinfo_id().equals(notifyAllOrderRvEvent.getOrderId())) {
                    zorderList.remove(i);
                    orderListAdapter.getData().remove(i);
                    orderListAdapter.notifyDataSetChanged();
                    break;
                }
            }
            if (zorderList.size() == 0) {
                mTvListEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 再次购买创建订单中商品参数列表
     *
     * @param position
     */
    private void buyAgain(int position) {
        ConfirmOrderBean confirmOrderBean = new ConfirmOrderBean();
        mBeanList = new ArrayList<ConfirmOrderBean.ItemBean>();
        for (int i = 0; i < zorderList.get(position).getProductList().size(); i++) {
            ConfirmOrderBean.ItemBean itemBean = new ConfirmOrderBean.ItemBean();
            itemBean.setCount(Integer.parseInt(zorderList.get(position).getProductList().get(i).getNumber()));
            itemBean.setGrossWeight(zorderList.get(position).getProductList().get(i).getGrossWeight());
            itemBean.setTitle(zorderList.get(position).getProductList().get(i).getBsjProductName());
            itemBean.setSpec(zorderList.get(position).getProductList().get(i).getSpecification());
            itemBean.setImgUrl(zorderList.get(position).getProductList().get(i).getImage_url());
            itemBean.setSpecificationId(zorderList.get(position).getProductList().get(i).getSpecificationId());
            itemBean.setProductCode(zorderList.get(position).getProductList().get(i).getBsjProductCode());
            itemBean.setProductId(zorderList.get(position).getProductList().get(i).getBsjProductId());
            itemBean.setPrice(zorderList.get(position).getProductList().get(i).getPrice());
            mBeanList.add(itemBean);
        }
        confirmOrderBean.setItemBeanList(mBeanList);
        OrderConfirmFragment.start(getActivity().getSupportFragmentManager(), R.id.fl_container, null, "", mBeanList, zorderList.get(position).getZorder_total_money(), null, "00");
    }

    @Override
    public void setPresenter() {
        presenter = new OrderPresenter(this);
    }

    @Override
    public void notifyOrderList(List<OrderListBean.ZorderListBean> zorderListBean) {
        mFrameLayout.showContent();
        mTvListEmpty.setVisibility(View.GONE);
        if (zorderListBean.size() > 0) {
                orderListAdapter.addData(zorderListBean);
                zorderList.addAll(zorderListBean);
                orderListAdapter.loadMoreComplete();

            if (zorderListBean.size() < 10) {
                orderListAdapter.setEnableLoadMore(false);
            } else {
                orderListAdapter.setEnableLoadMore(true);
            }
        } else {
            orderListAdapter.setEnableLoadMore(false);
            mTvListEmpty.setVisibility(View.VISIBLE);
            mFrameLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        currentIndex = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void listErr() {
        mFrameLayout.showContent();
        orderListView = null;
        mTvListEmpty.setVisibility(View.VISIBLE);
        mFrameLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        EventBus.getDefault().postSticky(new OrderCountEvent("order"));
    }


    @Override
    public void remindSuccess() {
        final NotificationDialogFragment notificationDialog = NotificationDialogFragment.newInstance("商家已收到您的发货请求");
        notificationDialog.show(getActivity().getSupportFragmentManager(), "orderList");
    }

    @Override
    public void remindFailed(String errMsg) {
        final NotificationDialogFragment notificationDialog = NotificationDialogFragment.newInstance(errMsg);
        notificationDialog.show(getActivity().getSupportFragmentManager(), "orderList");
    }


}
