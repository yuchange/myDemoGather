package com.bingstar.bingmall.main.bottom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bingstar.bingmall.R;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/7 下午1:51
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/7 下午1:51
 * @modify by reason:{方法名}:{原因}
 */
public class LeftBottomView extends LinearLayout {
    public LeftBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_bottom_left,this);
    }
}
