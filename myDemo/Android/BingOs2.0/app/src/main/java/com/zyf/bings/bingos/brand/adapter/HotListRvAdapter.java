package com.zyf.bings.bingos.brand.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.brand.bean.HotListBean;
import com.zyf.bings.bingos.utils.ImageLoader;


/**
 * Created by wangshiqi on 2017/9/11.
 */
public class HotListRvAdapter extends BaseQuickAdapter<HotListBean.BrandListBean,BaseViewHolder> {


    public HotListRvAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotListBean.BrandListBean item) {
        ImageLoader.load(helper.getView(R.id.item_hot_img), item.getBrand_url());
    }

}
