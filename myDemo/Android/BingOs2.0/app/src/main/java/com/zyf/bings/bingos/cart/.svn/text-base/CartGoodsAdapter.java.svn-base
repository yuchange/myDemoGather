package com.zyf.bings.bingos.cart;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.cart.bean.ShopCartGoodsBean;
import com.zyf.bings.bingos.cart.bean.ShopCartUpdateBean;
import com.zyf.bings.bingos.cart.http.CartClient;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.order.NotificationDialogFragment;
import com.zyf.bings.bingos.utils.ImageLoader;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangyifei on 17/8/29.
 */

public class CartGoodsAdapter extends RecyclerView.Adapter<CartGoodsAdapter.ViewHolder> {

    ArrayList<ShopCartGoodsBean.ProductInfoListBean> mShopCartGoodsItems;

    FragmentActivity mContext;


    OnDelete mOnDelete;
    OnNumberChange mOnNumberChange;
    private int mStock;


    public void setOnDelete(OnDelete onDelete) {
        mOnDelete = onDelete;
    }

    public void setOnNumberChange(OnNumberChange onNumberChange) {
        mOnNumberChange = onNumberChange;
    }

    public CartGoodsAdapter(FragmentActivity context) {
        mContext = context;
    }


    public void setShopCartGoodsItems(ArrayList<ShopCartGoodsBean.ProductInfoListBean> shopCartGoodsItems) {
        mShopCartGoodsItems = shopCartGoodsItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcart_goods_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShopCartGoodsBean.ProductInfoListBean shopCartGoodsItem = mShopCartGoodsItems.get(position);
        holder.mTvSpec.setText("规格：" + shopCartGoodsItem.getSpecification());
        holder.mTvName.setText(shopCartGoodsItem.getProductName());

        //折扣价格和原价设置
        if (!TextUtils.isEmpty(shopCartGoodsItem.getLimitPrice())) {
            holder.mTvDiscountprice.setVisibility(View.VISIBLE);
            holder.mTvPrice.setText(mContext.getResources().getString(R.string.money) + shopCartGoodsItem.getLimitPrice());
            holder.mTvDiscountprice.setText(mContext.getResources().getString(R.string.money) + shopCartGoodsItem.getPrice());
            holder.mTvDiscountprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            holder.mTvDiscountprice.setVisibility(View.GONE);
            holder.mTvPrice.setText(mContext.getResources().getString(R.string.money) + shopCartGoodsItem.getPrice());
        }

        holder.mTvDelete.setOnClickListener(v -> {
            onDelete(shopCartGoodsItem, holder.getLayoutPosition());
        });
        if (shopCartGoodsItem.getStock() != null) {
            mStock = Integer.valueOf(shopCartGoodsItem.getStock());
        }
        holder.mTvCount.setText(shopCartGoodsItem.getCount());

        if (mStock < 1) {
            holder.mTvCount.setText("1");
        }
        holder.mIvShopcartItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = shopCartGoodsItem.getCount();
                Integer integer = Integer.valueOf(count);
                String stock = shopCartGoodsItem.getScreenrePertory(); // 购物车列表库存判断
                if (shopCartGoodsItem.getIf_show().equals("4") || "0".equals(shopCartGoodsItem.getScreenrePertory())) {
                    remindDialog();
                    return;
                }
                if (stock != null) {
                    if (integer >= Integer.valueOf(stock)) {
                        shopCartGoodsItem.setCount(stock);
                        remindDialog();
                        updateCart(shopCartGoodsItem, Integer.parseInt(stock), holder.mTvCount);
                        return;
                    }
                    integer++;
                    shopCartGoodsItem.setCount(integer + "");
                    updateCart(shopCartGoodsItem, Integer.valueOf(count), holder.mTvCount);
                } else {
                    remindDialog();
                    return;
                }
            }
        });
        holder.mIvShopcartItemSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCartGoodsItem.getIf_show().equals("4") || shopCartGoodsItem.getScreenrePertory() == null || shopCartGoodsItem.getScreenrePertory().equals("0")) {
                    remindDialog();
                    return;
                }
                String count = shopCartGoodsItem.getCount();
                Integer integer = Integer.valueOf(count);
                if (integer > 1) {
                    integer--;
                    shopCartGoodsItem.setCount(integer + "");
                    updateCart(shopCartGoodsItem, Integer.valueOf(count), holder.mTvCount);
                }

            }
        });
        ImageLoader.load(holder.mIvShopcartItemImg, shopCartGoodsItem.getPicUrl());
        if (!TextUtils.isEmpty(shopCartGoodsItem.getIf_show())&&shopCartGoodsItem.getIf_show().equals("4")) {
            holder.mIvShopcartItemImgOffShelf.setVisibility(View.VISIBLE);
            holder.mIvShopcartItemSub.setImageResource(R.mipmap.shopcart_item_sub_off);
            holder.mIvShopcartItemAdd.setImageResource(R.mipmap.shopcart_item_add_off);
        } else {
            if (shopCartGoodsItem.getScreenrePertory() == null || shopCartGoodsItem.getScreenrePertory().equals("0")) {
                holder.mIvShopcartItemImgOffShelf.setVisibility(View.VISIBLE);
                holder.mIvShopcartItemSub.setImageResource(R.mipmap.shopcart_item_sub_off);
                holder.mIvShopcartItemAdd.setImageResource(R.mipmap.shopcart_item_add_off);
            }
        }


        if (shopCartGoodsItem.getIsSelected() == 2) {
            holder.mIvSelect.setImageResource(R.mipmap.shopcart_goodsitem_unselect);
            holder.mIvSelect.setTag(R.id.iv_shopcart_item_select, 2);

        } else if (shopCartGoodsItem.getIsSelected() == 1) {
            holder.mIvSelect.setImageResource(R.mipmap.shopcart_goodsitem_select);
            holder.mIvSelect.setTag(R.id.iv_shopcart_item_select, 1);

        } else {
            holder.mIvSelect.setImageResource(R.mipmap.shopcart_goodsitem_unselect);
            holder.mIvSelect.setTag(R.id.iv_shopcart_item_select, 2);

        }


        holder.mIvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (shopCartGoodsItem.getIf_show().equals("4") || shopCartGoodsItem.getScreenrePertory() == null|| shopCartGoodsItem.getScreenrePertory().equals("0")) {
//                    remindDialog();
//                    return;
//                } else {

                int tag = (int) v.getTag(R.id.iv_shopcart_item_select);
                Log.e("CartGoodsAdapter", "onClick: TAG  " + tag);
                int layoutPosition = holder.getLayoutPosition();
                switch (tag) {
                    case 1:
                        holder.mIvSelect.setImageResource(R.mipmap.shopcart_goodsitem_unselect);
                        holder.mIvSelect.setTag(R.id.iv_shopcart_item_select, 2);
                        mShopCartGoodsItems.get(layoutPosition).setSelected(2);
                        break;
                    case 2:
                        holder.mIvSelect.setImageResource(R.mipmap.shopcart_goodsitem_select);
                        holder.mIvSelect.setTag(R.id.iv_shopcart_item_select, 1);
                        mShopCartGoodsItems.get(layoutPosition).setSelected(1);

                        break;
                    default:
                        holder.mIvSelect.setImageResource(R.mipmap.shopcart_goodsitem_unselect);
                        holder.mIvSelect.setTag(R.id.iv_shopcart_item_select, 2);
                        mShopCartGoodsItems.get(layoutPosition).setSelected(2);
                        break;
                }

                Integer width = holder.mIvSelect.getWidth();
                if (mOnNumberChange != null) {
                    mOnNumberChange.onNumberChange();
                }
            }

