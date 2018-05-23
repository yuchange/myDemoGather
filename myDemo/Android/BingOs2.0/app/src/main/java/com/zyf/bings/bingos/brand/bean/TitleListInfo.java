package com.zyf.bings.bingos.brand.bean;

import java.util.List;

/**
 * Created by wangshiqi on 2017/9/12.
 */
public class TitleListInfo{

    private List<CategoryInfo> categoryList;

    public List<CategoryInfo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryInfo> categoryList) {
        this.categoryList = categoryList;
    }

    public class CategoryInfo {
        private String categoryId;

        private String categoryName;

        private String categoryOrder;

        private String activeKbn;

        public String getActiveKbn() {
            return activeKbn;
        }

        public void setActiveKbn(String activeKbn) {
            this.activeKbn = activeKbn;
        }

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
    }
}