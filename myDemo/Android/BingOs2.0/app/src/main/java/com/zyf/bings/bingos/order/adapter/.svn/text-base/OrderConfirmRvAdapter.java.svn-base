package com.zyf.bings.bingos.order.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.ImageLoader;

/**
 * Created by wangshiqi on 2017/9/29.
 */

public class OrderConfirmRvAdapter extends BaseQuickAdapter<ConfirmOrderBean.ItemBean, BaseViewHolder> {
    private TextView mTvTitle;
    private FlexboxLayout mFlexlayout;
    private TextView mTvCount;
    private int mTag;
    private int mCount;

    public OrderConfirmRvAdapter(@LayoutRes int layoutResId, int tag) {
        super(layoutResId);
        this.mTag = tag;
    }


    //获取规格布局
    public Button getButton(Context context, int resource, int textColor, String text) {
//        规格
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(textColor);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        button.setAllCaps(false); //防止自动转换为大写
//        button.setBackgroundResource(resource);
        button.setBackgroundDrawable(context.getResources().getDrawable(resource));
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dp2px(context, 40));
        params.setMargins(0, 0, DensityUtil.dp2px(context, 44), 0);
        button.setLayoutParams(params);
        return button;
    }

    @Override
    protected void convert(BaseViewHolder helper, ConfirmOrderBean.ItemBean item) {
        ImageLoader.load(helper.getView(R.id.iv_order_confirm_img), item.getImgUrl());
        mTvTitle = helper.getView(R.id.tv_order_confirm_title);
        mTvTitle.setText(item.getTitle());
        mFlexlayout = helper.getView(R.id.flex_order_confirm_spec);
        if (mFlexlayout != null) {
            mFlexlayout.removeAllViews();
        }
        mFlexlayout.addView(getButton(helper.getConvertView().getContext(), R.drawable.shape_rectangle_goods_detail_select,
                helper.getConvertView().getContext().getResources().getColor(R.color.tv_goods_detail_spec), item.getSpec()));
        mTvCount = helper.getView(R.id.tv_order_confirm_count);
        mCount = item.getCount();
        mTvCount.setText(mCount + "");
        ImageButton ivBtnDecre = helper.getView(R.id.iv_item_order_confirm_decre);
        ImageButton ivBtnIncre = helper.getView(R.id.iv_item_order_confirm_incre);
        helper.addOnClickListener(R.id.iv_item_order_confirm_decre);
        helper.addOnClickListener(R.id.iv_item_order_confirm_incre);
        if (mTag == 1) {
            ivBtnDecre.setVisibility(View.VISIBLE);
            ivBtnIncre.setVisibility(View.VISIBLE);

        } else {
            ivBtnDecre.setVisibility(View.GONE);
            ivBtnIncre.setVisibility(View.GONE);
        }
    }
}
