package com.bingstar.bingmall.discount.bean;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qianhechen on 17/3/7.
 */

public class KidShowInfo {

    private String kidId;
    private String kidCode;
    private String kidName;
    private String modifyTime;
    private String kidTime;
    private boolean isSelected;
    private boolean isStart;

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    public String getKidCode() {
        return kidCode;
    }

    public void setKidCode(String kidCode) {
        this.kidCode = kidCode;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getKidTime() {
        if(kidTime == null){
            return " ";
        }else{
            return kidTime.split(" ")[0];
        }
    }

    public String getShowKidTime(){
        if(kidTime == null|| kidTime.equals("")){
            return "";
        }else{
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = dateFormat.parse(kidTime);
                return (date.getMonth()+1)+"月"+date.getDate()+"日";
            } catch (ParseException e) {
                e.printStackTrace();
                return kidTime.split(" ")[0];
            }
        }
    }

    public void setKidTime(String kidTime) {
        this.kidTime = kidTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isStart() {
        String time = getKidTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean isStart = false;
        try {
            long t = System.currentTimeMillis()-dateFormat.parse(time).getTime();
            if (t>0&&t<86400000){
                isStart = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
