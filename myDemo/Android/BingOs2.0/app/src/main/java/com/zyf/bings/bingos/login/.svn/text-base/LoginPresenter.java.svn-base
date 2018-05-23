package com.zyf.bings.bingos.login;


import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.address.http.AddressStates;
import com.zyf.bings.bingos.business.UserBusiness;
import com.zyf.bings.bingos.event.LoginSuccessEvent;
import com.zyf.bings.bingos.login.http.LoginClient;
import com.zyf.bings.bingos.login.view.ISweepCodeFragment;
import com.zyf.bings.bingos.login.view.SweepCodeFragment;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.bean.LoginStateBean;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/25.
 */

public class LoginPresenter implements ILoginPresenter {


    private static final String LOGIN_STATES = "http://hz.bingstar.com.cn/bingstar/weixin/getMember.shtml";
    private ISweepCodeFragment mISweepCodeFragment;
    private final LoginClient mLoginClient;

    public LoginPresenter(ISweepCodeFragment ISweepCodeFragment) {
        mISweepCodeFragment = ISweepCodeFragment;
        mLoginClient = new LoginClient(this);
    }


    @Override
    public void sweepCodeLogin() {
        mLoginClient.getLoginStates();

    }

    @Override
    public void loginSuccess(List<LoginStateBean.ResponseBizDataBean> responseBizDataBeanList) {//登录成功
        SweepCodeFragment iSweepCodeFragment = (SweepCodeFragment) mISweepCodeFragment;
        //放入首选项
        String member_id = responseBizDataBeanList.get(0).getMember_id();
        Log.e("LoginPresenter", "loginSuccess: 用户ID   " + member_id);
        Log.e("LoginPresenter", "集合的size   " + responseBizDataBeanList.size());
        SPUtil.put(iSweepCodeFragment.getActivity().getApplicationContext(), Config.MEMBER_ID, member_id);
        getUserData(member_id);
        EventBus.getDefault().post(new LoginSuccessEvent());//发送登录成功的回调
        mISweepCodeFragment.loginSuccess();


    }

    /**
     * 登录成功获取用户信息
     */
    private static final String TITLE = "/memberAddressListQuery.shtml";
    private static final String METHOD = "memberAddress.list.query";

    private void getUserData(String memberId) {
        Map<String, String> requestMap = new ArrayMap<>();
        requestMap.put(AddressStates.MEMBER_ID, memberId);
        requestMap.put(AddressStates.MAC_ID, CommonUtils.getWifiMac());
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(requestMap));
        RxOkClient.doPost(map, TITLE, this, null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        AddressList addressList = GsonFactory.fromJson(data, AddressList.class);
                        List<AddressList.MemberAddressListBean> addressListBean = addressList.getMemberAddressList();

                        for (int i = 0; i < addressListBean.size(); i++) {
                            AddressList.MemberAddressListBean memberAddressListBean = addressListBean.get(i);
                            if (memberAddressListBean.getUsed().equals("1")) {
                                UserBusiness.saveAddressData(memberAddressListBean);
                                break;
                            }
                            UserBusiness.saveAddressData(addressListBean.get(0));
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                    }
                });
    }


    @Override
    public void loginFilaed(int code,String error) {
        SweepCodeFragment iSweepCodeFragment = (SweepCodeFragment) mISweepCodeFragment;

        mISweepCodeFragment.loginFailed(code,error);


    }

    @Override
    public void unBind() {
        mISweepCodeFragment = null;
        mLoginClient.unBlind();

    }
}
