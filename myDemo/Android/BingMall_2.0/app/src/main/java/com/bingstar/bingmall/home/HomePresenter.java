package com.bingstar.bingmall.home;

import android.support.v4.util.ArrayMap;

import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.home.bean.BeanTranslator;
import com.bingstar.bingmall.home.bean.TitleBean;
import com.bingstar.bingmall.home.http.TitleListInfo;
import com.bingstar.bingmall.home.http.HomeClient;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 上午10:30
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 上午10:30
 * @modify by reason:{方法名}:{原因}
 */
public class HomePresenter implements IHomeContract.HomePresenter {

    private IHomeContract.HomeView homeView;

    public HomePresenter(IHomeContract.HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void unBind() {
        homeView = null;
    }

    @Override
    public void getTitleList(final List<TitleBean> titleList) {
        LogUtils.Debug_E(HomeTitleView.class, "time");
        HomeClient.getTitleList(new ClientCallback<TitleListInfo>() {
            @Override
            public void onSuccess(TitleListInfo titleListInfo) {
                if (titleListInfo.getCategoryList() != null && titleListInfo.getCategoryList().size() > 0) {
                    titleList.clear();
                    List<TitleBean> beanList = BeanTranslator.categoryToTitle(titleListInfo.getCategoryList());
                    TitleBean titleBean = new TitleBean();
                    titleBean.setCategoryId("");
                    titleBean.setCategoryName("全部");
                    titleBean.setCategoryOrder(-1);
                    titleBean.setActiveKbn("");
                    titleList.add(titleBean);
                    titleList.addAll(beanList);
                    if (homeView != null) {
                        homeView.notifyTitleRefresh();
                    }
                    // homeView.listErr();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (homeView != null) {
                    homeView.listErr();
                }
            }
        });
    }

    public void getTitleList(final List<TitleBean> titleList, int type) {
        if (type == TitleStates.TYPE_CYCLE) {
            Map<String, String> map = new ArrayMap<>();
            map.put(CartStates.MEMBERID, User.getIntance().getMemberId());
            map.put(CartStates.SOURCE, TitleStates.TYPE_CYCLE_SOURCE);
            CartClient.getCartTitle(map, new ClientCallback<CartTitle>() {
                @Override
                public void onSuccess(CartTitle cartTitle) {
                    titleList.clear();
                    List<TitleBean> beanList = BeanTranslator.categoryToTitle(cartTitle);
                    if (beanList.size() == 0) {
                        TitleBean titleBean = new TitleBean();
                        titleBean.setCategoryName("全部");
                        titleBean.setCategoryId("0");
                        titleBean.setCategoryOrder(0);
                        titleBean.setActiveKbn("");
                        beanList.add(titleBean);
                    }
                    titleList.addAll(beanList);
                    if (homeView != null) {
                        homeView.notifyTitleRefresh();
                    }
                }

                @Override
                public void onFail(int code, String error) {
                    if (homeView != null) {
                        homeView.listErr();
                    }
                }
            });
        }
    }
}
