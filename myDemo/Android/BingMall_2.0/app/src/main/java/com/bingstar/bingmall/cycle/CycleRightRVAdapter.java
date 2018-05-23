package com.bingstar.bingmall.cycle;

import android.view.View;
import android.view.ViewGroup;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;
import com.bingstar.bingmall.cycle.view.CycleImageView;
import com.bingstar.bingmall.net.BingstarNet;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/2/15.
 */

public class CycleRightRVAdapter extends BaseRecycleAdapter<CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail, CycleRightRVAdapter.ViewHolder> {

    public CycleRightRVAdapter(ArrayList<CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail> list) {
        super(list);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.cycle_rightrv_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Util.imageLoader(holder.cycle_quick_img, getItem(position).getPicUrl());
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        CycleImageView cycle_quick_img;

        public ViewHolder(View itemView) {
            super(itemView);
            cycle_quick_img = (CycleImageView) itemView.findViewById(R.id.cycle_quick_img);
        }
    }

}
