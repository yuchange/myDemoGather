package com.bingstar.bingmall.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liumengqiang on 2017/2/27.
 */

public class SharedPreferencesUtils {

    private static final String CITYINFO = "cityInfo"; // sharedpreferences文件名称
    private static final String CITYString = "cityString"; //sharedpreferences文件内部的Key
    private static final String CITYDate = "cityDate";//最后一次登录该客户端的时间

    public static String getCITYString(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CITYINFO, Context.MODE_PRIVATE);
        return sp.getString(CITYString, "");
    }

    public static void setCITYString(Context context,String cityString) {
        SharedPreferences sp = context.getSharedPreferences(CITYINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CITYString, cityString);
        editor.apply();
    }
    public static String getCITYDate(Context context){
        SharedPreferences sp = context.getSharedPreferences(CITYINFO, Context.MODE_PRIVATE);
        return sp.getString(CITYDate, "");
    }
    public static void setCITYDate(Context context,String cityDate){
        SharedPreferences sp = context.getSharedPreferences(CITYINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CITYDate, cityDate);
        editor.apply();
    }
}
