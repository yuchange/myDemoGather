package com.bingstar.bingmall.user.addr.AddOrderAddr;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.addr.AddrStates;

import java.util.Map;

/**
 * Created by liumengqiang on 2017/2/27.
 */

public class AddAddrPresenter implements IAddOrderAddrContract.IAddAddrPresenter {
    private IAddOrderAddrContract.IAddAddrView iAddAddrView;

    public AddAddrPresenter(IAddOrderAddrContract.IAddAddrView iAddAddrView) {
        this.iAddAddrView = iAddAddrView;
    }

    @Override
    public void getAddHttp(String member_id, String name, String cardID, String mobile, String regoin, String detailed) {
        Map<String, String> map = new ArrayMap<>();
        map.put(AddrStates.MEMBER_ID, member_id);
        map.put(AddrStates.NAME, name);
        map.put(AddrStates.IDCARD, cardID);
        map.put(AddrStates.DETAILED, detailed);
        map.put(AddrStates.MOBILE, mobile);
        map.put(AddrStates.REGION, regoin);

        AddAddrOrderClient.getClient(map, new ClientCallback<AddAddrInfo>() {
            @Override
            public void onSuccess(AddAddrInfo addAddrInfo) {
//                LogUtils.Debug_E(AddAddrPresenter.class, );
                iAddAddrView.setUI();
//                org.greenrobot.eventbus.EventBus.getDefault().postSticky(AddAddrOrderClient.class);
            }

            @Override
            public void onFail(int code, String error) {
                iAddAddrView.failedSaveAdress();
            }
        });
    }

    @Override
    public void getAddHttpUpdate(String addressid, String member_id, String name, String cardID, String mobile, String regoin, String detailed) {
        Map<String, String> map = new ArrayMap<>();
        map.put(AddrStates.ADDRESSID, addressid);
        map.put(AddrStates.MEMBER_ID, member_id);
        map.put(AddrStates.NAME, name);
        map.put(AddrStates.IDCARD, cardID);
        map.put(AddrStates.DETAILED, detailed);
        map.put(AddrStates.MOBILE, mobile);
        map.put(AddrStates.REGION, regoin);

        AddAddrOrderClient.getClientUpdate(map, new ClientCallback<AddAddrInfo>() {
            @Override
            public void onSuccess(AddAddrInfo addAddrInfo) {
                iAddAddrView.setUI();
            }

            @Override
            public void onFail(int code, String error) {
                iAddAddrView.failedSaveAdress();
            }
        });
    }

    @Override
    public void unBind() {
        iAddAddrView = null;
    }

//    private IAddOrderAddrContract.IAddAddrView iAddAddrView;
//
//    public AddAddrPresenter(IAddOrderAddrContract.IAddAddrView iAddAddrView) {
//        this.iAddAddrView = iAddAddrView;
//    }
//
//
//    /**
//     * 初始化三个Spinner
//     */
//    @Override
//    public void getDate() {
//        List<String> privanceList = new ArrayList<>();
//        List<String> cityList = new ArrayList<>();
//        List<String> areaList = new ArrayList<>();
//        String cityStringJSON = SharedPreferencesUtils.getCITYString(iAddAddrView.setContext());
//        AddrInfo addrInfo = JSON.parseObject(cityStringJSON,AddrInfo.class);
//        for(int i = 0;i < addrInfo.getProcList().size();i++){
//            privanceList.add(addrInfo.getProcList().get(i).getName());
//        }
//        for(int i= 0 ;i < addrInfo.getProcList().get(0).getCityList().size() ;i++){
//            cityList.add(addrInfo.getProcList().get(0).getCityList().get(i).getName());
//        }
//        List<AddrInfo.ProcListBean.CityListBean.AreaListBean> areaListBean = addrInfo.getProcList().get(0).getCityList().get(0).getAreaList();
//        for(int i= 0 ;i < areaListBean.size();i++){
//            areaList.add(areaListBean.get(i).getName());
//        }
//        iAddAddrView.setPrivanceSpinner(privanceList);
//        iAddAddrView.setCitySpinner(cityList);
//        iAddAddrView.setAreaSpinner(areaList);
//    }
//
//    /**
//     * 设置城市和地区
//     * @param privancePosition 省份索引
//     */
//    @Override
//    public void getCityDate(int privancePosition) {
//        List<String> cityList = new ArrayList<>();
//        List<String> areaList = new ArrayList<>();
//        String cityStringJSON = SharedPreferencesUtils.getCITYString(iAddAddrView.setContext());
//        AddrInfo addrInfo = JSON.parseObject(cityStringJSON,AddrInfo.class);
//        for(int i= 0 ;i < addrInfo.getProcList().get(privancePosition).getCityList().size() ;i++){
//            cityList.add(addrInfo.getProcList().get(privancePosition).getCityList().get(i).getName());
//        }
//        List<AddrInfo.ProcListBean.CityListBean.AreaListBean> areaListBean = addrInfo.getProcList().get(0).getCityList().get(0).getAreaList();
//        for(int i= 0 ;i < areaListBean.size();i++){
//            areaList.add(areaListBean.get(i).getName());
//        }
//        iAddAddrView.setCitySpinner(cityList);
//        iAddAddrView.setAreaSpinner(areaList);
//    }
//
//    /**
//     * 设置地区
//     * @param privancePosition 省份索引
//     * @param cityPosition 城市索引
//     */
//    @Override
//    public void getAreaDate(int privancePosition, int cityPosition) {
//
//        List<String> areaList = new ArrayList<>();
//        String cityStringJSON = SharedPreferencesUtils.getCITYString(iAddAddrView.setContext());
//        AddrInfo addrInfo = JSON.parseObject(cityStringJSON,AddrInfo.class);
//        List<AddrInfo.ProcListBean.CityListBean.AreaListBean> areaListBean = addrInfo.getProcList().get(privancePosition).getCityList().get(cityPosition).getAreaList();
//        for(int i= 0 ;i < areaListBean.size();i++){
//            areaList.add(areaListBean.get(i).getName());
//        }
//        iAddAddrView.setAreaSpinner(areaList);
//    }
//
//    @Override
//    public void unBind() {
//        iAddAddrView = null;
//    }
}
