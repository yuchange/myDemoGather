package com.bingstar.bingmall.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.base.WrapGridLayoutManager;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.order.OrderApplyAfter.OrderApplayEditDialog;
import com.bingstar.bingmall.order.bean.OrderList;
import com.bingstar.bingmall.order.http.OrderStates;
import com.bingstar.bingmall.order.view.OrderInfoFragment;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianhechen on 17/2/7.
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener, OrderListAdapter.OrderItemOnItemOnClickListener, IOrderContract.OrderListView {
    //
    private ArrayList<OrderList.Zorder> orderList;//数据

    private IOrderContract.OrderPresenter presenter;

    private OrderListAdapter listAdapter;//RecycleView适配器

    private int lastVisibleItem = 0;//最后可见条目数

    private int pageSize = 10;//每页显示的条目数

    private int currentIndex = 0;//当前第几页

    private static int flug = 0;// 用于判断数据是否加载完毕 1 加载完毕， 2 还有数据
    //    订单标题 全部，待付款，已付款，已发货
    private ImageView order_alltv, order_unpaidtv, order_paid, order_devilery;

    private ImageView imageSearch;

    private RecyclerView orderListView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayout orderlist_no_order;//暂无订单

    private String flugflug = "1";  //不能连续点击

    /**
     * TODO 传入会员ID；
     */
    private String memberId; // 会员ID

    private String currentState = ""; // 状态（全部 ： 待收货 ：.....）；
    //private SynLinearLayout mSync;

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "Order";
        OrderFragment fragment = (OrderFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new OrderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof OrderFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                        LogUtils.Debug_E(OrderFragment.class, frag.getClass().getSimpleName());
                    }
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setPresenter();
        setParentResId(getArguments().getInt(KEY_RESID));
        View view = inflater.inflate(R.layout.order_fragment, container, false);
        memberId = User.getIntance(getContext()).getMemberId();
        orderListView = (RecyclerView) view.findViewById(R.id.order_orderlist);
        final WarpLinearLayoutManager layoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        orderListView.setLayoutManager(layoutManager);
        // mSync = (SynLinearLayout) view.findViewById(R.id.orderlist_synlinear_layout);
        order_alltv = (ImageView) view.findViewById(R.id.order_alltv);
        order_unpaidtv = (ImageView) view.findViewById(R.id.order_unpaidtv);
        order_paid = (ImageView) view.findViewById(R.id.order_paid);
        order_devilery = (ImageView) view.findViewById(R.id.order_devilery);
        imageSearch = (ImageView) view.findViewById(R.id.order_search_image);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.orderlist_swip);
        orderlist_no_order = (LinearLayout) view.findViewById(R.id.orderlist_no_order);

        order_alltv.setImageResource(R.drawable.order_title_order);

        order_alltv.setOnClickListener(this);
        order_unpaidtv.setOnClickListener(this);
        order_paid.setOnClickListener(this);
        order_devilery.setOnClickListener(this);
        imageSearch.setOnClickListener(this);

