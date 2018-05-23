package com.zyf.bings.bingos_libnet;

import android.app.ProgressDialog;
import android.support.v4.util.ArrayMap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.progressDialog.MyProgressDialog;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.EncryptUtils;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.L;
import com.zyf.bings.bingos_libnet.utils.NetWorkUtils;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyifei on 17/9/3.
 */

public class RxOkClient {

    public static final String TAG = "RxOkClient";

    public static Observable<Response<String>> doPost(Map<String, String> map, String requestUrl, Object tag, final MyProgressDialog progressDialog) {

       /* if (!NetWorkUtils.isNetworkConnected(ApplicationHolder.instance)) {
            ToastUtils.showToast("网络不可用!");
        }*/

        if (requestUrl == null || requestUrl.trim().equals("") || map == null) {
            throw new IllegalArgumentException("url or map can not be null");
        }
        Map<String, String> mapData = new ArrayMap<>();
        String timeBIZService = TimeUtil.getTimeBIZService();
        mapData.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        mapData.put(BingNetStates.BIZ_SOURCE, "123456");
        mapData.put(BingNetStates.TIMESTAMP, timeBIZService);//时间戳
        mapData.put(BingNetStates.METHOD, map.get(BingNetStates.METHOD));
        if (map.get(BingNetStates.REQUEST_DATA) != null) {
            mapData.put(BingNetStates.REQUEST_DATA, map.get(BingNetStates.REQUEST_DATA));
        }
        mapData.put(BingNetStates.SIGN, getSign(mapData));
        //请求参数日志
        L.json(GsonFactory.getGson().toJson(mapData));
        L.t(TAG).e("RequestURL ==>> " + MainBuildConfigure.HOST_URL + requestUrl);
        return OkGo.<String>post(MainBuildConfigure.HOST_URL + requestUrl).tag(tag)
                .params(BingNetStates.CUSTOME_CODE, "bsj123456")
                .params(BingNetStates.BIZ_SOURCE, "123456")
                .params(BingNetStates.TIMESTAMP, timeBIZService)
                .params(BingNetStates.METHOD, map.get(BingNetStates.METHOD))
                .params(BingNetStates.REQUEST_DATA, map.get(BingNetStates.REQUEST_DATA))
                .params(BingNetStates.SIGN, mapData.get(BingNetStates.SIGN))
                .converter(new StringConvert())
                .adapt(new ObservableResponse<String>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        if (progressDialog != null && !progressDialog.isShowing())
                            progressDialog.show();
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Response<String>>() {
                    @Override
                    public void accept(@NonNull Response<String> response) throws Exception {
                        String data = response.body().toString();
                        //网络返回数据日志
                        L.json(data);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();

                        L.t(TAG).v(throwable);
                    }
                });


    }

    public static void cancelRequest(Object tag) {
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), tag);
    }


    //get请求无请体参数拼接在url后
    public static Observable<Response<String>> doGet(Map<String, String> map, String requestUrl, Object tag) {
        String params = "";
        if (map != null && map.size() != 0) {
            Set<String> key = map.keySet();
            String beginLetter = "?";
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                if (params.equals("")) {
                    params += beginLetter + s + "=" + map.get(s);
                } else {
                    params += "&" + s + "=" + map.get(s);
                }
            }
        }
        return OkGo.<String>get(MainBuildConfigure.HOST_URL + requestUrl + params)
                .tag(tag)
                .converter(new StringConvert())//
                .adapt(new ObservableResponse<String>())//
                .subscribeOn(Schedulers.io())//
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


    public static String getSign(Map<String, String> map) {
        String customCode = map.get(BingNetStates.CUSTOME_CODE);
        String bizSource = map.get(BingNetStates.BIZ_SOURCE);
        String timestamp = map.get(BingNetStates.TIMESTAMP);
        String method = map.get(BingNetStates.METHOD);
        String requestBizData = map.get(BingNetStates.REQUEST_DATA);
        if (customCode == null || bizSource == null || method == null) {
            return "";
        }
        if (requestBizData == null) {
            requestBizData = "";
        } else {
            requestBizData = "," + BingNetStates.REQUEST_DATA + ":" + requestBizData;
        }

        return EncryptUtils.getMd5(BingNetStates.CUSTOME_CODE + ":" + customCode + "," + BingNetStates.BIZ_SOURCE + ":" + bizSource + "," + BingNetStates.TIMESTAMP + ":" + timestamp
                + "," + BingNetStates.METHOD + ":" + method + requestBizData.trim());
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
}
