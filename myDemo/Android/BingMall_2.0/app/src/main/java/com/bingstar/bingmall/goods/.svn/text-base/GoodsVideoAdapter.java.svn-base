package com.bingstar.bingmall.goods;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.view.OnAdsItemClickListener;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.goods.bean.ProductPic;
import com.bingstar.bingmall.goods.bean.ProductVideo;
import com.bingstar.bingmall.goods.view.MutableGoodsViewPager;
import com.bingstar.bingmall.video.lib.JCVideoPlayer;
import com.bingstar.bingmall.video.lib.JCVideoPlayerStandard;
import com.bingstar.bingmall.video.lib.OnPlayItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import static com.bingstar.bingmall.net.BingstarNet.IMG_SERVICE;

/**
 * Created by zhangyifei on 17/7/9.
 */

public class GoodsVideoAdapter extends PagerAdapter {


    private HashMap<String, View> cacheView;
    private List<ProductPic> imgInfo;
    private List<ProductVideo> videoInfo;
    MutableGoodsViewPager bannerPager;

    private boolean hideOenKey = false;

    private boolean hideAll = false;


    private Context context;

    private int mChildCount = 0;

    private OnAdsItemClickListener onClickListener;

    private OnPlayItemClickListener playClickListener;

    public GoodsVideoAdapter(List<ProductPic> viewImgInfos, List<ProductVideo> videoInfo, Context context, MutableGoodsViewPager pager) {
        if (viewImgInfos == null) {
            viewImgInfos = new ArrayList<>();
        }
        if (videoInfo == null) {
            videoInfo = new ArrayList<>();
        }
        this.imgInfo = viewImgInfos;
        this.videoInfo = videoInfo;
        this.context = context;
        cacheView = new HashMap<>();
        this.bannerPager = pager;
        handleData();
    }

    public HashMap<String, View> getCacheView() {
        return cacheView;
    }


    public Object getItem(int position) {
        if (videoInfo != null && position < videoInfo.size()) {
            return videoInfo.get(position);
        } else {
            return imgInfo.get(position);
        }
    }

    @Override
    public int getCount() {
        return videoInfo.size() + imgInfo.size();
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

    private int realPosition;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view;
        if (videoInfo.size() > 0 && position < videoInfo.size()) {
            ProductVideo productVideo = videoInfo.get(position);
            if (cacheView.get(productVideo.videoUrl) == null) {
                view = new JCVideoPlayerStandard(context);
                cacheView.put(productVideo.videoUrl, view);
            } else {
                view = cacheView.get(productVideo.videoUrl);
            }
            Log.e("ldd", "base instantiateItem: 商品详情 " + Uri.parse(IMG_SERVICE + productVideo.videoUrl));
            ((JCVideoPlayerStandard) view).setTagObj(productVideo);
            ((JCVideoPlayerStandard) view).setHideGoodsDetailLogo(true);
            ((JCVideoPlayerStandard) view).setHideGoodsOneKey(getHideOenKey());
            ((JCVideoPlayerStandard) view).setHideAll(getHideAll());
            ((JCVideoPlayerStandard) view).serOnPlayItemClickListener(playClickListener);
            ((JCVideoPlayerStandard) view).setUp(IMG_SERVICE + productVideo.videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            //((JCVideoPlayerStandard) view).setUp(IMG_SERVICE + "/homeVideo/milk.avi", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            Util.imageLoader(((JCVideoPlayerStandard) view).thumbImageView,productVideo.picUrl, context.getResources().getDrawable(R.drawable.img_error_bg));
            realPosition = videoInfo.size();
        } else {
            final ProductPic productPic = imgInfo.get(position - realPosition);
            view = new ImageView(context);
            ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
            Util.imageLoader((ImageView) view, productPic.getPicUrl(), context.getResources().getDrawable(R.drawable.img_error_bg));
        }
        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        handleData();
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

    public void setPlayClickListener(OnPlayItemClickListener playClickListener) {
        this.playClickListener = playClickListener;
    }

    Object pre = null;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (object != null) {
            if (!object.equals(pre)) {
                pre = object;
                currentView = (View) object;
                if (currentView instanceof JCVideoPlayerStandard) {
                    JCVideoPlayerStandard currentView = (JCVideoPlayerStandard) this.currentView;
                    currentView.startVideo();
                    if ((currentView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING ||
                            currentView.currentState == JCVideoPlayer.CURRENT_STATE_PREPARING ||
                            currentView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING_BUFFERING_START)) {
                        bannerPager.setShouldReturn(true);
                    } else {
                        bannerPager.setShouldReturn(false);
                    }
                }

                if (currentView instanceof ImageView) {
                    bannerPager.setShouldReturn(false);
                }
            }
        }
    }

    public View getCurrentView() {
        return currentView;
    }

    View currentView;


    public void handleData() {
        if (videoInfo != null) {
            Collections.sort(videoInfo);
        }
    }

    public boolean getHideOenKey() {
        return hideOenKey;
    }

    public void setHideOenKey(boolean hideOenKey) {
        this.hideOenKey = hideOenKey;
    }


    public boolean getHideAll() {
        return hideAll;
    }

    public void setHideAll(boolean hideAll) {
        this.hideAll = hideAll;
    }

}
