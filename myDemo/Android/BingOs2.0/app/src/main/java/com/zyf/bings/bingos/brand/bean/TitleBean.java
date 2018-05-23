package com.zyf.bings.bingos.brand.bean;

import java.util.List;

/**
 * Created by sd on 2017/9/12.
 */

public class TitleBean {

    private List<CategoryListBean> categoryList;

    public List<CategoryListBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public static class CategoryListBean {
        /**
         * categoryName : 优惠价格
         * categoryOrder : 0
         * categoryId : 65
         * activeKbn : 1
         */

        private String categoryName;
        private String categoryOrder;
        private String categoryId;
        private String activeKbn;

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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getActiveKbn() {
            return activeKbn;
        }

        public void setActiveKbn(String activeKbn) {
            this.activeKbn = activeKbn;
        }
    }
}
