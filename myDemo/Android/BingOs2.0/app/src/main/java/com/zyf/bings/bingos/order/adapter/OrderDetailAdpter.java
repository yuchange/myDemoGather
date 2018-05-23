package com.zyf.bings.bingos.order.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.OrderInfoBean;
import com.zyf.bings.bingos.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 * 订单详情界面的适配器
 */

public class OrderDetailAdpter extends BaseQuickAdapter<OrderInfoBean.OrderListBean.OrdergoodsListBean, BaseViewHolder> {

    private Context mContext;

    public OrderDetailAdpter(Context context, @LayoutRes int layoutResId, @Nullable List<OrderInfoBean.OrderListBean.OrdergoodsListBean> data) {
        super(layoutResId, data);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfoBean.OrderListBean.OrdergoodsListBean item) {

        if (!TextUtils.isEmpty(item.getSpecification())) {

            helper.setText(R.id.tv_item_order_product_spec, "规格:" + item.getSpecification());
        }
        helper.setText(R.id.tv_item_order_product_name, item.getGoods_name());
        helper.setText(R.id.tv_item_order_product_price, item.getPrice());//商品单价
        helper.setText(R.id.tv_item_order_product_num, "X" + item.getNumber());
        ImageLoader.load(helper.getView(R.id.iv_item_order_product_img), item.getPicUrl());

        setOrderState(helper, item);
        helper.addOnClickListener(R.id.tv_add_to_cart);
        helper.addOnClickListener(R.id.tv_order_refund);
        helper.addOnClickListener(R.id.tv_return_of_goods);
        helper.addOnClickListener(R.id.tv_upload_logistics);
        helper.addOnClickListener(R.id.tv_cancel_request);
        helper.addOnClickListener(R.id.tv_order_tracking);


    }

    private void setOrderState(BaseViewHolder helper, OrderInfoBean.OrderListBean.OrdergoodsListBean item) {
        //商品状态，1 申请退款中 2 同意退款 3 退款审核中 4 退款成功 5 申请退货 6 同意退货 7 退货审核中 8 退货成功
        //  10:待支付  11:待发货   12:待收货  15:已完成 16：已取消 17：售后中  199:失败
        String orderState = item.getGoodsState();
        String attribute = item.getAttribute();

        if (attribute == null) {
            return;
        }
        switch (attribute) {//商品是否是七天无理由 1:是 0：否
            case "0":
                helper.getView(R.id.tv_is_seven_day).setVisibility(View.GONE);
                break;
            case "1":
                helper.getView(R.id.tv_is_seven_day).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                break;
            default:
                break;
        }


        if (orderState == null) {
            return;
        }
        switch (orderState) {

            case "1":
                helper.setText(R.id.tv_item_goods_state, "申请退款中");
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));
                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);
                break;

            case "2":
                helper.setText(R.id.tv_item_goods_state, "卖家同意退款");
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);

                break;

            case "3":
                helper.setText(R.id.tv_item_goods_state, "买家已申请退款" + R.string.goods_detail_money + item.getGoodsTotal_amount() + "卖家正在审核");
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);
                break;
            case "4":
                helper.setText(R.id.tv_item_goods_state, "退款成功，实际退款金额" + item.getRefund_amount());
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);

                break;
            case "5":
                helper.setText(R.id.tv_item_goods_state, "申请退货中");
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);
                break;
            case "6":
                helper.setText(R.id.tv_item_goods_state, "卖家同意退货");
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);

                break;

            case "7":
                helper.setText(R.id.tv_item_goods_state, "买家已申请退货，等待卖家审核，预计退款金额" + item.getGoodsTotal_amount());
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_cancel_request).setVisibility(View.VISIBLE);
                break;

            case "8":
                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                ((TextView) helper.getView(R.id.tv_item_goods_state)).setTextColor(ContextCompat.getColor(mContext, R.color.tv_common_red));

                helper.setText(R.id.tv_item_goods_state, "退货成功,实际退款金额" + item.getRefund_amount());
                break;


            case "10":
                helper.setText(R.id.tv_item_goods_state, "待付款");
                ((LinearLayout) helper.getView(R.id.ll_bottom_operation_order)).setVisibility(View.GONE);

                break;

            case "11":
                helper.setText(R.id.tv_item_goods_state, "待发货");
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_add_to_cart).setVisibility(View.VISIBLE);
                break;

            case "12":

                helper.setText(R.id.tv_item_goods_state, "待收货");


                break;
            case "15":
                helper.setText(R.id.tv_item_goods_state, "已完成");
                helper.getView(R.id.tv_order_tracking).setVisibility(View.VISIBLE);
                break;

            case "16":
                helper.setText(R.id.tv_item_goods_state, "已取消");
                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);
                helper.getView(R.id.tv_add_to_cart).setVisibility(View.VISIBLE);

                break;
            case "17":
                helper.setText(R.id.tv_item_goods_state, "售后中");
                helper.getView(R.id.tv_order_refund).setVisibility(View.GONE);
                helper.getView(R.id.tv_return_of_goods).setVisibility(View.GONE);

                break;


            case "199":
                helper.setText(R.id.tv_item_goods_state, "失败");
                helper.getView(R.id.ll_bottom_operation_order).setVisibility(View.GONE);

                break;
            default:
                break;
        }


    }
}
