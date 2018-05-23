package com.zyf.bings.bingos.order.view;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.http.OrderClient;
import com.zyf.bings.bingos.order.adapter.LogisticsInfoAdapter;
import com.zyf.bings.bingos.order.adapter.LogisticsInfoItemDecoration;
import com.zyf.bings.bingos.order.bean.LogisticsInfoBean;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/10/7.
 * 物流详情的界面
 */
public class LogisticsInfoFragment extends DialogFragment {

    private RecyclerView mRvLogistics;
    private TextView mTvOrderState;
    private TextView mTvGoodsName;
    private ImageView mIvLogisticsGoods;
    private static final String LOGISTICS_NO = "logistics_no";
    private static final String ORDERINFO_ID = "orderinfo_id";
    private static final String LOGISTICS_COMPANY = "logistics_company";
    private static final String IV_URL = "iv_url";
    private String mLogisticsNo;
    private String mOrderInfoId;
    public final String TAG = "LogisticsInfoFragment";
    private String mIvUrl;
    private TextView mTvErrorLosistics;
    private ImageView mIvLogisticsClose;
    private TextView mTvLogisticsName;
    private TextView mTvLogisticsNum;
    private LinearLayout mLlLogisticsInfo;
    private String mLogisticsCompany;
    private ProgressFrameLayout mProgressFrameLayout;
    private ProgressFrameLayout mProgressFrameLayout1;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = DensityUtil.dp2px(getActivity(), 900);
        int dialogHeight = DensityUtil.dp2px(getActivity(), 680);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置dialog的背景为透明
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = (View) inflater.inflate(R.layout.fragment_logistics_info, container, false);
        mLogisticsNo = getArguments().getString(LOGISTICS_NO);
        mOrderInfoId = getArguments().getString(ORDERINFO_ID);
        mLogisticsCompany = getArguments().getString(LOGISTICS_COMPANY);
        initView(view);
        initListener();
        initData();

        return mProgressFrameLayout;
    }

    private void initListener() {

        mIvLogisticsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDialog() != null && getDialog().isShowing()) {

                    dismiss();
                }

            }
        });


    }


    private void initData() {
        mProgressFrameLayout.showLoading();
        getOrderLogisticswInfo();
    }

    private void initView(View view) {

        mProgressFrameLayout = (ProgressFrameLayout) view.findViewById(R.id.progress_view);
        mRvLogistics = (RecyclerView) view.findViewById(R.id.rv_logistics);
        mTvErrorLosistics = (TextView) view.findViewById(R.id.tv_error_logistics);
        mIvLogisticsClose = (ImageView) view.findViewById(R.id.iv_logistics_close);
        mTvLogisticsName = (TextView) view.findViewById(R.id.tv_logistics_name);
        mTvLogisticsNum = (TextView) view.findViewById(R.id.tv_logistics_num);
        mLlLogisticsInfo = (LinearLayout) view.findViewById(R.id.ll_logistics_info);
    }

    public void getOrderLogisticswInfo() {
        Map<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(LOGISTICS_NO, mLogisticsNo);
            jsonObject.put(ORDERINFO_ID, mOrderInfoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.ORDER_LOGISTIC_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        OkGoUtils.doStringPostRequest(map, OrderClient.ORDER_LOGISTIC_TITLE, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                mProgressFrameLayout.showContent();
                LogisticsInfoBean logisticsInfoBean = GsonFactory.getGson().fromJson(str, LogisticsInfoBean.class);
                List<LogisticsInfoBean.ExpressInfoListBean> expressInfoList = logisticsInfoBean.getExpressInfoList();

                if (expressInfoList == null || expressInfoList.size() == 0) {
                    mTvErrorLosistics.setVisibility(View.VISIBLE);
                    mLlLogisticsInfo.setVisibility(View.GONE);
                } else {
                    mTvLogisticsName.setText(mLogisticsCompany + ": ");
                    mTvLogisticsNum.setText(mLogisticsNo);
                    initLogisticsAdapter(expressInfoList);
                }


            }

            @Override
            public void onFail(int code, String error) {
                mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "加载失败...", error, "点击重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }

        });


    }

    private void initLogisticsAdapter(List<LogisticsInfoBean.ExpressInfoListBean> expressInfoList) {
        LogisticsInfoAdapter logisticsInfoAdapter = new LogisticsInfoAdapter(R.layout.item_logistics_info, expressInfoList);

        mRvLogistics.addItemDecoration(new LogisticsInfoItemDecoration(0));
        mRvLogistics.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvLogistics.setAdapter(logisticsInfoAdapter);

    }

    public synchronized static void start(FragmentManager fragmentManager, String logisticsNo, String orderInfoId, String logisticsCompany) {
        final String TAG = "LogisticsInfoFragment";
        LogisticsInfoFragment fragment = (LogisticsInfoFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new LogisticsInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString(LOGISTICS_NO, logisticsNo);
            bundle.putString(ORDERINFO_ID, orderInfoId);
            bundle.putString(LOGISTICS_COMPANY, logisticsCompany);
            fragment.setArguments(bundle);
        }
        if (!fragment.isAdded()) {
            fragment.show(fragmentManager, TAG);
        }
    }

}
