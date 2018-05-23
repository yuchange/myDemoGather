package com.yunzhi.lib.utils;

import android.util.Log;

import com.yunzhi.lib.BuildConfig;

/**
 * 功能：
 * Created by yaoyafeng on 17/1/16 下午5:16
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/1/16 下午5:16
 * @modify by reason:{方法名}:{原因}
 */
public class LogUtils {

    // private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final boolean DEBUG = true;

    private static final boolean OPEN = true;

    public static void I(Class classes, String message) {
        if (OPEN) {
            Log.i(classes.getSimpleName(), message);
        }
    }

    public static void E(Class classes, String message) {
        if (OPEN) {
            Log.e(classes.getSimpleName(), message);
        }
    }

    public static void Debug_I(Class classes, String message) {
        if (DEBUG) {
            Log.i(classes.getSimpleName(), message);
        }
    }

    public static void Debug_E(Class classes, String message) {
        if (DEBUG) {
            Log.e(classes.getSimpleName(), message);
        }
//        else{
//            Log.e(classes.getSimpleName(),message);
//        }
    }
}
