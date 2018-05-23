package com.zyf.bings.bingos.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.OrderListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class AllOrdersAdapter extends BaseQuickAdapter<OrderListBean.ZorderListBean, BaseViewHolder> {
    private Context mContext;


    public AllOrdersAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListBean.ZorderListBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ZorderListBean item) {
        helper.setText(R.id.tv_order_time, item.getAddTime());
        helper.setText(R.id.tv_order_price, "¥" + item.getZorder_total_money());
        helper.setText(R.id.tv_order_product_num, item.getGoodsCount() + "个商品");
        helper.addOnClickListener(R.id.tv_order_operation);
        setOrderStatus(helper, item);

        OrderProductAdapter adapter = new OrderProductAdapter(R.layout.item_all_orders_product, item.getProductList());
        RecyclerView view = (RecyclerView) helper.getView(R.id.rv_order_entry);

        view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);

    }


    private void setOrderStatus(BaseViewHolder helper, OrderListBean.ZorderListBean item) {

        switch (item.getZstate()) {
            case "0":
                helper.setText(R.id.tv_order_status, "待付款");
                helper.setText(R.id.tv_order_operation, "立即付款");
                helper.getView(R.id.tv_order_operation).setBackgroundResource(R.mipmap.immediate_payment);
                ((TextView) helper.getView(R.id.tv_order_operation)).setTextColor(Color.WHITE);
                break;
            case "1":
                helper.setText(R.id.tv_order_status, "待发货");
                helper.setText(R.id.tv_order_operation, "提醒发货");
                helper.getView(R.id.tv_order_operation).setBackgroundResource(R.mipmap.transparency);
                ((TextView) helper.getView(R.id.tv_order_operation)).setTextColor(mContext.getResources().getColor(R.color.tv_common_red));
                break;
            case "2":
                helper.setText(R.id.tv_order_status, "待收货");
                helper.setText(R.id.tv_order_operation, "查看物流");
                helper.getView(R.id.tv_order_operation).setBackgroundResource(R.mipmap.scan_logisuics);
                ((TextView) helper.getView(R.id.tv_order_operation)).setTextColor(mContext.getResources().getColor(R.color.c_919191));
                break;

            case "5":
                helper.setText(R.id.tv_order_status, "已完成");
                helper.setText(R.id.tv_order_operation, "再次购买");
                helper.getView(R.id.tv_order_operation).setBackgroundResource(R.mipmap.gray_box);
                ((TextView) helper.getView(R.id.tv_order_operation)).setTextColor(Color.WHITE);
                break;
            case "6":
                helper.setText(R.id.tv_order_status, "已取消");
                helper.setText(R.id.tv_order_operation, "再次购买");
                helper.getView(R.id.tv_order_operation).setBackgroundResource(R.mipmap.gray_box);
                ((TextView) helper.getView(R.id.tv_order_operation)).setTextColor(Color.WHITE);
                break;
            case "7":
                helper.setText(R.id.tv_order_status, "售后中");
                helper.setText(R.id.tv_order_operation, "再次购买");
                helper.getView(R.id.tv_order_operation).setBackgroundResource(R.mipmap.gray_box);
                ((TextView) helper.getView(R.id.tv_order_operation)).setTextColor(Color.WHITE);
                break;
            case "99":
                helper.setText(R.id.tv_order_status, "失败");
                helper.getView(R.id.tv_order_operation).setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

}
