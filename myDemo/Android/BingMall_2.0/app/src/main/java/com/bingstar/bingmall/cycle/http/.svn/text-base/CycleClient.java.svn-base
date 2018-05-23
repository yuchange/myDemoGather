package com.bingstar.bingmall.cycle.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.net.BingNetStates;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * Created by qianhechen on 17/2/14.
 */

public class CycleClient {

    private static final String LIST_TITLE= "/cycleProductListQuery.shtml";
    private static final String LIST_QUERY_METHOD = "cycleProduct.list.query";

    private static final String QLIST_TITLE= "/cycleProductQuickListQuery.shtml";
    private static final String QLIST_QUERY_METHOD = "cycleProduct.quickList.query";


    public static void getCycleProductList(Map<String,String> params, final ClientCallback<CycleProductInfo.CycleProduct> callback) {
        Map<String,String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD,LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(LIST_TITLE,map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_I(CycleClient.class,str);
                CycleProductInfo cycleProductInfo = JSON.parseObject(str,CycleProductInfo.class);
                if(cycleProductInfo != null){
                    callback.onSuccess(cycleProductInfo.getCycleProductList().get(0));
                }else{
                    callback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                    callback.onFail(code, error);
            }
        });
    }

    public static void getCycleProductQList(Map<String,String> params, final ClientCallback<CycleProductQInfo.CycleProductQ> callback) {
        Map<String,String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD,QLIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(QLIST_TITLE,map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_I(CycleClient.class,str);
                CycleProductQInfo cycleProductQInfo = JSON.parseObject(str,CycleProductQInfo.class);
                if(cycleProductQInfo != null){
                    callback.onSuccess(cycleProductQInfo.getCycleProductQList().get(0));
                }else{
                    callback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    

}
