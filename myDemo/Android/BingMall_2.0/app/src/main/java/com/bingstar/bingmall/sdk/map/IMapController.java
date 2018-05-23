package com.bingstar.bingmall.sdk.map;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 上午11:15
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 上午11:15
 * @modify by reason:{方法名}:{原因}
 */

public interface IMapController <T>{
    /**
     * 初始化sdk
     * @param t
     */
    void initSdk(T t);

    /**
     * 设置地图点
     * @param latLng
     */
    void setPoint(LatLng latLng);

    /**
     * 模糊搜索地区
     * @param key
     * @param callback
     */
    void startSearch(String key, SearchCallback callback);

    /**
     * 获取位置
     * @param area
     * @param addr
     * @param callback
     */
    void getPosition(String area,String addr,SearchCallback callback);

    /**
     * 设置区域
     * @param area
     */
    void setArea(String area);

    /**
     * 退出销毁资源
     */
    void onDestory();

    void getPrefecture(LatLng latLng,GetPrefectureCallback onGetPrefecture);

    interface SearchCallback{
        void callback(List<MapAddrBean> been);
    }
    interface GetPrefectureCallback{
        void callback(String prefecture);
    }

    interface LocationCallback{
        void callback(LatLng latLng);
    }

}
