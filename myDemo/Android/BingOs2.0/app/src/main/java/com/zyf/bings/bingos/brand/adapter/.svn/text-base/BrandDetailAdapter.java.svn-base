package com.zyf.bings.bingos.brand.adapter;

import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.brand.bean.BrandDetailBean;
import com.zyf.bings.bingos.utils.ImageLoader;


/**
 * Created by wangshiqi on 2017/9/11.
 */
public class BrandDetailAdapter extends BaseQuickAdapter<BrandDetailBean.ProductInfoListBean,BaseViewHolder> {

    public BrandDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandDetailBean.ProductInfoListBean item) {
        ImageLoader.load(helper.getView(R.id.item_brand_goods_img), item.getProductPicList().get(0).getPicUrl());
        helper.setText(R.id.item_brand_goods_name, item.getBsjProductname());
        helper.setText(R.id.item_brand_goods_nPrice,mContext.getResources().getString(R.string.goods_detail_money) + item.getPrice());
        helper.setText(R.id.item_brand_goods_rPrice, "¥" + item.getLimitPrice());
        if (item.getLimitPrice() == null || item.getLimitPrice().equals("")) {
            helper.getView(R.id.item_brand_goods_rPrice).setVisibility(View.GONE);
        } else {
            TextView textView = helper.getView(R.id.item_brand_goods_rPrice);
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }

}
