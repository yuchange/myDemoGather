package com.zyf.bings.bingos.order.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by asus on 2017/10/8.
 */
public class LogisticsInfoItemDecoration extends RecyclerView.ItemDecoration {


    private int space;

    public LogisticsInfoItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //  if (parent.getChildAdapterPosition(view) != 0)

        outRect.bottom = space;
        outRect.top = space;


    }
}
