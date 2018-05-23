package com.bingstar.bingmall.cycle.view;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;

/**
 * Created by John on 2017/3/8.
 */

public interface ICycleDialogContract {
    interface CycleDialogView extends IBaseView{
        void setDialog(int type);
//        void error();
    }
    interface CycleDialogPresenter extends IBasePresenter{
        void createOrder(CycleProductInfo.CycleProduct.CycleProductInfoDetail cycleProductInfoDetail,
                         String totalprice, String totaldate, String totalnumber);
        void createQListOrder(CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail cycleProductInfoQDetail);
    }
}
