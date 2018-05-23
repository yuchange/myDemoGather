package com.bingstar.bingmall.home.bean;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/7 下午8:28
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/7 下午8:28
 * @modify by reason:{方法名}:{原因}
 */

public class TitleBean {

    private String categoryId;

    private String categoryName;

    private boolean isSelected;

    private int categoryOrder;      //TODO  排序字段  需要进行排序

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

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleBean titleBean = (TitleBean) o;

        if (isSelected != titleBean.isSelected) return false;
        if (categoryId != null ? !categoryId.equals(titleBean.categoryId) : titleBean.categoryId != null) {
            return false;
        }
        return categoryName != null ? categoryName.equals(titleBean.categoryName) : titleBean.categoryName == null;

    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
