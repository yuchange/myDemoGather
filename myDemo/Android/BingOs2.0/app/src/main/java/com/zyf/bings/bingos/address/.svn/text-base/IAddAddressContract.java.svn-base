package com.zyf.bings.bingos.address;

import android.content.Context;

import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.address.bean.CityJsonBean;
import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.base.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyifei on 17/9/5.
 */

public interface IAddAddressContract {
    interface AddAddressView extends IBaseView {
        void insertSuccess();

        void updateSuccess();

        void showCityPicker(ArrayList<CityJsonBean> options1Items, ArrayList<ArrayList<String>> options2Items,
                            ArrayList<ArrayList<ArrayList<String>>> options3Items);

        Context getViewContext();

    }


    interface AddAddressPresenter extends IBasePresenter {
        void insertAddress(String member_id, String name,
                           String detailed, String mobile, String regoin, String postcode);

        void updateAddress(String addressId, String member_id, String name,
                           String detailed, String mobile, String regoin, String postcode);

        void handleCityPicker();
    }
}
