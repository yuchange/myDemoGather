package com.bingstar.bingmall.ads.bean;

import android.text.TextUtils;

import com.bingstar.bingmall.ads.http.AdsInfos;
import com.bingstar.bingmall.ads.http.HpgProduct;
import com.bingstar.bingmall.base.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/6 下午4:10
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/6 下午4:10
 * @modify by reason:{方法名}:{原因}
 */

public class AdsBeanTranslator {

    public static AdsInfosEntity adsBeanToEntity(AdsInfos adsInfos) {
        if (!Util.checkNotNull(adsInfos)) {
            return null;
        }
        AdsInfosEntity adsInfosEntity = new AdsInfosEntity();
        AdsInfos.Cycle cycle = adsInfos.getCycle();
        if (Util.checkNotNull(cycle)) {
            adsInfosEntity.setCycle(new AdsInfoBean(cycle.getHome_id(), cycle.getImgUrl(), -1));
        } else {
            adsInfosEntity.setCycle(new AdsInfoBean());
        }
        AdsInfos.Kid kid = adsInfos.getKid();
        if (Util.checkNotNull(kid)) {
            adsInfosEntity.setKid(new AdsInfoBean(kid.getHome_id(), kid.getImgUrl(), -1));
        } else {
            adsInfosEntity.setKid(new AdsInfoBean());
        }
        List<AdsInfos.Banner> bannerList = adsInfos.getBannerList();
        ArrayList<AdsInfoBean> bannerAdsList = new ArrayList<>();
        if (Util.checkNotNull(bannerList)) {
            for (AdsInfos.Banner banner : bannerList) {
                AdsInfoBean bannerAds = new AdsInfoBean();
                int numId;
                try {
                    numId = Integer.parseInt(banner.getNum_id());
                } catch (Exception e) {
                    numId = 0;
                }
                bannerAds.setNumId(numId);
                bannerAds.setHomeId(banner.getHome_id());
                if (!TextUtils.isEmpty(banner.getCouponIds())) {
                    bannerAds.setCouponIds(banner.getCouponIds());
                }
                if (Util.checkNotNull(banner.getVideoUrl()) && !banner.getVideoUrl().equals("")) {
                    bannerAds.setUrl(banner.getVideoUrl());
                    bannerAds.setUrlType(AdsInfoBean.TYPE_VIDEO);
                    bannerAds.setClickContent(banner.getVideoUrl());

                    bannerAds.setVideoImgUrl(banner.getImgUrl());
                    if (banner.getGoods_id() != null && !banner.getGoods_id().equals("")) {
                        bannerAds.setClickType(AdsInfoBean.TYPE_GOODS);
                        bannerAds.setClickContent(banner.getGoods_id());
                    }
                } else {
                    bannerAds.setUrl(banner.getImgUrl());
                    bannerAds.setUrlType(AdsInfoBean.TYPE_IMG);
                    if (banner.getGoods_id() != null && !banner.getGoods_id().equals("")) {
                        bannerAds.setClickType(AdsInfoBean.TYPE_GOODS);
                        bannerAds.setClickContent(banner.getGoods_id());
                    } else if (banner.getFileUrl() != null && !banner.getFileUrl().equals("")) {
                        bannerAds.setClickType(AdsInfoBean.TYPE_WEB);
                        bannerAds.setClickContent(banner.getFileUrl());
                    } else {
                        bannerAds.setClickType(AdsInfoBean.TYPE_UNKNOW);
                    }
                }
                bannerAdsList.add(bannerAds);
            }
        }
        adsInfosEntity.setBannerList(bannerAdsList);
        ArrayList<HpgProductAds> productList = new ArrayList<>();
        List<HpgProduct> hpgProductList = adsInfos.getHpgProductList();
        if (Util.checkNotNull(hpgProductList)) {
            for (int i = 0, size = hpgProductList.size(); i < size; i++) {
                HpgProduct hpgProduct = hpgProductList.get(i);
                String style = hpgProduct.getPageStyle();
                int numId;
                try {
                    numId = Integer.parseInt(hpgProduct.getNum_id());
                    if (numId == 2) {
                        if (style.equals("2")) {
                            numId = 3;
                        }
                    }
                } catch (Exception e) {
                    numId = 0;
                }
                AdsInfoBean adsInfoBean = new AdsInfoBean(hpgProduct.getHome_id(), hpgProduct.getImgUrl(), numId);
                if (hpgProduct.getGoods_id() != null && !hpgProduct.getGoods_id().equals("")) {
                    adsInfoBean.setClickType(AdsInfoBean.TYPE_GOODS);
                    adsInfoBean.setClickContent(hpgProduct.getGoods_id());
                } else if (hpgProduct.getFileUrl() != null && !hpgProduct.getFileUrl().equals("")) {
                    adsInfoBean.setClickType(AdsInfoBean.TYPE_WEB);
                    adsInfoBean.setClickContent(hpgProduct.getFileUrl());
                } else {
                    adsInfoBean.setClickType(AdsInfoBean.TYPE_UNKNOW);
                }
                int position;
                try {
                    position = Integer.parseInt(hpgProduct.getPosition_id());
                } catch (Exception e) {
                    position = 0;
                }
                HpgProductAds productAds = null;
                for (HpgProductAds hpgProductAds : productList) {
                    if (hpgProductAds.getPosition() == position) {
                        productAds = hpgProductAds;
                    }
                }
                if (productAds == null) {
                    productAds = new HpgProductAds();
                    if (style.equals("2")) {
                        productAds.setPageStyle(HpgProductAds.STYLE_TWO);
                    } else {
                        productAds.setPageStyle(HpgProductAds.STYLE_THREE);
                    }
                    productAds.setPosition(position);
                    ArrayList<AdsInfoBean> infoBeen = new ArrayList<>();
                    infoBeen.add(adsInfoBean);
                    productAds.setAdsInfoBeanList(infoBeen);
                    productList.add(productAds);
                } else {
                    productAds.getAdsInfoBeanList().add(adsInfoBean);
                }
            }
        }
        adsInfosEntity.setHpgProductAd(productList);
        return adsInfosEntity;
    }
}
