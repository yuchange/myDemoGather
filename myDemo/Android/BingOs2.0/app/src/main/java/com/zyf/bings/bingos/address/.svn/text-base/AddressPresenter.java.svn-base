package com.zyf.bings.bingos.address;

import android.support.v4.util.ArrayMap;

import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.address.http.AddressStates;
import com.zyf.bings.bingos.business.UserBusiness;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.progressDialog.MyProgressDialog;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyifei on 17/9/5.
 */

public class AddressPresenter implements IAddressContract.AddressPresenter {

    IAddressContract.AddressView mAddressView;
    String mMemberId;

    public AddressPresenter(IAddressContract.AddressView addressView) {
        mAddressView = addressView;
        mMemberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mAddressView != null) mAddressView = null;
    }

    private static final String TITLE = "/addressListQuery.shtml";
    private static final String METHOD = "address.list.query";

    @Override
    public void getAddressList(MyProgressDialog progressDialog) {
        Map<String, String> requestMap = new ArrayMap<>();
        requestMap.put(AddressStates.MEMBER_ID, mMemberId);
        requestMap.put(AddressStates.MAC_ID, CommonUtils.getWifiMac());
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(requestMap));
        RxOkClient.doPost(map, TITLE, this, progressDialog)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        AddressList addressList = GsonFactory.fromJson(data, AddressList.class);
                        List<AddressList.MemberAddressListBean> addressListBean = addressList.getMemberAddressList();
                        if (mAddressView != null) {
                            mAddressView.notifyAddressRefresh(addressListBean);
                        }
                        if (addressListBean.size() > 0) {
                            for (int i = 0; i < addressListBean.size(); i++) {
                                AddressList.MemberAddressListBean memberAddressListBean = addressListBean.get(i);
                                if (memberAddressListBean.getUsed().equals("1")) {
                                    UserBusiness.saveAddressData(memberAddressListBean);
                                    break;
                                }
                                UserBusiness.saveAddressData(addressListBean.get(0));
                            }
                        } else {
                            UserBusiness.removeUserInfo();
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {

                        if (mAddressView != null) mAddressView.onErr(msg);
                    }
                });
    }

    //删除地址

    private final static String TITLE_DELETE = "/memberAddressDelete.shtml";
    private final static String METHOD_DELETE = "memberAddress.delete";

    @Override
    public void deleteAddress(String addressIds) {
        Map<String, String> params = new ArrayMap<String, String>();
        params.put(AddressStates.ADDRESSID, addressIds);
        params.put(AddressStates.MEMBER_ID, mMemberId);
        params.put(AddressStates.MAC_ID, CommonUtils.getWifiMac());

        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD_DELETE);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(params));
        RxOkClient.doPost(map, TITLE_DELETE, getClass().getSimpleName(), PBUtil.getPD(mAddressView.getViewContext()))
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mAddressView != null) {
                            mAddressView.deleteSuccess();
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mAddressView != null) mAddressView.deleteError(msg);
                    }
                });
    }
}
