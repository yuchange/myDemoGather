package com.bingstar.bingmall.collect;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.collect.http.CollectClient;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.goods.bean.GoodsEntity;
import com.bingstar.bingmall.goods.bean.ProductInfo;
import com.bingstar.bingmall.goods.bean.ProductInfoDetail;
import com.bingstar.bingmall.goods.bean.ProductInfos;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/23 下午7:29
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/23 下午7:29
 * @modify by reason:{方法名}:{原因}
 */

public class CollectionPresenter implements ICollectionContract.CollectionPresenter {

    private ICollectionContract.CollectionListView collectionListView;

    public CollectionPresenter(ICollectionContract.CollectionListView collectionListView) {
        this.collectionListView = collectionListView;
    }

    @Override
    public void getGoodsList(final ArrayList<GoodsEntity> productLists, final String categoryId) {
        CollectClient.getCollectList(categoryId, new ClientCallback<ProductInfos>() {
            @Override
            public void onSuccess(ProductInfos productInfors) {
                List<ProductInfoDetail> productInfoDetails = productInfors.getProductInfoList();
                productLists.clear();
                BeanTranslater.productToGoods(productLists, productInfoDetails);
                if (collectionListView != null) {
                    collectionListView.refreshAdapter(categoryId);
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (collectionListView != null) {
                    collectionListView.listErr();
                }
            }
        });
    }

    @Override
    public void getCollectTittle(final List<CartTitle.CartCategoryInfo> cartTitleList, String memberId) {
        Map<String, String> map = new ArrayMap<>();
        map.put(CartStates.MEMBERID, User.getIntance().getMemberId());
        map.put(CartStates.SOURCE, "1");
        CartClient.getCartTitle(map, new ClientCallback<CartTitle>() {
            @Override
            public void onSuccess(CartTitle cartTitle) {
                cartTitleList.clear();
                CartTitle cart = new CartTitle();
                CartTitle.CartCategoryInfo cartCategoryInfo = cart.new CartCategoryInfo();
                cartCategoryInfo.setCategoryId("");
                cartCategoryInfo.setCategoryName("全部");
                cartCategoryInfo.setCategoryOrder("");
                cartTitleList.add(cartCategoryInfo);
                for (int i = 0; i < cartTitle.getCategoryList().size(); i++) {
                    cartTitleList.add(cartTitle.getCategoryList().get(i));
                }
                if (collectionListView != null) {
                    collectionListView.notifyTitle();
                }
                if (collectionListView != null) {
                    collectionListView.notifyTitleRefresh();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (collectionListView != null) {
                    collectionListView.listErr();
                }
            }
        });
    }


    @Override
    public void unBind() {
        collectionListView = null;
    }
}
