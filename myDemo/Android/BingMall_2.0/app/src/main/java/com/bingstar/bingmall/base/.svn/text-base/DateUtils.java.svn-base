package com.bingstar.bingmall.base;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by liumengqiang on 2017/2/27.
 */

/**
 * 计算两个日期之间的间隔天数
 */
public class DateUtils {
    /**
     * 计算两个日期之间的天数
     * @param beginTime  开始时间
     * @return
     */
    public static int DateTime(String beginTime){
        long beginLong = Long.parseLong(beginTime);
        long nowLong = Long.parseLong(getTime());//当前日期
        int dayCount = (int) (nowLong - beginLong)/(1000*3600*24);//从间隔毫秒变成间隔天数
        return dayCount;
    }

    /**
     * 获取当前系统时间
     * @return
     */
    public static String  getTime(){
        long time = System.currentTimeMillis();
        return time+"";
    }

    public static Date stringToDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = null;
        try {
           date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
