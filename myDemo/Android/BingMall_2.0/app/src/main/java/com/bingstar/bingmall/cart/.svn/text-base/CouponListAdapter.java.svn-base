package com.bingstar.bingmall.cart;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/3/21.
 */

public class CouponListAdapter extends BaseRecycleAdapter<Coupon.CouponInfo, CouponListAdapter.ViewHolder> {


    public CouponListAdapter(ArrayList list) {
        super(list);

    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.discount_card_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Coupon.CouponInfo couponInfo = getItem(position);
        holder.carditem_view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (couponInfo.isSelected()) {
            holder.discount_card_item_iv.setImageResource(R.drawable.discount_card_isselect);
        } else {
            holder.discount_card_item_iv.setImageResource(R.drawable.discount_card_unselect);
        }
        holder.card_item_price.setText(couponInfo.getCouponMoney());
        holder.discount_item_range.setText(couponInfo.getCouponRange());
        holder.discount_item_detail.setText(couponInfo.getCouponDetail());
        holder.discount_item_validity.setText(couponInfo.getCouponValidity());
        String remindText = couponInfo.getRemindText();
        if (!TextUtils.isEmpty(remindText)) {
            holder.coupon_remind.setText(remindText);
        }
        String remindFlag = couponInfo.getRemindFlag();
        if (!TextUtils.isEmpty(remindFlag) && remindFlag.equals("1")) {
            holder.coupon_remind.setTextColor(Color.parseColor("#FF553B"));
        } else {
            holder.coupon_remind.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, viewType));
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        TextView card_item_price;
        TextView discount_item_range;
        TextView discount_item_detail;
        TextView discount_item_validity;
        View carditem_view;
        ImageView discount_card_item_iv;
        TextView coupon_remind;


        public ViewHolder(View itemView) {
            super(itemView);
            card_item_price = (TextView) itemView.findViewById(R.id.card_item_price);
            discount_item_range = (TextView) itemView.findViewById(R.id.discount_item_range);
            discount_item_detail = (TextView) itemView.findViewById(R.id.discount_item_detail);
            discount_item_validity = (TextView) itemView.findViewById(R.id.discount_item_validity);
            carditem_view = itemView.findViewById(R.id.carditem_view);
            discount_card_item_iv = (ImageView) itemView.findViewById(R.id.discount_card_item_iv);
            coupon_remind = (TextView) itemView.findViewById(R.id.card_coupon_remind);
        }
    }
}
