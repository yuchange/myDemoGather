package com.zyf.bings.bingos.brandshome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.brandshome.bean.BannerInfo;
import com.zyf.bings.bingos.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<BannerInfo.BrandListBean> mBrandListBeen;


    private OnItemClickListener mOnItemClickListener;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public BrandsAdapter(Context context, List<BannerInfo.BrandListBean> brandListBeen) {
        mContext = context;
        mBrandListBeen = brandListBeen;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.grid_item, null);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoader.load(holder.iv, mBrandListBeen.get(position).getBrand_url());
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        if (mBrandListBeen != null) {
            return mBrandListBeen.size();
        } else {
            return 0;
        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            //    itemView.setOnClickListener(onClickListener);

            iv = (ImageView) itemView.findViewById(R.id.iv_brands);
        }


    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
