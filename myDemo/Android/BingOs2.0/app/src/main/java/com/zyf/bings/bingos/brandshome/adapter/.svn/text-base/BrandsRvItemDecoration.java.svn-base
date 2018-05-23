package com.zyf.bings.bingos.brandshome.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/9/14.
 */

public class BrandsRvItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public BrandsRvItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) != 0)
            outRect.top = space;
        outRect.bottom = space;

        outRect.right = space;
    }
}
