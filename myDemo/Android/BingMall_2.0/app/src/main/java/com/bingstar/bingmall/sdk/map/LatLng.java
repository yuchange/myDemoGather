package com.bingstar.bingmall.sdk.map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 下午12:43
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 下午12:43
 * @modify by reason:{方法名}:{原因}
 */

public class LatLng {
    private double latitude;

    private double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
