package com.bingstar.bingmall.cart.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhangyifei on 17/7/29.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {


    public CustomLinearLayoutManager(Context context) {
        super(context);

    }


    //购物车 防止点击edittext 列表自动回到头部
    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
        return false;
    }


    //... constructor
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("probe", "meet a IOOBE in RecyclerView");
        }
    }
}
