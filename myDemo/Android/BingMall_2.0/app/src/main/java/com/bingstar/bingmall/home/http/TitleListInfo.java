package com.bingstar.bingmall.home.http;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/13 上午11:24
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/13 上午11:24
 * @modify by reason:{方法名}:{原因}
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