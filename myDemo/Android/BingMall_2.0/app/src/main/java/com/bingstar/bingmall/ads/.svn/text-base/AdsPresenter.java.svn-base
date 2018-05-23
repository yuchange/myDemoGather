package com.bingstar.bingmall.ads;

import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.ads.bean.AdsInfoBean;
import com.bingstar.bingmall.ads.bean.AdsInfosEntity;
import com.bingstar.bingmall.ads.bean.HpgProductAds;
import com.bingstar.bingmall.ads.http.AdsInfos;
import com.bingstar.bingmall.ads.http.HpgProduct;
import com.bingstar.bingmall.ads.http.AdsClient;
import com.bingstar.bingmall.ads.http.AdsStates;
import com.bingstar.bingmall.net.ClientCallback;
import com.yunzhi.lib.utils.L;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 下午6:13
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 下午6:13
 * @modify by reason:{方法名}:{原因}
 */
public class AdsPresenter implements IAdsContract.AdsPresenter {

    private IAdsContract.AdsView adsView;

    public AdsPresenter(IAdsContract.AdsView adsView) {
        this.adsView = adsView;
    }

    @Override
    public void unBind() {
        adsView = null;
    }

    @Override
    public void getAds(final List<View> viewList) {

    }

    @Override
    public void getAdsInfo(final List<AdsInfoBean> bannerList, final List<HpgProductAds> hpgProductList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AdsStates.HOMEPAGE_ID, AdsStates.HOMEPAGE_ID_STATE);
        AdsClient.getAdsInfo(jsonObject.toJSONString(), new ClientCallback<AdsInfosEntity>() {
            @Override
            public void onSuccess(AdsInfosEntity adsInfos) {
                bannerList.clear();
                adsInfos.sortList();
                bannerList.addAll(adsInfos.getBannerList());
                filterVideo(bannerList);
                if (adsView != null) {
                    adsView.setChildView(adsInfos.getKid(), adsInfos.getCycle());
                }
                ArrayList<HpgProductAds> productAdsList = adsInfos.getHpgProductAd();
                hpgProductList.clear();
                hpgProductList.addAll(productAdsList);
                if (adsView != null) {
                    adsView.refreshPager();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (adsView != null) {
                    adsView.listErr();
                }
            }
        });
    }

    //目前不支持视频
    private void filterVideo(List<AdsInfoBean> bannerList) {
        List<AdsInfoBean> adsInfoBeanList = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            if (bannerList.get(i).getUrlType() != AdsInfoBean.TYPE_VIDEO) {
                adsInfoBeanList.add(bannerList.get(i));
            }
        }
        bannerList.clear();
        bannerList.addAll(adsInfoBeanList);
    }

    private void parseNetError() {

    }
}
