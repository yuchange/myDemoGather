package com.bingstar.bingmall.user.addr.AddrManage;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.addr.AddOrderAddr.AddAddrOrderClient;
import com.bingstar.bingmall.user.addr.AddrInfo;
import com.bingstar.bingmall.user.addr.AddrStates;
import com.bingstar.bingmall.user.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by John on 2017/3/6.
 */

public class AddrManagePresenter implements IAddrManageContract.IAddrManagePresenter {

    private IAddrManageContract.IAddrManageView iAddrManageView;

    public AddrManagePresenter(IAddrManageContract.IAddrManageView iAddrManageView) {
        this.iAddrManageView = iAddrManageView;
    }


    @Override
    public void unBind() {
        iAddrManageView = null;
    }

    @Override
    public void getHttpDate(String memeber_id, final List<AddrManageInfoBean.MemberAddress> addrManageInfoBeanList) {

        Map<String, String> map = new ArrayMap<>();
        map.put(AddrStates.MEMBER_ID, memeber_id);
        AddrManageClient.getAddrClient(map, new ClientCallback<AddrManageInfoBean>() {
            @Override
            public void onSuccess(AddrManageInfoBean addrManageInfoBean) {
                if (addrManageInfoBean != null) {
                    List<AddrManageInfoBean.MemberAddress> list = addrManageInfoBean.getMemberAddressList();
                    int size = list.size();
                    if (size == 0) {
                        setBufferAddr(null);
                    }
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getUsed().equals("1")) {
                            setBufferAddr(list.get(i));
                            break;
                        }
                        if (i == size - 1) {
                            setBufferAddr(list.get(0));
                        }
                    }
                    addrManageInfoBeanList.clear();
                    addrManageInfoBeanList.addAll(list);
                    if (iAddrManageView != null) {
                        iAddrManageView.notifyDatasetChange();
                    }
                }

            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.addrmanageFragment);
            }
        });
    }

    /**
     * 初始化User
     *
     * @param memeber_id
     */
    public static void getHttpDateUser(String memeber_id) {

        Map<String, String> map = new ArrayMap<>();
        map.put(AddrStates.MEMBER_ID, memeber_id);
        AddrManageClient.getAddrClient(map, new ClientCallback<AddrManageInfoBean>() {
            @Override
            public void onSuccess(AddrManageInfoBean addrManageInfoBean) {
                if (addrManageInfoBean != null) {
                    List<AddrManageInfoBean.MemberAddress> list = addrManageInfoBean.getMemberAddressList();
                    int size = list.size();
                    if (size == 0) {
                        setBufferAddr(null);
                    }
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getUsed().equals("1")) {
                            setBufferAddr(list.get(i));
                            break;
                        }
                        if (i == size - 1) {
                            setBufferAddr(list.get(0));
                        }
                    }
                }
            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.AdsFragment_final);
            }
        });
    }

    @Override
    public void getUpdateUsedHttp(AddrManageInfoBean.MemberAddress memberAddress, final List<AddrManageInfoBean.MemberAddress> memberAddressList, final int position) {
        Map<String, String> map = new ArrayMap<>();
        map.put(AddrStates.ADDRESSID, memberAddress.getAddressid());
        map.put(AddrStates.MEMBER_ID, memberAddress.getMember_id());
        map.put(AddrStates.USED, "1");//是否设为常用地址(设定值为：1)

        AddAddrOrderClient.getClientUsed(map, new ClientCallback<AddrInfo>() {
            @Override
            public void onSuccess(AddrInfo addrInfo) {
                ArrayList<AddrManageInfoBean.MemberAddress> list = new ArrayList<>();
                AddrManageInfoBean.MemberAddress memberAddress = memberAddressList.get(position);
                memberAddress.setUsed("1");
                //list.add(memberAddress);
                for (int i = 0; i < memberAddressList.size(); i++) {
                    if (i != position) {
                        AddrManageInfoBean.MemberAddress memberAddress1 = memberAddressList.get(i);
                        memberAddress1.setUsed("0");
                        list.add(memberAddress1);
                    } else {
                        list.add(memberAddress);
                    }
                }
                memberAddressList.clear();
                memberAddressList.addAll(list);
                /**
                 * 改变数据
                 */
                iAddrManageView.notifyDatasetChange();


                /**
                 * 维护User的数据
                 */
                setBufferAddr(memberAddress);

            }

            @Override
            public void onFail(int code, String error) {
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.addrmanageFragment);
            }
        });
    }

    private static void setBufferAddr(AddrManageInfoBean.MemberAddress memberAddress) {
        if (memberAddress == null) {
            User.getIntance().setAddressId("");
            User.getIntance().setAddress("");
            User.getIntance().setAreaId("");
            User.getIntance().setBuyer_IDcard("");
            User.getIntance().setMobile("");
            User.getIntance().setName("");
            User.getIntance().setRegion("");
        } else {
            User.getIntance().setAddressId(memberAddress.getAddressid());
            User.getIntance().setAddress(memberAddress.getDetailed());
            User.getIntance().setAreaId(memberAddress.getDetailed());
            User.getIntance().setBuyer_IDcard(memberAddress.getIdCard());
            User.getIntance().setMobile(memberAddress.getMobile());
            User.getIntance().setName(memberAddress.getName());
            User.getIntance().setRegion(memberAddress.getRegion());
        }

    }

}
