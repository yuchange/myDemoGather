package com.zyf.bings.bingos.order.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangshiqi on 2017/9/29.
 */

public class ConfirmOrderBean implements Serializable {
    private List<ItemBean> itemBeanList;

    public List<ItemBean> getItemBeanList() {
        return itemBeanList;
    }

    public void setItemBeanList(List<ItemBean> itemBeanList) {
        this.itemBeanList = itemBeanList;
    }

    @Override
    public String toString() {
        return "ConfirmOrderBean{" +
                "itemBeanList=" + itemBeanList +
                '}';
    }

    public static class ItemBean implements Parcelable {
        private String imgUrl;
        private String spec;
        private int count;
        private String title;
        private String productId;
        private String productCode;
        private String price;
        private String grossWeight;
        private String specificationId;

        public String getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(String specificationId) {
            this.specificationId = specificationId;
        }

        @Override
        public String toString() {
            return "ItemBean{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", spec='" + spec + '\'' +
                    ", count=" + count +
                    ", title='" + title + '\'' +
                    ", productId='" + productId + '\'' +
                    ", productCode='" + productCode + '\'' +
                    ", price='" + price + '\'' +
                    ", grossWeight='" + grossWeight + '\'' +
                    '}';
        }

        public String getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(String grossWeight) {
            this.grossWeight = grossWeight;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.imgUrl);
            dest.writeString(this.spec);
            dest.writeInt(this.count);
            dest.writeString(this.title);
            dest.writeString(this.productId);
            dest.writeString(this.productCode);
            dest.writeString(this.price);
            dest.writeString(this.grossWeight);
        }

        public ItemBean() {
        }

        protected ItemBean(Parcel in) {
            this.imgUrl = in.readString();
            this.spec = in.readString();
            this.count = in.readInt();
            this.title = in.readString();
            this.productId = in.readString();
            this.productCode = in.readString();
            this.price = in.readString();
            this.grossWeight = in.readString();
            this.specificationId = in.readString();
        }

        public static final Parcelable.Creator<ItemBean> CREATOR = new Parcelable.Creator<ItemBean>() {
            @Override
            public ItemBean createFromParcel(Parcel source) {
                return new ItemBean(source);
            }

            @Override
            public ItemBean[] newArray(int size) {
                return new ItemBean[size];
            }
        };

    }
}
