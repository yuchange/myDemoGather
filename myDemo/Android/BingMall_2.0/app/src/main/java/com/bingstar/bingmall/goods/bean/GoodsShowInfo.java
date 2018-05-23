package com.bingstar.bingmall.goods.bean;

import android.content.Context;

import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cart.bean.ShopCar;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.goods.http.GoodsClient;
import com.bingstar.bingmall.net.ClientCallback;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 功能：
 * Created by yaoyafeng on 17/2/23 下午3:43
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/23 下午3:43
 * @modify by reason:{方法名}:{原因}
 */

public class GoodsShowInfo {

    private String goodsTitle;

    //    private final ObservableField<String> choosNum = new ObservableField<>();
    private String choosNum;

    private String price;

    private String limitPrice;

    private String productname;

    private String grossWeight;

    private String unit;

    private ProductDetailEntity productDetailEntity;


    public String getGrossWeight() {
        return grossWeight;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public String getProductname() {
        return productname;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getChoosNum() {
        return choosNum;
    }

    public void setChoosNum(int choosNum) {
        this.choosNum = String.valueOf(choosNum);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void increaseNum() {
        try {
            int num = Integer.parseInt(getChoosNum());
            if (num != Integer.parseInt(getProductDetailEntity().getProductInfoDetail().getStock())) {
                setChoosNum(++num);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setChoosNum(1);
        }
    }

    public void decreaseNum() {
        try {
            int num = Integer.parseInt(getChoosNum());
            if (num > 1) {
                setChoosNum(--num);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setChoosNum(1);
        }

    }

    public void addToCart(final Context context, final GoodsShowInfo goodsShowInfo) {
        CartClient.addCart(BeanTranslater.goodsShowToGoods(goodsShowInfo), new ClientCallback<ShopCar>() {
            @Override
            public void onSuccess(ShopCar shopCar) {
                BeanTranslater.goodsShowToGoods(goodsShowInfo).getProductInfo().setShopCartsId(shopCar.getShoppingCartId());
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

    public void supportAdd() {
        GoodsClient.supportAdd(productDetailEntity.getProductInfoDetail().getBsjProductId(), new ClientCallback<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    public void quickBuy() {

    }

    public ProductDetailEntity getProductDetailEntity() {
        return productDetailEntity;
    }

    public void setProductDetailEntity(ProductDetailEntity productDetailEntity) {
        this.productDetailEntity = productDetailEntity;
    }


}
