package com.bingstar.bingmall.net;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/10 下午3:57
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/10 下午3:57
 * @modify by reason:{方法名}:{原因}
 */
public class BingNetStates {

    public static final String METHOD = "method";
    public static final String REQUEST_DATA = "requestBizData";
    public static final String CUSTOME_CODE = "customeCode";
    public static final String BIZ_SOURCE = "bizSource";
    public static final String TIMESTAMP = "timestamp";
    public static final String SIGN = "sign";

    public static final int UNKNOW_ERROR = -1;//未知错误
    public static final int DATA_ERROR = -2;//数据异常
    public static final int NEED_LOGIN = -3;//需要登录
    public static final int NULL_ERROR = -4;//空数据
    public static final int ORDER_EXIST = 2001;//订单重复
    public static final int ORDER_VERIFY_FAIL = 2002; //验证订单失败
    public static final int USER_HAS_ACTIVATED = 1001;//激活用户重复
    public static final int USER_ACTIVATE_FAIL = 1002;//用户激活失败
    public static final int COUPON_HAS_GET = 3001; //用户不能重复领取优惠券
    public static final int GET_COUPON_FAIL = 4002; //领取优惠券失败
    public static final int CONSTRACT_FAIL = 5001;//签约用户失败
    public static final int SYSTEM_ERROR = 10001;//系统错误
    public static final int JSON_DATA_ERROR = 10002;//Json数据错误


    public static final String DATA_ERROR_MSG="数据错误";
    public static final String NEED_LOGIN_MSG="用户未登录";
}
