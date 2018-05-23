package com.zyf.bings.bingos.goods;

import android.support.v4.util.ArrayMap;

import com.zyf.bings.bingos.goods.bean.AddCartProductInfo;
import com.zyf.bings.bingos.goods.bean.BeanTransform;
import com.zyf.bings.bingos.goods.bean.GoodsDetailBean;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import java.util.Map;

/**
 * Created by zhangyifei on 17/9/7.
 */

public class GoodsDetailPresenter implements IGoodsDetailContract.GoodsDetailPresenter {

    //商品详情
    private static final String PRODUCT_DETAIL_METHOD = "product.detail";
    private static final String PRODUCT_DETAIL_URL = "/productDetail.shtml";

    //添加购物车
    private static final String CART_ADD_METHOD = "shoppingCart.add";
    private static final String CART_ADD_URL = "/shoppingCartAdd.shtml";

    public static final String PRODUCT_ID = "bsjProductId";


    IGoodsDetailContract.GoodsDetailView mGoodsDetailView;

    public GoodsDetailPresenter(IGoodsDetailContract.GoodsDetailView goodsDetailView) {
        mGoodsDetailView = goodsDetailView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mGoodsDetailView != null) mGoodsDetailView = null;
    }

    public static final String CHINA = "{\n" +
            "    \"厂商\": \"海尔\",\n" +
            "    \"品牌\": \"人数\",\n" +
            "    \"厂址\": \"89.0\",\n" +
            "    \"价格\": null,\n" +
            "    \"数量\": \"80\"\n" +
            "}";

    @Override
    public void getGoodsDetailList(String goodsId) {
        Map<String, String> requestMap = new ArrayMap<>();
        requestMap.put(PRODUCT_ID, goodsId);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, PRODUCT_DETAIL_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(requestMap));
        RxOkClient.doPost(map, PRODUCT_DETAIL_URL, this, null)
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {

                        try {
                           /* JSONObject jsonObject = new JSONObject(data);
                            JSONObject productInfoDetail = jsonObject.getJSONObject("productInfoDetail");
                            JSONObject jsonObject1 = new JSONObject(CHINA);
                            productInfoDetail.put("spec", jsonObject1);

                            String s = jsonObject.toString();
                            Log.e("GoodsDetailPresenter", "onSuccess: " + s);*/
                            GoodsDetailBean goodsDetailBean = GsonFactory.fromJson(data, GoodsDetailBean.class);
                            if (null != goodsDetailBean)
                                mGoodsDetailView.notifyGoodsDetailList(goodsDetailBean);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mGoodsDetailView != null) mGoodsDetailView.listErr(msg);
                    }
                });
    }

    @Override
    public void increaseCount() {

    }

    @Override
    public void decreaseCount() {

    }

    @Override
    public void addCart(GoodsDetailBean goodsDetailBean, String spec) {
        AddCartProductInfo addCartProductInfo = BeanTransform.goodsDetail2addCart(goodsDetailBean, spec);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(addCartProductInfo));
        RxOkClient.doPost(map, CART_ADD_URL, this, PBUtil.getPD(mGoodsDetailView.getViewContext()))
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mGoodsDetailView != null) mGoodsDetailView.addCartMsg("加入购物车成功");
                        CartCountClient.getCartCount();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        if (mGoodsDetailView != null) mGoodsDetailView.addCartMsg(msg);
                    }
                });

    }
}
