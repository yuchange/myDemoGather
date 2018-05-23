package com.bingstar.bingmall.home;

import com.bingstar.bingmall.base.IBasePresenter;
import com.bingstar.bingmall.base.IBaseView;
import com.bingstar.bingmall.home.bean.TitleBean;
import com.bingstar.bingmall.home.http.TitleListInfo;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 上午10:33
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 上午10:33
 * @modify by reason:{方法名}:{原因}
 */
public interface IHomeContract {

    interface HomeView extends IBaseView {
        void notifyTitleRefresh();

        void listErr();

    }

    interface HomePresenter extends IBasePresenter {
        void getTitleList(List<TitleBean> titleList);

        void getTitleList(List<TitleBean> titleList, int type);
    }
}
