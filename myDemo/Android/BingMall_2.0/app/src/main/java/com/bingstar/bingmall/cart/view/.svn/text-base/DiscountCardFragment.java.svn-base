package com.bingstar.bingmall.cart.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.WrapGridLayoutManager;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.video.lib.SynLinearLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianhechen on 17/3/14.
 */

public class DiscountCardFragment extends BaseFragment implements View.OnClickListener, IDiscountCartContract.DiscountCartView {

    private RecyclerView recyclerView;
    private SynLinearLayout discount_card_syn_linear;

    private ArrayList<Coupon.CouponInfo> couponInfoList;
    private DiscountCardAdapter discountCardAdapter;
    private Button mAll;
    private Button mExpiring;
    private Button mUseOff;

    private String currentType;

    public static final String TYPE_USE = "01";   //全部可用
    public static final String TYPE_EXPIRED = "03";  //即将过期
    public static final String TYPE_OFF = "02";   //已失效

    IDiscountCartContract.DiscountCartPresenter mPresenter;
    private LinearLayout mExpiredInfo;
    private TextView mExpiredInfoTv;
    // private SwipeRefreshLayout mRefreshLayout;

    public static void start(int resId, final FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "DiscountCardFragment";
        DiscountCardFragment fragment = (DiscountCardFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new DiscountCardFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
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
        final List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof DiscountCardFragment) && frag != null) {
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
        View view = inflater.inflate(R.layout.discount_card_fragment, container, false);
        couponInfoList = new ArrayList<>();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycleview);
        discount_card_syn_linear = (SynLinearLayout) view.findViewById(R.id.discount_card_syn_linear);
        WrapGridLayoutManager gridLayoutManager = new WrapGridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        discountCardAdapter = new DiscountCardAdapter(couponInfoList);
        recyclerView.setAdapter(discountCardAdapter);
        mAll = (Button) view.findViewById(R.id.discount_use_btn);
        mExpiring = (Button) view.findViewById(R.id.discount_expired_btn);
        mUseOff = (Button) view.findViewById(R.id.discount_off_btn);
        mExpiredInfo = (LinearLayout) view.findViewById(R.id.discount_expired_info);
        mExpiredInfoTv = (TextView) view.findViewById(R.id.discount_warn_text);
       /* mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.discount_card_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                couponInfoList.clear();
                discountCardAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(true);
                mPresenter.getCouponShowList(currentType);
            }
        });*/
    }

    private void initData() {
        currentType = TYPE_USE;
        setPresenter();
        mAll.setText(getResources().getString(R.string.discount_use) + "0)");
        mExpiring.setText(getResources().getString(R.string.discount_expired) + "0)");
        mUseOff.setText(getResources().getString(R.string.discount_off) + "0)");
        mAll.setOnClickListener(this);
        mExpiring.setOnClickListener(this);
        mUseOff.setOnClickListener(this);
        discount_card_syn_linear.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                changeUI();
                mPresenter.getCouponShowList(currentType);
            }
        });
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
        mPresenter.getCouponShowList(TYPE_USE);
        mPresenter.getCouponShowList(TYPE_OFF);
        mPresenter.getCouponShowList(TYPE_EXPIRED);
        Log.e("DiscountCardFragment", "onResume: " + "测试啊");
        changeUI();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    public void changeUI() {
        resetUI();
        switch (currentType) {
            case TYPE_USE:
                mAll.setBackgroundResource(R.drawable.discount_use_press);
                mAll.setTextColor(getResources().getColor(R.color.discount_title_bg));
                break;
            case TYPE_EXPIRED:
                mExpiring.setBackgroundResource(R.drawable.discount_expired_press);
                mExpiring.setTextColor(getResources().getColor(R.color.discount_title_bg));
                break;
            case TYPE_OFF:
                mUseOff.setBackgroundResource(R.drawable.discount_off_press);
                mUseOff.setTextColor(getResources().getColor(R.color.discount_title_bg));
                break;
        }
    }

    private void resetUI() {
        mAll.setBackgroundResource(R.drawable.discount_use);
        mExpiring.setBackgroundResource(R.drawable.discount_expired);
        mUseOff.setBackgroundResource(R.drawable.discount_off);
        mAll.setTextColor(getResources().getColor(R.color.goodsDetailName));
        mExpiring.setTextColor(getResources().getColor(R.color.goodsDetailName));
        mUseOff.setTextColor(getResources().getColor(R.color.goodsDetailName));
    }

    @Override
    public void setPresenter() {
        mPresenter = new DiscountPresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unBind();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.discount_use_btn) {
            currentType = TYPE_USE;
            mPresenter.getCouponShowList(currentType);
            changeUI();
        }
        if (id == R.id.discount_expired_btn) {
            currentType = TYPE_EXPIRED;
            mPresenter.getCouponShowList(currentType);
            changeUI();
        }
        if (id == R.id.discount_off_btn) {
            currentType = TYPE_OFF;
            mPresenter.getCouponShowList(currentType);
            changeUI();
        }
    }

    @Override
    public void notifyCouponList(ArrayList<Coupon.CouponInfo> couponInfoLists, String type) {
        if (type.equals(currentType)) {
            couponInfoList.clear();
            couponInfoList.addAll(couponInfoLists);
            discountCardAdapter.setCurrentType(currentType);
            discountCardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showView(int kind, String type) {
        //mRefreshLayout.setRefreshing(false);
        if (type.equals(currentType)) {
            switch (kind) {
                case SynLinearLayout.SHOW_SUCCESS:
                    discount_card_syn_linear.showSuccess();
                    break;
                case SynLinearLayout.SHOW_LOAD:
                    discount_card_syn_linear.showLoad();
                    break;
                case SynLinearLayout.SHOW_EMPTY:
                    discount_card_syn_linear.showEmpty(getResources().getString(R.string.no_discount_card));
                    break;
                case SynLinearLayout.SHOW_ERROR:
                    discount_card_syn_linear.showError();
                    break;
            }
        }
    }

    @Override
    public void setNum(String type, int num, int totalCount) {
        switch (type) {
            case TYPE_USE:
                mAll.setText(getResources().getString(R.string.discount_use) + totalCount + ")");
                break;
            case TYPE_EXPIRED:
                mExpiring.setText(getResources().getString(R.string.discount_expired) + totalCount + ")");
                if (num > 0) {
                    mExpiredInfo.setVisibility(View.VISIBLE);
                    mExpiredInfoTv.setText(getResources().getString(R.string.discount_coupon_warn) + totalCount + getResources().getString(R.string.discount_coupon_warn2));
                }
                break;
            case TYPE_OFF:
                mUseOff.setText(getResources().getString(R.string.discount_off) + totalCount + ")");
                break;
        }
    }
}
