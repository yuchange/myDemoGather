package com.bingstar.bingmall.cart.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cart.http.CartClient;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * Created by qianhechen on 17/2/23.
 */

public class ProductInfoAdd implements Serializable {

    private String shopCartsId;
    private String memberId;//会员id
    private String categoryId;//分类id
    private String productId;//商品id
    private String productName;//商品名称
    private String picUrl;//商品图片
    private String price;//商品价格
    private String count;//商品数量
    private String totalPrice;//总价
    //    private String grossWeight;//商品重量
    private String unit;//单位
    private String productCode;//商品编号
    private String weight;
    private String post_money;
    private String gross_Weight;
    private String productType; // 1：完税商品 (境内商品) 2：保税商品 (跨境商品)
    private String stock;//商品库存

    private String discountExplain;//优惠说明

    private int isSelected = 2; //是否选择 1选择 2未选择


    //0：未发布 11：已发布  1:审核通过 2: 审核未通过   3:上架 4：下架   5：删除
    private String if_show; //是否下架


    private String virtualCount;


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPost_money() {
        return post_money;
    }

    public void setPost_money(String post_money) {
        this.post_money = post_money;
    }

    public String getWeight() {
        if (weight == null) {
            return "";
        }
        return weight;
    }

    public String getDiscountExplain() {
        return discountExplain;
    }

    public void setDiscountExplain(String discountExplain) {
        this.discountExplain = discountExplain;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUnit() {
        if (unit == null) {
            return "";
        }
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getShopCartsId() {
        if (shopCartsId == null) {
            return "";
        }
        return shopCartsId;
    }

    public String getVirtualCount() {
        return virtualCount;
    }

    public void setVirtualCount(String virtualCount) {
        this.virtualCount = virtualCount;
    }


    public void setShopCartsId(String shopCartsId) {
        this.shopCartsId = shopCartsId;
    }

    public String getMemberId() {
        if (memberId == null) {
            return "";
        }
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCategoryId() {
        if (categoryId == null) {
            return "";
        }
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductId() {
        if (productId == null) {
            return "";
        }
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        if (productName == null) {
            return "";
        }
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPicUrl() {
        if (picUrl == null) {
            return "";
        }
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPrice() {
        if (price == null) {
            return "0";
        }
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getIf_show() {
        return if_show;
    }

    public void setIf_show(String if_show) {
        this.if_show = if_show;
    }

    public String getCount() {
        if (count == null) {
            return "0";
        }
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getProductCode() {
        if (productCode == null) {
            return "";
        }
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTotalPrice() {
        if (totalPrice == null) {
            return "";
        }
        return totalPrice;
    }

    public String getGross_Weight() {
        return gross_Weight;
    }

    public void setGross_Weight(String gross_Weight) {
        this.gross_Weight = gross_Weight;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    public int getIsSelected() {
        return isSelected;
    }

    public void setSelected(int selected) {
        this.isSelected = selected;
    }

    public void decreaseNum(ProductInfoAdd productInfoAdd, final UpdateNumCallback callback) {
        int num = Integer.parseInt(getCount());
        if (num > 1) {
            setCount(String.valueOf(--num));
            productInfoAdd.setCount(String.valueOf(num));
            if (num != 0) {
                update(productInfoAdd, new UpdateNumCallback() {
                    @Override
                    public void update() {
                        callback.update();
                    }

                    @Override
                    public void rollback() {
                        int num = Integer.parseInt(getCount());
                        setCount(String.valueOf(++num));
                    }
                });
            }
        }
    }


    public void increaseNum(ProductInfoAdd productInfoAdd, final UpdateNumCallback callback) {
        int num = Integer.parseInt(getCount());
        if (num != 0) {
            setCount(String.valueOf(++num));
            productInfoAdd.setCount(String.valueOf(num));
            update(productInfoAdd, new UpdateNumCallback() {
                @Override
                public void update() {
                    callback.update();
                }

                @Override
                public void rollback() {
                    int num = Integer.parseInt(getCount());
                    if (num > 0) {
                        setCount(String.valueOf(--num));
                    }
                }
            });
        }
    }

    public void editNum(String count, final String originalCount, ProductInfoAdd productInfoAdd, final UpdateNumCallback callback) {
        int num = Integer.parseInt(getCount());
        if (num != 0) {
            setCount(count);
            productInfoAdd.setCount(count);
            update(productInfoAdd, new UpdateNumCallback() {
                @Override
                public void update() {
                    callback.update();
                }

                @Override
                public void rollback() {
                    int num = Integer.parseInt(getCount());
                    if (num > 0) {
                        setCount(originalCount);
                    }
                }
            });
        }
    }

    public void update(ProductInfoAdd productInfoAdd, UpdateNumCallback callback) {
        ProductInfoUpdate productInfoUpdate = new ProductInfoUpdate();
        productInfoUpdate.setMemberId(productInfoAdd.getMemberId());
        productInfoUpdate.setProductInfo(productInfoAdd);
        CartClient.updateCart(productInfoUpdate, callback);
    }

    public interface UpdateNumCallback {
        void update();

        void rollback();
    }

}
