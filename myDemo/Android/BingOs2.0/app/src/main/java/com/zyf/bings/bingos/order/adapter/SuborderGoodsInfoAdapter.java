package com.zyf.bings.bingos.order.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.LogisticsListBean;
import com.zyf.bings.bingos.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SuborderGoodsInfoAdapter extends BaseQuickAdapter<LogisticsListBean.OrderInfoListBean.GoodsListBean, BaseViewHolder> {
    public SuborderGoodsInfoAdapter(@LayoutRes int layoutResId, @Nullable List<LogisticsListBean.OrderInfoListBean.GoodsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsListBean.OrderInfoListBean.GoodsListBean item) {

        ImageLoader.load(helper.getView(R.id.iv_order_goods), item.getImage_url());

        helper.setText(R.id.tv_goods_name, item.getGoods_name());


    }
}
