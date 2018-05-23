package com.bingstar.bingmall.user.addr.AddrManage;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;

import java.util.List;

/**
 * Created by John on 2017/3/6.
 */

public interface IAddrManageContract {
    interface IAddrManageView extends IBaseView{
        void notifyDatasetChange();
        void error();
    }
    interface IAddrManagePresenter extends IBasePresenter{
        void getHttpDate(String memeber_id, List<AddrManageInfoBean.MemberAddress> list);
        void getUpdateUsedHttp(AddrManageInfoBean.MemberAddress memberAddress, List<AddrManageInfoBean.MemberAddress> memberAddressList, int position);
    }
}
