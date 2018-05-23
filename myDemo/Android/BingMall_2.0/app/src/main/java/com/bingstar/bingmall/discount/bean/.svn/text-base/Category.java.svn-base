package com.bingstar.bingmall.discount.bean;

import com.bingstar.bingmall.base.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by qianhechen on 17/2/24.
 */

public class Category {

    private String categoryId;
    private String goodsId;
    private ArrayList<KidTime> kidTimeList;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public ArrayList<KidTime> getKidTimeList() {
        return kidTimeList;
    }

    public void setKidTimeList(ArrayList<KidTime> kidTimeList) {
        this.kidTimeList = kidTimeList;
    }

    public class KidTime{
        private String kidTime;
        private String goodsNumber;

        public String getKidTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(DateUtils.stringToDate(kidTime));
        }

        public void setKidTime(String kidTime) {

            this.kidTime =kidTime;
        }

        public String getGoodsNumber() {
            return goodsNumber;
        }

        public void setGoodsNumber(String goodsNumber) {
            this.goodsNumber = goodsNumber;
        }
    }
}
