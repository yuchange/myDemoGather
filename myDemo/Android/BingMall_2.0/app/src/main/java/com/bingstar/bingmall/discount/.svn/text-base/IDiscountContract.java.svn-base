package com.bingstar.bingmall.discount;

import android.support.annotation.StringRes;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;
import com.bingstar.bingmall.discount.bean.Category;
import com.bingstar.bingmall.discount.bean.KidBaseInfo;
import com.bingstar.bingmall.discount.bean.KidModel;
import com.bingstar.bingmall.discount.bean.KidProductInfo;
import com.bingstar.bingmall.discount.bean.KidShowInfo;
import com.bingstar.bingmall.goods.bean.ProductInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/23 下午8:07
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/23 下午8:07
 * @modify by reason:{方法名}:{原因}
 */

public interface IDiscountContract {
    interface DiscountView extends IBaseView {
        /**
         * 通知内容更新
         */
        void notisfyImg();

        /**
         * 通知标题栏刷新
         */
        void notifyTitleRefresh();

        /**
         * 跳转到error页面
         */
        void listErr();
    }

    interface DiscountPresenter extends IBasePresenter {

        /**
         *
         * @param timeList
         */
        void getKidList(List<KidShowInfo> timeList);

        /**
         *
         * @param kidId
         * @param kidTime
         * @param kidCommodityInfoArrayList
         */
        void getKidInfo(String kidId, String kidTime, ArrayList<KidProductInfo> kidCommodityInfoArrayList);

        /**
         * 加入购物车
         * @param kidProductInfo 商品信息
         */
        void addToCart(KidProductInfo kidProductInfo);
    }
}
