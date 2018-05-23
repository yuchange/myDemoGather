package com.bingstar.bingmall.home.bean;

import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.home.http.TitleListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/7 下午10:33
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/7 下午10:33
 * @modify by reason:{方法名}:{原因}
 */

public class BeanTranslator {

    public static List<TitleBean> categoryToTitle(List<TitleListInfo.CategoryInfo> categoryInfos) {
        List<TitleBean> titleBeen = new ArrayList<>();
        if (categoryInfos != null) {
            for (TitleListInfo.CategoryInfo categoryInfo : categoryInfos) {
                TitleBean titleBean = new TitleBean();
                titleBean.setSelected(false);
                if (categoryInfo.getCategoryId() != null) {
                    titleBean.setCategoryId(categoryInfo.getCategoryId());
                }
                if (categoryInfo.getCategoryName() != null) {
                    titleBean.setCategoryName(categoryInfo.getCategoryName());
                }
                if (categoryInfo.getCategoryOrder() != null) {
                    titleBean.setCategoryOrder(Integer.parseInt(categoryInfo.getCategoryOrder()));
                }
                if (categoryInfo.getActiveKbn() != null) {
                    titleBean.setActiveKbn(categoryInfo.getActiveKbn());
                }
                titleBeen.add(titleBean);
            }
        }
        return titleBeen;
    }

    public static List<TitleBean> categoryToTitle(CartTitle cartTitle) {
        List<TitleBean> titleBeen = new ArrayList<>();
        if (cartTitle != null && cartTitle.getCategoryList() != null) {
            for (CartTitle.CartCategoryInfo categoryInfo : cartTitle.getCategoryList()) {
                TitleBean titleBean = new TitleBean();
                titleBean.setSelected(false);
                if (categoryInfo.getCategoryId() != null) {
                    titleBean.setCategoryId(categoryInfo.getCategoryId());
                }
                if (categoryInfo.getCategoryName() != null) {
                    titleBean.setCategoryName(categoryInfo.getCategoryName());
                }
                if (categoryInfo.getCategoryOrder() != null) {
                    titleBean.setCategoryOrder(Integer.parseInt(categoryInfo.getCategoryOrder()));
                }
                titleBeen.add(titleBean);
            }
        }
        return titleBeen;
    }
}
