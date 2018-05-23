package com.bingstar.bingmall.goods.view;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * Created by zhangyifei on 17/7/24.
 */

public abstract class DelayTextWatcher implements TextWatcher {

    public String text;

    public Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            onDelayFinish(text);
        }
    };
    public Handler mHandler;

    public DelayTextWatcher() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mRunnable != null) {
            //每次editText有变化的时候，则移除上次发出的延迟线程
            mHandler.removeCallbacks(mRunnable);
        }
        //延迟800ms，如果不再输入字符，则执行该线程的run方法
        text = s.toString().trim();
        mHandler.postDelayed(mRunnable, 500);
    }

    public abstract void onDelayFinish(String s);

    public void clean() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }


}
