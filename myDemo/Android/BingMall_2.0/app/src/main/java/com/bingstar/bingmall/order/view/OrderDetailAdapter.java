package com.bingstar.bingmall.order.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cycle.view.CycleImageView;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderListInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by liumengqiang on 2017/3/5.
 */

public class OrderDetailAdapter extends BaseAdapter {

    private List<OrderListInfo.OrderMoreInfo> orderGoodsArrayList;

    public OrderDetailAdapter(List<OrderListInfo.OrderMoreInfo> orderGoodsArrayList) {
        this.orderGoodsArrayList = orderGoodsArrayList;
    }

    @Override
    public int getCount() {
        return orderGoodsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderGoodsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderListViewHolder orderListViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.orderdetaillist, null);
            orderListViewHolder = new OrderListViewHolder();
            orderListViewHolder.listView = (ListView) convertView.findViewById(R.id.order_detail_item_list);
            convertView.setTag(orderListViewHolder);
        } else {
            orderListViewHolder = (OrderListViewHolder) convertView.getTag();
        }
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(orderGoodsArrayList.get(position));
        orderItemAdapter.setParentPosition(position);
        orderListViewHolder.listView.setAdapter(orderItemAdapter);
        return convertView;
    }

    /**
     * OrderMoreInfo的Viewholder
     */
    class OrderListViewHolder {
        public ListView listView;
    }






}
