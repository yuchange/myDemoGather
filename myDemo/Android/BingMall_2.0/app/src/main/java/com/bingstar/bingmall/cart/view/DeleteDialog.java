package com.bingstar.bingmall.cart.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cart.bean.DeleteDialogBean;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.bean.ProductInfoUpdate;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderState;
import com.bingstar.bingmall.user.addr.AddrStates;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianhechen on 17/3/7.
 */

public class DeleteDialog extends DialogFragment {

    private Button btn_cancle,btn_sure;

    private String flugString;

    public static DeleteDialog newInstance(String delete_titleString, String flugString) {
        DeleteDialog fragment = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(OrderStates.DELETE_TITLE, delete_titleString);
        bundle.putString(OrderStates.DELETE_FLUG,flugString);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = 450;
        int dialogHeight = 300;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.delete_dialog, container);
        TextView delete_title_text = (TextView)view.findViewById(R.id.delete_title);
        delete_title_text.setText(getArguments().getString(OrderStates.DELETE_TITLE));
//        final ProductInfoAdd productInfoAdd= (ProductInfoAdd) bundle.getSerializable("pro");

        flugString = getArguments().getString(OrderStates.DELETE_FLUG);

        btn_cancle = (Button) view.findViewById(R.id.delete_cancle_btn);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消删除
                EventBus.getDefault().post(new DeleteDialogBean( AddrStates.DELETE_CANCEL,flugString));
                onDestroyView();
            }
        });
        btn_sure = (Button) view.findViewById(R.id.delete_sure_btn);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确定删除
                EventBus.getDefault().post(new DeleteDialogBean( AddrStates.DELETE_CONFIRM,flugString));
                onDestroyView();
            }
        });
        setCancelable(false);
        return view;
    }
}
