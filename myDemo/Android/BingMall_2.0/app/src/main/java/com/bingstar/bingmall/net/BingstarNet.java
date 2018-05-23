package com.bingstar.bingmall.net;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.main.MainBuildConfigure;
import com.yunzhi.lib.net.ConnectStates;
import com.yunzhi.lib.net.IHttpCallback;
import com.yunzhi.lib.net.IHttpManager;
import com.yunzhi.lib.net.OkHttpManager;
import com.yunzhi.lib.utils.EncryptUtils;
import com.yunzhi.lib.utils.L;
import com.yunzhi.lib.utils.LogUtils;
import com.yunzhi.lib.utils.Util;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/6 下午3:40
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/6 下午3:40
 * @modify by reason:{方法名}:{原因}
 */
public class BingstarNet {

    //  public static final String IMG_SERVICE = "http://139.224.192.179:8088/bingstar";
//  public static final String IMG_SERVICE = "http://139.224.33.12:8088/bingstar";
//    public static final String IMG_SERVICE = "http://mall.bingstar.com.cn/";
    public static final String IMG_SERVICE = MainBuildConfigure.getInstance().getService();
    private static final String BING_SERVICE = IMG_SERVICE + "/if/haiEr";
    public static final String HTML_URL = MainBuildConfigure.getInstance().getHtmlUrl();

    private static long sysTime = 0;

    private static String cacheUrl = "";

    /**
     * @param url
     * @param map
     * @param callback
     */
    public static void doPost(final String url, Map<String, String> map, final NetResultCallback callback) {
        if (url == null || url.trim().equals("") || map == null) {
            throw new IllegalArgumentException("url or map can not be null");
        }
        if (url.equals(cacheUrl) && sysTime != 0 && System.currentTimeMillis() - sysTime < 500) {
            return;
        }
        cacheUrl = url;
        sysTime = System.currentTimeMillis();
        Map<String, String> mapData = new ArrayMap<>();
        mapData.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        mapData.put(BingNetStates.BIZ_SOURCE, "123456");
        mapData.put(BingNetStates.TIMESTAMP, Util.getTimeBIZService());
        mapData.put(BingNetStates.METHOD, map.get(BingNetStates.METHOD));
        if (map.get(BingNetStates.REQUEST_DATA) != null) {
            mapData.put(BingNetStates.REQUEST_DATA, map.get(BingNetStates.REQUEST_DATA));
        }
        mapData.put(BingNetStates.SIGN, getSign(mapData));
        //BING_SERVICE + url
        //LogUtils.Debug_E(BingstarNet.class, mapData.toString());
        L.json(JSON.toJSONString(mapData));
        Log.e("BingstarNet", "doPost:requestURL  ===   " + BING_SERVICE + url);
        IHttpManager httpManager = OkHttpManager.getInstance();
        httpManager.doPost(BING_SERVICE + url, mapData, new IHttpCallback() {
            @Override
            public void onSuccess(String resultStr) {

                //LogUtils.Debug_E(BingstarNet.class, "response:" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                jsonObject.put("requestURL==>>  ", BING_SERVICE + url);
                L.json(jsonObject.toJSONString());
                String result = (String) JSONObject.toJSON(resultStr);
                BingResponse response = JSON.parseObject(result, BingResponse.class);
                if (callback == null) {
                    return;
                }
                if (response == null) {
                    callback.onFail(ConnectStates.NET_ERROR, "");
                    return;
                }
                if (response.getReturnCode().equals("0000000")) {
                    callback.onSuccess(response.getResponseBizData());
                } else {
                    String errorMsg = response.getErrDesc();
                    if (errorMsg == null) {
                        errorMsg = response.getReturnMsg();
                    }
                    callback.onFail(parseErrorCode(response.getReturnCode()), errorMsg);
                }
            }

            @Override
            public void onFail(int code, String message) {
                LogUtils.Debug_E(BingstarNet.class, "code:" + code + "---" + message + "---" + url);
                if (callback != null) {
                    callback.onFail(code, message);
                }
            }
        });
    }


    /**
     * @param url
     * @param map
     * @param callback 对于特殊情况 不做缓存 连续请求
     */
    public static void doPost2(final String url, Map<String, String> map, final NetResultCallback callback) {
        if (url == null || url.trim().equals("") || map == null) {
            throw new IllegalArgumentException("url or map can not be null");
        }
        Map<String, String> mapData = new ArrayMap<>();
        mapData.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        mapData.put(BingNetStates.BIZ_SOURCE, "123456");
        mapData.put(BingNetStates.TIMESTAMP, Util.getTimeBIZService());
        mapData.put(BingNetStates.METHOD, map.get(BingNetStates.METHOD));
        if (map.get(BingNetStates.REQUEST_DATA) != null) {
            mapData.put(BingNetStates.REQUEST_DATA, map.get(BingNetStates.REQUEST_DATA));
        }
        mapData.put(BingNetStates.SIGN, getSign(mapData));
        L.json(JSON.toJSONString(mapData));
        IHttpManager httpManager = OkHttpManager.getInstance();
        // BING_SERVICE + url
        Log.e("BingstarNet", "http://192.168.1.134:7089/bingstar");
        httpManager.doPost(BING_SERVICE + url, mapData, new IHttpCallback() {
            @Override
            public void onSuccess(String resultStr) {

                JSONObject jsonObject = JSON.parseObject(resultStr);
                jsonObject.put("requestURL==>>  ", BING_SERVICE + url);
                L.json(jsonObject.toJSONString());
                BingResponse response = JSON.parseObject(resultStr, BingResponse.class);
                if (callback == null) {
                    return;
                }
                if (response == null) {
                    callback.onFail(ConnectStates.NET_ERROR, "");
                    return;
                }
                if (response.getReturnCode().equals("0000000")) {
                    callback.onSuccess(response.getResponseBizData());
                } else {
                    String errorMsg = response.getErrDesc();
                    if (errorMsg == null) {
                        errorMsg = response.getReturnMsg();
                    }
                    callback.onFail(parseErrorCode(response.getReturnCode()), errorMsg);
                }
            }

            @Override
            public void onFail(int code, String message) {
                LogUtils.Debug_E(BingstarNet.class, "code:" + code + "---" + message + "---" + url);
                if (callback != null) {
                    callback.onFail(code, message);
                }
            }
        });
    }

    private static String getSign(Map<String, String> map) {
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
        return EncryptUtils.MD5(BingNetStates.CUSTOME_CODE + ":" + customCode + "," + BingNetStates.BIZ_SOURCE + ":" + bizSource + "," + BingNetStates.TIMESTAMP + ":" + timestamp
                + "," + BingNetStates.METHOD + ":" + method + requestBizData);
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
