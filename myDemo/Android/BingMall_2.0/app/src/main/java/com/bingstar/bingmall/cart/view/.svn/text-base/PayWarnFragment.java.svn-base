package com.bingstar.bingmall.cart.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;

/**
 * Created by zhangyifei on 17/6/28.
 */

public class PayWarnFragment extends DialogFragment {

    private ImageView mWait;
    private ImageView mSelect;

    public synchronized static void start(FragmentManager fragmentManager) {
        final String TAG = "PayWarnFragment";
        PayWarnFragment fragment = (PayWarnFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PayWarnFragment();
            Bundle bundle = new Bundle();

            fragment.setArguments(bundle);
        }
        fragment.show(fragmentManager, TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = 500;
        int dialogHeight = 350;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = inflater.inflate(R.layout.cart_paywarn_fragment, null, false);
        mSelect = (ImageView) inflate.findViewById(R.id.cart_warnselect_iv);
        mWait = (ImageView) inflate.findViewById(R.id.cart_warnwait_iv);
        mWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                AddrManageFragment.start(R.id.main_fragment, getFragmentManager());
            }
        });
        return inflate;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getDialog() != null) {
            dismiss();
        }
    }
}
