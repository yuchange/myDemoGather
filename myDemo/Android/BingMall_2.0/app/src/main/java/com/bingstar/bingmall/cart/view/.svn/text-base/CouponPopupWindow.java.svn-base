package com.bingstar.bingmall.cart.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.cart.CouponListAdapter;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.yunzhi.lib.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangyifei on 17/8/23.
 */

public class CouponPopupWindow extends PopupWindow {

    private ArrayList<Coupon.CouponInfo> mCouponInfoList;

    private Activity mActivity;
    private LayoutInflater mInflater;
    private LinearLayout mCouponPopupWindow;
    private RecyclerView mCouponRecyclerView;
    private CouponListAdapter mCouponListAdapter;

    public CouponPopupWindow(Activity activity) {
        super(activity);

        this.mActivity = activity;
        this.mCouponInfoList = new ArrayList<>();
        mInflater = (LayoutInflater) mActivity
                .getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
        mCouponPopupWindow = (LinearLayout) mInflater.inflate(R.layout.cart_coupon_linear, null);
        mCouponRecyclerView = (RecyclerView) mCouponPopupWindow.findViewById(R.id.cartfragment_discount_popuprv);
        mCouponListAdapter = new CouponListAdapter(mCouponInfoList);
        WarpLinearLayoutManager couponLayoutManager = new WarpLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mCouponRecyclerView.setLayoutManager(couponLayoutManager);
        mCouponRecyclerView.setAdapter(mCouponListAdapter);
        this.setContentView(mCouponPopupWindow);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setWidth(UIUtils.dp2px(mActivity, 373));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        initData();
    }

    public void setNewData(ArrayList<Coupon.CouponInfo> couponInfoList) {
        this.mCouponInfoList.clear();
        this.mCouponInfoList.addAll(couponInfoList);
        mCouponListAdapter.notifyDataSetChanged();
        if (couponInfoList.size() > 3) { // 优惠劵布局最大高度是420dp
            setHeight(UIUtils.dp2px(mActivity, 470));
        } else {
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        Log.e("CouponPopupWindow", "setNewData: " + couponInfoList.size());
    }

    private void initData() {

    }

}
