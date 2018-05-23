package com.zyf.bings.bingos_libnet.bean;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by zhangyifei on 17/4/24.
 */

public class BaseResponse {

    /**
     * returnCode	描述
     * 0000000	成功
     * 0000001	订单重复
     * 0000002	验证订单失败
     * 0000003	库存不足
     * 9999999	Json数据错误
     * 1111111	系统错误
     * 0000010	激活用户重复
     * 0000012	用户不能重复领取优惠券
     * 0000013	领取优惠券失败
     * 0000014	用户激活失败
     * 0000015	签约用户失败
     */
    //@Expose
    @SerializedName("returnCode")
    private String returnCode;
    //@Expose
    @SerializedName("returnMsg")
    private String returnMsg;
    //@Expose
    @SerializedName("responseBizData")
    private JsonElement responseBizData;
    //@Expose
    @SerializedName("returnName")
    private String returnName;
    //@Expose
    @SerializedName("errDesc")
    private String errDesc;
    //@Expose
    @SerializedName("errItemList")
    private String errItemList;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getResponseBizData() {
        if (responseBizData != null) {
            return responseBizData.toString();
        } else {
            return "";
        }
    }

    public void setResponseBizData(JsonElement responseBizData) {
        this.responseBizData = responseBizData;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public String getErrItemList() {
        return errItemList;
    }

    public void setErrItemList(String errItemList) {
        this.errItemList = errItemList;
    }
}
