package com.zyf.bings.bingos.pay;


import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos.pay.bean.HaierPayInfo;


/**
 * Created by zhangyifei on 17/9/20.
 */

public class IPayContract {

    interface PayView extends IBaseView {
        void notifyPayInfo(HaierPayInfo haierPayInfo);
        void getPayInfoError(String msg);
        void paySuccess(String msg);
        void payError(String msg,int type);
    }

    interface PayPresenter extends IBasePresenter {
        void getPayInfo(String zorder_no);

        void getOrderStatus(String zorder_no);
    }
}
