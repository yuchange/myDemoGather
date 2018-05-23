package com.bingstar.bingmall.net;

import android.content.Context;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.main.bean.ToastEvent;

/**
 * Created by liumengqiang on 2017/3/29.
 */

public class BingstarErrorParser {
    /**
     * 包括服务器未来可能产生的已知错误 都是弹出Toast处理
     */
    public static final int UNKNOW_ERROR = -1;//未知错误 
    public static final int ORDER_EXIST = 2001;//订单重复 
    public static final int ORDER_VERIFY_FAIL = 2002; //验证订单失败 
    public static final int USER_HAS_ACTIVATED = 1001;//激活用户重复 
    public static final int USER_ACTIVATE_FAIL = 1002;//用户激活失败 
    public static final int COUPON_HAS_GET = 3001; //用户不能重复领取优惠券 
    public static final int GET_COUPON_FAIL = 4002; //领取优惠券失败 
    public static final int CONSTRACT_FAIL = 5001;//签约用户失败 
    public static final int SYSTEM_ERROR = 10001;//系统错误 
    public static final int JSON_DATA_ERROR = 10002;//Json数据错误

    /**
     * 跳转到error页面 ，重新请求数据
     */
    public final static int NET_ERROR = -100;   //网络请求异常 
    public final static int TIME_OUT = -101;    //网络请求超时
    public static final int DATA_ERROR = -2;    //数据异常

    /**
     * 错误处理 重新请求数据标记
     */
    public static final String OrderFragment_final = "1";
    public static final String OrderInfoFragment_final = "2";
    public static final String OrderInfo_logistics_final = "3";
    public static final String OrderInfo_delete_final = "4";
    public static final String OrderApplyDialog_final = "5";
    public static final String OrderApplyEditDialog_final = "6";
    public static final String cycleFragment_final = "7";
    public static final String addrmanageFragment = "8";
    public static final String cycledetail_dialog_final = "9";

    public static final String CartFragment_final = "10";
    public static final String GoodsDetailFragment_final = "11";
    public static final String GoodsListFragment_final = "12";
    public static final String DisCountFragment_final = "13";
    public static final String AdsFragment_final = "14";

    /**
     * 1：fastjson 转换产生的对象为Null异常归类为DATA_ERROR ，
     * 2：beantranslater 中关键性数据缺失 返回null做DATA_ERROR处理  非关键性数据为null做“”字符串处理
     */

    /**
     * 在mainactivity弹Toast处理
     * @param errorCode 输入错误的code
     * @param errorString 错误
     */
    public static void toastErr(int errorCode, String errorString, String flugFragment){
        switch (errorCode){
            case UNKNOW_ERROR:{//未知错误 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(errorString));
                break;
            }
            case ORDER_EXIST:{//订单重复 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.order_repeat));
                break;
            }
            case ORDER_VERIFY_FAIL:{ //验证订单失败 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.order_verify_fail));
                break;
            }
            case USER_HAS_ACTIVATED:{//激活用户重复 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.user_has_activated));

                break;
            }
            case USER_ACTIVATE_FAIL:{//用户激活失败 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.user_activate_fail));
                break;
            }
            case COUPON_HAS_GET:{//用户不能重复领取优惠券 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.coupon_has_get));
                break;
            }
            case GET_COUPON_FAIL:{//领取优惠券失败 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.get_coupon_fail));
                break;
            }
            case CONSTRACT_FAIL:{//签约用户失败 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.constract_fail));
                break;
            }
            case SYSTEM_ERROR:{//系统错误 
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(R.string.system_error));
                break;
            }
            case JSON_DATA_ERROR:{//Json数据错误
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(errorString));
                break;
            }
            case NET_ERROR:{//网络请求异常 
                org.greenrobot.eventbus.EventBus.getDefault().post(new EventMsg(BingstarErrorParser.class,flugFragment));
                break;
            }
            case TIME_OUT:{ //网络请求超时
                org.greenrobot.eventbus.EventBus.getDefault().post(new EventMsg(BingstarErrorParser.class,flugFragment));
                break;
            }
            case DATA_ERROR:{ //数据异常
                org.greenrobot.eventbus.EventBus.getDefault().post(new EventMsg(BingstarErrorParser.class,flugFragment));
                break;
            }
            default:{//未知错误
                org.greenrobot.eventbus.EventBus.getDefault().post(new ToastEvent(errorString));
                break;
            }
        }

    }

}
