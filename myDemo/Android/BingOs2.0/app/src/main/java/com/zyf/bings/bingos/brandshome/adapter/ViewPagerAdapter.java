package com.zyf.bings.bingos.brandshome.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zyf.bings.bingos.brandshome.bean.BannerInfo;
import com.zyf.bings.bingos_libnet.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<BannerInfo.BannerListBean> bannerList;
    private Context mContext;
    private List<ImageView> caches = new ArrayList<ImageView>();

    public ViewPagerAdapter(List<BannerInfo.BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public ViewPagerAdapter(List<BannerInfo.BannerListBean> bannerList, Context context) {
        this.bannerList = bannerList;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (bannerList != null) {
            return bannerList.size();
        } else {
            return 0;
        }

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (caches.isEmpty()) {
            ImageView imageView = new ImageView(mContext);
            Glide.with(mContext).load(BuildConfig.URL_IMAGE + bannerList.get(position).getImgUrl()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            caches.add(imageView);
        }
        ImageView iv = caches.remove(0);
        container.addView(iv);
        return iv;



    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ImageView iv = (ImageView) object;
        container.removeView(iv);
        iv.setImageBitmap(null);
        caches.add(iv);
        Log.i("test", "caches:"+caches.size());
    }
}
