package com.bingstar.bingmall.home;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.home.bean.TitleBean;
import com.bingstar.bingmall.home.http.TitleListInfo;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 上午11:21
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 上午11:21
 * @modify by reason:{方法名}:{原因}
 */
public class HomeTitleAdapter extends BaseRecycleAdapter<TitleBean,HomeTitleAdapter.ViewHolder> {
    public HomeTitleAdapter(ArrayList<TitleBean> list) {
        super(list);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.home_title_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((TextView)holder.itemView).setText(getItem(position).getCategoryName());
        if (getItem(position).isSelected()) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }
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
                    notifyItemChanged(change,getItem(position));
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

    public String getDefaultCategoryId() {
        int size = getItemCount();
        if (size == 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (getItem(i).isSelected()) {
                return getItem(i).getCategoryId();
            }
        }
        return getItem(0).getCategoryId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent,viewType));
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
