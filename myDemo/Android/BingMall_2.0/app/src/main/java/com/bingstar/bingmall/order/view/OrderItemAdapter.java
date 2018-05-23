package com.bingstar.bingmall.order.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderListInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/27 下午4:01
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/27 下午4:01
 * @modify by reason:{方法名}:{原因}
 */

public class OrderItemAdapter extends BaseAdapter {

    private OrderListInfo.OrderMoreInfo orderMoreInfo;
    private int parentPosition;

    private static final int TYPE_1 = 0;
    private static final int TYPE_2 = 1;

    public OrderItemAdapter(OrderListInfo.OrderMoreInfo orderMoreInfo) {
        this.orderMoreInfo = orderMoreInfo;
    }

    @Override
    public int getCount() {
        return orderMoreInfo.getOrdergoodsList().size();
    }

    @Override
    public OrderListInfo.OrderMoreInfo.OrderGoodsList getItem(int position) {
        return orderMoreInfo.getOrdergoodsList().get(position);
    }

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OrderViewHolder orderViewHolder1 = null;
        OrderViewHolder2 orderViewHolder2 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_1: {
                    convertView = View.inflate(parent.getContext(), R.layout.order_detail_list, null);
                    orderViewHolder1 = new OrderViewHolder();
                    orderViewHolder1.text_orderId = (TextView) convertView.findViewById(R.id.order_id);
                    orderViewHolder1.text_weight = (TextView) convertView.findViewById(R.id.order_weight);
                    orderViewHolder1.text_name = (TextView) convertView.findViewById(R.id.order_name);
                    orderViewHolder1.text_limitmoney = (TextView) convertView.findViewById(R.id.order_limitmoney);
                    orderViewHolder1.order_detail_image = (ImageView) convertView.findViewById(R.id.order_detail_image);
                    orderViewHolder1.image_logistic = (ImageView) convertView.findViewById(R.id.order_detail_logistic);
                    orderViewHolder1.text_order_state = (TextView) convertView.findViewById(R.id.order_state);
                    convertView.setTag(orderViewHolder1);
                    break;
                }
                case TYPE_2: {
                    convertView = View.inflate(parent.getContext(), R.layout.order_detail_list_2, null);
                    orderViewHolder2 = new OrderViewHolder2();
                    orderViewHolder2.text_weight = (TextView) convertView.findViewById(R.id.order_weight);
                    orderViewHolder2.text_name = (TextView) convertView.findViewById(R.id.order_name);
                    orderViewHolder2.text_limitmoney = (TextView) convertView.findViewById(R.id.order_limitmoney);
                    orderViewHolder2.order_diviler = (TextView) convertView.findViewById(R.id.order_text);
                    orderViewHolder2.order_detail_image = (ImageView) convertView.findViewById(R.id.order_detail_image);
                    convertView.setTag(orderViewHolder2);
                    break;
                }
            }

        } else {
            switch (type) {
                case TYPE_1: {
                    orderViewHolder1 = (OrderViewHolder) convertView.getTag();
                    break;
                }
                case TYPE_2: {
                    orderViewHolder2 = (OrderViewHolder2) convertView.getTag();
                    break;
                }
            }
        }

        switch (type) {
            case TYPE_1: {
                orderViewHolder1.text_orderId.setText(parent.getResources().getString(R.string.order_detail_adapter_title));
                orderViewHolder1.text_name.setText(getItem(position).getGoods_name());
                if (null != getItem(position).getWeight()) {
                    orderViewHolder1.text_weight.setText(getItem(position).getWeight() + getItem(position).getUnit()
                            + "  " + parent.getContext().getString(R.string.sign_multiplied) + getItem(position).getNumber());
                }
                if (orderMoreInfo.getCycleflag().equals("01")) {
                    orderViewHolder1.text_limitmoney.setText(parent.getResources().getString(R.string.discount_money) + orderMoreInfo.getOrder_total_money());
                }else {
                    orderViewHolder1.text_limitmoney.setText(parent.getResources().getString(R.string.discount_money) + getItem(position).getGoodsTotal_amount());
                }
                orderViewHolder1.image_logistic.setVisibility(View.VISIBLE);
//                    00:周期购未激活订单0:待支付1:待发货 2:已发货5:已收货99:失败12:申请退款中13:退款审核中14:退款成功22:申请退货中23:退货审核中24:退货成功
                if (orderMoreInfo != null  && ("2".equals(orderMoreInfo.getState()) ||
                        "5".equals(orderMoreInfo.getState()))) {//已发货和已收货
                    orderViewHolder1.image_logistic.setVisibility(View.VISIBLE);
                } else {
                    orderViewHolder1.image_logistic.setVisibility(View.GONE);
                }
                orderViewHolder1.image_logistic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 订单追踪
                         */
                        LogisticsBean logisticsBean = new LogisticsBean();
                        logisticsBean.setLogistics_id(orderMoreInfo.getLogistics_no());
                        logisticsBean.setOrderinfo_id(getItem(position).getOrderinfo_id());
                        EventBus.getDefault().post(new EventMsg(OrderDetailAdapter.class, OrderStates.LOGISTICS_CLICK, parentPosition + "", position + ""));
                    }
                });

                Util.imageTransform(parent.getContext(), orderViewHolder1.order_detail_image,getItem(position).getPicUrl());
                /**
                 * 设置子订单state
                 */
//                    00:周期购未激活订单0:待支付1:待发货 2:已发货5:已收货99:失败12:申请退款中13:退款审核中14:退款成功22:申请退货中23:退货审核中24:退货成功
           //     orderViewHolder1.text_order_state.setText(OrderStates.parseOrderState(parent.getResources(),orderMoreInfo.getState()));
                break;
            }
            case TYPE_2: {
                orderViewHolder2.text_name.setText(getItem(position).getGoods_name());
                if (null != getItem(position).getWeight()) {
                    orderViewHolder2.text_weight.setText(getItem(position).getWeight() + getItem(position).getUnit()
                            + "  " + parent.getContext().getString(R.string.sign_multiplied) + getItem(position).getNumber());
                }
                orderViewHolder2.text_limitmoney.setText(parent.getResources().getString(R.string.discount_money) + getItem(position).getGoodsTotal_amount());

                Util.imageTransform(parent.getContext(), orderViewHolder2.order_detail_image,getItem(position).getPicUrl());
                break;
            }
        }


        return convertView;
    }

    private class OrderViewHolder {
        TextView text_name;
        TextView text_weight;
        TextView text_orderId;
        TextView text_limitmoney;
        ImageView order_detail_image;
        TextView text_order_state;
        ImageView image_logistic;

    }

    private class OrderViewHolder2 {
        TextView text_name;
        TextView text_weight;
        TextView text_limitmoney;
        ImageView order_detail_image;
        TextView order_diviler;
    }

    private class LogisticsBean {
        private String logistics_id;//物流单号
        private String orderinfo_id;//子订单号

        public String getOrderinfo_id() {
            return orderinfo_id;
        }

        public void setOrderinfo_id(String orderinfo_id) {
            this.orderinfo_id = orderinfo_id;
        }

        public String getLogistics_id() {
            return logistics_id;
        }

        public void setLogistics_id(String logistics_id) {
            this.logistics_id = logistics_id;
        }
    }
}
