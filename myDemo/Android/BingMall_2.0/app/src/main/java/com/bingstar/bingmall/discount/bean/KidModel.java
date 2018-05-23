package com.bingstar.bingmall.discount.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by qianhechen on 17/2/25.
 */

public class KidModel {

    private ArrayList<KidProductInfo> kidCommodityList;

    public ArrayList<KidProductInfo> getKidCommodityList() {
        return kidCommodityList;
    }

    public void setKidCommodityList(ArrayList<KidProductInfo> kidCommodityList) {
        this.kidCommodityList = kidCommodityList;
    }

    public void sortList(){
        Comparator<KidProductInfo> comp = new SortComparator();
        Collections.sort(kidCommodityList,comp);
    }



    private class SortComparator implements Comparator<KidProductInfo> {
        @Override
        public int compare(KidProductInfo o1, KidProductInfo o2) {
            return (Integer.parseInt(o1.getSort()) - Integer.parseInt(o2.getSort()));
        }
    }
}
