package com.zyf.bings.bingos_libnet;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zyf.bings.bingos_libnet.bean.BingResponse;
import com.zyf.bings.bingos_libnet.bean.LoginStateBean;
import com.zyf.bings.bingos_libnet.callback.LoginResultCallback;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.constant.ConnectStates;
import com.zyf.bings.bingos_libnet.utils.EncryptUtils;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.L;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangyifei on 17/9/1.
 */

public class OkGoUtils {


    public static void doStringPostRequest(Map<String, String> map, String url, String tag, final NetResultCallback netResultCallback) {

        if (url == null || url.trim().equals("") || map == null) {
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
        Log.e("OkGoUtils", "requestUrl>>>>>" + MainBuildConfigure.HOST_URL + url);
        OkGo.<String>post(MainBuildConfigure.HOST_URL + url).tag(tag).
                params(BingNetStates.CUSTOME_CODE, "bsj123456").
                params(BingNetStates.BIZ_SOURCE, "123456").
                params(BingNetStates.TIMESTAMP, timeBIZService).
                params(BingNetStates.METHOD, map.get(BingNetStates.METHOD)).
                params(BingNetStates.REQUEST_DATA, map.get(BingNetStates.REQUEST_DATA)).
                params(BingNetStates.SIGN, mapData.get(BingNetStates.SIGN)).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        L.json(response.body().toString());
                        if (response == null) {
                            netResultCallback.onFail(ConnectStates.NET_ERROR, "");
                            return;
                        }
                        if (response.code() == 200) {

                            String data = response.body().toString();
                            L.json(data);
                            BingResponse bingResponse = GsonFactory.fromJson(data, BingResponse.class);
                            Log.i("OkGoUtils", "bingResponse" + bingResponse);
                            Log.i("OkGoUtils", "data" + data.length());
                            if (netResultCallback == null) {
                                return;
                            }
                            if ("0000000".equals(bingResponse.getReturnCode()) && bingResponse.getResponseBizData() != null) {
                                netResultCallback.onSuccess(bingResponse.getResponseBizData().toString());
                                Log.e("OkGoUtils", "responseData>>>>" + bingResponse.getResponseBizData().toString());
                            } else if (bingResponse.getReturnCode().equals("0000000") && bingResponse.getResponseBizData() == null) {
                                netResultCallback.onSuccess("");
                            } else {
                                String errorMsg = bingResponse.getErrDesc();
                                if (errorMsg == null) {
                                    errorMsg = bingResponse.getReturnMsg();
                                }
                                netResultCallback.onFail(parseErrorCode(bingResponse.getReturnCode()), errorMsg);
                            }

                        } else {
                            netResultCallback.onFail(response.code(), response.code() + "");


                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("OkGoUtils", "  code" + response.code() + response.getException().getMessage());
                        netResultCallback.onFail(response.code(), response.getException().getMessage());
                    }
                });
        L.json(GsonFactory.getGson().toJson(mapData));
    }


    //get请求无情体参数拼接在url后
    public static void doStringGetRequest(String baseUrl, Map<String, String> map, String tag, final NetResultCallback netResultCallback) {
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
        Log.e("OkGoUtils", "requestUrl>>>>>" + baseUrl + params);
        OkGo.<String>get(baseUrl + params).tag(tag).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                L.json(response.body().toString());
                if (response.code() == 200) {
                    String data = response.body().toString();
                    L.json(data);
                    BingResponse bingResponse = new Gson().fromJson(data, BingResponse.class);
                    Log.i("OkGoUtils", "bingResponse" + bingResponse);
                    Log.i("OkGoUtils", "data" + data.length());
                    if (netResultCallback == null) {
                        return;
                    }
                    if (response == null) {
                        netResultCallback.onFail(ConnectStates.NET_ERROR, "");
                        return;
                    }
                    if (bingResponse.getReturnCode().equals("0000000")) {
                        netResultCallback.onSuccess(bingResponse.getResponseBizData().toString());
                        Log.e("OkGoUtils", "responseData>>>>" + bingResponse.getResponseBizData().toString());
                    } else {
                        String errorMsg = bingResponse.getErrDesc();
                        if (errorMsg == null) {
                            errorMsg = bingResponse.getReturnMsg();
                        }
                        netResultCallback.onFail(parseErrorCode(bingResponse.getReturnCode()), errorMsg);
                    }

                } else {
                    netResultCallback.onFail(response.code(), response.code() + "");


                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                netResultCallback.onFail(response.code(), response.getException().getMessage());

            }
        });

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


    public static void pollingLoginStates(String baseUrl, String tag, Map<String, String> map, final LoginResultCallback loginResultCallback) {


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

        Log.e("OkGoUtils", "url" + baseUrl + params);

        OkGo.<String>get(baseUrl + params).tag(tag).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                if (response == null) {
                    loginResultCallback.onFail(ConnectStates.NET_ERROR, "");
                    return;
                }
                if (response.code() == 200) {
                    String data = response.body().toString();
                    Log.e("OkGoUtils", "data" + data);
                    LoginStateBean loginStateBean = new Gson().fromJson(data, LoginStateBean.class);
                    if (loginResultCallback == null) {
                        return;
                    }

                    if (loginStateBean.getReturnCode().equals("0000000")) {
                        loginResultCallback.onSuccess(loginStateBean.getResponseBizData());
                        Log.e("OkGO", data);
                        Log.e("OkGO", "SIZE" + loginStateBean.getResponseBizData().size());
                    } else {

                        String errorMsg = loginStateBean.getReturnMsg();

                        loginResultCallback.onFail(parseErrorCode(loginStateBean.getReturnCode()), errorMsg);
                    }

                } else {

                    loginResultCallback.onFail(response.code(), response.code() + "");


                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("OkGO", MainBuildConfigure.HOST_URL + baseUrl);
                loginResultCallback.onFail(response.code(), response.getException().getMessage());

            }
        });

    }


}
