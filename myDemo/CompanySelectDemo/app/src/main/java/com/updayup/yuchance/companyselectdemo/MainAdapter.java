package com.updayup.yuchance.companyselectdemo;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by yuchance on 2018/5/9.
 */

public class MainAdapter extends BaseQuickAdapter<CompanyNameBean.ResultBean,BaseViewHolder> {


    public MainAdapter(int layoutResId, @Nullable List<CompanyNameBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyNameBean.ResultBean item) {
        helper.setText(R.id.item_tv,item.getCompanyName());
    }
}
