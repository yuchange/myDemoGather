package com.bingstar.bingmall.ads;

import android.view.View;

import com.bingstar.bingmall.ads.bean.AdsInfoBean;
import com.bingstar.bingmall.ads.bean.HpgProductAds;
import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 下午6:14
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 下午6:14
 * @modify by reason:{方法名}:{原因}
 */
public interface IAdsContract {

    interface AdsView extends IBaseView {
        void setChildView(AdsInfoBean kid, AdsInfoBean cycle);
        void refreshPager();
        void listErr();
    }

    interface AdsPresenter extends IBasePresenter {
        void getAds(List<View> viewList);

        void getAdsInfo(List<AdsInfoBean> list, List<HpgProductAds> hpgProductList);
    }
}
