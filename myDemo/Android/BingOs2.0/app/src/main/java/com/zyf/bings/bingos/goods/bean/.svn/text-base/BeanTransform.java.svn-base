package com.zyf.bings.bingos.goods.bean;

import android.text.TextUtils;

import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;

import java.math.BigDecimal;

/**
 * Created by zhangyifei on 17/9/12.
 */

public class BeanTransform {
    //商品详情转换购物车参数
    public static AddCartProductInfo goodsDetail2addCart(GoodsDetailBean goodsDetailBean, String spec) {
        AddCartProductInfo addCartProductInfo = new AddCartProductInfo();
        AddCartProductInfo.ProductInfo productInfo = new AddCartProductInfo.ProductInfo();
        GoodsDetailBean.ProductInfoDetailBean productInfoDetail = goodsDetailBean.getProductInfoDetail();
        String memberId = SPUtil.getString(ApplicationHolder.instance, Config.MEMBER_ID);
        if (TextUtils.isEmpty(memberId)) {
            memberId = "18336092504";
        }
        productInfo.setMemberId(memberId);
        productInfo.setCategoryId(productInfoDetail.getCategoryId());
        productInfo.setProductId(productInfoDetail.getBsjProductId());
        productInfo.setProductCode(productInfoDetail.getBsjProductCode());
        productInfo.setProductName(productInfoDetail.getBsjProductname());
        productInfo.setGross_Weight("");  //快递重量 暂时无用
        if (!TextUtils.isEmpty(spec)) {
            productInfo.setSpecificationId(spec);
        } else {
            productInfo.setSpecificationId("");
        }
        productInfo.setWeight(productInfoDetail.getWeight());
        productInfo.setUnit(productInfoDetail.getUnit());

        String priceActual;  //考虑折扣价格
        if (!TextUtils.isEmpty(productInfoDetail.getLimitPrice())) {
            priceActual = productInfoDetail.getLimitPrice();
        } else {
            priceActual = productInfoDetail.getPrice();
        }
        productInfo.setPrice(priceActual);
        productInfo.setCount(productInfoDetail.getCount() + "");
        productInfo.setPicUrl(productInfoDetail.getProductPicList().get(0).getPicUrl());
        BigDecimal price = new BigDecimal(priceActual);
        BigDecimal count = new BigDecimal(productInfoDetail.getCount());
        BigDecimal totalPrice = price.multiply(count);
        double v = totalPrice.doubleValue();
        productInfo.setTotalPrice(v + "");
        addCartProductInfo.setProductInfo(productInfo);
        return addCartProductInfo;
    }


}
