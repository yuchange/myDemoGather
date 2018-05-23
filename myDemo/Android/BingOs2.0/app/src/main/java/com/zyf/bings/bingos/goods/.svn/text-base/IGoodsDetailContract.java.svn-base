package com.zyf.bings.bingos.goods;

import android.content.Context;

import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.goods.bean.GoodsDetailBean;

/**
 * Created by zhangyifei on 17/9/7.
 */

public interface IGoodsDetailContract {

    interface GoodsDetailView extends IBaseView {
        void notifyGoodsDetailList(GoodsDetailBean goodsDetailBean);

        void listErr(String errMsg);

        void addCartMsg(String msg);

        void increaseMsg(String msg);

        void decreaseMsg(String msg);

        Context getViewContext();

    }

    interface GoodsDetailPresenter extends IBasePresenter {
        void getGoodsDetailList(String goodsId);

        void increaseCount();

        void decreaseCount();

        void addCart(GoodsDetailBean goodsDetailBean, String spec);
    }
}
