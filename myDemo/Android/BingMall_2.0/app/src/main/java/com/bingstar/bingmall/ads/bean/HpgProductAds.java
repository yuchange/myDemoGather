package com.bingstar.bingmall.ads.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/28 下午3:07
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/28 下午3:07
 * @modify by reason:{方法名}:{原因}
 */
public class HpgProductAds {

    public final static int STYLE_TWO = 0;

    public final static int STYLE_THREE = 1;

    private int pageStyle;//页面风格（2：分2块区域，3：分3块区域）

    private int position;//当前页面的第几屏广告

    private List<AdsInfoBean> adsInfoBeanList;

    public void setPageStyle(int pageStyle) {
        this.pageStyle = pageStyle;
    }

    public int getPageStyle() {
        return pageStyle;
    }

    public void setAdsInfoBeanList(List<AdsInfoBean> adsInfoBeanList) {
        this.adsInfoBeanList = adsInfoBeanList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<AdsInfoBean> getAdsInfoBeanList() {
        return adsInfoBeanList;
    }

    public AdsInfoBean getAds(int postion) {
        if (adsInfoBeanList != null) {
            for (AdsInfoBean bean : adsInfoBeanList) {
                if (bean.getNumId() == postion) {
                    return bean;
                }
            }
        }
        return new AdsInfoBean();
    }
}