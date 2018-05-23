package com.updayup.yuchance.indexdemo;

import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by yuchance on 2018/3/26.
 */

public class IndexAdapter extends BaseQuickAdapter<Bean.DataBean,BaseViewHolder> {


    public IndexAdapter(int layoutResId, @Nullable List<Bean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bean.DataBean item) {
       helper.setText(R.id.item_index_nameTv,item.getRealname());
       helper.setText(R.id.item_index_position,item.getId()+"");
       helper.setText(R.id.item_index_recycler_tv,item.getCooperation_mode_cn());
      CardView cardView = helper.getView(R.id.item_index_cardview);
      cardView.setRadius(20);
      cardView.setCardElevation(8);



    }
}
