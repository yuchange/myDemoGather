package com.bingstar.bingmall.goods;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.goods.bean.ProductPic;

import java.util.List;

import static com.bingstar.bingmall.net.BingstarNet.IMG_SERVICE;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/28 上午9:29
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/28 上午9:29
 * @modify by reason:{方法名}:{原因}
 */

public class GoodsImgAdapter extends PagerAdapter {

    private List<ProductPic> viewImgInfos;

    private Context context;

    private int mChildCount = 0;


    public GoodsImgAdapter(List<ProductPic> viewImgInfos, Context context) {
        this.viewImgInfos = viewImgInfos;
        this.context = context;
    }


    public ProductPic getItem(int position) {
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
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        view = new ImageView(context);
        ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
        Util.imageLoader((ImageView) view, getItem(position).getPicUrl(), context.getResources().getDrawable(R.drawable.img_error_bg));
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