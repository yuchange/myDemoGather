package com.zyf.bings.bingos.order.bean;

import java.util.List;

/**
 *
 * @author wangshiqi
 * @date 2017/10/19
 */

public class OrderCountBean {

    private List<ZorderNumberListBean> zorderNumberList;

    public List<ZorderNumberListBean> getZorderNumberList() {
        return zorderNumberList;
    }

    public void setZorderNumberList(List<ZorderNumberListBean> zorderNumberList) {
        this.zorderNumberList = zorderNumberList;
    }

    public static class ZorderNumberListBean {
        /**
         * zstate : 0
         * number : 9
         */

        private String zstate;
        private String number;

        public String getZstate() {
            return zstate;
        }

        public void setZstate(String zstate) {
            this.zstate = zstate;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
