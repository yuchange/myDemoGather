package com.bingstar.bingmall.home.http;

import com.alibaba.fastjson.JSON;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.goods.bean.ProductInfo;
import com.bingstar.bingmall.goods.bean.ProductInfoDetail;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static final String JSON = "{\n" +
            "    \"productInfo\": {\n" +
            "        \"productInfoList\": [\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"7777\",\n" +
            "                \"bsjProductName\": \"婴儿纸巾\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"5632412500\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"263\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/4/1493879366324.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"129\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"price\": \"10.00\",\n" +
            "                \"unit\": \"包\",\n" +
            "                \"bsjProductId\": \"129\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"\",\n" +
            "                \"bsjProductName\": \"奶粉\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"4561236500\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"260\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/4/1493862347151.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"128\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"price\": \"100.00\",\n" +
            "                \"unit\": \"罐\",\n" +
            "                \"bsjProductId\": \"128\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"\",\n" +
            "                \"bsjProductName\": \"婴儿浴巾\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"6523256300\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"255\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/3/1493777321822.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"127\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"price\": \"100.00\",\n" +
            "                \"unit\": \"条\",\n" +
            "                \"bsjProductId\": \"127\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"\",\n" +
            "                \"bsjProductName\": \"婴儿枕头\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"6523652300\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"250\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/3/1493777135652.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"126\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"price\": \"200.00\",\n" +
            "                \"unit\": \"个\",\n" +
            "                \"bsjProductId\": \"126\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"\",\n" +
            "                \"bsjProductName\": \"婴儿被子\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"6523652300\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"245\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/3/1493777088106.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"125\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"price\": \"50.00\",\n" +
            "                \"unit\": \"条\",\n" +
            "                \"bsjProductId\": \"125\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"\",\n" +
            "                \"bsjProductName\": \"婴儿帽子\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"4563214100\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"240\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/3/1493776902952.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"124\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"price\": \"50.00\",\n" +
            "                \"unit\": \"件\",\n" +
            "                \"bsjProductId\": \"124\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"productType\": \"0\",\n" +
            "                \"categoryId\": \"38\",\n" +
            "                \"activity\": \"9.8折\",\n" +
            "                \"bsjProductName\": \"婴儿床\",\n" +
            "                \"isDeleted\": \"1\",\n" +
            "                \"bsjProductCode\": \"4563214500\",\n" +
            "                \"productPicList\": [\n" +
            "                    {\n" +
            "                        \"picId\": \"233\",\n" +
            "                        \"isDefault\": \"1\",\n" +
            "                        \"picUrl\": \"/uploadImage/2017/5/3/1493776737954.png\",\n" +
            "                        \"picType\": \"0\",\n" +
            "                        \"bsjProductId\": \"123\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"weight\": \"1.00\",\n" +
            "                \"categorySearchName\": \"婴儿用品\",\n" +
            "                \"limitPrice\": \"196.0\",\n" +
            "                \"price\": \"200.00\",\n" +
            "                \"unit\": \"张\",\n" +
            "                \"bsjProductId\": \"123\",\n" +
            "                \"brandName\": \"无品牌用\",\n" +
            "                \"categoryName\": \"婴儿用品\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"totalCount\": 7\n" +
            "    }\n" +
            "}";

    @Test
    public void addition_isCorrect() throws Exception {
        ProductInfo productInfo = com.alibaba.fastjson.JSON.parseObject(JSON, ProductInfo.class);
        List<ProductInfoDetail> productInfoList = productInfo.getProductInfo().getProductInfoList();
        for (int i = 0; i < productInfoList.size(); i++) {
            ProductInfoDetail productInfoDetail = productInfoList.get(i);
            String activity = productInfoDetail.getActivity();
            String categoryId = productInfoDetail.getCategoryId();
            System.out.println(activity + "  " + categoryId);

        }
    }

    @Test
    public void test() {
        Integer maxValue = Integer.MAX_VALUE;
        System.out.println(String.valueOf(maxValue).length());
        System.out.println(maxValue);
        int a = 1;
        long b = 2;
        boolean b1 = b > a;
        double sqrt = Math.pow(2, 31) - 1;
        System.out.println(sqrt);
        System.out.println(b1);
    }

    @Test
    public void test2() {
        float price = Float.parseFloat("92.80") * 9973;
        BigDecimal bigDecimal = new BigDecimal(price);
        System.out.println(Util.priceToString(bigDecimal.doubleValue()));
        String s = Util.priceToString(price);
        System.out.println(s);
        double price2 = Double.parseDouble("92.80") * 9973;
        String s2 = Util.priceToString(price2);
        System.out.println(s2);
    }


    public static final String COUPON = "{\n" +
            "  \"couponInfoList\": [\n" +
            "    {\n" +
            "      \"couponRange\": \"部分商品使用\",\n" +
            "      \"couponName\": \"测试2\",\n" +
            "      \"remindText\": \"8小时后过期\",\n" +
            "      \"couponMoney\": \"4\",\n" +
            "      \"couponId\": \"238\",\n" +
            "      \"remindFlag\": \"1\",\n" +
            "      \"couponDetail\": \"无门槛使用\",\n" +
            "      \"couponValidity\": \"2017.08.24 18:00\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void testCoupon() {
        Coupon coupon = com.alibaba.fastjson.JSON.parseObject(COUPON, Coupon.class);
        System.out.println(coupon);

    }
}