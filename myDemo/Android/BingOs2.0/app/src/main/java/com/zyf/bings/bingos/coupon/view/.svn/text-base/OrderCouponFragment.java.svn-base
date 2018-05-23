package com.zyf.bings.bingos.coupon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.coupon.CouponItemDecoration;
import com.zyf.bings.bingos.coupon.adapter.OrderCouponListAdapter;
import com.zyf.bings.bingos.coupon.bean.OrderCouponBean;
import com.zyf.bings.bingos.coupon.presenter.CouponCartPresenter;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.event.OrderCouponEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshiqi on 2017/11/2.
 */

public class OrderCouponFragment extends BaseFragment implements ICouponContract.CartCouponListView {

    private RecyclerView mRvOrderCoupon;
    private ICouponContract.CartCouponPresener mCartCouponPresener;
    private OrderCouponListAdapter mOrderCouponListAdapter;
    private static final String GOODS_ID_LIST = "goodsIdList";
    private static final String GOOD_COUNT_LIST = "goodCountList";
    private static final String STATE = "state";
    private List<String> mGoodsIdList;
    private List<String> mGoodCountList;
    private String mState;

    public static void start(int resId, FragmentManager manager, List<String> goodsIdList, List<String> goodCountList, String state) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "OrderCouponFragment";
        OrderCouponFragment fragment = (OrderCouponFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof OrderCouponFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        if (fragment == null) {
            fragment = OrderCouponFragment.newInstance(goodsIdList, goodCountList, state);
            transaction.add(resId, fragment, TAG);
            transaction.addToBackStack(null);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                postTitle();
                return;
            }
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
        postTitle();
    }

    public static OrderCouponFragment newInstance(List<String> goodsIdList, List<String> goodCountList, String state) {

        Bundle args = new Bundle();
        args.putStringArrayList(GOODS_ID_LIST, (ArrayList<String>) goodsIdList);
        args.putStringArrayList(GOOD_COUNT_LIST, (ArrayList<String>) goodCountList);
        args.putString(STATE, state);
        OrderCouponFragment fragment = new OrderCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_coupon, null);
        mGoodsIdList = getArguments().getStringArrayList(GOODS_ID_LIST);
        mGoodCountList = getArguments().getStringArrayList(GOOD_COUNT_LIST);
        mState = getArguments().getString(STATE);
        setPresenter();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        mRvOrderCoupon.setLayoutManager(gridLayoutManager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
//        stringIntegerHashMap.put(CouponItemDecoration.TOP_DECORATION,50);//top间距

        stringIntegerHashMap.put(CouponItemDecoration.BOTTOM_DECORATION,30);//底部间距

//        stringIntegerHashMap.put(CouponItemDecoration.LEFT_DECORATION,30);//左间距

        stringIntegerHashMap.put(CouponItemDecoration.RIGHT_DECORATION,30);//右间距

        mRvOrderCoupon.addItemDecoration(new CouponItemDecoration(stringIntegerHashMap));
        mOrderCouponListAdapter = new OrderCouponListAdapter(R.layout.item_coupon);
        mRvOrderCoupon.setAdapter(mOrderCouponListAdapter);
        mCartCouponPresener.getCartCouponList(mGoodsIdList, mGoodCountList, mState);
        mOrderCouponListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new OrderCouponEvent(mOrderCouponListAdapter.getItem(position).getCouponId(), mOrderCouponListAdapter.getItem(position).getCouponMoney()));
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            postTitle();
        }
    }

    private void initView(View view) {
        mRvOrderCoupon = (RecyclerView) view.findViewById(R.id.rv_order_coupon);
    }

    @Override
    public void setPresenter() {
        mCartCouponPresener = new CouponCartPresenter(this);
    }
    /**
     * 修改主页面标题
     */
    public static void postTitle() {
        EventBus.getDefault().postSticky(new MainTitleEvent("优惠券"));
    }


    @Override
    public void notifyListData(List<OrderCouponBean.CouponInfoListBean> couponInfoListBeen) {
        if (couponInfoListBeen.size() > 0) {
            mOrderCouponListAdapter.setNewData(couponInfoListBeen);
        }
    }
}
