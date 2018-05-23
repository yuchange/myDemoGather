package com.zyf.bings.bingos.event;

/**
 *
 * @author wangshiqi
 * @date 2017/10/26
 */

public class LogOutEvent {
    String logOut;

    public LogOutEvent(String logOut) {
        this.logOut = logOut;
    }

    public String getLogOut() {
        return logOut;
    }

    public void setLogOut(String logOut) {
        this.logOut = logOut;
    }
}
