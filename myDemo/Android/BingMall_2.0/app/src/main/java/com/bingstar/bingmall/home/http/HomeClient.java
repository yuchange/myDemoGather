package com.bingstar.bingmall.home.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.net.BingNetStates;


import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 下午2:53
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 下午2:53
 * @modify by reason:{方法名}:{原因}
 */
public class HomeClient {

    private static final String TITLE = "/categoryInfoQuery.shtml";
    private static final String TITLE_METHOD = "category.info.query";


    public static void getTitleList(final ClientCallback<TitleListInfo> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, TITLE_METHOD);
        BingstarNet.doPost(TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                TitleListInfo listInfo = JSON.parseObject(str,TitleListInfo.class);
                if (listInfo!=null&&listInfo.getCategoryList()!=null) {
                    callback.onSuccess(listInfo);
                }else {
                    callback.onFail(BingNetStates.DATA_ERROR,"data error");
                }
            }


            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }


}
