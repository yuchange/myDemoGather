package com.zyf.bings.bingos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/4.
 */

public class SPUtil {

    /**
     * 文件名称
     */
    private static final String SP_NAME = "config";

    private static Set<String> historySet = new HashSet<>();

    public static SharedPreferences getSharedPreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        return preferences;
    }


    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = getSharedPreference(context);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        }
        edit.commit();

    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = getSharedPreference(context);
        return sp.getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getSharedPreference(context);
        return sp.getBoolean(key, false);
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = getSharedPreference(context);
        return sp.getInt(key, 0);
    }


    /**
     * 保存字符串到sp
     *
     * @param key    存取key
     * @param values 要保存的值
     */
    public static void putStringToSp(String key, String values) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, values);
        editor.commit();
    }

    /**
     * 保存布尔值到sp
     *
     * @param key    存取key
     * @param values 要保存的值
     */
    public static void putBooleanToSp(String key, Boolean values) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    /**
     * 保存数组到sp
     *
     * @param key    存取key
     * @param values 要保存的值
     */
    public static void putStringSetToSp(String key, String values) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String historys = sharedPreferences.getString(key, "");
        String[] tmp = historys.split(",");
        List<String> list = new ArrayList<>();
        for (String s : tmp) {
            if (!TextUtils.isEmpty(s.trim())) list.add(s);
        }
        if (!list.contains(values)) {
            if (list.size() == 10) {
                list.remove(list.size() - 1);
                list.add(0, values);
            } else
                list.add(0, values);
        } else {
            list.remove(values);
            list.add(0, values);
        }

        historys = arrayToString(list);
        editor.putString(key, historys);
        editor.commit();
    }

    private static String arrayToString(List<String> list) {
        String _s = "";
        for (String s : list) {
            if (TextUtils.isEmpty(_s)) {
                _s = s;
            } else {
                _s = _s + "," + s;
            }
        }
        return _s;
    }

    public static List<String> getHistoryList(String key) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String historys = sharedPreferences.getString(key, "");
        List<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(historys))
            return list;
        for (String s : historys.split(",")) {
            list.add(s);
        }
        return list;
    }

    public static void clearHistorySet(String key) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, "");
        editor.commit();
    }

    /**
     * 从sp文件获取STRINGSET
     *
     * @param key 要获取的值
     * @return
     */
    public static Set<String> getStringSetFromSp(String key) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(key, historySet);
    }

    /**
     * 从sp文件获取字符串
     *
     * @param key 要获取的值
     * @return
     */
    public static String getStringFromSp(String key, String defaultVlaues) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultVlaues);
    }

    /**
     * 从sp文件获取boolean值
     *
     * @param key 要获取的值
     * @return
     */
    public static Boolean getBooleanFromSp(String key, Boolean defaultVlaues) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultVlaues);
    }

    /**
     * 移除String值
     *
     * @param key 要移除的值
     */
    public static void remove(String key) {
        SharedPreferences sharedPreferences = ApplicationHolder.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = ApplicationHolder.instance.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = ApplicationHolder.instance.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}