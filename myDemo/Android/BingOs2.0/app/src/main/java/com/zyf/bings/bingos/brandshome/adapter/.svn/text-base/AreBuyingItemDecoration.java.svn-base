package com.zyf.bings.bingos.brandshome.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/9/14.
 */

public class AreBuyingItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public AreBuyingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position!= 0)

       outRect.bottom = space;


 }


     //   GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager(); //判断总的数量是否可以整除 int totalCount = layoutManager.getItemCount(); int surplusCount = totalCount % layoutManager.getSpanCount(); int childPosition = parent.getChildAdapterPosition(view); if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {//竖直方向的 if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) { //后面几项需要bottom outRect.bottom = topBottom; } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) { outRect.bottom = topBottom; } if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要右边 outRect.right = leftRight; } outRect.top = topBottom; outRect.left = leftRight; } else { if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) { //后面几项需要右边 outRect.right = leftRight; } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) { outRect.right = leftRight; } if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要下边 outRect.bottom = topBottom; } outRect.top = topBottom; outRect.left = leftRight; }



}