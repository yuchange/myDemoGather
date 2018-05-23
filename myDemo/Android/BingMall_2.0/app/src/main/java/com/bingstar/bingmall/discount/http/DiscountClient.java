package com.bingstar.bingmall.discount.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.discount.DiscountFragment;
import com.bingstar.bingmall.discount.bean.KidBaseInfo;
import com.bingstar.bingmall.discount.bean.KidModel;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/23 下午8:18
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/23 下午8:18
 * @modify by reason:{方法名}:{原因}
 */

public class DiscountClient {
    //套包列表
    private static String KID_LIST_QUERY_METHOD="kid.list.query";
    private static String KID_LIST_QUERY_URL="/kidListQuery.shtml";
    //套包详情
    private static String KID_INFO_QUERY_METHOD="kid.info.query";
    private static String KID_INFO_QUERY_URL="/kidInfoQuery.shtml";

    public static void getDisTitle(final ClientCallback<KidBaseInfo> callback){
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, KID_LIST_QUERY_METHOD);
        BingstarNet.doPost(KID_LIST_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
               //LogUtils.Debug_E(DiscountClient.class,str);
               KidBaseInfo kidBaseInfo = JSON.parseObject(str,KidBaseInfo.class);
                if(kidBaseInfo != null && kidBaseInfo.getKidList() != null){
                    callback.onSuccess(kidBaseInfo);
                }else{
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }

    public static void getKidInfo(Map<String,String> params, final ClientCallback<KidModel> callback){
        Map<String,String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD,KID_INFO_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA,JSON.toJSONString(params));
        BingstarNet.doPost(KID_INFO_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(DiscountClient.class,str);
                KidModel kidModel = JSON.parseObject(str,KidModel.class);
                if(kidModel!=null && kidModel.getKidCommodityList() != null){
                    callback.onSuccess(kidModel);
                }else{
                    callback.onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }
}
