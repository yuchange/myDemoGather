package com.bingstar.bingmall.ads.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/22 上午9:48
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/22 上午9:48
 * @modify by reason:{方法名}:{原因}
 */

public class AdsInfosEntity {

    private ArrayList<AdsInfoBean> bannerList;

    private AdsInfoBean kid;

    private AdsInfoBean cycle;

    private ArrayList<HpgProductAds> hpgProductAd;

    public ArrayList<AdsInfoBean> getBannerList() {
        return bannerList;
    }

    public AdsInfoBean getKid() {
        return kid;
    }

    public AdsInfoBean getCycle() {
        return cycle;
    }

    public ArrayList<HpgProductAds> getHpgProductAd() {
        return hpgProductAd;
    }

    public void setBannerList(ArrayList<AdsInfoBean> bannerList) {
        this.bannerList = bannerList;
    }

    public void setKid(AdsInfoBean kid) {
        this.kid = kid;
    }

    public void setCycle(AdsInfoBean cycle) {
        this.cycle = cycle;
    }

    public void setHpgProductAd(ArrayList<HpgProductAds> hpgProductAd) {
        this.hpgProductAd = hpgProductAd;
    }

    public void sortList(){
        Comparator<AdsInfoBean> comp = new SortComparator();
        Collections.sort(bannerList,comp);
    }



    private class SortComparator implements Comparator<AdsInfoBean> {
        @Override
        public int compare(AdsInfoBean o1, AdsInfoBean o2) {
            return (o1.getNumId() - o2.getNumId());
        }
    }

}
