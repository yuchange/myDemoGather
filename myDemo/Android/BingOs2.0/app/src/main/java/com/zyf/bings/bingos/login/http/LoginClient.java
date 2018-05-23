package com.zyf.bings.bingos.login.http;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.zyf.bings.bingos.login.ILoginPresenter;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.bean.LoginStateBean;
import com.zyf.bings.bingos_libnet.callback.LoginResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;

import java.util.List;
import java.util.Map;

import static com.zyf.bings.bingos_libnet.RxOkClient.getSign;


/**
 * Created by Administrator on 2017/8/25.
 */

public class LoginClient {
    private ILoginPresenter mILoginPresenter;
    private Context mContext;
    //http://bingstar.com.cn/bingstar-mobile
    //http://store.bingstar.com.cn/bingstar-mobile/if/haiEr
    private static final String LOGIN_STATES = "http://192.168.1.138:8099/bingstar-mobile/weChat/getMember.shtml";

    private boolean flag = true;
    private static final String LOGIN_METHOD = "weixin.getMember";
    private CountDownTimer mTimer;


    public LoginClient(ILoginPresenter ILoginPresenter) {
        mILoginPresenter = ILoginPresenter;
    }

    public LoginClient(ILoginPresenter ILoginPresenter, Context context) {
        mILoginPresenter = ILoginPresenter;
        mContext = context;
    }

    //获得登录的状态
    public void getLoginStates() {
        startPolling();//开启轮询

    }

    private void startPolling() {
        String timeBIZService = TimeUtil.getTimeBIZService();
        final Map<String, String> mapData = new ArrayMap<>();
        mapData.put(BingNetStates.CUSTOME_CODE, "bsj123456");
        mapData.put(BingNetStates.BIZ_SOURCE, "123456");
        mapData.put(BingNetStates.TIMESTAMP, timeBIZService);
        mapData.put(BingNetStates.METHOD, LOGIN_METHOD);
        String sign = getSign(mapData);
        mapData.put(BingNetStates.SIGN, sign);
        mapData.put("mac_id", CommonUtils.getWifiMac());
        mapData.put("state","1");

        mTimer = new CountDownTimer(60 * 2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                OkGoUtils.pollingLoginStates(LOGIN_STATES, "LoginClient", mapData, new LoginResultCallback() {
                    @Override
                    public void onSuccess(List<LoginStateBean.ResponseBizDataBean> responseBizDataBeanList) {

                        if (responseBizDataBeanList != null && responseBizDataBeanList.size() == 1 && !TextUtils.isEmpty(responseBizDataBeanList.get(0).getState()) && responseBizDataBeanList.get(0).getState().equals("1")) {//代表登录成功
                            Log.i("LoginClient", "集合" + responseBizDataBeanList.toString());
                            if(mILoginPresenter!=null){

                                mILoginPresenter.loginSuccess(responseBizDataBeanList);//登录成功
                            }
                            if (mTimer != null) {
                                mTimer.cancel();
                                OkGo.getInstance().cancelTag("LoginClient");

                            }
                        }


                    }

                    @Override
                    public void onFail(int code, String error) {
                        if(mILoginPresenter!=null){

                            mILoginPresenter.loginFilaed(code ,error);
                        }
                        if (mTimer != null) {
                            mTimer.cancel();
                            OkGo.getInstance().cancelTag("LoginClient");

                        }


                    }
                });


            }

            @Override
            public void onFinish() {
                if (mTimer != null) {
                    mTimer.cancel();
                }
                OkGo.getInstance().cancelTag("LoginClient");
                mILoginPresenter.loginFilaed(1,"登录超时");
            }
        };

        mTimer.start();


    }




    public void unBlind() {
        mILoginPresenter = null;
        if (mTimer != null) {
            mTimer.cancel();
        }
    }


}
