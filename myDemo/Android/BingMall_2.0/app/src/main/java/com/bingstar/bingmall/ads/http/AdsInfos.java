package com.bingstar.bingmall.ads.http;

import java.util.List;

/**
 * Created by qianhechen on 17/3/22.
 */

public class AdsInfos {

    private List<Banner> bannerList;
    private Kid kid;
    private Cycle cycle;
    private List<HpgProduct> hpgProductList;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public List<HpgProduct> getHpgProductList() {
        return hpgProductList;
    }

    public void setHpgProductList(List<HpgProduct> hpgProductList) {
        this.hpgProductList = hpgProductList;
    }

    public class Banner{
        private String home_id;
        private String imgUrl;
        private String videoUrl;
        private String num_id;
        private String fileUrl;
        private String goods_id;

        public String getCouponIds() {
            return couponIds;
        }

        public void setCouponIds(String couponIds) {
            this.couponIds = couponIds;
        }

        private String couponIds;

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getNum_id() {
            return num_id;
        }

        public void setNum_id(String num_id) {
            this.num_id = num_id;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }

    public class Kid{
        private String home_id;
        private String imgUrl;

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }


    }

    public class Cycle{
        private String home_id;
        private String imgUrl;

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

}
