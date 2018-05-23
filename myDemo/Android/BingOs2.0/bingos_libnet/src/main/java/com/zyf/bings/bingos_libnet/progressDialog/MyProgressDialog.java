package com.zyf.bings.bingos_libnet.progressDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;


public class MyProgressDialog extends ProgressDialog {
    private int layoutRes;

    public MyProgressDialog(Context context, int theme, int layoutRes) {
        super(context, theme);
        this.layoutRes = layoutRes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes);
    }

}
