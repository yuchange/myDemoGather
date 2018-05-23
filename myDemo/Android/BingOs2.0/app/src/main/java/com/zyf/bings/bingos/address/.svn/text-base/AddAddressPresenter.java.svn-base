package com.zyf.bings.bingos.address;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.zyf.bings.bingos.address.bean.CityJsonBean;
import com.zyf.bings.bingos.address.http.AddressStates;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyifei on 17/9/5.
 */

public class AddAddressPresenter implements IAddAddressContract.AddAddressPresenter {

    IAddAddressContract.AddAddressView mAddAddressView;

    public AddAddressPresenter(IAddAddressContract.AddAddressView addAddressView) {
        mAddAddressView = addAddressView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mAddAddressView != null) mAddAddressView = null;
        //RxOkClient.cancelRequest(getClass().getSimpleName());
    }

    /**
     * 新增地址
     */
    private static final String TITLE = "/memberAddressAdd.shtml";
    private static final String METHOD = "memberAddress.add";

    @Override
    public void insertAddress(String member_id, String name,
                              String detailed, String mobile, String regoin, String postcode) {

        Map<String, String> params = new ArrayMap<>();
        params.put(AddressStates.MEMBER_ID, member_id);
        params.put(AddressStates.NAME, name);
        params.put(AddressStates.DETAILED, detailed);
        params.put(AddressStates.MOBILE, mobile);
        params.put(AddressStates.REGION, regoin);
        params.put(AddressStates.POSTCODE, postcode);

        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));

        RxOkClient.doPost(map, TITLE, getClass().getSimpleName(), null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mAddAddressView != null) mAddAddressView.insertSuccess();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showToast(msg);
                    }
                });
    }

    /**
     * 修改地址
     */
    private final static String TITLE_UPDATE = "/memberAddressUpdate.shtml";
    private final static String METHOD_UPDATE = "memberAddress.update";

    @Override
    public void updateAddress(String addressid, String member_id, String name,
                              String detailed, String mobile, String regoin, String postcode) {
        Map<String, String> params = new ArrayMap<>();
        params.put(AddressStates.ADDRESSID, addressid);
        params.put(AddressStates.MEMBER_ID, member_id);
        params.put(AddressStates.NAME, name);
        params.put(AddressStates.DETAILED, detailed);
        params.put(AddressStates.MOBILE, mobile);
        params.put(AddressStates.REGION, regoin);
        params.put(AddressStates.POSTCODE, postcode);

        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, METHOD_UPDATE);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));

        RxOkClient.doPost(map, TITLE_UPDATE, getClass().getSimpleName(), null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mAddAddressView != null) mAddAddressView.updateSuccess();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showToast(msg);
                    }
                });
    }

    @Override
    public void handleCityPicker() {

        ArrayList<CityJsonBean> options1Items = new ArrayList<>();
        ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

        String JsonData = new CommonUtils().getCityJson(ApplicationHolder.instance, "province.json");//获取assets目录下的json文件数据

        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        if (mAddAddressView != null)
            mAddAddressView.showCityPicker(options1Items, options2Items, options3Items);

    }


    public ArrayList<CityJsonBean> parseData(String result) {//Gson 解析
        ArrayList<CityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
