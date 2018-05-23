package com.bingstar.bingmall.ads.view;

import android.view.View;

import com.bingstar.bingmall.ads.bean.AdsInfoBean;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/1 下午1:27
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/1 下午1:27
 * @modify by reason:{方法名}:{原因}
 */

public interface OnAdsItemClickListener {
    void onItemClick(View view, AdsInfoBean infoBean);
}
