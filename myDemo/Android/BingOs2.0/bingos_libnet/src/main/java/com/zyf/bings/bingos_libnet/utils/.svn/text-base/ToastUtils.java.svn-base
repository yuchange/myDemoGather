package com.zyf.bings.bingos_libnet.utils;

import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by zhangyifei on 17/9/3.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(ApplicationHolder.instance, str, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public static void showToast(@StringRes int strId) {
        if (toast == null) {
            toast = Toast.makeText(ApplicationHolder.instance, strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }

    private static void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(20);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
