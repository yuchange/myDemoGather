package com.allure.mvc.model;

import android.os.Handler;
import android.util.Log;

/**
 * 作者：luomin on 16/12/27 08:06
 * 邮箱：asddavid@163.com
 */

public class UserLoginModelImpl implements UserLoginModel {
    @Override
    public void login(final String account, final String passWord, final UserLoginListener userLoginListener) {
        //这里是处理登陆的业务逻辑
        Log.d("登陆中","登陆中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if("jike".equals(account)&&"123".equals(passWord)){
                    userLoginListener.loginSuccess();

                }else{
                    userLoginListener.loginFailed();

                }

            }
        },3000);
    }
}
