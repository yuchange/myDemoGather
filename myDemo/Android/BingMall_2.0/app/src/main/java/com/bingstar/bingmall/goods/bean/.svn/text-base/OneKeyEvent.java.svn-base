package com.bingstar.bingmall.goods.bean;

import android.support.v4.app.DialogFragment;

/**
 * Created by zhangyifei on 17/7/4.
 */

public class OneKeyEvent {
    //一键下单 该商品只在购物车临时存在 离开页面删除该商品(目前只是做一下过滤)
    public String bsjProductId;
    public String from;
    public String count;
    private DialogFragment mFragment;

    public DialogFragment getFragment() {
        return mFragment;
    }

    public void setFragment( DialogFragment fragment) {
        mFragment = fragment;
    }

    public OneKeyEvent(String bsjProductId, String count, DialogFragment fragment) {
        this.bsjProductId = bsjProductId;
        this.count = count;
        mFragment = fragment;
    }

    public OneKeyEvent(String count, String from, String bsjProductId,  DialogFragment fragment) {
        this.count = count;
        this.from = from;
        this.bsjProductId = bsjProductId;
        mFragment = fragment;
    }

    public OneKeyEvent(String bsjProductId, String count) {
        this.bsjProductId = bsjProductId;
        this.count = count;
    }

    public OneKeyEvent(String bsjProductId, String count, String from) {
        this.bsjProductId = bsjProductId;
        this.count = count;
        this.from = from;
    }

    public String getBsjProductId() {
        return bsjProductId;
    }

    public void setBsjProductId(String bsjProductId) {
        this.bsjProductId = bsjProductId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
