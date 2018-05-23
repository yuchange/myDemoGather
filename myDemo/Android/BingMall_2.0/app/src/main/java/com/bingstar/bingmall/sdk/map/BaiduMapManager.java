package com.bingstar.bingmall.sdk.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bingstar.bingmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/9 上午11:16
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/9 上午11:16
 * @modify by reason:{方法名}:{原因}
 */

public class BaiduMapManager implements IMapController<BaiduMap> {

    private String city;

    private BaiduMap baiduMap;

    private Context myContext;

    private BaiduMapManager(Context context) {
        try {
            SDKInitializer.initialize(context.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        myContext = context.getApplicationContext();
    }

    public static synchronized BaiduMapManager getInstance(Context context) {
        return new BaiduMapManager(context);
    }

    @Override
    public void setPoint(LatLng latLng) {
        if (latLng == null) {
            return;
        }
        com.baidu.mapapi.model.LatLng lng = new com.baidu.mapapi.model.LatLng(latLng.getLatitude(), latLng.getLongitude());
        // 添加圆
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x2201A4F1)
                .center(lng).stroke(new Stroke(5, 0xAA01A4F1))
                .radius(200);
        baiduMap.addOverlay(ooCircle);

        //定义Maker坐标点
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_map_gcoding);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions options = new MarkerOptions()
                .position(lng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(options);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(lng)
                .zoom(18.5f)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void startSearch(String key, final SearchCallback callback) {
        final SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {
                List<MapAddrBean> been = new ArrayList<>();
                if (res == null || res.getAllSuggestions() == null) {
                    callback.callback(been);
                    mSuggestionSearch.destroy();
                    return;
                }
                for (SuggestionResult.SuggestionInfo suggestionInfo : res.getAllSuggestions()) {
                    if (suggestionInfo == null || suggestionInfo.pt == null) {
                        continue;
                    }
                    StringBuilder builder = new StringBuilder();
                    String city = "";
                    if (suggestionInfo.city != null) {
                        city = suggestionInfo.city;
                    }
                    if (suggestionInfo.district != null) {
//                        builder.append(suggestionInfo.district);
                        city = city + suggestionInfo.district;
                    }
                    if (suggestionInfo.key != null) {
                        builder.append(suggestionInfo.key);
                    }
                    LatLng latLng = new LatLng(suggestionInfo.pt.latitude, suggestionInfo.pt.longitude);
                    MapAddrBean bean = new MapAddrBean(builder.toString(), city, latLng);
                    been.add(bean);
                }
                callback.callback(been);
                mSuggestionSearch.destroy();
                //获取在线建议检索结果
            }
        };
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(key)
                .city(city));
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);

    }

    @Override
    public void getPosition(String area, String addr, final SearchCallback callback) {
        if (area != null && !area.equals("") && addr != null && !addr.equals("")) {
            city = area;
            startSearch(addr, callback);
        } else {
            LocationClient mLocationClient = new LocationClient(myContext);
            //声明LocationClient类
            mLocationClient.registerLocationListener(new BDLocationListener() {
                @Override
                public void onReceiveLocation(final BDLocation location) {
                    if (location != null) {
                        int type = location.getLocType();
                        if (type == BDLocation.TypeServerError||type==BDLocation.TypeNetWorkException||type== BDLocation.TypeCriteriaException) {
                            callback.callback(null);
                            return;
                        }
                        GeoCoder mSearch = GeoCoder.newInstance();
                        com.baidu.mapapi.model.LatLng ptCenter = new com.baidu.mapapi.model.LatLng(location.getLatitude(), location.getLongitude());
                        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                                .location(ptCenter));
                        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                            @Override
                            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                            }

                            @Override
                            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                                if (reverseGeoCodeResult != null) {
                                    ReverseGeoCodeResult.AddressComponent component = reverseGeoCodeResult.getAddressDetail();
                                    if (component != null && component.city != null) {
                                        if (component.province.equals(component.city)) {
                                            city = component.city + component.district;
                                        } else {
                                            city = component.province + component.city + component.district;
                                        }
                                        setPoint(new LatLng(location.getLatitude(), location.getLongitude()));
                                        List<MapAddrBean> been = new ArrayList<>();
                                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        MapAddrBean bean = new MapAddrBean(component.street + component.streetNumber, city, latLng);
                                        been.add(bean);
                                        callback.callback(been);
                                    }
                                }
                            }
                        });
                    }else {
                        callback.callback(null);
                    }
                }

                @Override
                public void onConnectHotSpotMessage(String s, int i) {

                }
            });
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
            option.setIgnoreKillProcess(false);
            mLocationClient.setLocOption(option);
            mLocationClient.start();
        }
    }

    @Override
    public void setArea(String area) {
        city = area;
    }


    @Override
    public void onDestory() {
        baiduMap.clear();
    }

    @Override
    public void getPrefecture(LatLng latLng, final GetPrefectureCallback onGetPrefecture) {
        if (latLng == null || onGetPrefecture == null) {
            return;
        }
        GeoCoder mSearch = GeoCoder.newInstance();
        com.baidu.mapapi.model.LatLng ptCenter = new com.baidu.mapapi.model.LatLng(latLng.getLatitude(), latLng.getLongitude());
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult != null) {
                    ReverseGeoCodeResult.AddressComponent component = reverseGeoCodeResult.getAddressDetail();
                    if (component != null && component.city != null) {
                        String addr;
                        if (component.province.equals(component.city)) {
                            addr = component.city + component.district;
                        } else {
                            addr = component.province + component.city + component.district;
                        }
                        onGetPrefecture.callback(addr);
                    } else {
                        onGetPrefecture.callback(city);
                    }
                }
            }
        });
    }

    @Override
    public void initSdk(BaiduMap baiduMap) {
        this.baiduMap = baiduMap;
        city = myContext.getString(R.string.map_default_position);
//        loadCity();
    }


    private void loadCity() {
        LocationClient mLocationClient = new LocationClient(myContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                GeoCoder mSearch = GeoCoder.newInstance();
                com.baidu.mapapi.model.LatLng ptCenter = new com.baidu.mapapi.model.LatLng(location.getLatitude(), location.getLongitude());
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(ptCenter));
                mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        if (reverseGeoCodeResult != null) {
                            ReverseGeoCodeResult.AddressComponent component = reverseGeoCodeResult.getAddressDetail();
                            if (component != null && component.city != null) {
                                city = component.province + component.city;
                            }
                        }
                    }
                });

            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setIgnoreKillProcess(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public void getLatLng(final LocationCallback callback) {
        LocationClient mLocationClient = new LocationClient(myContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location){
                int type = location.getLocType();
                if (type == BDLocation.TypeServerError||type==BDLocation.TypeNetWorkException||type== BDLocation.TypeCriteriaException) {
                    callback.callback(null);
                }else {
                    callback.callback(new LatLng(location.getLatitude(),location.getLongitude()));
                }
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setIgnoreKillProcess(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


}
