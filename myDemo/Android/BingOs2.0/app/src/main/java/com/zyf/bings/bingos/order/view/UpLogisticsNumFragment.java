package com.zyf.bings.bingos.order.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.adapter.LogisticsCompanyAdapter;
import com.zyf.bings.bingos.order.bean.LogisticsComanyInfo;
import com.zyf.bings.bingos.order.http.OrderClient;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.ImageLoader;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.progressDialog.MyProgressDialog;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/19.
 */

public class UpLogisticsNumFragment extends DialogFragment implements View.OnClickListener {

    private ImageView mIvClose;
    private ProgressFrameLayout mPfRelaCustom;
    private TextView mTvCourierCompay;
    private EditText mEtLogisticsNumber;
    private ImageView mIvQrCode;
    private Button mBtnConfirm;
    private ImageView mIvCourierCom;
    private static final String TAG = "UpLogisticsNmu";
    private RelativeLayout mRlSelectCompany;
    private RecyclerView mRvLogisticsCompany;
    private PopupWindow mPopupWindow;
    private static final String ORDER_GOODS_ID = "ordergoodsid";
    private static final String LOGISTICS_NO = "logistics_no";
    private static final String SHIPPIG_EGNAME = "shipping_egname";

    private String shipping_egname;
    private String mOrderGoodsId;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {

            return;
        }
        int dialogWidth = DensityUtil.dp2px(getActivity(), 900);
        int dialogHeight = DensityUtil.dp2px(getActivity(), 680);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置dialog的背景为透明
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_up_logistics_num, container, false);
        mOrderGoodsId = getArguments().getString(ORDER_GOODS_ID);
        initView(view);
        initData();
        initListener();

        return view;
    }

    private void initListener() {
        mIvClose.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        mTvCourierCompay.setOnClickListener(this);
        mRlSelectCompany.setOnClickListener(this);
    }

    private void initData() {
        Map<String, String> map = new ArrayMap<>();
        map.put("method", OrderClient.QR_CODE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, "{}");
        OkGoUtils.doStringPostRequest(map, OrderClient.QR_CODE, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(str);
                    String imageUrl = jsonObject.getString("imageUrl");
                    ImageLoader.load(mIvQrCode, imageUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(int code, String error) {

            }
        });


    }

    private void initView(View view) {
        mIvClose = (ImageView) view.findViewById(R.id.iv_close);
        mPfRelaCustom = (ProgressFrameLayout) view.findViewById(R.id.pf_rela_custom);
        mTvCourierCompay = (TextView) view.findViewById(R.id.tv_courier_compay);
        mEtLogisticsNumber = (EditText) view.findViewById(R.id.et_logistics_number);
        mIvQrCode = (ImageView) view.findViewById(R.id.iv_qr_code);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        mIvCourierCom = (ImageView) view.findViewById(R.id.iv_courier_compay);
        mRlSelectCompany = (RelativeLayout) view.findViewById(R.id.rl_select_compay);
        mEtLogisticsNumber.setEnabled(true);


        getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    Context context = activity.getApplicationContext();
                    if (context != null) {
                        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (im != null && getDialog().getCurrentFocus() != null) {
                            boolean b = im.hideSoftInputFromWindow(getDialog().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            Log.e("AddAddressFragment", "onDismiss: " + "隐藏软键盘 " + b);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                this.dismiss();
                break;
            case R.id.btn_confirm: //提交物流单号
                if (!TextUtils.isEmpty(mTvCourierCompay.getText().toString()) && !TextUtils.isEmpty(mEtLogisticsNumber.getText().toString())) {


                    upLoadLogisticsNnm();

                } else {
                    ToastUtils.showToast("请完善信息");
                }


                break;
            case R.id.rl_select_compay:
                Log.i(TAG, "点击事件执行了");
                getLogisticsComapy();

                break;


            default:
                break;
        }


    }

    private void upLoadLogisticsNnm() {
        Map<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(LOGISTICS_NO, mEtLogisticsNumber.getText().toString());
            jsonObject.put(SHIPPIG_EGNAME, shipping_egname);
            jsonObject.put(ORDER_GOODS_ID, mOrderGoodsId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.UP_LOAD_LOGISTICS_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        OkGoUtils.doStringPostRequest(map, OrderClient.UP_LOAD_LOGISTICS, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ToastUtils.showToast("上传成功");
                getDialog().dismiss();

            }

            @Override
            public void onFail(int code, String error) {
                ToastUtils.showToast("上除失败");
            }
        });


    }

    private void getLogisticsComapy() {
        MyProgressDialog pd = PBUtil.getPD(getActivity());
        pd.show();
        Map<String, String> map = new ArrayMap<>();
        map.put("method", OrderClient.LOGISTICS_COMPANY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, "{}");
        OkGoUtils.doStringPostRequest(map, OrderClient.LOGISTICS_COMPANY_QUERY, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                pd.dismiss();
                LogisticsComanyInfo logisticsComanyInfo = GsonFactory.fromJson(str, LogisticsComanyInfo.class);
                List<LogisticsComanyInfo.ShippingListBean> shippingList = logisticsComanyInfo.getShippingList();
                showPopupWindow(shippingList);

            }

            @Override
            public void onFail(int code, String error) {
                pd.dismiss();
            }
        });


    }

    public static void start(FragmentManager fragmentManager, String orderGoodsId) {
        final String TAG = "UpLogisticsNumFragment";
        UpLogisticsNumFragment fragment = (UpLogisticsNumFragment) fragmentManager.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = new UpLogisticsNumFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ORDER_GOODS_ID, orderGoodsId);
            fragment.setArguments(bundle);


        }
        if (!fragment.isAdded()) {
            fragment.show(fragmentManager, TAG);
        }
        fragment.setCancelable(false);
    }


    private void showPopupWindow(List<LogisticsComanyInfo.ShippingListBean> shippingList) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_logistics_compay, null);
        mPopupWindow = new PopupWindow(contentView, 200, 300);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mRvLogisticsCompany = (RecyclerView) contentView.findViewById(R.id.rv_logistics_company);

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        initRvLogisticsCompany(shippingList);


        mPopupWindow.showAsDropDown(mRlSelectCompany, -200, 20);

    }

    private void initRvLogisticsCompany(List<LogisticsComanyInfo.ShippingListBean> shippingList) {

        mRvLogisticsCompany.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LogisticsCompanyAdapter companyAdapter = new LogisticsCompanyAdapter(R.layout.item_logistics_company, shippingList);
        mRvLogisticsCompany.setAdapter(companyAdapter);
        companyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTvCourierCompay.setText(shippingList.get(position).getShipping_name());
                shipping_egname = shippingList.get(position).getShipping_egname();
                mPopupWindow.dismiss();

            }
        });


    }


}
