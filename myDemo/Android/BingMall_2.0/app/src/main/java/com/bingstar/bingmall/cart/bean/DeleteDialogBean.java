package com.bingstar.bingmall.cart.bean;

/**
 * Created by ${liumegnqiang} on 2017/6/12.
 */

public class DeleteDialogBean {
    private String confirmOrCancel;
    private String deleteFlug;

    public DeleteDialogBean(String confirmOrCancel, String deleteFlug) {
        this.confirmOrCancel = confirmOrCancel;
        this.deleteFlug = deleteFlug;
    }

    public String getConfirmOrCancel() {
        return confirmOrCancel;
    }

    public void setConfirmOrCancel(String confirmOrCancel) {
        this.confirmOrCancel = confirmOrCancel;
    }

    public String getDeleteFlug() {
        return deleteFlug;
    }

    public void setDeleteFlug(String deleteFlug) {
        this.deleteFlug = deleteFlug;
    }
}
