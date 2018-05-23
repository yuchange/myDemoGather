package com.zyf.bings.bingos.order.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.Map;

/**
 * Created by wangshiqi on 2017/10/16.
 */

public class OrderApplyDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button mBtnPackageBreak, mBtnExpired, mBtnOtherReason, mBtnLeakage, mBtnCommit;
    private ImageView mIvClose;
    private String mGoodsId, mAfterSaletype, mUserRemark, mZorderInfoId, mOderInfoId;
    private static final String RETURN_METHOD = "refund.of.return";
    private static final String RETURN_URL = Config.AFTER_SALE + "/refundOfReturn.shtml";
    private static final String GOODS_ID = "order_goods_id";
    private static final String AFTER_SALE_TYPE = "after_sale_type";
    private static final String USER_REMARK = "user_remark";
    private static final String ZORDERINFO_ID = "zorderinfo_id";
    private static final String ORDERINFO_ID = "orderinfo_id";
    public static OrderApplyDialogFragment newInstance(String goodsId, String afterSaleType) {

        Bundle args = new Bundle();
        args.putString(GOODS_ID, goodsId);
        args.putString(AFTER_SALE_TYPE, afterSaleType);
        OrderApplyDialogFragment fragment = new OrderApplyDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        int dialogWidth = DensityUtil.px2dp(getActivity(), 900);
        int dialogHeight = DensityUtil.px2dp(getActivity(), 650);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置dialog的背景为透明
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();
        mGoodsId = bundle.getString(GOODS_ID);
        mAfterSaletype = bundle.getString(AFTER_SALE_TYPE);
        View v = inflater.inflate(R.layout.fragment_order_apply_dialog, null);
        initView(v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnPackageBreak.setOnClickListener(this);
        mBtnOtherReason.setOnClickListener(this);
        mBtnLeakage.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mBtnExpired.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
    }

    private void initView(View v) {
        mBtnPackageBreak = (Button) v.findViewById(R.id.btn_package_breakage);
        mBtnExpired = (Button) v.findViewById(R.id.btn_expired_goods);
        mBtnLeakage = (Button) v.findViewById(R.id.btn_leakage);
        mBtnOtherReason = (Button) v.findViewById(R.id.btn_other_reason);
        mIvClose = (ImageView) v.findViewById(R.id.iv_order_apply_cancel);
        mBtnCommit = (Button) v.findViewById(R.id.btn_order_apply_commit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_package_breakage:
                mBtnPackageBreak.setTextColor(Color.WHITE);
                mBtnPackageBreak.setBackgroundResource(R.mipmap.order_apply_btn_select);
                mBtnLeakage.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnLeakage.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnOtherReason.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnOtherReason.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnExpired.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnExpired.setTextColor(getResources().getColor(R.color.c_919191));
                mUserRemark = mBtnPackageBreak.getText().toString();
                break;
            case R.id.btn_expired_goods:
                mBtnExpired.setTextColor(Color.WHITE);
                mBtnExpired.setBackgroundResource(R.mipmap.order_apply_btn_select);
                mBtnLeakage.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnLeakage.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnOtherReason.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnOtherReason.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnPackageBreak.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnPackageBreak.setTextColor(getResources().getColor(R.color.c_919191));
                mUserRemark = mBtnExpired.getText().toString();
                break;
            case R.id.btn_leakage:
                mBtnLeakage.setTextColor(Color.WHITE);
                mBtnLeakage.setBackgroundResource(R.mipmap.order_apply_btn_select);
                mBtnOtherReason.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnOtherReason.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnExpired.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnExpired.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnPackageBreak.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnPackageBreak.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mUserRemark = mBtnLeakage.getText().toString();
                break;
            case R.id.btn_other_reason:
                mBtnOtherReason.setTextColor(Color.WHITE);
                mBtnOtherReason.setBackgroundResource(R.mipmap.order_apply_btn_select);
                mBtnPackageBreak.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnPackageBreak.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnExpired.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnLeakage.setTextColor(getResources().getColor(R.color.c_919191));
                mBtnLeakage.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnExpired.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mUserRemark = mBtnOtherReason.getText().toString();
                break;
            case R.id.iv_order_apply_cancel:
                dismiss();
                break;
            case R.id.btn_order_apply_commit:
                if (TextUtils.isEmpty(mUserRemark)) {
                    ToastUtils.showToast("请选择售后理由");
                } else {
                    applyReturn();
                }
                break;
            default:
                mBtnOtherReason.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnExpired.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnLeakage.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
                mBtnPackageBreak.setBackgroundResource(R.mipmap.order_apply_btn_unselect);
        }
    }

    private void applyReturn() {
        Map<String, String> params = new ArrayMap<>();
        params.put(GOODS_ID, mGoodsId);
        params.put(USER_REMARK, mUserRemark);
        params.put(AFTER_SALE_TYPE, mAfterSaletype);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, RETURN_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, RETURN_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast("申请成功");
                dismiss();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
