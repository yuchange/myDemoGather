package com.bingstar.bingmall.launcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.main.MainBuildConfigure;
import com.bingstar.bingmall.main.MainStates;
import com.bingstar.bingmall.user.bean.User;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/30 下午5:57
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/30 下午5:57
 * @modify by reason:{方法名}:{原因}
 */

public class BingMallAddrView extends ImageView {

    public BingMallAddrView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public BingMallAddrView(Context context) {
        super(context);
        initData(context);
    }

    public BingMallAddrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(final Context context) {
        setImageResource(R.drawable.launcher_addr_bg);
        setScaleType(ScaleType.FIT_CENTER);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                User.checkMemberId(context, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                       /* Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra(MainStates.START_KEY, MainStates.START_ADDR);
                        context.startActivity(intent);*/
                        Intent intent = new Intent();
                        intent.setAction("com.bingstar.bingmall.MAIN");
                        intent.putExtra(MainStates.START_KEY, MainStates.START_ADDR);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

}
