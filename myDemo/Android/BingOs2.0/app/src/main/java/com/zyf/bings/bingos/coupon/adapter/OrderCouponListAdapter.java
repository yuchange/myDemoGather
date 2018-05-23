package com.zyf.bings.bingos.coupon.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.coupon.bean.OrderCouponBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangshiqi on 2017/11/3.
 */

public class OrderCouponListAdapter extends BaseQuickAdapter<OrderCouponBean.CouponInfoListBean, BaseViewHolder> {
    public OrderCouponListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCouponBean.CouponInfoListBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(item.getCouponValidity());
            String time = dateFormat.format(date);
            helper.setText(R.id.tv_coupon_time, "使用时间" + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        helper.setText(R.id.tv_coupon_detail, item.getCouponDetail());
        helper.setText(R.id.tv_coupon_range, item.getCouponRange());
        helper.setText(R.id.tv_coupon_price, item.getCouponMoney());
        if (item.getCouponRange().equals("全场使用")) {
            helper.setVisible(R.id.tv_will_expire, true);
            helper.setText(R.id.tv_will_expire, "新人专享");
        } else {
            helper.setVisible(R.id.tv_will_expire, false);
        }
    }
}
