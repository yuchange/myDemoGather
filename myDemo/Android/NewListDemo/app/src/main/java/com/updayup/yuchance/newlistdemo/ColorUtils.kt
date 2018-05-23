package com.updayup.yuchance.newlistdemo

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

/**
 * Created by yuchance on 2018/4/12.
 */

class ColorUtils {


    fun setColor(view: TextView, context: Context, color: Int) {
        view.setTextColor(ContextCompat.getColor(context, color))
    }


}
