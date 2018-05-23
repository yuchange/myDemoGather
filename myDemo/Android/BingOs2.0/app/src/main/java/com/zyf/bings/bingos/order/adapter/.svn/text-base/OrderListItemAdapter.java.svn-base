package com.zyf.bings.bingos.order.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.OrderListBean;
import com.zyf.bings.bingos.utils.ImageLoader;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/27 下午3:38
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/27 下午3:38
 * @modify by reason:{方法名}:{原因}
 */

public class OrderListItemAdapter extends BaseQuickAdapter< OrderListBean.ZorderListBean.ProductListBean,OrderListItemAdapter.OrderListItemHolder> {


    public OrderListItemAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListBean.ZorderListBean.ProductListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(OrderListItemHolder helper, OrderListBean.ZorderListBean.ProductListBean item) {
        helper.textItem.setText(item.getBsjProductName());
        ImageLoader.load(helper.imageItem, item.getImage_url());
        helper.itemNum.setText("X" + item.getNumber());
        helper.itemPrice.setText("￥" + item.getPrice());
        if (item.getSpecification() != null) {
            helper.itemSpec.setText("规格：" + item.getSpecification());
        }
    }

//    @Override
//    public OrderListItemAdapter.OrderListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_rv, parent,
//                false);
//        return new OrderListItemAdapter.OrderListItemHolder(view);
//    }

//    @Override
//    public void onBindViewHolder(OrderListItemAdapter.OrderListItemHolder holder, int position) {
//        holder.textitem.setText(myItemInfoList.get(position).getBsjProductName());
//        ImageLoader.load(holder.imageItem, myItemInfoList.get(position).getImage_url());
//       // Log.i("OrderLitem",myItemInfoList.get"调用了1次");
//        holder.itemNum.setText(myItemInfoList.get(position).getNumber());
//        holder.itemPrice.setText("￥" + myItemInfoList.get(position).getPrice());
//        if (myItemInfoList.get(position).getSpecification() != null) {
//            holder.itemSpec.setText("规格：" + myItemInfoList.get(position).getSpecification());
//        }
//    }



//    @Override
//    public int getItemCount() {
//        return myItemInfoList.size();
//    }

    class OrderListItemHolder extends BaseViewHolder{

        private TextView textItem;//名字
        private ImageView imageItem;//图片
        private TextView itemSpec; // 规格
        private TextView itemNum; // 数量
        private TextView itemPrice;
        public OrderListItemHolder(View itemView) {
            super(itemView);
            textItem = (TextView) itemView.findViewById(R.id.item_order_product_name);
            imageItem = (ImageView) itemView.findViewById(R.id.item_order_product_img);
            itemSpec = (TextView) itemView.findViewById(R.id.item_order_product_spec);
            itemNum = (TextView) itemView.findViewById(R.id.item_order_product_num);
            itemPrice = (TextView) itemView.findViewById(R.id.item_order_product_price);
        }
    }

}
