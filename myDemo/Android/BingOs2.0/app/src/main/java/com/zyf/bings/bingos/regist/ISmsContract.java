package com.zyf.bings.bingos.regist;

import com.zyf.bings.bingos.base.IBaseView;

/**
 * Created by wangshiqi on 2017/8/24.
 */

public interface ISmsContract {
    interface SmsView extends IBaseView {
        void registSuccess();
    }

    interface SmsPresenter {
        void unBind();
        void getSmsCode(String phoneNumber);  // 参数待定

        void regist(String phoneNum);// 参数待定
    }
}
