package com.zyf.bings.bingos_libnet.bean;

import com.google.gson.JsonElement;
import com.zyf.bings.bingos_libnet.utils.Constant;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/20 下午2:06
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/20 下午2:06
 * @modify by reason:{方法名}:{原因}
 */

public class BingResponse {
    private String returnCode;

    private String returnMsg;

    private JsonElement responseBizData;
    private String errDesc;

    public String getReturnCode() {
        if (returnCode==null){
            returnCode = Constant.NET_ERROR+"";
        }
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public JsonElement getResponseBizData() {
        return responseBizData;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public void setResponseBizData(JsonElement responseBizData) {
        this.responseBizData = responseBizData;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    @Override
    public String toString() {
        return "BingResponse:["+"returnMsg:"+returnMsg+","+"returnCode:"+returnCode+","+"responseBizData:"+responseBizData
                +"]";
    }
}
