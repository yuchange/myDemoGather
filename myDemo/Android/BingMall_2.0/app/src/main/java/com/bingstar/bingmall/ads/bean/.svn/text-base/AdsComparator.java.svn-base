package com.bingstar.bingmall.ads.bean;

import com.bingstar.bingmall.ads.http.HpgProduct;

import java.util.Comparator;

/**
 * Created by qianhechen on 17/3/23.
 */

public class AdsComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        HpgProduct hpgProduct1 = (HpgProduct) o1;
        HpgProduct hpgProduct2 = (HpgProduct) o2;

        int flag = hpgProduct1.getPosition_id().compareTo(hpgProduct2.getPosition_id());
        return flag;
    }

}
