package com.bingstar.bingmall.collect;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.goods.bean.GoodsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/23 下午7:28
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/23 下午7:28
 * @modify by reason:{方法名}:{原因}
 */

public class ICollectionContract {
    interface CollectionListView extends IBaseView {
        void refreshAdapter(String categoryId);

        void notifyTitle();

        void notifyTitleRefresh();

        void listErr();
    }

    interface CollectionPresenter extends IBasePresenter {
        void getGoodsList(ArrayList<GoodsEntity> productLists, String categoryId);

        void getCollectTittle(List<CartTitle.CartCategoryInfo> cartTitleList, String memberId);

    }
}
