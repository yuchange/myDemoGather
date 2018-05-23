package com.bingstar.bingmall.cycle;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/2/14.
 */

public interface ICycleContract {

    interface CycleListView{
        void setPresenter();

        void notifyQList(int type);
        void notifyList(int type);

//        void error();
    }

    interface CyclePresenter extends IBasePresenter{

        void getCycleProductList(ArrayList<CycleProductInfo.CycleProduct.CycleProductInfoDetail> cycleProductInfoList,String categoryId);

        void getCycleProductQList(ArrayList<CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail> cycleProductInfoQList,String categoryId);

    }
}
