package com.yunzhi.lib.view.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/12 下午8:05
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/12 下午8:05
 * @modify by reason:{方法名}:{原因}
 */
public abstract class ViewPagerAdapter<T extends View> extends PagerAdapter{

    private List<T> views;

    public ViewPagerAdapter(List<T> views){
        this.views = views;
    }

    public T getItem(int position){
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(getItem(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(getItem(position));
        return getItem(position);
    }

}
