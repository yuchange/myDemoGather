package com.zyf.bings.bingos.order;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.utils.DensityUtil;


/**
 * Created by wangshiqi on 17/10/10.
 */

public class NotificationDialogFragment extends DialogFragment {

    private TextView mTvSure;
    private String mText;
    private TextView mMTvText;

    public static NotificationDialogFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        NotificationDialogFragment fragment = new NotificationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        int dialogWidth = DensityUtil.px2dp(getActivity(), 500);
        int dialogHeight = DensityUtil.px2dp(getActivity(), 300);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置dialog的背景为透明

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();
        mText = bundle.getString("text");
        View inflate = inflater.inflate(R.layout.notification_dialog,  container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mMTvText = (TextView) inflate.findViewById(R.id.tv_notification_text);
        mMTvText.setText(mText);
        mTvSure = (TextView) inflate.findViewById(R.id.tv_notification_sure);
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
