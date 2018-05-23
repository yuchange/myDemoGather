package com.zyf.bings.bingos.order.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.LogisticsComanyInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class LogisticsCompanyAdapter extends BaseQuickAdapter<LogisticsComanyInfo.ShippingListBean, BaseViewHolder> {


    public LogisticsCompanyAdapter(@LayoutRes int layoutResId, @Nullable List<LogisticsComanyInfo.ShippingListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, LogisticsComanyInfo.ShippingListBean item) {


        helper.setText(R.id.tv_logistics_company, item.getShipping_name());


    }
}
