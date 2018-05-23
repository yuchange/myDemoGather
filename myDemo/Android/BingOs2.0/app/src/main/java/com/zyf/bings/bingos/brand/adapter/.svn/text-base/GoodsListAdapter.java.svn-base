package com.zyf.bings.bingos.brand.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseRecycleAdapter;
import com.zyf.bings.bingos.brand.bean.GoodsEntity;
import com.zyf.bings.bingos.brand.bean.ProductPic;
import com.zyf.bings.bingos.utils.ImageLoader;
import com.zyf.bings.bingos.utils.WrapGridLayoutManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangshiqi on 2017/9/11.
 */
public class GoodsListAdapter extends BaseRecycleAdapter<GoodsEntity, GoodsListAdapter.ViewHolder> {

    private int isFrom = 1;

    private Context context;

    private long lastTime = 0;

    final int TIME_SPACE = 3000;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;


    private List<GoodsEntity> data = new ArrayList<>();
    private RecyclerView mRecyclerView;

    public GoodsListAdapter(ArrayList<GoodsEntity> list, int from) {
        super(list);
        data = list;
        isFrom = from;
    }


//    @Override
//    protected int getItemLayoutId(int viewType) {
//            return R.layout.item_brand_goods;
//    }

    private String getImgUrl(GoodsEntity goodsEntity) {
        if (goodsEntity == null) {
            return null;
        }
        List<ProductPic> pics = goodsEntity.getProductInfoDetail().getProductPicList();
        if (pics == null || pics.size() == 0) {
            return null;
        }
        for (ProductPic pic : pics) {
            if ("1".equals(pic.getIsDefault())) {
                return pic.getPicUrl();
            }
        }
        return pics.get(0).getPicUrl();
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
//        final ViewHolder viewHolder = new ViewHolder(getView(parent, viewType));

        context = parent.getContext();
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new ViewHolder(VIEW_HEADER);
        } else {
            return new ViewHolder(getLayout(R.layout.item_brand_goods));
        }

//        viewHolder.goods_item_add_cart.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onSingleClick(View view) {
//                User.checkMemberId(parent.getContext(), new User.CheckMemberCallback() {
//                    @Override
//                    public void hasLogin() {
//                        try {
//                            GoodsEntity goodsEntity = getItem(viewHolder.getLayoutPosition());
//                            String stock = goodsEntity.getProductInfoDetail().getStock();
//                        /*if (!TextUtils.isEmpty(stock) && stock.equals("0")) {
//                            ((BaseActivity) context).showToast(context.getResources().getString(R.string.goods_detail_num_warnH1) + stock);
//                            return;
//                        }*/
////                            goodsEntity.addToCart(goodsEntity.getProductInfoDetail());
//                            long currentTime = System.currentTimeMillis();
////                            if (currentTime - lastTime > TIME_SPACE) {
////                                StatClient.upLoadClickCount(goodsEntity.getProductInfoDetail().getBsjProductId(),
////                                        goodsEntity.getProductInfoDetail().getBsjProductName());
////                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//            }
//        });
//        viewHolder.goods_item_one_key.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                User.checkMemberId(parent.getContext(), new User.CheckMemberCallback() {
//                    @Override
//                    public void hasLogin() {
//                        try {
//                            final GoodsEntity goodsEntity = getItem(viewHolder.getLayoutPosition());
//                            String stock = goodsEntity.getProductInfoDetail().getStock();
//                        /*if (!TextUtils.isEmpty(stock) && stock.equals("0")) {
//                            ((BaseActivity) context).showToast(context.getResources().getString(R.string.goods_detail_num_warnH1) + stock);
//                            return;
//                        }*/
//                            if (User.checkAddr()) {
//                                EventBus.getDefault().postSticky(new OneKeyEvent(goodsEntity.getProductInfoDetail().getBsjProductId(), "1"));//商品列表界面默认传1
//                            } else {
//                                EventBus.getDefault().post(new ToastEvent(context.getResources().getString(R.string.cart_oneKey_warn)));
//                            }
//                            StatClient.upLoadClickCount(goodsEntity.getProductInfoDetail().getBsjProductId(),
//                                    goodsEntity.getProductInfoDetail().getBsjProductName());
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                });
//            }
//        });

//        return viewHolder;
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
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

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof WrapGridLayoutManager) {
            final WrapGridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((WrapGridLayoutManager) layoutManager).getSpanSizeLookup();
            ((WrapGridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((WrapGridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    @Override
    public void onBindViewHolder(GoodsListAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            holder.goods_item_price_d.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            GoodsEntity goodsEntity = getItem(position);
            ImageLoader.load(holder.goods_item_img, getImgUrl(goodsEntity));

            holder.goods_item_name.setText(goodsEntity.getProductInfoDetail().getBsjProductName());
            if (goodsEntity.getProductInfoDetail().getLimitPrice() == null || goodsEntity.getProductInfoDetail().getLimitPrice().equals("")) {
                /**
                 * liumengqiang 修改：将“¥”提出到string文件中
                 */
                holder.goods_item_price.setText(context.getResources().getString(R.string.goods_detail_money) + goodsEntity.getProductInfoDetail().getPrice() + "");
                holder.goods_item_price_d.setVisibility(View.GONE);
            } else {
                holder.goods_item_price.setText(context.getResources().getString(R.string.goods_detail_money) + goodsEntity.getProductInfoDetail().getLimitPrice() + "");
                holder.goods_item_price_d.setText(context.getResources().getString(R.string.goods_detail_money) + goodsEntity.getProductInfoDetail().getPrice() + "");
                //划线
                holder.goods_item_price_d.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                holder.goods_item_price_d.setVisibility(View.VISIBLE);
            }
//        String promotionInfo = goodsEntity.getProductInfoDetail().getActivity();
//        if (!TextUtils.isEmpty(promotionInfo)) {
//            holder.goods_item_promotionInfo.setVisibility(View.VISIBLE);
//            holder.goods_item_promotionInfo.setText(promotionInfo);
//        } else {
//            holder.goods_item_promotionInfo.setVisibility(View.GONE);
//        }
        }

    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        ImageView goods_item_img;
        TextView goods_item_name;
        TextView goods_item_price_d;
        TextView goods_item_price;
        ImageView goods_item_soldout;
        ImageView goods_item_add_cart;
        //促销图标
        TextView goods_item_promotionInfo;
        //一建构
        ImageView goods_item_one_key;


        public ViewHolder(View itemView) {
            super(itemView);
            goods_item_img = (ImageView) itemView.findViewById(R.id.item_brand_goods_img);
            goods_item_name = (TextView) itemView.findViewById(R.id.item_brand_goods_name);
            goods_item_price_d = (TextView) itemView.findViewById(R.id.item_brand_goods_rPrice);
            goods_item_price = (TextView) itemView.findViewById(R.id.item_brand_goods_nPrice);
//            goods_item_promotionInfo = (TextView) itemView.findViewById(R.id.goods_item_imgInfo);
//            goods_item_price_d.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            goods_item_soldout = (ImageView) itemView.findViewById(R.id.item_brand_goods_soldout);

        }
    }

}
