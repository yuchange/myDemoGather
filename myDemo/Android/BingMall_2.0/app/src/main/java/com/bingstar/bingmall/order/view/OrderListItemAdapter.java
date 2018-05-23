package com.bingstar.bingmall.order.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.order.bean.OrderList;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/27 下午3:38
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/27 下午3:38
 * @modify by reason:{方法名}:{原因}
 */

public class OrderListItemAdapter extends  RecyclerView.Adapter<OrderListItemAdapter.OrderListItemHolder>{

    private List<OrderList.Zorder.ZorderProduct> myItemInfoList;


    public List<OrderList.Zorder.ZorderProduct> getMyItemInfoList() {
        return myItemInfoList;
    }

    public void setMyItemInfoList(List<OrderList.Zorder.ZorderProduct> myItemInfoList) {
        this.myItemInfoList = myItemInfoList;
    }

    public OrderListItemAdapter(List<OrderList.Zorder.ZorderProduct> myItemInfoList) {
        this.myItemInfoList = myItemInfoList;
    }

    @Override
    public OrderListItemAdapter.OrderListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent,
                false);
        return new OrderListItemAdapter.OrderListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderListItemAdapter.OrderListItemHolder holder, int position) {
        holder.textitem.setText(myItemInfoList.get(position).getBsjProductName());
        Util.imageTransform(holder.itemView.getContext(), holder.imageItem,myItemInfoList.get(position).getImage_url());
       // Log.i("OrderLitem",myItemInfoList.get"调用了1次");
    }

    @Override
    public int getItemCount() {
        return myItemInfoList.size();
    }

    class OrderListItemHolder extends RecyclerView.ViewHolder{

        private TextView textitem;//名字
        private ImageView imageItem;//图片
        public OrderListItemHolder(View itemView) {
            super(itemView);
            textitem = (TextView) itemView.findViewById(R.id.order_list_item_text);
            imageItem = (ImageView) itemView.findViewById(R.id.order_list_item_image);
        }
    }

}
