package com.bingstar.bingmall.cycle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.view.CycleImageView;
import com.bingstar.bingmall.net.BingstarNet;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/2/15.
 */

public class CycleLeftRVAdapter extends BaseRecycleAdapter<CycleProductInfo.CycleProduct.CycleProductInfoDetail, CycleLeftRVAdapter.ViewHolder> {


    public CycleLeftRVAdapter(ArrayList<CycleProductInfo.CycleProduct.CycleProductInfoDetail> list) {
        super(list);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.cycle_leftrv_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CycleProductInfo.CycleProduct.CycleProductInfoDetail cycleProductInfoDetail = getItem(position);
        if (cycleProductInfoDetail != null) {
            Util.imageLoader(holder.cycle_item_img,cycleProductInfoDetail.getPicUrl());
            holder.cycle_item_productName.setText(cycleProductInfoDetail.getProductName());
            holder.cycle_item_price.setText(holder.itemView.getContext().getResources().getString(R.string.cycle_money)
                    + cycleProductInfoDetail.getPrice() + "");
            holder.cycle_item_specs.setText("/" + cycleProductInfoDetail.getWeight() + cycleProductInfoDetail.getUnit());
            holder.cycle_item_count.setText(cycleProductInfoDetail.getCount());
            holder.cycle_item_num.setText("  *"+cycleProductInfoDetail.getNumber()+"");
        }
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        CycleImageView cycle_item_img;
        TextView cycle_item_productName;
        TextView cycle_item_price;
        TextView cycle_item_specs;
        TextView cycle_item_count;
        TextView cycle_item_num;

        public ViewHolder(View itemView) {
            super(itemView);
            cycle_item_img = (CycleImageView) itemView.findViewById(R.id.cycle_item_img);
            cycle_item_productName = (TextView) itemView.findViewById(R.id.cycle_item_productName);
            cycle_item_price = (TextView) itemView.findViewById(R.id.cycle_item_price);
            cycle_item_specs = (TextView) itemView.findViewById(R.id.cycle_item_specs);
            cycle_item_count = (TextView) itemView.findViewById(R.id.cycle_item_count);
            cycle_item_num = (TextView) itemView.findViewById(R.id.cycle_item_num);

        }
    }
}

