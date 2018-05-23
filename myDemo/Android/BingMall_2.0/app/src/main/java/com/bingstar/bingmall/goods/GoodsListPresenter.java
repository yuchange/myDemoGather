package com.bingstar.bingmall.goods;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.goods.bean.GoodsConfig;
import com.bingstar.bingmall.goods.bean.GoodsEntity;
import com.bingstar.bingmall.goods.bean.ProductInfo;
import com.bingstar.bingmall.goods.bean.ProductInfoDetail;
import com.bingstar.bingmall.goods.http.GoodsClient;
import com.bingstar.bingmall.goods.http.GoodsStates;
import com.bingstar.bingmall.goods.view.GoodsSearchDialog;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.utils.LogUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/12 下午7:46
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/12 下午7:46
 * @modify by reason:{方法名}:{原因}
 */
public class GoodsListPresenter implements IGoodsListContract.GoodsListPresenter {

    private IGoodsListContract.GoodsListView goodsListView;

    public GoodsListPresenter(IGoodsListContract.GoodsListView goodsListView) {
        this.goodsListView = goodsListView;
    }


    @Override
    public void unBind() {
        goodsListView = null;
    }

    @Override
    public void getGoodsList(final ArrayList<GoodsEntity> productLists, String categoryId, final String currentPage, String activeFlag, final boolean isNewCateGoryId) {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        if (null != categoryId) {
            arrayMap.put(GoodsStates.CATEGORY_ID, categoryId);
        }
        arrayMap.put(GoodsStates.AREA_ID, "9");
        arrayMap.put(GoodsStates.PAGESIZE, GoodsConfig.goodsListLoadSize);
        arrayMap.put(GoodsStates.CURRENT_INDEX, currentPage);
        if (null != activeFlag) {
            arrayMap.put(GoodsStates.ACTIVE_FLAG, activeFlag);
        }
        if (currentPage.equals("0")) {
            goodsListView.showView(SynLinearLayout.SHOW_LOAD);
        }
        GoodsClient.getGoodsList(arrayMap, new ClientCallback<ProductInfo.ProductInfors>() {
            @Override
            public void onSuccess(ProductInfo.ProductInfors productInfors) {
                goodsListView.showView(SynLinearLayout.SHOW_SUCCESS);
                List<ProductInfoDetail> productInfoDetails = productInfors.getProductInfoList();
                if (productInfoDetails != null) {
                    if (isNewCateGoryId) {
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
                        if (goodsListView != null && currentPage.equals("0")) {
                            goodsListView.showView(SynLinearLayout.SHOW_EMPTY);
                        }
                    }
                } else {
                    onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (code > 1000) {
                    if (goodsListView != null) {
                        goodsListView.showToast(error);
                    }
                }
                if (goodsListView != null) {
                    goodsListView.showView(SynLinearLayout.SHOW_ERROR);
                }

            }
        });
    }

    @Override
    public void seachGoods(final ArrayList<GoodsEntity> productLists, String categoryId, String key_word, final String currentPage) {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        arrayMap.put(GoodsStates.CATEGORY_ID, categoryId);
        if (key_word != null && !key_word.equals("")) {
            arrayMap.put(GoodsStates.KEY_WORD, key_word);
        }
        arrayMap.put(GoodsStates.PAGESIZE, GoodsConfig.goodsListLoadSize);
        arrayMap.put(GoodsStates.CURRENT_INDEX, currentPage);
        if (currentPage.equals("0")) {
            goodsListView.showView(SynLinearLayout.SHOW_LOAD);
        }
        GoodsClient.searchGoodsList(arrayMap, new ClientCallback<ProductInfo.ProductInfors>() {
            @Override
            public void onSuccess(ProductInfo.ProductInfors productInfors) {
                goodsListView.showView(SynLinearLayout.SHOW_SUCCESS);
                List<ProductInfoDetail> productInfoDetails = productInfors.getProductInfoList();
                if (currentPage.equals("0")) {
                    productLists.clear();
                }
                if (productInfoDetails != null && productInfoDetails.size() > 0) {
                    BeanTranslater.productToGoods(productLists, productInfoDetails);
                } else {
                    if (productLists.size() != 0) {
                        return;
                    }
                    if (currentPage.equals("0")) {
                        goodsListView.showView(SynLinearLayout.SHOW_EMPTY);
                    }
                }
                if (goodsListView != null) {
                    goodsListView.refreshAdapter();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (code > 1000) {
                    if (goodsListView != null) {
                        goodsListView.showToast(error);
                    }
                } else {
                    if (goodsListView != null) {
                        goodsListView.showView(SynLinearLayout.SHOW_ERROR);
                    }
                }
            }
        });
    }


}
