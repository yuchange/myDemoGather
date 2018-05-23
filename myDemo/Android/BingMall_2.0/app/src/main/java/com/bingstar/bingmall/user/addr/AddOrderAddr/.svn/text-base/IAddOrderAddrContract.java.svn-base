package com.bingstar.bingmall.user.addr.AddOrderAddr;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;

/**
 * Created by liumengqiang on 2017/2/27.
 */

public interface IAddOrderAddrContract {
    interface  IAddAddrView extends IBaseView{
        void setPresenter();
        void setUI();
        void failedSaveAdress();
    }
    interface  IAddAddrPresenter extends  IBasePresenter{
        void getAddHttp(String member_id, String name, String cardID , String mobile, String regoin, String detailed);
        void getAddHttpUpdate(String addressid,String member_id, String name, String cardID , String mobile, String regoin, String detailed );
    }
//    interface IAddAddrView extends IBaseView{
//        void setPresenter();
//        void setPrivanceSpinner(List<String> privanceList);
//        void setCitySpinner(List<String> cityList);
//        void setAreaSpinner(List<String> areaList);
//        Context setContext();
//    }
//    interface  IAddAddrPresenter extends IBasePresenter{
//        void getDate();
//        void getCityDate(int privancePosition);
//        void getAreaDate(int privancePosition, int cityPosition);
//    }
}
