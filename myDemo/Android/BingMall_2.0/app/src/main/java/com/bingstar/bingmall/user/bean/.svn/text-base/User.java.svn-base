package com.bingstar.bingmall.user.bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.main.MainBuildConfigure;
import com.bingstar.bingmall.main.bean.LoginEvent;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.sdk.map.LatLng;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManagePresenter;
import com.haiersmart.user.sdk.UserUtils;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by qianhechen on 17/3/13.
 */

public class User {


    private final String HAIER_PHONE_SP = "nation_sp";
    private final String HAIER_PHONE_KEY = "binding_phone";
    public static final String BINGOS_PHONE_SP = "UserPhone";
    public static final String BINGOS_PHONE_KEY = "phone";
    private final String BINGMALL_USER_SP = "bingmall_user_sp";
    private final String BINGMALL_PHONE_KEY = "binding_phone";
    private final String HAIER_FRIDGE_TYPE = "fridge_type";
    private final String HAIER_FRIDGE_INIT = "fridge_init";
    public final static String TYPE_INIT_VIEW = "01";
    public final static String TYPE_BINGMALL = "02";
    public final static String TYPE_BINGMALL_ORDER = "03";
    public final static String TYPE_BINGMALL_ADDR = "04";

    private String memberId;
    private String areaId;
    private String addressId;
    private String name;
    private String address;
    private String mobile;
    private String orderUserName;
    private String phone;
    private String region;
    private String buyer_IDcard;
    private Context applicationContext;

    private static User user;

    public User(String memberId, String areaId, String name, String address, String mobile, String orderUserName, String phone, String region, String buyer_IDcard, String addressId) {
        this.memberId = memberId;
        this.areaId = areaId;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.orderUserName = orderUserName;
        this.phone = phone;
        this.region = region;
        this.buyer_IDcard = buyer_IDcard;
        this.addressId = addressId;
    }

    public User(Context context) {
        applicationContext = context.getApplicationContext();
    }

    private User() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBuyer_IDcard() {
        return buyer_IDcard;
    }

    public void setBuyer_IDcard(String buyer_IDcard) {
        this.buyer_IDcard = buyer_IDcard;
    }

