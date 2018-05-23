package com.bingstar.bingmall.main;

import com.bingstar.bingmall.BuildConfig;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/8 下午3:44
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/8 下午3:44
 * @modify by reason:{方法名}:{原因}
 */
@SuppressWarnings("ConstantConditions")
public class MainBuildConfigure {

    private static MainBuildConfigure instance;

    public static final int BUILD_HAIER = 0;
    public static final int BUILD_BINGOS = 1;
    public static final int BUILD_TEST = 2;
    public static final String IMAGE_DEBUG = "http://dev-bucket.bingstar.com.cn";
    public static final String IMAGE_RELEASE = "http://prod-bucket.bingstar.com.cn";

    private MainBuildConfigure() {
    }

    public synchronized static MainBuildConfigure getInstance() {
        if (instance == null) {
            instance = new MainBuildConfigure();
        }
        return instance;
    }

    /**
     * 获取构建版本
     */
    public int getBuildType() {
        if (BuildConfig.BUILD_FLAG.equals("haier")) {
            return BUILD_HAIER;
        } else if (BuildConfig.BUILD_FLAG.equals("bingOS")) {
            return BUILD_BINGOS;
        } else {
            return BUILD_TEST;
        }
    }


    public String getImageService() {
        if (getBuildType() == BUILD_HAIER) {
            return IMAGE_RELEASE;
        } else if (getBuildType() == BUILD_BINGOS) {
            return IMAGE_DEBUG;
        } else {
            return IMAGE_DEBUG;
        }
    }

    public String getService() {
        if (getBuildType() == BUILD_HAIER) {
            return "http://store.bingstar.com.cn/bingstar-mobile";
            // return "http://mall.bingstar.com.cn";
            //return "http://139.224.33.12:8088/bingstar";
            //return "http://192.168.1.138:8080/bingstar";
        } else if (getBuildType() == BUILD_BINGOS) {
            //return "http://192.168.1.138:8080/bingstar";
            return "http://139.224.33.12:8088/bingstar";
        } else {
            // return "http://139.224.33.12:8088/bingstar";
            //return "http://192.168.1.138:8099/bingstar-mobile";
            //return "http://139.224.33.12:8088/bingstar";
            return "http://store.bingstar.com.cn/bingstar-mobile";
            //return "http://mall.bingstar.com.cn";
            //return "http://192.168.1.60:8080/bingstar1.3.0-mobile";
            // return "http://mall.bingstar.com.cn";

        }
    }


    public String getHtmlUrl() {
        if (getBuildType() == BUILD_HAIER) {
            return "http://store.bingstar.com.cn/bingstar-web";
            //return "http://mall.bingstar.com.cn";
            //return "http://139.224.33.12:8088/bingstar";
            //return "http://192.168.1.138:8080/bingstar";
        } else if (getBuildType() == BUILD_BINGOS) {
            //return "http://192.168.1.138:8080/bingstar";
            return "http://139.224.33.12:8088/bingstar";
        } else {
            // return "http://139.224.33.12:8088/bingstar";
           return "http://192.168.1.138:8099/bingstar-mobile/if/haiEr";
            //return "http://192.168.1.138:8099/bingstar-web";
            //return "http://139.224.33.12:8088/bingstar";
            //return "http://store.bingstar.com.cn";
            //return "http://mall.bingstar.com.cn";
            //return "http://192.168.1.60:8080/bingstar1.3.0-mobile";
            // return "http://mall.bingstar.com.cn";

        }
    }

}
