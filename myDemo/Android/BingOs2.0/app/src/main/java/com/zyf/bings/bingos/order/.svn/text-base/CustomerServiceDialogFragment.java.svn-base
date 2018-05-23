package com.zyf.bings.bingos.order;

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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.bean.QrcodeBean;
import com.zyf.bings.bingos.order.http.OrderCountClient;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.ImageLoader;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;

import java.util.Map;

/**
 * @author wangshiqi
 * @date 2017/10/20
 */

public class CustomerServiceDialogFragment extends DialogFragment {

    private ImageView mIvCustomService;
    private static final String METHOD = "query.qrcode";
    private static final String URL = Config.AFTER_SALE +"/queryQrcode.shtml";
    private ImageView mIvClose;

    public static CustomerServiceDialogFragment newInstance() {

        Bundle args = new Bundle();

        CustomerServiceDialogFragment fragment = new CustomerServiceDialogFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_customer, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        mIvCustomService = (ImageView) v.findViewById(R.id.iv_customer_service);
        mIvClose = (ImageView) v.findViewById(R.id.iv_customer_close);
        mIvClose.setOnClickListener(view -> dismiss());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getServiceImg();
    }

    private void getServiceImg() {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, "{}");
        RxOkClient.doPost(map, URL, OrderCountClient.class, null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    QrcodeBean bean = gson.fromJson(data, QrcodeBean.class);
                    ImageLoader.load(mIvCustomService, bean.getImageUrl());
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }
}
