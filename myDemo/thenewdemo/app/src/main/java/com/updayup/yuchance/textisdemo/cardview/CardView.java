package com.updayup.yuchance.textisdemo.cardview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * *          _       _
 * *   __   _(_)_   _(_) __ _ _ __
 * *   \ \ / / \ \ / / |/ _` | '_ \
 * *    \ V /| |\ V /| | (_| | | | |
 * *     \_/ |_| \_/ |_|\__,_|_| |_|
 * <p>
 * Created by vivian on 2016/12/1.
 */

public class CardView extends LinearLayout {
    RelativeLayout topLayout;
    ImageView centerLayout;
    TextView content;
    EditText comment;

    TopDrawable topDrawable;
    CenterDrawable centerDrawable;
    BottomDrawable bottomDrawable;
    GradientDrawable myGrad;

    public CardView(Context context) {
        super(context);
        init(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    public void init(final Context context) {


//        myGrad = (GradientDrawable) content.getBackground();

        topDrawable = new TopDrawable();
//        topLayout.setBackground(topDrawable);

//        centerLayout.setBackground(centerDrawable);

        bottomDrawable = new BottomDrawable();
//        comment.setBackground(bottomDrawable);

    }

    public void changeTheme(final int color) {
        //顶部阴影颜色
        topDrawable.setColor(color);
//        //底部阴影颜色
        bottomDrawable.setColor(color);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
