package com.zyf.bings.bingos_libnet;

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

    public static final String BUILD_HAIER = "haier";
    public static final String BUILD_BINGOS = "bingOs";
    public static final String BUILD_DEBUG = "debug";

    //服务器地址配置
    public static final String HOST_URL = BuildConfig.HOSTNAME;
    //图片地址配置
    public static final String IMAGE_SERVER = BuildConfig.URL_IMAGE;
    //h5地址配置
    public static final String HTML_URL = BuildConfig.WEB_HTML;

    public static final String BUILD_FLAG = BuildConfig.BUILD_FLAG;


    private MainBuildConfigure() {
    }

    public synchronized static MainBuildConfigure getInstance() {
        if (instance == null) {
            instance = new MainBuildConfigure();
        }
        return instance;
    }

}
