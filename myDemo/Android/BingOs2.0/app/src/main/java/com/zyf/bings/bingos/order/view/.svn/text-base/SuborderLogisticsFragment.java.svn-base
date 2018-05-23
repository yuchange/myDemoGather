package com.zyf.bings.bingos.order.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.order.CustomerServiceDialogFragment;
import com.zyf.bings.bingos.order.bean.LogisticsListBean;
import com.zyf.bings.bingos.order.http.OrderClient;
import com.zyf.bings.bingos.order.adapter.SuborderAapter;
import com.zyf.bings.bingos.order.adapter.SuborderItemDecoration;
import com.zyf.bings.bingos.order.bean.OrderInfoBean;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 * 拆分订单的信息
 */


public class SuborderLogisticsFragment extends Fragment {

    private TextView mTvOrder;
    private RecyclerView mRvProductList;
    private static final String GOODS_LIST = "good_list";
    private static final String COME_FROM = "comefrom";
    private static final String ORDER_ID = "zorderinfo_id";
    private static final String ORDER_NO = "zorder_no";
    private static final String TAG = "SuborderLogisticsFragment";
    private ArrayList<OrderInfoBean.OrderListBean> mTotalOrderInfoList;
    private ProgressFrameLayout mProgressFrameLayout;
    private RelativeLayout mRlOnlineCustomer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.fragment_suborder_logistics, container, false);
        initView(mProgressFrameLayout);
        initListener();
        initData();
        return mProgressFrameLayout;
    }

    private void initListener() {

        mRlOnlineCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerServiceDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "");

            }
        });
    }

    private void initData() {


        String orderId = getArguments().getString(ORDER_ID);
        String orderNo = getArguments().getString(ORDER_NO);
        mProgressFrameLayout.showLoading();
        getOrderDetailsInfo(orderId, orderNo);


    }

    private void getOrderDetailsInfo(String orderId, String orderNo) {


        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ORDER_ID, orderId);


        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.LOGISTICS_INFO_LIST_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());


        OkGoUtils.doStringPostRequest(map, OrderClient.LOGISTICS_INFO_LIST, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                mProgressFrameLayout.showContent();
                LogisticsListBean logisticsListinBean = GsonFactory.fromJson(str, LogisticsListBean.class);
                List<LogisticsListBean.OrderInfoListBean> logisticsInfoList = logisticsListinBean.getOrderInfoList();
                if (logisticsInfoList != null && logisticsInfoList.size() == 1) {
                    mTvOrder.setVisibility(View.GONE);
                } else if (logisticsInfoList != null && logisticsInfoList.size() > 1) {
                    mTvOrder.setVisibility(View.VISIBLE);
                }
                initRvOrderList(logisticsInfoList);
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

    private void initRvOrderList(List<LogisticsListBean.OrderInfoListBean> totalOrderInfoList) {

        mRvProductList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        SuborderAapter suborderAapter = new SuborderAapter(R.layout.item_suborder_logistics, totalOrderInfoList, getActivity());
        mRvProductList.addItemDecoration(new SuborderItemDecoration(20));
        mRvProductList.setAdapter(suborderAapter);
        suborderAapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override

            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LogisticsInfoDialogFragment.start(getActivity().getSupportFragmentManager(), totalOrderInfoList.get(position).getLogistics_no(), totalOrderInfoList.get(position).getOrderinfo_id(), totalOrderInfoList.get(position).getShipping_name());

            }
        });


    }

    private void initView(View view) {

        mTvOrder = (TextView) view.findViewById(R.id.tv_order);
        mRvProductList = (RecyclerView) view.findViewById(R.id.rv_product_list);
        mRlOnlineCustomer = (RelativeLayout) view.findViewById(R.id.rl_online_customer);

    }


    public static void start(int resId, FragmentManager manager, List<OrderInfoBean.OrderListBean> list, String zorderinfo_id, String zorder_no, String comeFrom) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "SuborderLogisticsFragment";
        SuborderLogisticsFragment fragment = (SuborderLogisticsFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof SuborderLogisticsFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }


        if (fragment == null) {

            fragment = new SuborderLogisticsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(GOODS_LIST, (ArrayList<OrderInfoBean.OrderListBean>) list);
            bundle.putString(COME_FROM, comeFrom);
            bundle.putString(ORDER_ID, zorderinfo_id);
            bundle.putString(ORDER_NO, zorder_no);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);

        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                return;
            }
            Log.i("OneKeyCart", "fragemt不为空");
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);

        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().postSticky(new MainTitleEvent("物流信息"));
    }
}
