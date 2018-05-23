package com.zyf.bings.bingos.brand.presenter;

import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.brand.bean.HotListBean;

import java.util.List;

/**
 * Created wangshiqi sd on 2017/9/11.
 */

public interface IBrandShopListContract {
    interface BrandShopListView extends IBaseView {
        void refreshAdapter(List<HotListBean.BrandListBean> beanList);

    }

    interface BrandPresenter extends IBasePresenter {
        void getBrandList(String pageSize, String currentPage);
    }
}
