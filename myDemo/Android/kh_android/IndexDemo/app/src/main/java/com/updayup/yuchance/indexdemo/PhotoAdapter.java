package com.updayup.yuchance.indexdemo;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by yuchance on 2018/4/4.
 */

public class PhotoAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public PhotoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
