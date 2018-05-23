package com.bingstar.bingmall.cycle.bean;

/**
 * Created by John on 2017/2/25.
 */

public class CycleEventBus {
    private String categoriesId;

    public CycleEventBus(String categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }
}
