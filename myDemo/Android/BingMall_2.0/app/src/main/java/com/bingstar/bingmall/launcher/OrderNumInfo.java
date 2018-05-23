package com.bingstar.bingmall.launcher;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/1 上午9:27
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/1 上午9:27
 * @modify by reason:{方法名}:{原因}
 */

public class OrderNumInfo {
    private List<ZorderNumber> zorderNumberList;

    public List<ZorderNumber> getZorderNumberList() {
        return zorderNumberList;
    }

    public void setZorderNumberList(List<ZorderNumber> zorderNumberList) {
        this.zorderNumberList = zorderNumberList;
    }

    class ZorderNumber {
        private String zstate;//总订单状态(0:待付款;1：待发货;2：待收货)
        private String number; //数量

        public String getZstate() {
            return zstate;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setZstate(String zstate) {
            this.zstate = zstate;
        }
    }
}
