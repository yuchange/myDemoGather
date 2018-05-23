package com.bingstar.bingmall;

import android.app.Application;

import com.bingstar.bingmall.base.CrashHandler;
import com.bingstar.bingmall.sdk.map.BaiduMapManager;
import com.bingstar.bingmall.sdk.map.IMapController;
import com.bingstar.bingmall.sdk.map.LatLng;
import com.bingstar.bingmall.user.bean.User;
import com.haiersmart.user.sdk.OnChangeListener;
import com.haiersmart.user.sdk.UserUtils;


/**
 * Created by zhangyifei on 17/7/26.
 */

public class BingStarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // ctx可以传递ApplicatonContext，
        // debug模式在没有用户中心apk的时候传递true，
        // 如果用户中心apk未安装，
        // 所有的用户信息都返回字符串空(NOT null)，
        // loginToDo()方法的Runnable都会执行。
        CrashHandler.getInstance().init(getApplicationContext());

        //CrashHandler.getInstance().init(this);

        UserUtils.get().setup(this, false);

        // 如果需要监听数据变化，可以使用registerOnChangeListener()
        UserUtils.get().registerOnChangeListener(new OnChangeListener() {
            public void onChange() {
                User user = User.getIntance(BingStarApp.this);
                if (user != null) {
                    user.setMemberId(null);
                }
            }
        });

        initBaiduMap();


    }


    private void initBaiduMap() {
        if (User.getIntance(this).getFridgeInit()) {
            User.getIntance(this).setFridgeInit(false);
            BaiduMapManager.getInstance(this).getLatLng(new IMapController.LocationCallback() {
                @Override
                public void callback(LatLng latLng) {
                    if (latLng != null) {
                        User.getIntance(getApplicationContext()).initUser(User.TYPE_INIT_VIEW, latLng);
                    } else {
                        User.getIntance(getApplicationContext()).initUser(User.TYPE_INIT_VIEW, null);

                    }
                }
            });
        }


    }


}
