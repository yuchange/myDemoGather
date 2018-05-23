package com.zyf.bings.bingos.manager;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.utils.DensityUtil;

/**
 * Created wangshiqi sd on 2017/9/4.
 */

public class PersonalQRcodeFragment extends DialogFragment {
    public static PersonalQRcodeFragment newInstance() {

        Bundle args = new Bundle();

        PersonalQRcodeFragment fragment = new PersonalQRcodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = DensityUtil.px2dp(getActivity(), 500);
        int dialogHeight = DensityUtil.px2dp(getActivity(), 300);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置dialog的背景为透明

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.personal_qr, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

    }
}