//            }
        });
    }

    /**
     * 弹窗提醒
     */
    private void remindDialog() {
        NotificationDialogFragment notificationDialogFragment = NotificationDialogFragment.newInstance("当前商品库存不足");
        notificationDialogFragment.show(mContext.getSupportFragmentManager(), "shopCart");
        return;
    }

    private void onDelete(final ShopCartGoodsBean.ProductInfoListBean productInfoListBean, final int position) {
        if (mOnDelete != null) {
            mOnDelete.onDelete(productInfoListBean, position);
        }
    }


    @Override
    public int getItemCount() {
        return mShopCartGoodsItems == null ? 0 : mShopCartGoodsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_shopcart_item_select)
        ImageView mIvSelect;
        @BindView(R.id.iv_shopcart_item_img)
        ImageView mIvShopcartItemImg;
        @BindView(R.id.tv_shopcart_goodsitem_name)
        TextView mTvName;
        @BindView(R.id.tv_shopcart_goodsitem_spec)
        TextView mTvSpec;
        @BindView(R.id.tv_shopcart_goodsitem_price)
        TextView mTvPrice;
        @BindView(R.id.tv_shopcart_goodsitem_discountprice)
        TextView mTvDiscountprice;
        @BindView(R.id.iv_shopcart_item_add)
        ImageView mIvShopcartItemAdd;
        @BindView(R.id.tv_shopcart_item_count)
        TextView mTvCount;
        @BindView(R.id.iv_shopcart_item_sub)
        ImageView mIvShopcartItemSub;
        @BindView(R.id.tv_shopcart_item_del)
        TextView mTvDelete;
        @BindView(R.id.iv_shopcart_item_img_off_shelf)
        ImageView mIvShopcartItemImgOffShelf;
        @BindView(R.id.iv_shopcart_item_img_seld_out)
        ImageView mIvShopcartItemImgSeldOut;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateCart(ShopCartGoodsBean.ProductInfoListBean itemBean, int originalCount, TextView textView) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CartClient.CART_UPDATE_METHOD);
        ShopCartUpdateBean productInfoUpdate = new ShopCartUpdateBean();
        productInfoUpdate.setMemberId(itemBean.getMemberId());
        productInfoUpdate.setProductInfo(itemBean);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(productInfoUpdate));
        RxOkClient.doPost(map, CartClient.CART_UPDATE_URL, getClass().getSimpleName(), PBUtil.getPD(mContext))
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        textView.setText(itemBean.getCount());
                        if (mOnNumberChange != null) {
                            mOnNumberChange.onNumberChange();
                        }
                        CartCountClient.getCartCount();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showToast(msg);
                        itemBean.setCount(originalCount + "");
                        textView.setText(originalCount + "");
                        if (mOnNumberChange != null) {
                            mOnNumberChange.onNumberChange();
                        }
                    }
                });
    }


    interface OnDelete {
        void onDelete(ShopCartGoodsBean.ProductInfoListBean productInfoListBean, int position);
    }

    interface OnNumberChange {
        void onNumberChange();
    }


}
