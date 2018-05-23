package com.bingstar.bingmall.cart.bean;

import java.util.ArrayList;

/**
 * Created by zhangyifei on 17/7/4.
 */

public class OneKeyTranslater {


    public static String carListToOneKey(ArrayList<ProductInfoAdd> cartList, String productId, String oneKeyCount) {
        ArrayList<ProductInfoAdd> oneKeyList = new ArrayList<>();
        int remainCount = 0;
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getProductId().equals(productId)) {
                ProductInfoAdd cart = cartList.get(i);
                String originalCount = cart.getCount();
                if (Integer.parseInt(originalCount) >= Integer.parseInt(oneKeyCount)) {
                    remainCount = Integer.parseInt(originalCount) - Integer.parseInt(oneKeyCount);
                }
                cart.setCount(oneKeyCount);
                oneKeyList.add(cart);
            }
        }
        cartList.clear();
        cartList.addAll(oneKeyList);
        return String.valueOf(remainCount);
    }


    public static ArrayList<CartTitle.CartCategoryInfo> catTitleToOneKey(ArrayList<CartTitle.CartCategoryInfo> cartTitleList, String categoryId) {
        ArrayList<CartTitle.CartCategoryInfo> oneKeyTitleList = new ArrayList<>();

        for (int i = 0; i < cartTitleList.size(); i++) {
            if (cartTitleList.get(i).getCategoryId().equals(categoryId)) {
                CartTitle cart = new CartTitle();
                CartTitle.CartCategoryInfo cartCategoryInfo = cart.new CartCategoryInfo();
                cartCategoryInfo.setCategoryId("");
                cartCategoryInfo.setCategoryName("全部");
                cartCategoryInfo.setCategoryOrder("");
                oneKeyTitleList.add(cartCategoryInfo);
                oneKeyTitleList.add(cartTitleList.get(i));
            }
        }
        cartTitleList.clear();
        cartTitleList.addAll(oneKeyTitleList);
        return cartTitleList;
    }
}
