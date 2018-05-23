package com.bingstar.bingmall.goods.bean;

import android.content.Context;

import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cart.bean.ShopCar;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.net.ClientCallback;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/8 下午2:32
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/8 下午2:32
 * @modify by reason:{方法名}:{原因}
 */
public class GoodsEntity {

    private ProductInfoDetail productInfoDetail;

    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ProductInfoDetail getProductInfoDetail() {
        return productInfoDetail;
    }

    public void setProductInfoDetail(ProductInfoDetail productInfoDetail) {
        this.productInfoDetail = productInfoDetail;
    }

    public void addToCart(final ProductInfoDetail productInfoDetail) {
        LogUtils.Debug_I(GoodsEntity.class, productInfoDetail.getBsjProductName());
        CartClient.addCart(BeanTranslater.productInfoDetailToGoods(productInfoDetail), new ClientCallback<ShopCar>() {
            @Override
            public void onSuccess(ShopCar shopCar) {
                BeanTranslater.productInfoDetailToGoods(productInfoDetail).getProductInfo().setShopCartsId(shopCar.getShoppingCartId());
                EventBus.getDefault().post(new EventMsg(CartClient.class, EventMsg.CARTADDSUCCESS));
            }

            @Override
            public void onFail(int code, String error) {
                EventMsg event = new EventMsg(CartClient.class, EventMsg.CARTADDERR);
                event.setDetailMsg(error);
                EventBus.getDefault().post(event);
            }
        });

    }

    public void quickBuyAddCart() {
        LogUtils.Debug_I(GoodsEntity.class, productInfoDetail.getBsjProductName());
        CartClient.addCart(BeanTranslater.productInfoDetailToGoods(productInfoDetail), new ClientCallback<ShopCar>() {
            @Override
            public void onSuccess(ShopCar shopCar) {
                BeanTranslater.productInfoDetailToGoods(productInfoDetail).getProductInfo().setShopCartsId(shopCar.getShoppingCartId());
                EventBus.getDefault().post(new EventMsg(CartClient.class, EventMsg.CARTADDSUCCESS));
            }

            @Override
            public void onFail(int code, String error) {
                EventBus.getDefault().post(new EventMsg(CartClient.class, EventMsg.CARTADDERR));
            }
        });
    }
}
