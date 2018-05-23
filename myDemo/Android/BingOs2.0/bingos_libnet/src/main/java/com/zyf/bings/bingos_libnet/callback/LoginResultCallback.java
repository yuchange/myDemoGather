package com.zyf.bings.bingos_libnet.callback;

import com.zyf.bings.bingos_libnet.bean.LoginStateBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public interface LoginResultCallback {

    void onSuccess(List<LoginStateBean.ResponseBizDataBean> responseBizDataBeanList);
    void onFail(int code, String error);
}
