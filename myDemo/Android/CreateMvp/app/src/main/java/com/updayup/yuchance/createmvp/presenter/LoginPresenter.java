package com.updayup.yuchance.createmvp.presenter;

import android.view.View;

import com.updayup.yuchance.createmvp.contract.NewContact;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by yuchance on 2018/3/26.
 */

public class LoginPresenter implements NewContact.Presenter{

    private NewContact.View view;

    public LoginPresenter(NewContact.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        Login();
    }

    private void Login() {
        String phoneNum = "18210722011";
        String password = "123456";
        OkHttpUtils.get().url("http://47.92.94.122:8080/api/v2/user/login.json")
                .addParams("phonenumber",phoneNum)
                .addParams("password",password)
                .addParams("version","1.1.19")
                .addHeader("Content-Type","application/json")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                view.dismissLoading();
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    JSONObject object = new JSONObject((String) response);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void LoginPresenter() {
        Login();
    }
}
