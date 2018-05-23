package com.bingstar.bingmall.sdk.pay.haier;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseActivity;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.event.FinishOneKeyFragmentEvent;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.OrderFragment;
import com.bingstar.bingmall.order.bean.OrderState;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.sdk.pay.PayClient;
import com.bingstar.bingmall.user.pay.PayStates;
import com.bingstar.bingmall.video.lib.SynLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Formatter;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/11 上午11:28
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/11 上午11:28
 * @modify by reason:{方法名}:{原因}
 */

public class HaierPayActivity extends BaseActivity {

    private String orderNum;
    private SynLinearLayout synLinearLayout;
    private TextView payMoney;
    private TextView timeString;
    private ImageView aliPay;
    private ImageView wxPay;
    private PayResultDialog dialog;
    private CountDownTimer timer;
    private static String OEDER_KEY = "orderNum";
    private static Context mContext;

    public static void start(Context context, String orderInfo) {
        if (context == null || orderInfo == null) {
            return;
        }
        mContext = context;
        Intent i = new Intent(context, HaierPayActivity.class);
        i.putExtra(OEDER_KEY, orderInfo);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderNum = getIntent().getStringExtra(OEDER_KEY);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pay_haierpay_activity);
        synLinearLayout = (SynLinearLayout) findViewById(R.id.pay_haierpay_syn);
        payMoney = (TextView) findViewById(R.id.pay_haier_money);
        timeString = (TextView) findViewById(R.id.pay_haier_time);
        TextView close = (TextView) findViewById(R.id.pay_haier_back);
        aliPay = (ImageView) findViewById(R.id.pay_haier_ali);
        wxPay = (ImageView) findViewById(R.id.pay_haier_wx);
        TextView payNum = (TextView) findViewById(R.id.pay_haier_pay_num);
        synLinearLayout.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                getPayMessage();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext != null) {
                    MainActivity mainActivity = (MainActivity) HaierPayActivity.mContext;
                    OrderFragment.start(R.id.main_fragment, mainActivity.getSupportFragmentManager());
                }
                finish();
            }
        });
        payNum.setText(orderNum);
        getPayMessage();
        EventBus.getDefault().post(new FinishOneKeyFragmentEvent());//关掉自己
    }

    private void getPayMessage() {
        synLinearLayout.showLoad();
        PayClient.getHaierPayMessage(orderNum, new ClientCallback<HaierPayInfo>() {
            @Override
            public void onSuccess(HaierPayInfo haierPayInfo) {
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
                    for (HaierPayList haierPayList : haierPayInfos) {
                        Bitmap bitmap = Util.encodeAsBitmap(haierPayList.getCode_url());
                        if ("zfb".equals(haierPayList.getPayment_type())) {
                            if (bitmap != null) {
                                aliPay.setImageBitmap(bitmap);
                            }
                        } else if ("wx".equals(haierPayList.getPayment_type())) {
                            if (bitmap != null) {
                                wxPay.setImageBitmap(bitmap);
                            }
                        }
                    }
                    startTimer(time);
                    payMoney.setText(" " + "¥" + "" + haierPayInfo.getZorder_total_money());
                    synLinearLayout.showSuccess();
                } else {
                    synLinearLayout.showError();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (code > 0) {
                    showToast(error);
                }
                synLinearLayout.showError();
            }
        });
    }

    private void checkPaied() {
        OrderClient.getOrderStatus(orderNum, new ClientCallback<OrderState>() {
            @Override
            public void onSuccess(OrderState orderStateList) {
                String state = orderStateList.getOrderStatus();
                if (state != null) {
                    if ("99".equals(state)) {
                        createDialog(PayResultDialog.TYPE_FAIL);
                    } else if ("1".equals(state)) {
                        createDialog(PayResultDialog.TYPE_SUCCESS);
                    }
                }
            }

            @Override
            public void onFail(int code, String error) {
            }
        });
    }

    private void createDialog(final int type) {
        if (isFinishing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed()) {
                return;
            }
        }
        if (timer != null) {
            timer.cancel();
        }
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new PayResultDialog.Builder(HaierPayActivity.this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HaierPayActivity", "点击了Dialog");
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (type == PayResultDialog.TYPE_SUCCESS) {
                    Log.i("HaierPayActivity", "支付成功");
                    finish();

                } else if (type == PayResultDialog.TYPE_FAIL) {//支付失败
                    getPayMessage();
                } else {//时间超时订单取消
                    if (dialog != null) {
                        dialog.dismiss();
                        finish();
                    }

                    //getPayMessage();
                }
                if (mContext != null) {
                    MainActivity mainActivity = (MainActivity) HaierPayActivity.mContext;
                    OrderFragment.start(R.id.main_fragment, mainActivity.getSupportFragmentManager());
                }

            }
        }).setType(type).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void startTimer(int time) {
        if (isFinishing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed()) {
                return;
            }
        }
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        timer = new CountDownTimer(time * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (timeString != null) {
                    int minute = (int) (millisUntilFinished / (1000 * 60));
                    int second = (int) (millisUntilFinished / 1000 % 60);
                    timeString.setText(new Formatter().format("%02d", minute).toString() + ":"
                            + new Formatter().format("%02d", second).toString());
                }
                checkPaied();
            }

            @Override
            public void onFinish() {
                Log.i("HaierPayActivity", "时间到了执行了");
                createDialog(PayResultDialog.TYPE_TIMEOUT);
            }
        };
        timer.start();
    }

    @Override
    public void finish() {
        super.finish();
        EventBus.getDefault().post(new EventMsg(HaierPayActivity.class, PayStates.PAY_SUCCESS));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
