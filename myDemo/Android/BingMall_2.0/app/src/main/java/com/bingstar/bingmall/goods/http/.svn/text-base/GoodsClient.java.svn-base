package com.bingstar.bingmall.goods.http;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.goods.bean.ProductDetailEntity;
import com.bingstar.bingmall.goods.bean.ProductInfo;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.net.NetResultCallback;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.bean.UserStates;
import com.yunzhi.lib.utils.LogUtils;

import java.util.Map;

import static com.bingstar.bingmall.goods.http.GoodsStates.PRODUCT_ID;
import static com.bingstar.bingmall.goods.http.GoodsStates.PRODUCT_ID_DOWN;
import static com.bingstar.bingmall.goods.http.GoodsStates.GOODS_ID;
/**
 * 功能：
 * Created by yaoyafeng on 17/2/9 下午2:12
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/9 下午2:12
 * @modify by reason:{方法名}:{原因}
 */
public class GoodsClient {

    //商品列表
    private static final String LIST_QUERY_METHOD = "product.list.query";
    private static final String LIST_QUERY_URL = "/productListQuery.shtml";
    //商品详情
    private static final String PRODUCT_DETAIL_METHOD = "product.detail";
    private static final String PRODUCT_DETAIL_URL = "/productDetail.shtml";
    //商品搜索
    private static final String PRODUCT_SEARCH_METHOD = "product.list.search";
    private static final String PRODUCT_SEARCH_URL = "/productListSearch.shtml";
    //商品是否已点赞
    private static final String SUPPORT_QUERY_METHOD = "support.member.query";
    private static final String SUPPORT_QUERY_URL = "/supportMemberQuery.shtml";
    //查询商品点赞数
    private static final String SUPPORT_METHOD = "support.query";
    private static final String SUPPORT_URL = "/supportQuery.shtml";
    //商品添加点赞
    private static final String SUPPORT_ADD_METHOD = "support.add";
    private static final String SUPPORT_ADD_URL = "/supportAdd.shtml";



    public static void getGoodsList(Map<String, String> params, final ClientCallback<ProductInfo.ProductInfors> callback) {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(LIST_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                LogUtils.Debug_E(GoodsClient.class, str);
                ProductInfo productInfo = JSON.parseObject(str, ProductInfo.class);
                if (productInfo != null) {
                    callback.onSuccess(productInfo.getProductInfo());
                } else {
                    callback.onFail(BingNetStates.NULL_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void getGoodsDetail(String goodsId, final ClientCallback<ProductDetailEntity> clientCallback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PRODUCT_ID, goodsId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, PRODUCT_DETAIL_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        BingstarNet.doPost(PRODUCT_DETAIL_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ProductDetailEntity detailEntity = JSON.parseObject(str,ProductDetailEntity.class);
                if (detailEntity!=null){
                    clientCallback.onSuccess(detailEntity);
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

    public static void searchGoodsList(Map<String, String> params, final ClientCallback<ProductInfo.ProductInfors> callback){
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, PRODUCT_SEARCH_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(PRODUCT_SEARCH_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ProductInfo productInfo = JSON.parseObject(str, ProductInfo.class);
                if (productInfo != null) {
                    callback.onSuccess(productInfo.getProductInfo());
                } else {
                    callback.onFail(BingNetStates.DATA_ERROR, "data error");
                }
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code, error);
            }
        });
    }

    public static void supportInfoQuery(Map<String, String> params, final ClientCallback<String> callback){
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, SUPPORT_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(params));
        BingstarNet.doPost(SUPPORT_QUERY_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                callback.onSuccess(str);
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }

    public static void supportCountQuery(String product_Id, final ClientCallback<String> callback){
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, SUPPORT_METHOD);
        map.put(BingNetStates.REQUEST_DATA, JSON.toJSONString(product_Id));
        BingstarNet.doPost(SUPPORT_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                callback.onSuccess(str);
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }

    public static void supportAdd(String productId,final  ClientCallback<String> callback){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PRODUCT_ID_DOWN,productId);
        jsonObject.put(UserStates.MEMBER_ID,User.getIntance().getMemberId());
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, SUPPORT_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        BingstarNet.doPost(SUPPORT_ADD_URL, map, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                callback.onSuccess(null);
            }

            @Override
            public void onFail(int code, String error) {
                callback.onFail(code,error);
            }
        });
    }

}
