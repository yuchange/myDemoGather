package com.yunzhi.lib.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lambda 2017/2/19.
 * Copyright (c) 2017.All rights reserved.
 * blog:http://blog.csdn.net/github_35096760
 * code:https://github.com/lalambda
 */

public class Util {
    public static String getTimeBIZService(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(new Date());
    }



}
