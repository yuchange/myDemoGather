package com.bingstar.bingmall.goods.bean;

import android.support.annotation.NonNull;

/**
 * Created by zhangyifei on 17/7/9.
 */

public class ProductVideo implements Comparable<ProductVideo> {

    /*bsjProductId 商品id
    videoId 商品视频id
    picUrl 商品图片URL
    videoUrl 商品视频URL*/

    public String bsjProductId;
    public String videoId;
    public String picUrl;
    public String videoUrl;

    @Override
    public int compareTo(@NonNull ProductVideo o) {
        Integer i1 = Integer.parseInt(this.videoId);
        Integer i2 = Integer.parseInt(o.videoId);
        if (i1 != null && i2 != null) {
            return i1.compareTo(i2);
        } else {
            return -1;
        }
    }
}
