package com.bingstar.bingmall.order.LogisticsSearch;

import java.util.List;

/**
 * Created by liumengqiang on 2017/3/24.
 */

public class LogisticsInfo {
    private List<ExpressInfoList> expressInfoList;

    public List<ExpressInfoList> getExpressInfoList() {
        return expressInfoList;
    }

    public void setExpressInfoList(List<ExpressInfoList> expressInfoList) {
        this.expressInfoList = expressInfoList;
    }

    public class ExpressInfoList{
        private String time;
        private String context;
        private String ftime;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }
    }
}
