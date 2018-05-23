package com.zyf.bings.bingos.order.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.LogisticsListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SuborderAapter extends BaseQuickAdapter<LogisticsListBean.OrderInfoListBean, BaseViewHolder> {
    private Context context;

    public SuborderAapter(@LayoutRes int layoutResId, @Nullable List<LogisticsListBean.OrderInfoListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsListBean.OrderInfoListBean item) {

        List< LogisticsListBean.OrderInfoListBean.GoodsListBean> ordergoodsList = item.getGoodsList();
        RecyclerView rvGoodsIv = helper.getView(R.id.rv_goods_iv);
        SuborderGoodsInfoAdapter suborderGoodsInfoAdapter = new SuborderGoodsInfoAdapter(R.layout.item_suborder_goods_info, ordergoodsList);
        rvGoodsIv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvGoodsIv.addItemDecoration(new SuborderGoodsItemDecoration(39));
        rvGoodsIv.setAdapter(suborderGoodsInfoAdapter);
        helper.addOnClickListener(R.id.tv_check_logistics);
    }
}
