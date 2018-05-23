package com.bingstar.bingmall.user.addr.AddrManage;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.user.addr.AddrClient;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * Created by John on 2017/3/6.
 */

public class AddrManageClient  {
    private static final String TITLE = "/memberAddressListQuery.shtml";
    private static final String METHOD = "memberAddress.list.query";
    public static void getAddrClient(Map<String, String > stringMap, final ClientCallback<AddrManageInfoBean> clientCallback){
        Map<String, String > map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(stringMap));
        BingstarNet.doPost(TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                AddrManageInfoBean addrManageInfoBean = JSON.parseObject(str, AddrManageInfoBean.class);
                if(addrManageInfoBean == null){
                    clientCallback.onFail(BingstarErrorParser.DATA_ERROR,"data_error");
                }
                if(addrManageInfoBean != null && addrManageInfoBean.getMemberAddressList() != null){
                    clientCallback.onSuccess(addrManageInfoBean);
                }
            }

            @Override
            public void onFail(int code, String error) {
                clientCallback.onFail(code, error);
            }
        });
    }
}
