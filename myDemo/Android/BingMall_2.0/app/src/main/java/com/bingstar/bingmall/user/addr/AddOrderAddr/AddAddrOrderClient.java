package com.bingstar.bingmall.user.addr.AddOrderAddr;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.user.addr.AddrInfo;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

/**
 * Created by John on 2017/3/6.
 */

public class AddAddrOrderClient {
    /**
     * 新增地址
     */
    private static final String TITLE = "/memberAddressAdd.shtml";
    private static final String METHOD = "memberAddress.add";

    public static void getClient(Map<String, String > params, final ClientCallback<AddAddrInfo> clientCallback){

        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));

        BingstarNet.doPost(TITLE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
//                LogUtils.Debug_E(AddAddrOrderClient.class, "===============" + str);
                clientCallback.onSuccess(new AddAddrInfo());
            }

            @Override
            public void onFail(int code, String error) {
//                LogUtils.Debug_E(AddAddrOrderClient.class, "===============+" );
            }
        });
    }

    private final static String TITLE_UPDATE = "/memberAddressUpdate.shtml";
    private final static String METHOD_UPDATE = "memberAddress.update";

    public static void getClientUpdate(Map<String,String> params, final ClientCallback<AddAddrInfo>  clientCallback){
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD_UPDATE);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));

        BingstarNet.doPost(TITLE_UPDATE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
//
                clientCallback.onSuccess(new AddAddrInfo());
            }

            @Override
            public void onFail(int code, String error) {
//                LogUtils.Debug_E(AddAddrOrderClient.class, "===============");
            }
        });
    }

    private final static String TITLE_DELETE = "/memberAddressDelete.shtml";
    private final static String METHOD_DELETE = "memberAddress.delete";

    public static void getClientDELETE(Map<String,String > params, final ClientCallback<AddAddrInfo> clientCallback){
        Map<String,String > map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD_DELETE);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(TITLE_DELETE, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                clientCallback.onSuccess(new AddAddrInfo());
            }

            @Override
            public void onFail(int code, String error) {
                clientCallback.onFail(code, error);
            }
        });
    }

    /**
     * 修改默认地址
     */
    private final static String TITLE_SET = "/memberAddressUpdateUsed.shtml";
    private final static String METHOD_SET = "memberAddress.updateUsed";

    public static void getClientUsed(Map<String,String> params, final ClientCallback<AddrInfo> clientCallback){
        Map<String, String> map = new ArrayMap<String, String>();
        map.put(BingNetStates.METHOD, METHOD_SET);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));

        BingstarNet.doPost(TITLE_SET, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                clientCallback.onSuccess(new AddrInfo());
            }

            @Override
            public void onFail(int code, String error) {
                clientCallback.onFail(code, error);
            }
        });
    }
}