    public String getMemberId() {
        if (memberId == null || memberId.equals("")) {
            memberId = getHaierPhone();
        }
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public static User getIntance(Context context) {
        if (user == null) {
            user = new User(context.getApplicationContext());
        }
        return user;
    }

    public static User getIntance() {
        if (user == null) {
            throw new IllegalArgumentException("You should init user first");
        }
        return user;
    }

    public synchronized void initUser(String type) {
        initUser(type, null);
    }

    private int count = 0;

    public void initUser(final String type, final LatLng latLng) {
        final String phone = user.getHaierPhone();
        memberId = phone;
        if (type != null) {
            String mac = Util.getWifiMac();
            UserClient.setFridge(mac, phone, type, latLng, new ClientCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    count = 0;
                }

                @Override
                public void onFail(int code, String error) {
                    count++;
                    if (count < 5) {
                        initUser(type, latLng);
                    }
                }
            });
            return;
        }
        if (!phone.equals(user.getPhoneFromSP()) &&
                //避免向服务器传入""空id
                !TextUtils.isEmpty(phone)) {
            String mac = Util.getWifiMac();
            user.setAddressId("");
            user.setAddress("");
            user.setAreaId("");
            user.setBuyer_IDcard("");
            user.setMobile("");
            user.setName("");
            user.setRegion("");
            UserClient.setMember(mac, phone, new ClientCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    EventBus.getDefault().post(new LoginEvent());
                    user.setSPPhone(phone);
                }

                @Override
                public void onFail(int code, String error) {

                }
            });

            /**
             * 判断User里面的数据是否为空
             * 给User赋值
             */
            if (!user.getMemberId().equals("")) {
                AddrManagePresenter.getHttpDateUser(phone);
            }
        }
        if (!user.getMemberId().equals("") && !checkAddr()) {
            AddrManagePresenter.getHttpDateUser(phone);
        }
    }

    /**
     * 此方法已被废弃 {@link com.bingstar.bingmall.user.bean.User#getMemberFromSP()
     * instead }
     */
    @Deprecated
    public String getHaierPhone() {
        return getMemberFromSP();
    }

    /**
     * 获取存储的用户  目前用户名为手机号
     *
     * @return 用户
     */
    public String getMemberFromSP() {
        int type = MainBuildConfigure.getInstance().getBuildType();
        if (type == MainBuildConfigure.BUILD_HAIER) {
            String s = UserUtils.get().bindingPhone();
            if (!TextUtils.isEmpty(s)) {
                return s;
            }
            return "";
        } else if (type == MainBuildConfigure.BUILD_BINGOS) {
            if (applicationContext == null) {
                return null;
            }
            SharedPreferences sp = applicationContext.getSharedPreferences(BINGOS_PHONE_SP, Context.MODE_PRIVATE);
            return sp.getString(BINGOS_PHONE_KEY, "");
        } else {
            return "15869153840";
        }
    }

    //获取冰箱型号
    public String getFridgeType() {
        int type = MainBuildConfigure.getInstance().getBuildType();
        if (type == MainBuildConfigure.BUILD_HAIER) {
            String s = UserUtils.get().fridgeMode();
            if (!TextUtils.isEmpty(s)) {
                return s;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    //获取冰箱初始化
    public boolean getFridgeInit() {
        int type = MainBuildConfigure.getInstance().getBuildType();
        if (type == MainBuildConfigure.BUILD_HAIER) {
            if (applicationContext == null) {
                return false;
            }
            SharedPreferences sp = applicationContext.getSharedPreferences(BINGMALL_USER_SP, Context.MODE_PRIVATE);
            return sp.getBoolean(HAIER_FRIDGE_INIT, true);
        } else {
            return false;
        }
    }

    //获取冰箱初始化
    public void setFridgeInit(boolean init) {
        if (applicationContext == null) {
            return;
        }
        SharedPreferences.Editor editor = applicationContext.getSharedPreferences(BINGMALL_USER_SP, Context.MODE_PRIVATE).edit();
        editor.putBoolean(HAIER_FRIDGE_INIT, init);
        editor.apply();
    }

    private void setSPPhone(String phone) {
        if (applicationContext == null) {
            return;
        }
        SharedPreferences.Editor editor = applicationContext.getSharedPreferences(BINGMALL_USER_SP, Context.MODE_PRIVATE).edit();
        editor.putString(BINGMALL_PHONE_KEY, phone);
        editor.apply();
    }

    public String getPhoneFromSP() {
        if (applicationContext == null) {
            return "";
        }
        SharedPreferences sp = applicationContext.getSharedPreferences(BINGMALL_USER_SP, Context.MODE_PRIVATE);
        return sp.getString(BINGMALL_PHONE_KEY, "");
    }


    public static void checkMemberId(Context context, CheckMemberCallback callback) {
        if (MainBuildConfigure.getInstance().getBuildType() == MainBuildConfigure.BUILD_HAIER) {
            checkHaierLogin(context, callback);
        } else if (MainBuildConfigure.getInstance().getBuildType() == MainBuildConfigure.BUILD_BINGOS) {
            checkOSLogin(context, callback);
        } else {
            String memberId = User.getIntance(context).getMemberFromSP();
            if (memberId == null || memberId.equals("")) {
                showToast(context, R.string.launcher_login_message);
            } else {
                callback.hasLogin();
            }
        }
    }

    public static boolean checkAddr() {
        if (user.getAddress() == null || user.getAddress().equals("")) {
            return false;
        }
        if (user.getAddressId() == null || user.getAddressId().equals("")) {
            return false;
        }
        if (user.getName() == null || user.getName().equals("")) {
            return false;
        }
        if (user.getMobile() == null || user.getMobile().equals("")) {
            return false;
        }
        if (user.getRegion() == null || user.getRegion().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * 海尔校验用户登录接口
     */
    private static void checkHaierLogin(final Context context, final CheckMemberCallback callback) {
        if (context instanceof Activity) {
            try {
                UserUtils.get().loginToDo((Activity) context, new Runnable() {
                    @Override
                    public void run() {
                        User.getIntance(context).initUser(null);
                        if (callback != null) {
                            callback.hasLogin();
                        }
                    }
                });
            } catch (Exception e) {
                showToast(context, R.string.login_error);
                e.printStackTrace();
            }
        } else {
            showToast(context, R.string.login_error);
        }
    }

    /**
     * bingOS校验用户登录接口
     */
    private static void checkOSLogin(final Context context, final CheckMemberCallback callback) {
        if (context instanceof Activity) {
            try {
                Class<?> cls = Class.forName("com.bingstar.bingos.login.LoginUtils");
                Method method = cls.getMethod("loginAuto", Activity.class, Runnable.class);
                method.invoke(null, context, new Runnable() {
                    @Override
                    public void run() {
                        User.getIntance(context).initUser(null);
                        if (callback != null) {
                            callback.hasLogin();
                        }
                    }
                });
            } catch (Exception e) {
                showToast(context, R.string.login_error);
                e.printStackTrace();
            }
        } else {
            showToast(context, R.string.login_error);
        }
    }

    public interface CheckMemberCallback {
        void hasLogin();
    }

    private static void showToast(Context context, @StringRes int strId) {
        Toast toast;
        toast = Toast.makeText(context, strId, Toast.LENGTH_SHORT);
        setToastTextSize(toast);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(50);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}