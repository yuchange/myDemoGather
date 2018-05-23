package com.updayup.yuchance.companyselectdemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yu on 2017/5/4.
 */
public class ShareUtils {
    private static SharedPreferences sharedPreferences;
    private static final String SHAREDNAME = "config";
    private static final String SHAREDNAME1="config1";
    /**
     *
     * @param context
     * @param value
     *            设置的value
     * @param name
     *            设置的key
     */
    public static void setBoolean(Context context, boolean value, String name) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHAREDNAME,
                    context.MODE_PRIVATE);

        }
        sharedPreferences.edit().putBoolean(name, value).commit();
    }

    /**
     *
     * @param context
     * @param name
     * @param defValue
     *            默认的值
     * @return
     */
    public static boolean getBoolean(Context context, String name,
                                     boolean defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHAREDNAME,
                    context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(name, defValue);

    }

    /**
     *
     * @param context
     * @param value
     * @param name
     */
    public static void setString(Context context, String name, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHAREDNAME,
                    context.MODE_PRIVATE);

        }
        sharedPreferences.edit().putString(name, value).commit();
    }

    /**
     *
     * @param context
     * @param name
     * @param defValue
     * @return
     */
    public static String getString(Context context, String name, String defValue) {
        if (sharedPreferences == null && context != null ) {
            sharedPreferences = context.getSharedPreferences(SHAREDNAME,
                    context.MODE_PRIVATE);
        }

        return sharedPreferences.getString(name, defValue);
    }

    public static void clearAllData(Context context){
        if (sharedPreferences==null){
            sharedPreferences = context.getSharedPreferences(SHAREDNAME,
                    context.MODE_PRIVATE);
        }
        sharedPreferences.edit().clear().commit();
    }
}
