package com.bingstar.bingmall.discount;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.discount.bean.KidShowInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by qianhechen on 17/3/3.
 */

public class DiscountAdapter extends BaseRecycleAdapter<KidShowInfo, DiscountAdapter.ViewHolder> {

    public DiscountAdapter(ArrayList<KidShowInfo> list) {
        super(list);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.discount_timerv_item;
    }

    @Override
    public void onBindViewHolder(DiscountAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItem(position).isSelected()) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }
        boolean isStart = getItem(position).isStart();
        String state;
        if (isStart){
            state = "已开抢";
            getItem(position).setStart(true);
        }else {
            state = "未开抢";
            getItem(position).setStart(false);
        }
        holder.time.setText(getItem(position).getShowKidTime());
        holder.state.setText(state);
    }

    @Override
    public void setItemClickListener(final OnItemClickListener clickListener) {
        if (clickListener == null) {
            return;
        }
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setSelected(true);
                getItem(position).setSelected(true);
                int change = getChangedItem(position);
                if (change >= 0) {
                    notifyItemChanged(change);
                }
                clickListener.onItemClick(view, position);
            }
        };
        super.setItemClickListener(itemClickListener);
    }

    private int getChangedItem(int position) {
        for (int i = 0, size = getItemCount(); i < size; i++) {
            if (getItem(i).isSelected() && position != i) {
                getItem(i).setSelected(false);
                return i;
            }
        }
        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, viewType));
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        TextView time;
        TextView state;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.discount_title_time);
            state = (TextView) itemView.findViewById(R.id.discount_title_state);
        }
    }

}
