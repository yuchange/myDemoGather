package com.bingstar.bingmall.ads;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.bean.AdsInfoBean;
import com.bingstar.bingmall.ads.view.OnAdsItemClickListener;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.video.lib.JCVideoPlayerStandard;
import com.yunzhi.lib.view.OnSingleClickListener;

import java.util.HashMap;
import java.util.List;

import static com.bingstar.bingmall.net.BingstarNet.IMG_SERVICE;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/6 下午8:21
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/6 下午8:21
 * @modify by reason:{方法名}:{原因}
 */

public class ImgVideoViewAdapter extends PagerAdapter {

    private List<AdsInfoBean> viewImgInfos;

    private HashMap<String, View> cacheView;

    private Context context;

    private int mChildCount = 0;

    private OnAdsItemClickListener onClickListener;

    public ImgVideoViewAdapter(List<AdsInfoBean> viewImgInfos, Context context) {
        this.viewImgInfos = viewImgInfos;
        cacheView = new HashMap<>();
        this.context = context;
    }


    public AdsInfoBean getItem(int position) {
        return viewImgInfos.get(position);
    }

    @Override
    public int getCount() {
        return viewImgInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof JCVideoPlayerStandard) {
            JCVideoPlayerStandard destroy = (JCVideoPlayerStandard) object;
            destroy.release();
        }
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view;
        final AdsInfoBean infoBean = viewImgInfos.get(position);
        if (infoBean.getUrlType() == AdsInfoBean.TYPE_VIDEO) {
         /*   *//*view = new VideoView(context);
            ((VideoView) view).setVideoURI(Uri.parse(IMG_SERVICE + infoBean.getUrl()));
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onItemClick(v, infoBean);
                    }
                }
            });*//*

            if (cacheView.get(infoBean.getUrl()) == null) {
                view = new JCVideoPlayerStandard(context);
                cacheView.put(infoBean.getUrl(), view);
            } else {
                view = cacheView.get(infoBean.getUrl());
            }
            ((JCVideoPlayerStandard) view).setTagObj(infoBean);
            ((JCVideoPlayerStandard) view).setHideAll(true);
            ((JCVideoPlayerStandard) view).setUp(IMG_SERVICE + infoBean.getUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");*/
        } else {

        }
        view = new ImageView(context);
        ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
        Util.imageLoader((ImageView) view,infoBean.getUrl(), context.getResources().getDrawable(R.drawable.img_error_bg));
        view.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(v, infoBean);
                }
            }
        });
        container.addView(view);
        return view;
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

    public void setOnClickListener(OnAdsItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
