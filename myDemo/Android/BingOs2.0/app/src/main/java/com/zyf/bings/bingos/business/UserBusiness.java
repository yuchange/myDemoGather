package com.zyf.bings.bingos.business;


import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;

/**
 * 当前用户信息保存 默认收货地址
 * Created by zhangyifei on 17/9/13.
 */


public class UserBusiness {

    /**
     * 默认收货地址名称
     *
     * @param userName 默认收货地址名称
     */
    public static void saveUserName(String userName) {

        SPUtil.putStringToSp(Config.USER_NAME, userName);
    }

    /**
     * 默认收货地址名称
     *
     * @return 默认收货地址名称
     */
    public static String getUserName() {
        return SPUtil.getStringFromSp(Config.USER_NAME, "");
    }

    /**
     * 移除默认收货地址名称
     */
    public static void removeUserName() {
        SPUtil.remove(Config.USER_NAME);
    }


    /**
     * 保存默认收货地址手机号
     *
     * @param userMobile 默认收货地址手机号
     */
    public static void saveUserMobile(String userMobile) {
        SPUtil.putStringToSp(Config.USER_MOBILE, userMobile);
    }

    /**
     * 获得默认收货地址手机号
     *
     * @return 默认收货地址手机号
     */
    public static String getUserMobile() {
        return SPUtil.getStringFromSp(Config.USER_MOBILE, "");
    }

    /**
     * 移除默认收货地址手机号
     */
    public static void removeUserMobile() {
        SPUtil.remove(Config.USER_MOBILE);
    }

    /**
     * 保存默认收货地址用户所在区域
     *
     * @param address 默认收货地址用户区域
     */
    public static void saveUserAddress(String address) {
        SPUtil.putStringToSp(Config.USER_ADDRESS, address);
    }

    public static void saveUserRegion(String region) {
        SPUtil.putStringToSp(Config.REGION, region);
    }

    public static String getUserRegion() {
        return SPUtil.getStringFromSp(Config.REGION, "");
    }


    /**
     * 获得默认收货地址用户所在区域
     *
     * @return 默认收货地址用户区域
     */
    public static String getUserAddress() {
        return SPUtil.getStringFromSp(Config.USER_ADDRESS, "");//如果没有数据

    }

    /**
     * 移除默认收货地址用户所在区域
     */
    public static void removeUserAreaID() {
        SPUtil.remove(Config.USER_ADDRESS);
    }


    /**
     * 保存默认收货地址用户身份证号码
     *
     * @param idCard 默认收货地址身份证号码
     */
    public static void saveUserIdCard(String idCard) {
        SPUtil.putStringToSp(Config.USER_ID_CARD, idCard);
    }


    /**
     * 获得默认收货地址用户身份证号码
     *
     * @return 身份证号码
     */
    public static String getUserIdCard() {
        return SPUtil.getStringFromSp(Config.USER_ID_CARD, "");//如果没有数据

    }

    /**默认收货地址
     * 移除用户身份证号码
     */
    public static void removeUserIdCard() {
        SPUtil.remove(Config.USER_ID_CARD);
    }

    public static void removeUserRegion() {
        SPUtil.remove(Config.REGION);
    }


    /**
     * 清楚所有关键信息
     */
    public static void removeUserInfo() {
        UserBusiness.removeUserName();
        UserBusiness.removeUserAreaID();
        UserBusiness.removeUserMobile();
        UserBusiness.removeUserIdCard();
        UserBusiness.removeUserRegion();
    }

    public static void saveAddressData(AddressList.MemberAddressListBean memberAddressListBean) {
        UserBusiness.saveUserName(memberAddressListBean.getName());
        UserBusiness.saveUserAddress(memberAddressListBean.getDetailed());
        UserBusiness.saveUserIdCard(memberAddressListBean.getIdCard());
        UserBusiness.saveUserMobile(memberAddressListBean.getMobile());
        UserBusiness.saveUserRegion(memberAddressListBean.getRegion());
    }

}
