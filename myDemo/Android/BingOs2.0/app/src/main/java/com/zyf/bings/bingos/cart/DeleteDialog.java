package com.zyf.bings.bingos.cart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.utils.DensityUtil;


/**
 * Created by zhangyifei on 17/8/29.
 */

public class DeleteDialog extends DialogFragment {

    TextView mDelConfirm;
    TextView mDelCancle;


    OnDeleteItem mOnDeleteItem;
    private String mTag;
    private TextView mTvDialogContent;


    public static DeleteDialog newInstance() {

        Bundle args = new Bundle();

        DeleteDialog fragment = new DeleteDialog();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTag = this.getTag();


        View inflate = inflater.inflate(R.layout.shopcart_delete_dialog, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mDelConfirm = (TextView) inflate.findViewById(R.id.tv_shopcart_del_confirm);
        mDelCancle = (TextView) inflate.findViewById(R.id.tv_shopcart_del_cancle);
        mTvDialogContent = (TextView) inflate.findViewById(R.id.tv_dialog_content);
        switch (mTag) {

            case "cancelOrder"://取消订单
                mDelConfirm.setBackgroundResource(R.mipmap.shopcart_del_cancle);
                mTvDialogContent.setText("是否确定取消订单，此操作不可逆");
                break;
            case "cancelSales"://取消申请售后
                mDelConfirm.setBackgroundResource(R.mipmap.shopcart_del_cancle);
                mTvDialogContent.setText("是否确定取消申请售后，此操作不可逆");
                break;
            case "deleteOrder"://删除订单
                mTvDialogContent.setText("是否删除订单，此操作不可逆");
                mDelConfirm.setBackgroundResource(R.mipmap.shopcart_del_cancle);

                break;
            case "logOut": // 退出登录
                mTvDialogContent.setText("是否退出登录");
                mDelConfirm.setBackgroundResource(R.mipmap.shopcart_del_cancle);
                break;
            default:
                break;
        }
        Log.e("DeleteDialog", "initView: " + mDelConfirm);
        mDelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }
                if (mOnDeleteItem != null) {
                    mOnDeleteItem.onDeleteItem();
                }
            }
        });

        mDelCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DeleteDialog", "onClick: " + "点击事件");
                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }
            }
        });
    }


    public void setOnDeleteItem(OnDeleteItem onDeleteItem) {
        mOnDeleteItem = onDeleteItem;
    }

    public interface OnDeleteItem {
        void onDeleteItem();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
