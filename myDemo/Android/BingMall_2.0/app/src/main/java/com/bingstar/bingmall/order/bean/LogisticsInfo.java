package com.bingstar.bingmall.order.bean;

import java.util.List;

/**
 * Created by qianhechen on 17/2/7.
 */

public class LogisticsInfo {

    private String bsjExpressNo;
    private String expressNumber;
    private String distributionSupplier;
    private List<LogisticsDetail> logisticsDetailList;

    public String getBsjExpressNo() {
        return bsjExpressNo;
    }

    public void setBsjExpressNo(String bsjExpressNo) {
        this.bsjExpressNo = bsjExpressNo;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getDistributionSupplier() {
        return distributionSupplier;
    }

    public void setDistributionSupplier(String distributionSupplier) {
        this.distributionSupplier = distributionSupplier;
    }

    public List<LogisticsDetail> getLogisticsDetailList() {
        return logisticsDetailList;
    }

    public void setLogisticsDetailList(List<LogisticsDetail> logisticsDetailList) {
        this.logisticsDetailList = logisticsDetailList;
    }

    public class LogisticsDetail {

        private String operateTime;
        private String operateDetail;

        public String getOperateTime() {
            return operateTime;
        }

        public String getOperateDetail() {
            return operateDetail;
        }
    }

}
