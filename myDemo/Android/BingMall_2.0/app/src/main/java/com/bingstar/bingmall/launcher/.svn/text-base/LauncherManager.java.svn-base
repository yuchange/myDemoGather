package com.bingstar.bingmall.launcher;

import android.content.Context;
import android.content.Intent;

import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.main.MainStates;
import com.bingstar.bingmall.user.bean.User;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/26 下午3:33
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/26 下午3:33
 * @modify by reason:{方法名}:{原因}
 */

public class LauncherManager {

    public static String SupportAction = "com.bingstar.bingmall.SUPPORT";

    public static void startSupportView(final Context context){
        User.checkMemberId(context, new User.CheckMemberCallback() {
            @Override
            public void hasLogin() {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(MainStates.START_KEY, MainStates.START_SUPPORT);
                context.startActivity(intent);
            }
        });
    }
}
