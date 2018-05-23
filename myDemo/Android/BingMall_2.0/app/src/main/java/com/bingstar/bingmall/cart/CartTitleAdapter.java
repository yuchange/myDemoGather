package com.bingstar.bingmall.cart;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/3/7.
 */

public class CartTitleAdapter extends BaseRecycleAdapter<CartTitle.CartCategoryInfo,CartTitleAdapter.ViewHolder>{

    private int clickPosition;

    public CartTitleAdapter(ArrayList<CartTitle.CartCategoryInfo> list) {
        super(list);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.cart_title_item;
    }

    @Override
    public void onBindViewHolder(CartTitleAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
//        if(position == getItemCount()-1){
//            holder.getDataBinding().getRoot().findViewById(R.id.cart_title_view).setVisibility(View.GONE);
//        }
        CartTitle.CartCategoryInfo categoryInfo = getItem(position);
        if (categoryInfo!=null) {
            holder.cart_categoryName.setText(categoryInfo.getCategoryName());
            if (getItem(position).isSelected()) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent,viewType));
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder{

        TextView cart_categoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            cart_categoryName = (TextView) itemView.findViewById(R.id.cart_categoryName);
        }
    }
}
