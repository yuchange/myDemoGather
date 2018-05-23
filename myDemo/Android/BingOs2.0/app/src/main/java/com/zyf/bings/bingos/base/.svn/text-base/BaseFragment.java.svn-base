package com.zyf.bings.bingos.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;

import java.lang.reflect.Field;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/7 下午1:43
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/7 下午1:43
 * @modify by reason:{方法名}:{原因}
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener, IBaseView {

    public static String KEY_RESID = "resID";
    public static String KEY_FROM = "isFrom";

    private int parentResId;

    private Toast toast;

    private Activity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setParentResId(int parentResId) {
        if (parentResId == 0) {
            return;
        }
        this.parentResId = parentResId;
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        } else {
            StatService.onPageEnd(getActivity(), getClass().getSimpleName());
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        // 页面埋点
        StatService.onPageStart(getActivity(), getClass().getSimpleName());
    }

    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    protected int getParentResId() {
        return parentResId;
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public void showToast(@StringRes int strId) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }

    private void setToastTextSize(Toast toast) {
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
            tv.setTextSize(50);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    private void hideSoft() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Context context = activity.getApplicationContext();
            if (context != null) {
                InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null && getActivity().getCurrentFocus() != null) {
                    im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }
}
