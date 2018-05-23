package com.zyf.bings.bingos.pay;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.Util;
import com.zyf.bings.bingos.event.ToOrderDetailFragEvent;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.pay.bean.HaierPayInfo;
import com.zyf.bings.bingos.pay.bean.HaierPayList;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.DensityUtil;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * `
 * Created by zhangyifei on 17/9/20.
 */

public class PayFragment extends DialogFragment implements IPayContract.PayView {


    @BindView(R.id.tv_pay_orderlook)
    TextView mTvOrderlook;
    @BindView(R.id.iv_pay_close)
    ImageView mIvClose;
    @BindView(R.id.tv_pay_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_pay_discount_price)
    TextView mTvDiscountPrice;
    @BindView(R.id.iv_pay_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_pay_alipay)
    ImageView mIvAlipay;
    Unbinder unbinder;

    IPayContract.PayPresenter mPayPresenter;
    @BindView(R.id.rela_custom)
    ProgressFrameLayout mRelaContent;
    @BindView(R.id.iv_pay_status)
    ImageView mIvStatus;
    @BindView(R.id.tv_pay_status)
    TextView mTvStatus;
    @BindView(R.id.rela_pay_status)
    RelativeLayout mRelaPayStatus;
    @BindView(R.id.pay_haier_time)
    TextView mPayHaierTime;


    private CountDownTimer timer;

    private boolean isTimeOut;//是否超时
    private static final String ZORDER_NO = "zorder_no";
    private static final String ZORDER_ID = "zorder_id";

    private String mZorderNo; //总订单号
    private String mZorderId;

    public synchronized static void start(FragmentManager fragmentManager, String zorderno, String zOrderId) {
        final String TAG = "PayFragment";
        PayFragment fragment = (PayFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PayFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ZORDER_NO, zorderno);
            bundle.putString(ZORDER_ID, zOrderId);
            fragment.setArguments(bundle);
        }
        if (!fragment.isAdded()) {
            fragment.show(fragmentManager, TAG);
        }
        fragment.setCancelable(false);
    }


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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        CartCountClient.getCartCount();
        View view = inflater.inflate(R.layout.pay_fragment, container, false);
        mZorderNo = getArguments().getString(ZORDER_NO);
        mZorderId = getArguments().getString(ZORDER_ID);
        unbinder = ButterKnife.bind(this, view);
        setPresenter();
        initData();
        return view;
    }

    private void initData() {
        mRelaContent.showLoading();
        mPayPresenter.getPayInfo(mZorderNo);
    }


    @OnClick({R.id.tv_pay_orderlook, R.id.iv_pay_close})
    public void onclick(View view) {
        switch (view.getId()) {
            //查看订单
            case R.id.tv_pay_orderlook:

                break;
            case R.id.iv_pay_close:
                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }


                    EventBus.getDefault().post(new ToOrderDetailFragEvent(mZorderId, mZorderNo));



                break;

            default:
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        unbinder.unbind();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void notifyPayInfo(HaierPayInfo haierPayInfo) {

        if (mRelaContent != null) {
            mRelaContent.setVisibility(View.VISIBLE);
            mRelaPayStatus.setVisibility(View.GONE);
        }


        mRelaContent.showContent();
        List<HaierPayList> haierPayInfos = haierPayInfo.getHaierpayInfoList();
        if (haierPayInfos != null && haierPayInfos.size() > 0) {
            int time;
            try {
                time = Integer.parseInt(haierPayInfo.getTime_expire());
                Log.i("HaierPayActivity", "时间" + time + "weijixi" + haierPayInfo.getTime_expire());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                time = 20 * 60;
            }
            Integer width = DensityUtil.dp2px(getActivity(), 300);
            Integer height = DensityUtil.dp2px(getActivity(), 300);
            for (HaierPayList haierPayList : haierPayInfos) {
                if ("wx".equals(haierPayList.getPayment_type())) {
                    Bitmap bitmap = Util.createQRCodeBitmap(haierPayList.getCode_url(), width, height);
                    if (bitmap != null) {
                        mIvWechat.setImageBitmap(bitmap);
                    }
                } else if ("zfb".equals(haierPayList.getPayment_type())) {
                    Bitmap bitmap = Util.createQRCodeBitmap(haierPayList.getCode_url(), width, height);
                    if (bitmap != null) {
                        mIvAlipay.setImageBitmap(bitmap);
                    }
                }
            }
            startTimer(time);
            String zorder_total_money = haierPayInfo.getZorder_total_money();
            String couponMoney = haierPayInfo.getCouponMoney();
            BigDecimal total = new BigDecimal(zorder_total_money);  //实付金额
            BigDecimal coupon = new BigDecimal(couponMoney); // 优惠金额

            mTvTotalPrice.setText("合计: " + "¥" + "" + zorder_total_money);
            if (couponMoney.equals("0")) {
                mTvDiscountPrice.setVisibility(View.GONE);
            } else {
                mTvDiscountPrice.setText("(¥" + (total.add(coupon).doubleValue()) + "-¥" + coupon + ")");
            }
        } else {

        }
    }

    private void checkPaied() {
        mPayPresenter.getOrderStatus(mZorderNo);
    }


    private void startTimer(int time) {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        timer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (mPayHaierTime != null) {
                    int minute = (int) (millisUntilFinished / (1000 * 60));
                    int second = (int) (millisUntilFinished / 1000 % 60);
                    mPayHaierTime.setText(new Formatter().format("%02d", minute).toString() + ":"
                            + new Formatter().format("%02d", second).toString());
                }
                checkPaied();
            }

            @Override
            public void onFinish() {
                payError("支付超时", 0);
                isTimeOut = true;
                Log.i("HaierPayActivity", "时间到了执行了");
            }
        };
        timer.start();
    }

    @Override
    public void getPayInfoError(String msg) {

        mRelaContent.setVisibility(View.VISIBLE);
        mRelaPayStatus.setVisibility(View.GONE);


        mRelaContent.showError(R.mipmap.syn_error_icon, "加载失败...", msg, "点击重试", v -> {
            mRelaContent.showLoading();
            mPayPresenter.getPayInfo(mZorderNo);
        });
    }

    @Override
    public void paySuccess(String msg) {
        if (mRelaContent != null) {

            mRelaContent.setVisibility(View.GONE);
        }
        mRelaPayStatus.setVisibility(View.VISIBLE);

        mTvStatus.setText("支付成功");
        mIvStatus.setImageResource(R.mipmap.pay_success);
        if (timer != null) {
            timer.cancel();
        }

    }

    @Override
    public void payError(String msg, int type) {
        mRelaContent.setVisibility(View.GONE);
        mRelaPayStatus.setVisibility(View.VISIBLE);
        if (type == 0) {//支付超时
            mTvStatus.setText(msg + "，请重新下单");
        } else if (type == 1) {//其它原因
            mTvStatus.setText(msg);
        }

        mIvStatus.setImageResource(R.mipmap.pay_error);
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void setPresenter() {
        mPayPresenter = new PayPresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPayPresenter.unsubscribe();
    }
}
