package com.bingstar.bingmall.statistics;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.NetResultCallback;


import java.util.Map;

/**
 * Created by zhangyifei on 17/7/5.
 */

public class StatClient {
    //商品点击统计
    private static final String GOODS_CLICK = "/goodsClickCount.shtml";
    private static final String GOODS_CLICK_COUNT = "goods.click.count";


    //商品UV统计
    private static final String GOODS_UVCLICK = "/goodsUvCount.shtml";
    private static final String GOODS_UVCLICK_COUNT = "goods.uv.count";

    //上传统计
    public static void upLoadClickCount(String goodsId, String goodsName) {
        if (goodsId == null) {
            return;
        }
        upLoadGoodsClick(goodsId);
        if (goodsName == null) {
            return;
        }
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        arrayMap.put(StatStates.GOODS_ID, goodsId);
        arrayMap.put(StatStates.GOODS_NAME, goodsName);
        arrayMap.put(StatStates.MAC_ID, Util.getWifiMac());
        upLoadGoodsUVClick(arrayMap);

    }

    /**
     * 商品点击统计
     */
    public static void upLoadGoodsClick(String goodsId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(StatStates.GOODS_ID, goodsId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, GOODS_CLICK_COUNT);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        BingstarNet.doPost2(GOODS_CLICK, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onFail(int code, String error) {
            }
        });
    }

    /**
     * 商品UV统计
     *
     * @param params
     * @param
     */
    public static void upLoadGoodsUVClick(Map<String, String> params) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, GOODS_UVCLICK_COUNT);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost2(GOODS_UVCLICK, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onFail(int code, String error) {
            }
        });
    }


}
