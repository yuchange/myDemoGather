package com.bingstar.bingmall.ads;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.bean.AdsInfoBean;
import com.bingstar.bingmall.ads.bean.HpgProductAds;
import com.bingstar.bingmall.ads.view.OnAdsItemClickListener;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.net.BingstarNet;
import com.yunzhi.lib.utils.LogUtils;
import com.yunzhi.lib.view.OnSingleClickListener;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/22 上午12:51
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/22 上午12:51
 * @modify by reason:{方法名}:{原因}
 */

public class AdsPagerAdapter extends PagerAdapter{

    private List<HpgProductAds> adsList;

    private int mChildCount = 0;

    private Context context;

    private OnAdsItemClickListener onClickListener;

    public AdsPagerAdapter(List<HpgProductAds> adsList, Context context) {
        this.context = context;
        this.adsList = adsList;
    }

    @Override
    public int getCount() {
        return adsList.size();
    }

    public HpgProductAds getItem(int position) {
        return adsList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.getTag() == ((View) object).getTag();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final HpgProductAds productAds = getItem(position);
        View view;
        if (getItem(position).getPageStyle() == HpgProductAds.STYLE_TWO) {
            view = LayoutInflater.from(context).inflate(R.layout.ads_layout_one, null);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.ads_layout_two, null);
            ImageView ads_info_left_second = (ImageView) view.findViewById(R.id.ads_info_left_second);
            ads_info_left_second.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    AdsInfoBean adsInfoBean = productAds.getAds(2);
                    if (onClickListener != null) {
                        onClickListener.onItemClick(view, adsInfoBean);
                    }
                }
            });
            Util.imageLoader(ads_info_left_second, productAds.getAds(2).getUrl());
        }
        ImageView ads_info_left_first = (ImageView) view.findViewById(R.id.ads_info_left_first);
        ImageView ads_info_right = (ImageView) view.findViewById(R.id.ads_info_right);
        ads_info_left_first.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                AdsInfoBean adsInfoBean = productAds.getAds(1);
                if (onClickListener != null) {
                    onClickListener.onItemClick(view, adsInfoBean);
                }
            }
        });
        ads_info_right.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                AdsInfoBean adsInfoBean = productAds.getAds(3);
                if (onClickListener != null) {
                    onClickListener.onItemClick(view, adsInfoBean);
                }
            }
        });
        Util.imageLoader(ads_info_left_first,productAds.getAds(1).getUrl());
        Util.imageLoader(ads_info_right,productAds.getAds(3).getUrl());
        view.setTag(getItem(position).getPosition());
        container.addView(view);
        return view;
    }

    public void setOnChildClickListener(OnAdsItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
