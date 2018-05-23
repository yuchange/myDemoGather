package com.bingstar.bingmall.ads.bean;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/28 下午2:02
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/28 下午2:02
 * @modify by reason:{方法名}:{原因}
 */
public class AdsInfoBean {

    public final static int TYPE_IMG = 0;
    public final static int TYPE_VIDEO = 1;

    public final static int TYPE_UNKNOW = 0;
    public final static int TYPE_WEB = 3;
    public final static int TYPE_GOODS = 4;

    private String homeId;//广告主键ID
    private String url;      //图片地址\视频地址
    private int numId;          //广告编号
    private int urlType;    //url类型
    private int clickType;
    private String clickContent;
    private String videoImgUrl;
    private String goodsId;
    private String couponIds; //优惠券字符串id

    public String getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
    }

    public AdsInfoBean() {

    }

    public AdsInfoBean(String homeId, String url, int numId) {
        this.homeId = homeId;
        this.url = url;
        this.numId = numId;
    }

    public String getUrl() {
        if (url == null) {
            return "";
        }
        return url;
    }

    public int getNumId() {
        return numId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getHomeId() {
        if (homeId == null) {
            return "";
        }
        return homeId;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public int getClickType() {
        return clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }

    public String getClickContent() {
        return clickContent;
    }

    public void setClickContent(String clickContent) {
        this.clickContent = clickContent;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }


    @Override
    public String toString() {
        return "AdsInfoBean{" +
                "homeId='" + homeId + '\'' +
                ", url='" + url + '\'' +
                ", numId=" + numId +
                ", urlType=" + urlType +
                ", clickType=" + clickType +
                ", clickContent='" + clickContent + '\'' +
                ", videoImgUrl='" + videoImgUrl + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", couponIds='" + couponIds + '\'' +
                '}';
    }
}
