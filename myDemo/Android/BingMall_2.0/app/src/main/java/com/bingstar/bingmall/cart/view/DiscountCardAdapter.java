package com.bingstar.bingmall.cart.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.cart.bean.Coupon;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/3/14.
 */

public class DiscountCardAdapter extends BaseRecycleAdapter<Coupon.CouponInfo, DiscountCardAdapter.ViewHolder> {

    public DiscountCardAdapter(ArrayList<Coupon.CouponInfo> list) {
        super(list);
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public String currentType;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.discount_card_show_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Context context = holder.itemView.getContext();
        Coupon.CouponInfo couponInfo = getItem(position);
        switch (currentType) {
            case DiscountCardFragment.TYPE_USE:
            case DiscountCardFragment.TYPE_EXPIRED:
                holder.itemView.setBackgroundResource(R.drawable.discount_coupon_custom);
                holder.carditem_price_tv.setTextColor(context.getResources().getColor(R.color.white));
                holder.discount_card_name.setTextColor(context.getResources().getColor(R.color.white));
                holder.discount_card_detail.setTextColor(context.getResources().getColor(R.color.white));
                holder.discount_card_date.setTextColor(context.getResources().getColor(R.color.white));
                holder.discount_card_range.setTextColor(context.getResources().getColor(R.color.white));
                holder.discount_money_logo.setTextColor(context.getResources().getColor(R.color.white));
                holder.discount_card_range.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.discount_card_cshape));
                String type = couponInfo.getCouponType();
                if ("1".equals(type)) {  //通用
                    holder.discount_card_num.setTextColor(context.getResources().getColor(R.color.coupon_coutom_color));
                    holder.itemView.setBackgroundResource(R.drawable.discount_coupon_custom);
                } else if ("2".equals(type)) { //类目
                    holder.discount_card_num.setTextColor(context.getResources().getColor(R.color.coupon_category_color));
                    holder.itemView.setBackgroundResource(R.drawable.discount_coupon_single);
                } else if ("3".equals(type)) {  //单品
                    holder.discount_card_num.setTextColor(context.getResources().getColor(R.color.coupon_single_color));
                    holder.itemView.setBackgroundResource(R.drawable.discount_coupon_point);
                }

                break;
            case DiscountCardFragment.TYPE_OFF:
                holder.itemView.setBackgroundResource(R.drawable.discount_coupon_off);
                holder.carditem_price_tv.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                holder.discount_card_name.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                holder.discount_card_detail.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                holder.discount_card_date.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                holder.discount_card_range.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                holder.discount_money_logo.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                holder.discount_card_range.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.discount_card_tshape));
                holder.discount_card_num.setTextColor(context.getResources().getColor(R.color.goodsDetailName));
                break;
        }
        String couponNum = couponInfo.getCouponNum();
        if (couponNum != null) {
            holder.discount_card_num.setText("x" + couponInfo.getCouponNum() + " ");
        }
        holder.carditem_price_tv.setText(couponInfo.getCouponMoney());
        holder.discount_card_name.setText(couponInfo.getCouponName());
        holder.discount_card_detail.setText(couponInfo.getCouponDetail());
        holder.discount_card_date.setText("使用时间: " + couponInfo.getCouponValidity());
        holder.discount_card_range.setText(couponInfo.getCouponRange());
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        TextView carditem_price_tv;
        TextView discount_card_detail;
        TextView discount_card_date;
        TextView discount_card_range;
        TextView discount_card_name;
        TextView discount_money_logo;
        TextView discount_card_num;

        public ViewHolder(View itemView) {
            super(itemView);
            carditem_price_tv = (TextView) itemView.findViewById(R.id.carditem_price_tv);
            discount_card_detail = (TextView) itemView.findViewById(R.id.discount_card_detail);
            discount_card_date = (TextView) itemView.findViewById(R.id.discount_card_date);
            discount_card_range = (TextView) itemView.findViewById(R.id.discount_card_range);
            discount_card_name = (TextView) itemView.findViewById(R.id.discount_card_name);
            discount_money_logo = (TextView) itemView.findViewById(R.id.discount_money_logo);
            discount_card_num = (TextView) itemView.findViewById(R.id.discount_card_num);
        }
    }
}
