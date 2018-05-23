package com.bingstar.bingmall.discount;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.yunzhi.lib.view.viewpager.ViewPagerAdapter;

import java.util.List;

/**
 * Created by qianhechen on 17/3/13.
 */

public class MViewPagerAdapter extends ViewPagerAdapter<View>{

    private int mChildCount = 0;

    public MViewPagerAdapter(List<View> views) {
        super(views);
    }

//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view.getTag().equals(((View)object).getTag());
//    }

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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
