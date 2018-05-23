package com.bingstar.bingmall.ads.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.ads.bean.AdsBeanTranslator;
import com.bingstar.bingmall.ads.bean.AdsInfosEntity;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/22 下午1:25
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/22 下午1:25
 * @modify by reason:{方法名}:{原因}
 */

public class AdsClient {

    private static final String AD_INFO_QUERY_METHOD = "ad.info.query";
    private static final String AD_INFO_QUERY_URL = "/adInfoQuery.shtml";

    public static void getAdsInfo(String data, final ClientCallback<AdsInfosEntity> clientCallback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, AD_INFO_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, data);
        BingstarNet.doPost(AD_INFO_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                AdsInfos adsInfos = JSON.parseObject(str, AdsInfos.class);
                if (adsInfos != null && adsInfos.getHpgProductList() != null && adsInfos.getHpgProductList() != null) {
                    AdsInfosEntity infosEntity = AdsBeanTranslator.adsBeanToEntity(adsInfos);
                    clientCallback.onSuccess(infosEntity);
                } else {
                    clientCallback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                LogUtils.Debug_E(AdsClient.class, code + error);
                clientCallback.onFail(code, error);
            }
        });
    }
}
