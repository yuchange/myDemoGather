package com.zyf.bings.bingos.coupon;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created by wangshiqi on 2017/10/11.
 */

public class CouponItemDecoration extends RecyclerView.ItemDecoration {
    private HashMap<String, Integer> mSpaceValueMap;

    public static final String TOP_DECORATION = "top_decoration";
    public static final String BOTTOM_DECORATION = "bottom_decoration";
    public static final String LEFT_DECORATION = "left_decoration";
    public static final String RIGHT_DECORATION = "right_decoration";

    public CouponItemDecoration(HashMap<String, Integer> mSpaceValueMap) {
        this.mSpaceValueMap = mSpaceValueMap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (mSpaceValueMap.get(TOP_DECORATION) != null) {
            outRect.top = mSpaceValueMap.get(TOP_DECORATION);
        }
        if (mSpaceValueMap.get(LEFT_DECORATION) != null)

        {
            outRect.left = mSpaceValueMap.get(LEFT_DECORATION);
        }
        if (mSpaceValueMap.get(RIGHT_DECORATION) != null) {
            outRect.right = mSpaceValueMap.get(RIGHT_DECORATION);
        }

        if (mSpaceValueMap.get(BOTTOM_DECORATION) != null)

        {
            outRect.bottom = mSpaceValueMap.get(BOTTOM_DECORATION);
        }

    }
}
