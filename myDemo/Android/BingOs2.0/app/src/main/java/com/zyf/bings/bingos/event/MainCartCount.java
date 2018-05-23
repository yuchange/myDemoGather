package com.zyf.bings.bingos.event;

/**
 * Created by zhangyifei on 17/9/19.
 */

public class MainCartCount {
    public String cartCount;

    public MainCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public String getCartCount() {

        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }
}
