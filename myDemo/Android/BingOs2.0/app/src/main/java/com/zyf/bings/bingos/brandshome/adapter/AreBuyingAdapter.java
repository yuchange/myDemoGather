package com.zyf.bings.bingos.brandshome.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.brandshome.bean.BannerInfo;
import com.zyf.bings.bingos.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class AreBuyingAdapter extends RecyclerView.Adapter<AreBuyingAdapter.AreBuyingViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<BannerInfo.ProductInfoBean.ProductInfoListBean> mProductInfoList;

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


    public AreBuyingAdapter(Context context, List<BannerInfo.ProductInfoBean.ProductInfoListBean> productInfoList) {
        mContext = context;
        mProductInfoList = productInfoList;
    }

    @Override
    public AreBuyingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_are_buying, null);
        view.setOnClickListener(this);
        return new AreBuyingViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AreBuyingViewHolder holder, int position) {
        if (mProductInfoList != null && mProductInfoList.size() != 0) {
            ImageLoader.load(holder.ivGoods, mProductInfoList.get(position).getPicUrl());
        }
        holder.mTvGoodsName.setText(mProductInfoList.get(position).getBsjProductName());
        holder.mTvGoodsPrice.setText(mContext.getResources().getString(R.string.discount_money)+mProductInfoList.get(position).getPrice());

        if (!TextUtils.isEmpty(mProductInfoList.get(position).getLimitPrice())) {
            holder.mTvGoodsLimtPrice.setText(R.string.discount_money+mProductInfoList.get(position).getLimitPrice());

        }



        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mProductInfoList.size();
    }

    class AreBuyingViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoods;
        TextView mTvGoodsName;
        TextView mTvGoodsPrice;
        TextView mTvGoodsLimtPrice;
        ImageView mIvSoldOut;


        public AreBuyingViewHolder(View itemView) {
            super(itemView);

            //    itemView.setOnClickListener(onClickListener);

            ivGoods = (ImageView) itemView.findViewById(R.id.item_brand_goods_img);
            mTvGoodsName = (TextView) itemView.findViewById(R.id.item_brand_goods_name);
            mTvGoodsPrice = (TextView) itemView.findViewById(R.id.item_brand_goods_nPrice);
            mTvGoodsLimtPrice = (TextView) itemView.findViewById(R.id.item_brand_goods_rPrice);
            mTvGoodsLimtPrice.setPaintFlags((Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG));


        }


    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
