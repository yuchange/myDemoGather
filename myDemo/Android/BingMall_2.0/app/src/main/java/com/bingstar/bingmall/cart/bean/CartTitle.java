package com.bingstar.bingmall.cart.bean;

import java.util.ArrayList;

/**
 * Created by qianhechen on 17/3/7.
 */

public class CartTitle {

    private ArrayList<CartCategoryInfo> categoryList;

    public ArrayList<CartCategoryInfo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<CartCategoryInfo> categoryList) {
        this.categoryList = categoryList;
    }

    public  class CartCategoryInfo{
        private String categoryId;
        private String categoryName;
        private String categoryOrder;
        private boolean isSelected;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryOrder() {
            return categoryOrder;
        }

        public void setCategoryOrder(String categoryOrder) {
            this.categoryOrder = categoryOrder;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
