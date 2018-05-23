package com.zyf.bings.bingos.event;

/**
 * Created by zhangyifei on 17/9/19.
 */

public class MainTitleEvent {
    public String title;

    public MainTitleEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
