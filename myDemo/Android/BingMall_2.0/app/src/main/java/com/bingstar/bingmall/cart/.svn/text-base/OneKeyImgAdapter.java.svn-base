package com.bingstar.bingmall.cart;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;

import java.util.List;

import static com.bingstar.bingmall.net.BingstarNet.IMG_SERVICE;

/**
 * Created by Administrator on 2017/7/27.
 */
public class OneKeyImgAdapter extends PagerAdapter {


    private List<OneKeyCartOrder.ProductInfoBean.ProductPicListBean> viewImgInfos;

    private Context context;

    private int mChildCount = 0;

    public OneKeyImgAdapter(List<OneKeyCartOrder.ProductInfoBean.ProductPicListBean> viewImgInfos, Context context) {
        this.viewImgInfos = viewImgInfos;
        this.context = context;
    }


    public OneKeyCartOrder.ProductInfoBean.ProductPicListBean getItem(int position) {
        return viewImgInfos.get(position);
    }

    @Override
    public int getCount() {
        if (viewImgInfos != null) {
            return viewImgInfos.size();
        } else {
            return 0;
        }

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        view = new ImageView(context);
        ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
        Util.imageLoader((ImageView) view,getItem(position).getPicUrl(), context.getResources().getDrawable(R.drawable.img_error_bg));
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


}
