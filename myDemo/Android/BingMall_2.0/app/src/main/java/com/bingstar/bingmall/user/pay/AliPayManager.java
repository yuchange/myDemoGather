package com.bingstar.bingmall.user.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.view.OrderInfoPresenter;
import com.bingstar.bingmall.sdk.pay.PayClient;
import com.bingstar.bingmall.sdk.pay.alipay.PayResult;
import com.bingstar.bingmall.user.pay.IPayManager;
import com.yunzhi.lib.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 下午5:46
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 下午5:46
 * @modify by reason:{方法名}:{原因}
 */

public class AliPayManager implements IPayManager {

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016080400167513";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */

    private static final int SDK_PAY_FLAG = 1;

    private PayCallback payCallback;

    private Activity activity;

    public AliPayManager(Activity activity){
        this.activity = activity;
    }

    public void payV2(final String orderInfo, final Activity activity) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        this.activity = activity;
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast(activity, R.string.ali_pay_success);
                        if (payCallback!=null) {
                            payCallback.onSuccess();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast(activity, R.string.ali_pay_fail);
                        if (payCallback!=null) {
                            payCallback.onFail();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    public void pay(String orderInfo,PayCallback payCallback) {
        this.payCallback = payCallback;
        PayClient.getAliPayMessage(orderInfo, new ClientCallback<String>() {
            @Override
            public void onSuccess(String s) {
                payV2(s,activity);
            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    private static void showToast(Context context, @StringRes int strId) {
        Toast toast;
        toast = Toast.makeText(context, strId, Toast.LENGTH_SHORT);
        setToastTextSize(toast);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) { return;}
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) { return;}
            tv.setTextSize(50);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
