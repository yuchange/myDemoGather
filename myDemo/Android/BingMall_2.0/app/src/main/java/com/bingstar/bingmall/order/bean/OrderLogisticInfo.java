package com.bingstar.bingmall.order.bean;

import java.util.List;

/**
 * Created by John on 2017/3/13.
 */

public class OrderLogisticInfo {
    private List<LogisticsInfo> logisticsInfoList;

    public List<LogisticsInfo> getLogisticsInfoList() {
        return logisticsInfoList;
    }

    public void setLogisticsInfoList(List<LogisticsInfo> logisticsInfoList) {
        this.logisticsInfoList = logisticsInfoList;
    }

    public class LogisticsInfo {
        private String bsjExpressNo;//冰事记子单号
        private String expressNumber;//快递单号
        private String distributionSupplier;//快递公司
        private List<LogisticsDetail> logisticsDetailList;//物流详情

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

        public class LogisticsDetail{
            private String operateTime;//操作时间
            private String operateDetail;//操作详情

            public String getOperateTime() {
                return operateTime;
            }

            public void setOperateTime(String operateTime) {
                this.operateTime = operateTime;
            }

            public String getOperateDetail() {
                return operateDetail;
            }

            public void setOperateDetail(String operateDetail) {
                this.operateDetail = operateDetail;
            }
        }
    }

}
