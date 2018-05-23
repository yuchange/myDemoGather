package com.bingstar.bingmall.collect.http;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.goods.bean.GoodsEntity;
import com.bingstar.bingmall.goods.bean.ProductInfo;
import com.bingstar.bingmall.goods.bean.ProductInfos;
import com.bingstar.bingmall.goods.http.GoodsStates;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.bean.UserStates;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/23 下午3:07
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/23 下午3:07
 * @modify by reason:{方法名}:{原因}
 */

public class CollectClient {

    private final static String SUPPORT_LIST_QUERY_METHOD="support.list.query";
    private final static String SUPPORT_LIST_QUERY_URL="/supportListQuery.shtml";

    private static final String COLLECT_TITLE_METHOD ="shoppingCart.categoryInfo.query";
    private static final String COLLECT_TITLE_URL ="/shoppingCartCategoryInfoQuery.shtml";

    public static void getCollectList(String categoryId, final ClientCallback<ProductInfos> clientCallback){
        JSONObject jsonObject = new JSONObject();
        if (categoryId!=null&&!categoryId.equals("")){
            jsonObject.put(GoodsStates.CATEGORY_ID,categoryId);
        }
        jsonObject.put(UserStates.MEMBER_ID, User.getIntance().getMemberId());
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, SUPPORT_LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        BingstarNet.doPost2(SUPPORT_LIST_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ProductInfos productInfo = JSON.parseObject(str, ProductInfos.class);
                if (productInfo != null) {
                    clientCallback.onSuccess(productInfo);
                } else {
                    clientCallback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                clientCallback.onFail(code, error);
            }
        });
    }


    public static void getCartTitle(Map<String,String> params, final ClientCallback<CartTitle> callback){
        Map<String,String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD,COLLECT_TITLE_METHOD);
        map.put(BingNetStates.REQUEST_DATA,JSON.toJSONString(params));
        BingstarNet.doPost(COLLECT_TITLE_URL,map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                CartTitle cartTitle = JSON.parseObject(str,CartTitle.class);
                if(cartTitle!=null){
                    callback.onSuccess(cartTitle);
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }

}
