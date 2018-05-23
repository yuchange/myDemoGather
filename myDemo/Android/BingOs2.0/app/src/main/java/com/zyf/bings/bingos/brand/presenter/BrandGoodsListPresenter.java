package com.zyf.bings.bingos.brand.presenter;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.zyf.bings.bingos.brand.bean.BeanTranslater;
import com.zyf.bings.bingos.brand.bean.GoodsConfig;
import com.zyf.bings.bingos.brand.bean.GoodsEntity;
import com.zyf.bings.bingos.brand.bean.ProductInfo;
import com.zyf.bings.bingos.brand.bean.ProductInfoDetail;
import com.zyf.bings.bingos.brand.http.GoodsStates;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshiqi on 17/11/9
 *
 */
public class BrandGoodsListPresenter implements IBrandGoodsListContract.GoodsListPresenter {


    private IBrandGoodsListContract.GoodsListView goodsListView;

    public BrandGoodsListPresenter(IBrandGoodsListContract.GoodsListView goodsListView) {
        this.goodsListView = goodsListView;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        goodsListView = null;
    }

    //商品列表
    private static final String LIST_QUERY_METHOD = "product.list.query";
    private static final String LIST_QUERY_URL = "/productListQuery.shtml";
    @Override
    public void getGoodsList(ArrayList<GoodsEntity> productLists, String categoryId, String currentPage, String activeFlag, boolean isNewCategoryId) {
        Map<String, String> params = new ArrayMap<>();
        if (null != categoryId) {
            params.put(GoodsStates.CATEGORY_ID, categoryId);
        }
        params.put(GoodsStates.AREA_ID, "9");
        params.put(GoodsStates.PAGESIZE, GoodsConfig.goodsListLoadSize);
        params.put(GoodsStates.CURRENT_INDEX, currentPage);
        if (null != activeFlag) {
            params.put(GoodsStates.ACTIVE_FLAG, activeFlag);
        }
        if (currentPage.equals("0")) {
//            ToastUtils.showToast("正在加载");
        }
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, LIST_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, LIST_QUERY_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                ProductInfo productInfo = gson.fromJson(data, ProductInfo.class);
                if (productInfo != null) {
                    List<ProductInfoDetail> productInfoDetails = productInfo.getProductInfo().getProductInfoList();
                    if (productInfoDetails != null) {
                        if (isNewCategoryId) {
                            productLists.clear();
                        }
                        if (productInfoDetails.size() > 0) {
                            BeanTranslater.productToGoods(productLists, productInfoDetails);
                            if (goodsListView != null) {
                                goodsListView.refreshAdapter();
                            }
                        } else {
                            if (productLists.size() != 0) {
                                return;
                            }
                            if (goodsListView != null && !currentPage.equals("1")) {
                                goodsListView.listEmpty();
                            }
                        }
                    }
                }


            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    @Override
    public void seachGoods(ArrayList<GoodsEntity> productLists, String categoryId, String key_word, String currentPage) {

    }
}
