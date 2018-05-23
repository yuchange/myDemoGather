package com.bingstar.bingmall.main.bean;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/29 下午4:59
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/29 下午4:59
 * @modify by reason:{方法名}:{原因}
 */

public class ToastEvent {

    private int msg;

    private String msgString;

    public ToastEvent(String msgString) {
        this.msgString = msgString;
    }
    public ToastEvent(int msg){
        this.msg = msg;
    }

    public String getMsgString() {
        return msgString;
    }

    public void setMsgString(String msgString) {
        this.msgString = msgString;
    }


    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }
}
