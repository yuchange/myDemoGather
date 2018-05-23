package com.bingstar.bingmall.collect;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.bean.ShopCar;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.GoodsListAdapter;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.goods.bean.GoodsEntity;
import com.bingstar.bingmall.goods.bean.OneKeyEvent;
import com.bingstar.bingmall.goods.bean.ProductInfoDetail;
import com.bingstar.bingmall.goods.bean.ProductPic;
import com.bingstar.bingmall.main.bean.ToastEvent;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.view.OnSingleClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyifei on 17/7/5.
 */

public class CollectListAdapter extends BaseRecycleAdapter<GoodsEntity, CollectListAdapter.ViewHolder> {

    private int isFrom = 1;

    private Context context;

    public CollectListAdapter(ArrayList<GoodsEntity> list, int from) {
        super(list);
        isFrom = from;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.collect_list_item;
    }

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
                return BingstarNet.IMG_SERVICE + pic.getPicUrl();
            }
        }
        return pics.get(0).getPicUrl();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final ViewHolder viewHolder = new ViewHolder(getView(parent, viewType));
        viewHolder.goods_item_add_cart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                User.checkMemberId(parent.getContext(), new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        GoodsEntity goodsEntity = getItem(viewHolder.getLayoutPosition());
                        goodsEntity.addToCart(goodsEntity.getProductInfoDetail());
                    }
                });

            }
        });
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GoodsEntity goodsEntity = getItem(position);
        if (isFrom == ErrorFragment.CollectFragment && position == 0) {
            LinearLayout linearLayout = (LinearLayout) holder.itemView.findViewById(R.id.goods_product_item_linear);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(240, 275);
            layoutParams.setMargins(0, 15, 0, 0);
            linearLayout.setLayoutParams(layoutParams);
        }
        Util.imageLoader(holder.goods_item_img, getImgUrl(goodsEntity), holder.itemView.getResources().getDrawable(R.drawable.img_error_bg));
        holder.goods_item_name.setText(goodsEntity.getProductInfoDetail().getBsjProductName() + " " + goodsEntity.getProductInfoDetail().getWeight()
                + goodsEntity.getProductInfoDetail().getUnit() + "");
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
        String promotionInfo = goodsEntity.getProductInfoDetail().getActivity();
    }

    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        ImageView goods_item_img;
        TextView goods_item_name;
        TextView goods_item_price_d;
        TextView goods_item_price;
        Button goods_item_add_cart;



        public ViewHolder(View itemView) {
            super(itemView);
            goods_item_img = (ImageView) itemView.findViewById(R.id.goods_item_img);
            goods_item_name = (TextView) itemView.findViewById(R.id.goods_item_name);
            goods_item_price_d = (TextView) itemView.findViewById(R.id.goods_item_price_d);
            goods_item_price = (TextView) itemView.findViewById(R.id.goods_item_price);
            goods_item_add_cart = (Button) itemView.findViewById(R.id.goods_item_add_cart);
            goods_item_price_d.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
