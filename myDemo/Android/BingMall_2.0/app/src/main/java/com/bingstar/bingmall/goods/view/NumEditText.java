package com.bingstar.bingmall.goods.view;

import android.content.Context;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zhangyifei on 17/7/25.
 */

public class NumEditText extends EditText {
    public NumEditText(Context context) {
        super(context);
        init();
    }


    public NumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        /*setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (getText().toString().equals("")) {
                        setText("0");
                        Selection.setSelection(getText(), getText().length());
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    clearFocus();
                    return true;
                }
                return false;
            }
        });*/
    }
}
