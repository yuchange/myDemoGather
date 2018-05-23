package com.zyf.bings.bingos.coupon.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.coupon.bean.CouponListBean;

/**
 * @author wangshiqi
 * @date 2017/10/27
 */

public class CouponListAdapter extends BaseQuickAdapter<CouponListBean.CouponInfoListBean, BaseViewHolder> {
    private String flag;
    private Context mContext;

    public CouponListAdapter(@LayoutRes int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.CouponInfoListBean item) {
//        String startStr = item.getCouponValidity().substring(0, item.getCouponValidity().indexOf("-") - 6);
//        Log.d("aaa", startStr);
//        String endStr = item.getCouponValidity().substring(item.getCouponValidity().indexOf("-") + 1, item.getCouponValidity().length() - 6);
//        Log.d("aaa", endStr);
        helper.setText(R.id.tv_coupon_time,"使用时间" + item.getCouponValidity());

        helper.setText(R.id.tv_coupon_detail, item.getCouponDetail());
        helper.setText(R.id.tv_coupon_range, item.getCouponRange());
        helper.setText(R.id.tv_coupon_price, item.getCouponMoney());
        switch (flag) {
            case "0":
                if (item.getCouponRange().equals("全场使用")) {
                    helper.setVisible(R.id.tv_will_expire, true);
                    helper.setText(R.id.tv_will_expire, "新人专享");
                } else {
                    helper.setVisible(R.id.tv_will_expire, false);
                }
                break;
            case "1":
                helper.setVisible(R.id.tv_will_expire, true);
                helper.setText(R.id.tv_will_expire, "即将过期");
                break;
            case "2":
                helper.setVisible(R.id.ll_coupon, true);
                helper.setVisible(R.id.tv_coupon_time, false);
                helper.setVisible(R.id.tv_will_expire, false);
                helper.setText(R.id.tv_coupon_failed_count, item.getCouponNum());
                helper.setVisible(R.id.iv_delete_coupon, true);
                helper.setVisible(R.id.tv_coupon_used_txt, true);
                helper.setText(R.id.tv_coupon_used_txt, "已失效");
                helper.addOnClickListener(R.id.iv_delete_coupon);
                helper.setBackgroundRes(R.id.tv_coupon_range, R.mipmap.coupon_range_gray_bg);
                helper.setTextColor(R.id.tv_coupon_money, mContext.getResources().getColor(R.color.c_919191));
                helper.setTextColor(R.id.tv_coupon_price, mContext.getResources().getColor(R.color.c_919191));
                break;
            case "3":
                helper.setVisible(R.id.tv_will_expire, false);
                helper.setVisible(R.id.iv_delete_coupon, true);
                helper.setVisible(R.id.tv_coupon_used_txt, true);
                helper.setText(R.id.tv_coupon_used_txt, "已使用");
                helper.addOnClickListener(R.id.iv_delete_coupon);
                helper.setText(R.id.tv_coupon_time,"使用时间" + item.getUseDateString());
                helper.setBackgroundRes(R.id.tv_coupon_range, R.mipmap.coupon_range_gray_bg);
                helper.setTextColor(R.id.tv_coupon_money, mContext.getResources().getColor(R.color.c_919191));
                helper.setTextColor(R.id.tv_coupon_price, mContext.getResources().getColor(R.color.c_919191));
                break;
            default:
                break;

        }
    }


}
