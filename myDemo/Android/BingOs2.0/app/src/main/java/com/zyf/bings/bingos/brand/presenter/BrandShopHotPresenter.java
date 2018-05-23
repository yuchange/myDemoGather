package com.zyf.bings.bingos.brand.presenter;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.zyf.bings.bingos.brand.bean.HotListBean;
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

public class BrandShopHotPresenter implements IBrandShopListContract.BrandPresenter {
    private IBrandShopListContract.BrandShopListView brandListView;

    public BrandShopHotPresenter(IBrandShopListContract.BrandShopListView brandListView) {
        this.brandListView = brandListView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        brandListView = null;
    }

    private static final String BRAND_QUERY_METHOD = "list.brands.query";
    private static final String BRAND_QUERY_URL = "/listBrandsQuery.shtml";
    @Override
    public void getBrandList(String pageSize, String currentPage) {
        Map<String, String> params = new ArrayMap<>();
        params.put("pageSize", pageSize);
        params.put("currentIndex", currentPage);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, BRAND_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, BRAND_QUERY_URL, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                HotListBean hotListBean = gson.fromJson(data, HotListBean.class);
                if (hotListBean != null) {
                    List<HotListBean.BrandListBean> brandListBeen = hotListBean.getBrandList();
                    brandListView.refreshAdapter(brandListBeen);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }
}
