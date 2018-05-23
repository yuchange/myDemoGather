package com.bingstar.bingmall.sdk.map;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 下午1:02
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 下午1:02
 * @modify by reason:{方法名}:{原因}
 */

public class MapAddrBean {

    //详细地址
    private String address;

    //行政区划
    private String city;

    private LatLng latLng;

    public MapAddrBean(String address,String city,LatLng latLng){
        this.address = address;
        this.city = city;
        this.latLng = latLng;
    }


    public String getAddress() {
        return address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return address+"------"+city;
    }
}
