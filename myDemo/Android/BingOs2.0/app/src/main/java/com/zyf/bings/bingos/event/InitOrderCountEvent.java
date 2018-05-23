package com.zyf.bings.bingos.event;

import com.zyf.bings.bingos.order.bean.OrderCountBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class InitOrderCountEvent {
    private List<OrderCountBean.ZorderNumberListBean> list;

    public InitOrderCountEvent(List<OrderCountBean.ZorderNumberListBean> list) {
        this.list = list;
    }

    public List<OrderCountBean.ZorderNumberListBean> getList() {
        return list;
    }
}
