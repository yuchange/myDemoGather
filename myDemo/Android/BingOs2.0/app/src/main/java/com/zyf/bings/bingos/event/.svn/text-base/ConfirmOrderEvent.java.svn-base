package com.zyf.bings.bingos.event;

import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;

import java.util.List;

/**
 *
 * @author wangshiqi
 * @date 2017/10/31
 */

public class ConfirmOrderEvent {
    private List<ConfirmOrderBean.ItemBean> mBeanList;
    private String totalPrice;

    public ConfirmOrderEvent(List<ConfirmOrderBean.ItemBean> beanList, String totalPrice) {
        mBeanList = beanList;
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }



    public List<ConfirmOrderBean.ItemBean> getBeanList() {
        return mBeanList;
    }

    public void setBeanList(List<ConfirmOrderBean.ItemBean> beanList) {
        mBeanList = beanList;
    }

}
