package com.zyf.bings.bingos.address;


import android.content.Context;

import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;
import com.zyf.bings.bingos_libnet.progressDialog.MyProgressDialog;

import java.util.List;

/**
 * Created by qianhechen on 17/2/13.
 */

public interface IAddressContract {

    interface AddressView extends IBaseView {
        void notifyAddressRefresh(List<AddressList.MemberAddressListBean> addressListBeen);

        void deleteSuccess();

        void deleteError(String msg);

        void onErr(String errMsg);

        Context getViewContext();

    }

    interface AddressPresenter extends IBasePresenter {
        void getAddressList(MyProgressDialog progressDialog);

        void deleteAddress(String addressIds);
    }
}
