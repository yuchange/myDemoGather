package com.bingstar.bingmall.discount;

import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.cart.bean.ShopCar;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.discount.bean.Category;
import com.bingstar.bingmall.discount.bean.KidBaseInfo;
import com.bingstar.bingmall.discount.bean.KidModel;
import com.bingstar.bingmall.discount.bean.KidProductInfo;
import com.bingstar.bingmall.discount.bean.KidShowInfo;
import com.bingstar.bingmall.discount.http.DiscountClient;
import com.bingstar.bingmall.discount.http.DiscountStates;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.goods.bean.ProductInfo;
import com.bingstar.bingmall.goods.bean.ProductInfoDetail;
import com.bingstar.bingmall.net.BingNetStates;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.statistics.StatClient;
import com.yunzhi.lib.net.ConnectStates;
import com.yunzhi.lib.utils.LogUtils;
import com.yunzhi.lib.utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/23 下午7:59
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/23 下午7:59
 * @modify by reason:{方法名}:{原因}
 */

public class DiscountPresent implements IDiscountContract.DiscountPresenter {

    private IDiscountContract.DiscountView discountView;

    public DiscountPresent(IDiscountContract.DiscountView discountView) {
        this.discountView = discountView;
    }

    @Override
    public void unBind() {
        discountView = null;
    }


    @Override
    public void getKidList(final List<KidShowInfo> timeList) {
        DiscountClient.getDisTitle(new ClientCallback<KidBaseInfo>() {
            @Override
            public void onSuccess(KidBaseInfo kidBaseInfo) {
                timeList.clear();
                timeList.addAll(kidBaseInfo.getKidList());
                if (discountView != null) {
                    discountView.notifyTitleRefresh();
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (code == ConnectStates.TIME_OUT) {
                    if (discountView != null) {
                        discountView.listErr();
                    }
                } else if (code == BingNetStates.DATA_ERROR) {
                    if (discountView != null) {
                        discountView.listErr();
                    }
                }
            }
        });
    }

    @Override
    public void getKidInfo(String kidId, String kidTime, final ArrayList<KidProductInfo> kidCommodityInfoArrayList) {
        Map<String, String> map = new ArrayMap<>();
        map.put(DiscountStates.KIDID, kidId);
        map.put(DiscountStates.KIDTIME, kidTime);
        DiscountClient.getKidInfo(map, new ClientCallback<KidModel>() {
            @Override
            public void onSuccess(KidModel kidInfo) {
                kidCommodityInfoArrayList.clear();
                kidInfo.sortList();
                kidCommodityInfoArrayList.addAll(kidInfo.getKidCommodityList());
                discountView.notisfyImg();
            }

            @Override
            public void onFail(int code, String error) {
                if (code == ConnectStates.TIME_OUT) {
                    if (discountView != null) {
                        discountView.listErr();
                    }
                } else if (code == BingNetStates.DATA_ERROR) {
                    if (discountView != null) {
                        discountView.listErr();
                    }
                }
            }
        });
    }

    private long lastTime = 0;

    final int TIME_SPACE = 3000;

    @Override
    public void addToCart(KidProductInfo productInfo) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > TIME_SPACE) {
            lastTime = System.currentTimeMillis();//单品点击统计
            StatClient.upLoadClickCount(productInfo.getProductId(), productInfo.getProductName());
        }
        CartClient.addCart(BeanTranslater.KidCommodityInfoToGoods(productInfo), new ClientCallback<ShopCar>() {
            @Override
            public void onSuccess(ShopCar shopCar) {
                if (discountView != null) {
                    discountView.showToast(R.string.cart_add_success);
                }
            }

            @Override
            public void onFail(int code, String error) {
                if (discountView != null) {
                    discountView.showToast(error);
                }

            }
        });
    }


}
