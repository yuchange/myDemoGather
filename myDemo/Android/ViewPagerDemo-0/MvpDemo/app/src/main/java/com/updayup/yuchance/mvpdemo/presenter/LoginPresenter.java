package com.updayup.yuchance.mvpdemo.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.updayup.yuchance.mvpdemo.model.LoginModel;
import com.updayup.yuchance.mvpdemo.model.LoginModelInterface;
import com.updayup.yuchance.mvpdemo.model.LoginModelListener;

/**
 * Created by yuchance on 2017/12/24.
 */

public class LoginPresenter {


    private LoginView loginView;
    private LoginModelInterface loginModelInterface;
    private Context context;

    public LoginPresenter(Context context,LoginView loginView){
        this.loginView = loginView;
        loginModelInterface = new LoginModel();
        this.context = context;

    }
    public void login(String account, String password){
        loginView.showLoading();

        if (TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            Toast.makeText(context, "请输入账号密码", Toast.LENGTH_SHORT).show();
        }else {

            loginModelInterface.LoginModel(account, password, new LoginModelListener() {
                @Override
                public void success() {
                    loginView.hideLoading();
                    loginView.success();
                }

                @Override
                public void failed() {
                    loginView.hideLoading();
                    loginView.failed();
                }
            });
        }

    }




}
