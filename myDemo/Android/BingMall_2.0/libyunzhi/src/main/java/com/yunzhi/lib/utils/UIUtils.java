package com.yunzhi.lib.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * view工具类
 * Created by yaoyafeng on 16/8/19.
 */
public class UIUtils {
    /**
     * 简化ViewHolder
     *
     * @param view 父控件
     * @param id   资源ID
     * @param <T>  继承自View的控件
     * @return 控件
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T getViewByHolder(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static Fragment getCurrentFragment(FragmentManager manager) {
        if (manager != null) {
            List<Fragment> fragments = manager.getFragments();
            if (fragments != null && fragments.size() != 0) {
                for (Fragment fragment : fragments) {
                    if (!fragment.isHidden()) {
                        return fragment;
                    }
                }
            }
        }
        return null;
    }


    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
