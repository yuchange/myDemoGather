package com.zyf.bings.bingos.order;

import com.zyf.bings.bingos.base.IBasePresenter;
import com.zyf.bings.bingos.order.bean.OrderListBean;

import java.util.List;

/**
 * @author wangshiqi
 * @date 2017/9/25
 */

public interface IOrderContract {
    interface OrderListView {
        void setPresenter();

        void notifyOrderList(List<OrderListBean.ZorderListBean> zorderListBean);

        void listErr();

        void remindSuccess();

        void remindFailed(String errMsg);

    }

    interface OrderPresenter extends IBasePresenter {
        /**
         * @param memberId    会员ID
         * @param state       通过改变state的值来切换数据
         * @param pageSize    　每个页面显示数据的个数
         * @param currentPage 　当前页数
         */
        void getAllOrderList(String memberId, String state, int pageSize, int currentPage);

        void notificationUser(String zorderInfoId);

    }
}
