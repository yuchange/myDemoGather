package com.zyf.bings.bingos.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyf.bings.bingos.R;

import java.util.List;


public class SmallHeadRvAdapter extends RecyclerView.Adapter<SmallHeadRvAdapter.MyViewholder> {
    private View FOOTVIEW;
    private RecyclerView mRecyclerView;
    private int TYPE_NORMAL = 1000;
    private int TYPE_FOOTER = 1002;

    private Context context;
    private List<String> datas;
    private SelectionOnRvItemClick selectionOnRvItemClick;

    public void setSelectionOnRvItemClick(SelectionOnRvItemClick selectionOnRvItemClick) {
        this.selectionOnRvItemClick = selectionOnRvItemClick;
    }

    public SmallHeadRvAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_small_head, parent, false);
        if (viewType == TYPE_FOOTER) {
            return new MyViewholder(FOOTVIEW);
        }
        else {
            return new MyViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {
        if (!isFooterView(position)) {
            holder.name.setText(datas.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }



        private boolean isFooterView(int position) {
            return haveFooterView() && position == getItemCount() - 1;
        }



    public boolean haveFooterView() {
        return FOOTVIEW != null;
    }




    @Override
    public int getItemCount() {
        int count = (datas== null ? 0 : datas.size());
        if (FOOTVIEW != null) {
            count++;
        }

        return count;
    }


    class MyViewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public MyViewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_head_name);
        }
    }
    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            FOOTVIEW = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return ( isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }



}
