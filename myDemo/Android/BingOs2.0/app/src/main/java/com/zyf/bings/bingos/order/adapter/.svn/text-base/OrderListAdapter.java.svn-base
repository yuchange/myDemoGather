package com.zyf.bings.bingos.order.adapter;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.WrapLinearLayoutManager;
import com.zyf.bings.bingos.order.bean.OrderListBean;
import com.zyf.bings.bingos.utils.CustomRecycleView;

/**
 * Created by wangshiqi on 2017/9/22.
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.ZorderListBean, BaseViewHolder> {
    private FragmentManager mFragmentManager;
    private OrderListItemAdapter mOrderListItemAdapter;

    public OrderListAdapter(@LayoutRes int layoutResId, FragmentManager fragmentManager) {
        super(layoutResId);
        this.mFragmentManager = fragmentManager;
    }


//    public OrderListAdapter(@LayoutRes int layoutResId, FragmentManager fragmentManager) {
//        super(layoutResId);
//        this.mFragmentManager = fragmentManager;
//    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ZorderListBean item) {
        helper.setText(R.id.item_unpay_time, "下单时间：" + item.getAddTime());
        helper.setText(R.id.item_unpay_num, item.getGoodsCount() + "个商品");
        helper.setText(R.id.item_upay_realprice, item.getZorder_total_money());
        CustomRecycleView recyclerView = helper.getView(R.id.item_order_rv);
        WrapLinearLayoutManager wrapLinearLayoutManager = new WrapLinearLayoutManager(helper.getConvertView().getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(wrapLinearLayoutManager);
        mOrderListItemAdapter = new OrderListItemAdapter(R.layout.item_order_rv, item.getProductList());
        recyclerView.setAdapter(mOrderListItemAdapter);
        TextView payText = helper.getView(R.id.immediate_payment_tv);
        helper.addOnClickListener(R.id.immediate_payment_tv);
        // 订单标记   0:待支付 1:待发货 2:待收货 5:已收货 6:已取消 99:失败 7 ：售后中
        int zState = Integer.parseInt(item.getZstate().trim());
        switch (zState) {
            case 0:
                helper.setText(R.id.item_unpay_zsState, "待付款");
                break;
            case 1:
                payText.setText("提醒发货");
                payText.setTextColor(helper.getConvertView().getResources().getColor(R.color.tv_common_red));
                payText.setBackgroundResource(R.mipmap.wait_send);
                helper.setText(R.id.item_unpay_zsState, "待发货");
                break;
            case 2:
                payText.setText("再次购买");
                payText.setBackgroundResource(R.mipmap.buy_again);
                helper.setText(R.id.item_unpay_zsState, "待收货");
                break;
            case 5:
                payText.setText("再次购买");
                payText.setBackgroundResource(R.mipmap.buy_again);
                helper.setText(R.id.item_unpay_zsState, "已完成");
                break;
            case 6:
                payText.setText("再次购买");
                payText.setBackgroundResource(R.mipmap.buy_again);
                helper.setText(R.id.item_unpay_zsState, "已取消");
                break;
            case 7:
                payText.setText("再次购买");
                payText.setBackgroundResource(R.mipmap.buy_again);
                helper.setText(R.id.item_unpay_zsState, "售后中");
                break;
            default:
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }
}
