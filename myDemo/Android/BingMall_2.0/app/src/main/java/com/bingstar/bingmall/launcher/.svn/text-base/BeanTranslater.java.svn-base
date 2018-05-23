package com.bingstar.bingmall.launcher;

/**
 * 功能：
 * Created by yaoyafeng on 17/4/1 上午9:26
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/4/1 上午9:26
 * @modify by reason:{方法名}:{原因}
 */

public class BeanTranslater {

    public static OrderNumBean infoToBean(OrderNumInfo orderNumInfo){
        if (orderNumInfo==null){
            return null;
        }
        OrderNumBean orderNumBean = new OrderNumBean();
        for (OrderNumInfo.ZorderNumber zorderNumber :orderNumInfo.getZorderNumberList()){
            String zState = zorderNumber.getZstate();
            if (zState!=null){
                switch (zState){
                    case "0":
                        orderNumBean.setUnPayNum(zorderNumber.getNumber());
                        break;
                    case "1":
                        orderNumBean.setUnTransNum(zorderNumber.getNumber());
                        break;
                    case "2":
                        orderNumBean.setUnCheckNum(zorderNumber.getNumber());
                        break;
                }
            }
        }
        return orderNumBean;
    }
}
