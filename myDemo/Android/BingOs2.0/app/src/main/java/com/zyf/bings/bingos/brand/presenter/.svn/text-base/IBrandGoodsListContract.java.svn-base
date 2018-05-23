package com.zyf.bings.bingos.brand.presenter;


import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.brand.bean.GoodsEntity;

import java.util.ArrayList;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/12 下午7:51
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/12 下午7:51
 * @modify by reason:{方法名}:{原因}
 */
public interface IBrandGoodsListContract {

    interface GoodsListView extends IBaseView {
        void refreshAdapter();

        void listErr();

        void listEmpty();

        void showView(int kind);
    }

    interface GoodsListPresenter extends IBasePresenter {
        void getGoodsList(ArrayList<GoodsEntity> productLists, String categoryId, String currentPage, String activeFlag, boolean isNewCategoryId);

        void seachGoods(ArrayList<GoodsEntity> productLists, String categoryId, String key_word, String currentPage);
    }
}
