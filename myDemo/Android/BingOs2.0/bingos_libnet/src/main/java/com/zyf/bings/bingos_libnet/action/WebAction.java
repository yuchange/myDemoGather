package com.zyf.bings.bingos_libnet.action;

import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;
import com.zyf.bings.bingos_libnet.bean.BaseResponse;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.constant.ConnectStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.L;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangyifei on 17/9/3.
 */

public abstract class WebAction implements Observer<Response<String>> {

    public static final int RX_ERR = 1314;

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Response<String> response) {
        if (response == null) {
            onFailed(ConnectStates.NET_ERROR, "数据集为空");
            return;
        }
        if (response.code() == 200) {
            String data = response.body().toString();
            BaseResponse baseResponse = GsonFactory.fromJson(data, BaseResponse.class);
            if (baseResponse.getReturnCode().equals("0000000")) {
                String responseBizData = baseResponse.getResponseBizData();
                onSuccess(responseBizData);
            } else {
                String errorMsg = baseResponse.getErrDesc();
                if (errorMsg == null) {
                    errorMsg = baseResponse.getReturnMsg();
                }
                onFailed(parseErrorCode(baseResponse.getReturnCode()), errorMsg);
            }

        } else {
            String errorMsg;
            Throwable throwable = response.getException();
            if (throwable instanceof IOException) {
                errorMsg = "请检查网络状态";
            } else if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                errorMsg = httpException.response().message();
            } else if (throwable != null) {
                errorMsg = throwable.getMessage();
            } else {
                errorMsg = "网络异常";
            }
            onFailed(response.code(), errorMsg);

        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailed(RX_ERR, e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    private static int parseErrorCode(String code) {
        switch (code) {
            case "0000001":             //订单重复
                return BingNetStates.ORDER_EXIST;
            case "0000002":             //验证订单失败
                return BingNetStates.ORDER_VERIFY_FAIL;
            case "0000010":             //激活用户重复
                return BingNetStates.USER_HAS_ACTIVATED;
            case "0000012":             //用户不能重复领取优惠券
                return BingNetStates.COUPON_HAS_GET;
            case "0000013":             //领取优惠券失败
                return BingNetStates.GET_COUPON_FAIL;
            case "0000014":             //用户激活失败
                return BingNetStates.USER_ACTIVATE_FAIL;
            case "0000015":             //签约用户失败
                return BingNetStates.CONSTRACT_FAIL;
            case "1111111":             //系统错误
                return BingNetStates.SYSTEM_ERROR;
            case "9999999":             //Json数据错误
                return BingNetStates.JSON_DATA_ERROR;
            default:
                return BingNetStates.UNKNOW_ERROR;
        }

    }


    //状态等于200
    public abstract void onSuccess(String data);

    //状态不等于200
    public abstract void onFailed(int code, String msg);
}
