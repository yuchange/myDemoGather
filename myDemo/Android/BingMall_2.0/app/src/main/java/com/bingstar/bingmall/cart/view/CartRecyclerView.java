package com.bingstar.bingmall.cart.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangyifei on 17/7/30.
 */

public class CartRecyclerView extends RecyclerView {


    public CartRecyclerView(Context context) {
        super(context);
    }

    public CartRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CartRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //addOnItemTouchListener(mListener);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //removeOnItemTouchListener(mListener);
        }
        return super.dispatchTouchEvent(ev);
    }


}
