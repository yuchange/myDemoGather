package com.updayup.yuchance.mvcdemo.model;

import android.util.Log;

/**
 * Created by yuchance on 2017/12/23.
 */

public class LoginModel implements logininterface {
    @Override
    public void logininterface(String account, String password, LoginResultListener listener) {

        if (account.equals("haha")&&password.equals("123")){
            listener.successReslt();
        }else {
           listener.failedResult();
        }




    }
}
