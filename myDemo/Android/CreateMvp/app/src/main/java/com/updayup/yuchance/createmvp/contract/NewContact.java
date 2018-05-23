package com.updayup.yuchance.createmvp.contract;

import com.updayup.yuchance.createmvp.Model.LoginBean;
import com.updayup.yuchance.createmvp.base.BasePresenter;
import com.updayup.yuchance.createmvp.base.BaseView;
import com.updayup.yuchance.createmvp.presenter.LoginPresenter;

/**
 * Created by yuchance on 2018/3/26.
 */

public class NewContact {

    public interface View extends BaseView<Presenter>{
        void showLoading();//展示加载框
        void dismissLoading();//取消加载框展示
        void showUserInfo(LoginBean userInfoModel);//将网络请求得到的用户信息回调
        String loadUserId();//假设接口请求需要一个userId


    }

    public interface Presenter extends BasePresenter{

        void LoginPresenter();

    }





}
