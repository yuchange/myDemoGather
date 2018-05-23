package com.zyf.bings.bingos.brand.presenter;

import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.brand.bean.BrandDetailBean;

import java.util.List;

/**
 * Created by wangshiqi on 2017/9/12.
 */

public interface IBrandDetailContract {
    interface BrandDetailListView extends IBaseView {
        void refreshAdapter(List<BrandDetailBean.ProductInfoListBean> beanList);

        void listErr();
    }

    interface BrandPresenter extends IBasePresenter {
        void getBrandList(String pageSize, String currentPage, String brandId);
    }
}
