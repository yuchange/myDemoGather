package com.zyf.bings.bingos.order.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.OrderListBean;
import com.zyf.bings.bingos.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class OrderProductAdapter extends BaseQuickAdapter<OrderListBean.ZorderListBean.ProductListBean, BaseViewHolder> {
    private List<OrderListBean.ZorderListBean.ProductListBean> mZorderProducts;

    public OrderProductAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListBean.ZorderListBean.ProductListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ZorderListBean.ProductListBean item) {
        helper.setText(R.id.item_order_product_name, item.getBsjProductName());
        if (!TextUtils.isEmpty(item.getSpecification())) {

            helper.setText(R.id.item_order_product_spec, "规格:" + item.getSpecification());
        }
        helper.setText(R.id.item_order_product_price, "¥" + item.getPrice());
        helper.setText(R.id.item_order_product_num, "X" + item.getNumber());
        ImageLoader.load((ImageView) helper.getView(R.id.item_order_product_img), item.getImage_url());


    }
}