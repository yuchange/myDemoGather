package com.bingstar.bingmall.base;

import com.bingstar.bingmall.user.addr.AddrManage.AddrManageInfoBean;

import java.io.Serializable;

/**
 * Created by qianhechen on 17/2/24.
 */

public class EventMsg implements Serializable {

    public static final String CARTADDERR = "CartAddFailed";
    public static final String CARTADDSUCCESS = "CartAddSuccess";
    public static final String NOMEMBERID = "NoMemberId";
    public static final String NOCATEGORYID = "NoCategoryId";
    public static final String LISTERR = "ListErr";

    private Class className;
    private String msg;
    private String name;
    private String phone;
    private String isFrom;

    private String detailMsg;//详细信息

    private AddrManageInfoBean.MemberAddress obj;

    public EventMsg(Class classz) {
        this.className = classz;
    }

    public EventMsg(Class classz, AddrManageInfoBean.MemberAddress obj) {
        this.className = classz;
        this.obj = obj;
    }

    public EventMsg(Class classz, String msg) {
        this.className = classz;
        this.msg = msg;
    }

    public EventMsg(Class className, String msg, String isFrom) {
        this.className = className;
        this.msg = msg;
        this.isFrom = isFrom;
    }

    public EventMsg(Class classz, String msg, String name, String phone) {
        this.className = classz;
        this.msg = msg;
        this.name = name;
        this.phone = phone;
    }

    public Class getClassName() {
        return className;
    }

    public AddrManageInfoBean.MemberAddress getObj() {
        return obj;
    }

    public void setObj(AddrManageInfoBean.MemberAddress obj) {
        this.obj = obj;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsFrom() {
        return isFrom;
    }

    public void setIsFrom(String isFrom) {
        this.isFrom = isFrom;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

}
