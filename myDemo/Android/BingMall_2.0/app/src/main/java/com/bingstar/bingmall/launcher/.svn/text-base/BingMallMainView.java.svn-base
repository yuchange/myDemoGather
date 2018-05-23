package com.bingstar.bingmall.launcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bingstar.bingmall.BuildConfig;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.main.MainStates;
import com.yunzhi.lib.utils.L;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/30 下午5:56
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/30 下午5:56
 * @modify by reason:{方法名}:{原因}
 */

public class BingMallMainView extends ImageView {
    public BingMallMainView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public BingMallMainView(Context context) {
        super(context);
        initData(context);
    }

    public BingMallMainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(final Context context) {
        setImageResource(R.drawable.launcher_main_bg);
        setScaleType(ScaleType.FIT_CENTER);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra(MainStates.START_KEY,MainStates.START_MAIN);
                context.startActivity(intent);*/
                Intent intent = new Intent();
                intent.setAction("com.bingstar.bingmall.MAIN");
                intent.putExtra(MainStates.START_KEY, MainStates.START_MAIN);
                context.startActivity(intent);
            }
        });
    }
}
