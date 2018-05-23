package com.zyf.bings.bingos.order.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SuborderGoodsItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SuborderGoodsItemDecoration(int space) {
        this.space = space;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

     if (parent.getChildAdapterPosition(view) != 0){

         outRect.left = space;
     }




    }
}
