package com.zyf.bings.bingos.brand.presenter;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.zyf.bings.bingos.brand.bean.BrandDetailBean;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangshiqi on 2017/9/12.
 */

public class BrandDetailPresenter implements IBrandDetailContract.BrandPresenter {
    private IBrandDetailContract.BrandDetailListView brandListView;

    public BrandDetailPresenter(IBrandDetailContract.BrandDetailListView brandListView) {
        this.brandListView = brandListView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        brandListView = null;
    }

    private static final String BRAND_QUERY_METHOD = "list.brand.query";
    private static final String BRAND_QUERY_URL = "/listBrandQuery.shtml";

    @Override
    public void getBrandList(String pageSize, String currentPage, String brandId) {
        Map<String, String> params = new ArrayMap<>();
        params.put("pageSize", pageSize);
        params.put("currentIndex", currentPage);
        params.put("brand_id", brandId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, BRAND_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, BRAND_QUERY_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    Gson gson = new Gson();
                    BrandDetailBean brandDetailBean = gson.fromJson(data, BrandDetailBean.class);
                    if (brandDetailBean != null) {
                        List<BrandDetailBean.ProductInfoListBean> brandListBeen = brandDetailBean.getProductInfoList();
                        Log.d("BrandDetailPresenter", "brandListBeen:" + brandListBeen);
                        brandListView.refreshAdapter(brandListBeen);
                    } else {
                        brandListView.listErr();
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }
}
