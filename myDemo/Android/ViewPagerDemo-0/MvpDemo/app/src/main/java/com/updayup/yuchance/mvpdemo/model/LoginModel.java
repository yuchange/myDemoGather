package com.updayup.yuchance.mvpdemo.model;

import java.util.logging.Handler;

/**
 * Created by yuchance on 2017/12/24.
 */

public class LoginModel implements LoginModelInterface{


    @Override
    public void LoginModel(final String account, final String password, final LoginModelListener listener) {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("haha".equals(account)&&"123".equals(password)){
                    listener.success();
                }else {
                    listener.failed();
                }

            }
        },2000);

    }
}