//        数据
        orderList = new ArrayList<>();

        final WrapGridLayoutManager gridLayoutManager = new WrapGridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        listAdapter = new OrderListAdapter(orderList);
        orderListView.setLayoutManager(gridLayoutManager);
        orderListView.setAdapter(listAdapter);
        listAdapter.setOnClickItem(this);

        /**
         * 设置swiprefreshlayout刷新
         */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);//开始刷新
                currentIndex = 0;//从头开始拉去数据
                presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);
            }
        });

        orderListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == listAdapter.getItemCount()) {
                    if (flugflug.equals("1")) {
                        return;
                    }
                    if (flug == OrderStates.ORDERDATE_NO) {
//                        Toast.makeText(getContext(), "到底了", Toast.LENGTH_SHORT).show();
                    } else if (flug == OrderStates.ORDERDATE_YES) {
                        currentIndex++;
                        presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 2);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }

        });

        flugflug = "1";
        presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);

        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        initTitleImage();
        orderlist_no_order.setVisibility(View.GONE);
        int i = v.getId();
        if (i == R.id.order_alltv) {
            currentState = com.bingstar.bingmall.order.OrderStates.ORDER_LIST_QEURY_ALL;//全部订单
            orderList.clear();
            listAdapter.notifyDataSetChanged();
            order_alltv.setImageResource(R.drawable.order_title_order);
            currentIndex = 0;
            presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);

        } else if (i == R.id.order_unpaidtv) {
            currentState = com.bingstar.bingmall.order.OrderStates.ORDER_LIST_QUERY_UNPAY;//待付款
            orderList.clear();
            listAdapter.notifyDataSetChanged();
            order_unpaidtv.setImageResource(R.drawable.order_title_unpayment);
            currentIndex = 0;
            presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);

        } else if (i == R.id.order_paid) {
            /**
             * 标记错误
             */
            currentState = com.bingstar.bingmall.order.OrderStates.ORDER_LIST_QUERY_PAY;//已付款
            orderList.clear();
            listAdapter.notifyDataSetChanged();
            order_paid.setImageResource(R.drawable.order_title_payment);
            currentIndex = 0;
            presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);

        } else if (i == R.id.order_devilery) {
            currentState = com.bingstar.bingmall.order.OrderStates.ORDER_LIST_QUERY_DEVILERY;//已发货
            orderList.clear();
            listAdapter.notifyDataSetChanged();
            order_devilery.setImageResource(R.drawable.order_title_delivery);
            currentIndex = 0;
            presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);

        } else if (i == R.id.order_search_image) {/**
         * TODO 订单搜索
         */

        }
    }

    /**
     * 将标题都置为未点击状态
     */
    private void initTitleImage() {
        order_alltv.setImageResource(R.drawable.order_title_order_un);
        order_unpaidtv.setImageResource(R.drawable.order_title_unpayment_un);
        order_paid.setImageResource(R.drawable.order_title_payment_un);
        order_devilery.setImageResource(R.drawable.order_title_delivery_un);
    }

    @Override
    public void setPresenter() {
        presenter = new OrderPresenter(this);
    }

    @Override
    public void notifyOrderList(int flug, String state) {
        if (state.equals(currentState)) {
            if (listAdapter.getItemCount() == 0) {
                swipeRefreshLayout.setVisibility(View.GONE);
                orderlist_no_order.setVisibility(View.VISIBLE);
            } else {
                orderlist_no_order.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
            Log.i("OrderFragmnet", orderList.toString());
            listAdapter.notifyDataSetChanged();
        }
        this.flug = flug;//是否有数据
        flugflug = "2";//防止连续点击
        swipeRefreshLayout.setRefreshing(false);//取消刷新

    }


    /**
     * 此方法 暂时无用
     *
     * @param kind
     * @param type
     */
    @Override
    public void showView(int kind, String type) {
        if (type.equals(currentState)) {
            switch (kind) {
               /* case SynLinearLayout.SHOW_SUCCESS:
                    mSync.showSuccess();
                    break;
                case SynLinearLayout.SHOW_LOAD:
                    mSync.showLoad();
                    break;
                case SynLinearLayout.SHOW_EMPTY:
                    mSync.showEmpty(getResources().getString(R.string.no_discount_card));
                    break;
                case SynLinearLayout.SHOW_ERROR:
                    mSync.showError();
                    break;*/
            }
        }
    }

//    @Override
//    public void error() {
//        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.OrderFragment);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.unBind();
        }
    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.OrderFragment))) {
            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.OrderFragment);
        } else if (msg.getClassName().equals(OrderApplayEditDialog.class)) {
            onResume();
        } else if (msg.getClassName().equals(OrderInfoFragment.class) && msg.getMsg().equals(com.bingstar.bingmall.order.OrderStates.FLUSH_ORDER_LIST)) {
            onResume();
        } else if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.OrderFragment_final.equals(msg.getMsg())) {
            /**
             * 处理错误, 重新请求数据
             */
            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.OrderFragment);
        } else if (msg.getClassName().equals(OrderInfoFragment.class) && com.bingstar.bingmall.order.OrderStates.FLUSH_ORDER_LIST.equals(msg.getMsg())) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OrderFragment", "onResume执行了");
        orderList.clear();
        listAdapter.notifyDataSetChanged();
        currentIndex = 0;
        presenter.getAllOrderList(orderList, memberId, currentState, pageSize, currentIndex, 1);
    }

    private List<Integer> deleteList = new ArrayList<>();//存放点击删除条目position

    @Override
    public void setOnItemClick(View view, int position) {
        /**
         * 订单详情页面
         */
        String post_money;
        if (null == orderList.get(position).getPost_money()) {
            post_money = "0.00";
        } else {
            post_money = orderList.get(position).getPost_money();
        }
        String couponMoney;
        if (null == orderList.get(position).getCouponMoney()) {
            couponMoney = "0.00";
        } else {
            couponMoney = orderList.get(position).getCouponMoney();
        }
        OrderInfoFragment orderInfoFragment =
                OrderInfoFragment.newInstance(orderList.get(position).getZorderinfo_id(), orderList.get(position).getZorder_no(), orderList.get(position).getZstate(),
                        orderList.get(position).getZorder_total_money(), post_money, couponMoney, orderList.get(position).getPay_type(),
                        orderList.get(position).getZstate());
        orderInfoFragment.show(getChildFragmentManager(), "OrderInfoFragment");
    }

}
