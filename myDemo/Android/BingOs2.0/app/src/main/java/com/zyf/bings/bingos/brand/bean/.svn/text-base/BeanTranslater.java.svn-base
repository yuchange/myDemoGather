package com.zyf.bings.bingos.brand.bean;

import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/21 下午6:23
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/21 下午6:23
 * @modify by reason:{方法名}:{原因}
 */

public class BeanTranslater {
    public static void productToGoods(List<GoodsEntity> goodsEntities, List<ProductInfoDetail> infoDetails) {
        if (infoDetails == null) {
            return;
        }
        for (ProductInfoDetail infoDetail : infoDetails) {
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setProductInfoDetail(infoDetail);
            goodsEntity.setNum(0);
            goodsEntities.add(goodsEntity);
        }
    }

    /**
     * 商品列表页面添加商品到购物车参数校验
     *
     * @param goodsShowInfo
     * @return
     */
//    public static ProductInfos goodsShowToGoods(GoodsShowInfo goodsShowInfo) {
//        ProductInfos productInfos = new ProductInfos();
//        ProductInfoAdd productInfoAdd = new ProductInfoAdd();
//        productInfoAdd.setMemberId(User.getIntance().getMemberId());
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getCategoryId() != null) {
//            productInfoAdd.setCategoryId(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getCategoryId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsDetailFragment_final);
//        }
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getBsjProductName() != null) {
//            productInfoAdd.setProductName(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getBsjProductName());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsDetailFragment_final);
//        }
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getWeight() != null) {
//            productInfoAdd.setWeight(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getWeight());
//        } else {
//            productInfoAdd.setWeight("");
//        }
//        productInfoAdd.setCount(goodsShowInfo.getChoosNum());
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getUnit() != null) {
//            productInfoAdd.setUnit(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getUnit());
//        } else {
//            productInfoAdd.setUnit("");
//        }
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getBsjProductId() != null) {
//            productInfoAdd.setProductId(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getBsjProductId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsDetailFragment_final);
//        }
//        //优先判断是否存在折后价格
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getLimitPrice() != null && !goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getLimitPrice().equals("")) {
//            productInfoAdd.setPrice(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getLimitPrice());
//        } else {
//            if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getPrice() != null) {
//                productInfoAdd.setPrice(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getPrice());
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsDetailFragment_final);
//            }
//        }
//        if (goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getBsjProductCode() != null) {
//            productInfoAdd.setProductCode(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getBsjProductCode());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsDetailFragment_final);
//            //toastErr(ErrorFragment.GoodsDetailFragment);
//        }
//        productInfoAdd.setPicUrl(goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getProductPicList().get(0).getPicUrl());
//        float totalPrice = Float.parseFloat(productInfoAdd.getPrice()) * Integer.parseInt(goodsShowInfo.getChoosNum());
//        productInfoAdd.setTotalPrice(String.valueOf(totalPrice));
//        productInfos.setProductInfo(productInfoAdd);
//        return productInfos;
//    }


    /**
     * 商品列表一键下单
     *
     * @param productInfoAdd 创建订单的商品对象
     * @return
     */
//    public static void createLOrder(ProductInfoAdd productInfoAdd, String payMoney, String productMoney) {
//        CreateOrderInfo createOrderInfo = new CreateOrderInfo();
//        Trade trade = new Trade();
//        trade.setCycleflag("00");
//        trade.setCouponId("0");
//        trade.setCouponMoney("0");
//        trade.setThirdOrderCode(Util.getTimeBIZService());
//
//        Receiver receiver = new Receiver();
//        /**
//         * 创建订单之前判断 Receiver是否是空
//         */
//        receiver.setId(User.getIntance().getMobile());
//        receiver.setName(User.getIntance().getName());
//        receiver.setMemberId(User.getIntance().getMemberId());
//        receiver.setAddress(User.getIntance().getAddress());
//        receiver.setMobile(User.getIntance().getMobile());
//        receiver.setPhone(User.getIntance().getPhone());
//        receiver.setRegion(User.getIntance().getRegion());
//        receiver.setBuyer_IDcard(User.getIntance().getBuyer_IDcard());
//        /**
//         * TODO  缺少orderUserName   但是创建订单成功
//         *
//         * 地址列表没有返回orderUserName字段
//         */
//        List<TradeItem> tradeItemList = new ArrayList<>();
//
//        TradeItem tradeItem = new TradeItem();
//        if (productInfoAdd.getProductId() != null) {
//            tradeItem.setBsjProductId(productInfoAdd.getProductId());
//        } else {
////                toastErr(ErrorFragment.CartFragment);
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//        }
//        if (productInfoAdd.getProductCode() != null) {
//            tradeItem.setBsjProductCode(productInfoAdd.getProductCode());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//        }
//        if (productInfoAdd.getProductName() != null) {
//            tradeItem.setBsjProductName(productInfoAdd.getProductName());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//        }
//        if (productInfoAdd.getCount() != null) {
//            tradeItem.setNumber(productInfoAdd.getCount() + "");
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//        }
//        if (productInfoAdd.getPrice() != null) {
//            tradeItem.setPrice(productInfoAdd.getPrice());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//        }
//        if (productInfoAdd.getGross_Weight() != null) {
//            tradeItem.setGrossWeight(productInfoAdd.getGross_Weight());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//        }
//
//        double totalfree = Double.parseDouble(productInfoAdd.getPrice()) * Double.parseDouble(productInfoAdd.getCount());
//
//        tradeItem.setTotalFee(String.format("%.2f", totalfree) + "");
//        tradeItem.setDiscountFee("0");
//        tradeItemList.add(tradeItem);
//
//
//        trade.setPaymentAmount(payMoney);
//        trade.setProductAmount(productMoney);
//        trade.setTradeItemList(tradeItemList);
//        trade.setReceiver(receiver);
//        createOrderInfo.setTrade(trade);
//
//        OrderClient.createOrder(createOrderInfo, new ClientCallback<CreateOrderBeanInfo>() {
//            @Override
//            public void onSuccess(CreateOrderBeanInfo createOrderBeanInfo) {
//                /**
//                 * 支付宝支付查询接口
//                 */
//                LogUtils.Debug_E(BeanTranslater.class, "创建订单成功");
//                /**
//                 * 支付宝支付查询
//                 */
//                if (createOrderBeanInfo.getZorderinfo().getZorder_no() != null) {
//                    EventBus.getDefault().post(new PayEvent(createOrderBeanInfo.getZorderinfo().getZorder_no()));
//                } else {
//                    onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
//                }
//            }
//
//            @Override
//            public void onFail(int code, String error) {
//                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.CartFragment_final);
//            }
//        });
//    }


    /**
     * 商品详情页面添加商品到购物车参数校验
     *
     * @param productInfoDetail 商品详情
     * @return
     */
//    public static ProductInfos productInfoDetailToGoods(ProductInfoDetail productInfoDetail) {
//        ProductInfos productInfos = new ProductInfos();
//        ProductInfoAdd productInfoAdd = new ProductInfoAdd();
//        productInfoAdd.setMemberId(User.getIntance().getMemberId());
//        if (productInfoDetail.getCategoryId() != null) {
//            productInfoAdd.setCategoryId(productInfoDetail.getCategoryId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsListFragment_final);
//        }
//        if (productInfoDetail.getBsjProductName() != null) {
//            productInfoAdd.setProductName(productInfoDetail.getBsjProductName());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsListFragment_final);
//        }
//        productInfoAdd.setCount("1");
//        if (productInfoDetail.getUnit() != null) {
//            productInfoAdd.setUnit(productInfoDetail.getUnit());
//        } else {
//            productInfoAdd.setUnit("");
//        }
//        if (productInfoDetail.getWeight() != null) {
//            productInfoAdd.setWeight(productInfoDetail.getWeight());
//        } else {
//            productInfoAdd.setWeight("");
//        }
//        if (productInfoDetail.getBsjProductId() != null) {
//            productInfoAdd.setProductId(productInfoDetail.getBsjProductId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsListFragment_final);
//        }
//        //优先判断是否存在折后价格
//        if (productInfoDetail.getLimitPrice() != null && !productInfoDetail.getLimitPrice().equals("")) {
//            productInfoAdd.setPrice(productInfoDetail.getLimitPrice());
//        } else {
//            if (productInfoDetail.getPrice() != null) {
//                productInfoAdd.setPrice(productInfoDetail.getPrice());
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsListFragment_final);
//            }
//        }
//        if (productInfoDetail.getBsjProductCode() != null) {
//            productInfoAdd.setProductCode(productInfoDetail.getBsjProductCode());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.GoodsListFragment_final);
//        }
//        productInfoAdd.setPicUrl(productInfoDetail.getProductPicList().get(0).getPicUrl());
//        float totalPrice = Float.parseFloat(productInfoAdd.getPrice());
//        productInfoAdd.setTotalPrice(String.valueOf(totalPrice));
//        productInfos.setProductInfo(productInfoAdd);
//        return productInfos;
//    }

    /**
     * 今日特惠添加到购物车参数校验
     *
     * @param kidCommodityInfo
     * @return
     */
//    public static ProductInfos KidCommodityInfoToGoods(KidProductInfo kidCommodityInfo) {
//        ProductInfos productInfos = new ProductInfos();
//        ProductInfoAdd productInfoAdd = new ProductInfoAdd();
//        productInfoAdd.setMemberId(User.getIntance().getMemberId());
//        if (kidCommodityInfo.getCategoryId() != null) {
//            productInfoAdd.setCategoryId(kidCommodityInfo.getCategoryId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.DisCountFragment_final);
//        }
//        if (kidCommodityInfo.getProductName() != null) {
//            productInfoAdd.setProductName(kidCommodityInfo.getProductName());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.DisCountFragment_final);
//        }
//        productInfoAdd.setCount("1");
//        if (kidCommodityInfo.getUnit() != null) {
//            productInfoAdd.setUnit(kidCommodityInfo.getUnit());
//        } else {
//            productInfoAdd.setUnit("");
//        }
//        if (kidCommodityInfo.getWeight() != null) {
//            productInfoAdd.setWeight(kidCommodityInfo.getWeight());
//        } else {
//            productInfoAdd.setWeight("");
//        }
//        if (kidCommodityInfo.getProductId() != null) {
//            productInfoAdd.setProductId(kidCommodityInfo.getProductId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.DisCountFragment_final);
//        }
//        if (kidCommodityInfo.getLimitPrice() != null) {
//            productInfoAdd.setPrice(kidCommodityInfo.getLimitPrice());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.DisCountFragment_final);
//        }
//        //productInfoAdd.setProductCode(kidCommodityInfo.);
//        productInfoAdd.setPicUrl(kidCommodityInfo.getPicUrl());
//        float totalPrice = Float.parseFloat(productInfoAdd.getPrice());
//        productInfoAdd.setTotalPrice(String.valueOf(totalPrice));
//        productInfos.setProductInfo(productInfoAdd);
//        return productInfos;
//    }

    /**
     * 周期购快捷下单 创建订单商品对象；参数校验（按照一般的商品创建订单流程走）
     *
     * @param cycleProductInfoQDetail 创建订单的商品对象
     * @return
     */
//    public static CreateOrderInfo createQOrder(CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail cycleProductInfoQDetail) {
//        CreateOrderInfo createOrderInfo = new CreateOrderInfo();
//        Trade trade = new Trade();
//        if (cycleProductInfoQDetail.getTotalPrice() != null) {
//            /**
//             *  应该填写总价格
//             */
//            trade.setPaymentAmount(Double.parseDouble(cycleProductInfoQDetail.getNumber()) * Double.parseDouble(cycleProductInfoQDetail.getPrice()) + "");//必须填写单价
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        trade.setCycleflag("02");
//        trade.setThirdOrderCode(Util.getTimeBIZService());
//        trade.setCouponId("0");
//        trade.setCouponMoney("0");
//
//        Receiver receiver = new Receiver();
//        receiver.setId(User.getIntance().getMemberId());
//        receiver.setName(User.getIntance().getName());
//        receiver.setMemberId(User.getIntance().getMemberId());
//        receiver.setAddress(User.getIntance().getAddress());
//        receiver.setMobile(User.getIntance().getMobile());
//        receiver.setPhone(User.getIntance().getPhone());
//        receiver.setRegion(User.getIntance().getRegion());
//        receiver.setBuyer_IDcard(User.getIntance().getBuyer_IDcard());
//
//        TradeItem tradeItem = new TradeItem();
//        if (cycleProductInfoQDetail.getProductId() != null) {
//            tradeItem.setBsjProductId(cycleProductInfoQDetail.getProductId());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        if (cycleProductInfoQDetail.getProductCode() != null) {
//            tradeItem.setBsjProductCode(cycleProductInfoQDetail.getProductCode());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        if (cycleProductInfoQDetail.getProductName() != null) {
//            tradeItem.setBsjProductName(cycleProductInfoQDetail.getProductName());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        if (cycleProductInfoQDetail.getNumber() != null) {
//            tradeItem.setNumber(cycleProductInfoQDetail.getNumber() + "");
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        if (cycleProductInfoQDetail.getPrice() != null) {
//            tradeItem.setPrice(cycleProductInfoQDetail.getPrice());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        if (cycleProductInfoQDetail.getGrossWeight() != null) {
//            tradeItem.setGrossWeight(cycleProductInfoQDetail.getGrossWeight());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);
//        }
//        double totalfee = Double.parseDouble(cycleProductInfoQDetail.getNumber()) * Double.parseDouble(cycleProductInfoQDetail.getPrice());
//        tradeItem.setTotalFee(totalfee + "");
//        tradeItem.setDiscountFee("0");
//        List<TradeItem> tradeItemList = new ArrayList<>();
//        tradeItemList.add(tradeItem);
//
//        trade.setTradeItemList(tradeItemList);
//        trade.setReceiver(receiver);
//        createOrderInfo.setTrade(trade);
//        return createOrderInfo;
//    }

    /**
     * 周期购下单， 创建商品订单对象； 参数校验（按照周期购创建订单流程走）
     *
     * @param cycleProductInfoDetail 商品详情
     * @param totalprice             总价格
     * @param totalDate              购买的天数
     * @param totalnum               购买的数量
     * @return 创建的CreateOrderInfo类，创建订单需要该参数
     */
//    public static CreateOrderInfo createOrder(CycleProductInfo.CycleProduct.CycleProductInfoDetail cycleProductInfoDetail,
//                                              String totalprice, String totalDate, String totalnum) {
//        CreateOrderInfo createOrderInfo = new CreateOrderInfo();
//        Trade trade = new Trade();
//        if (totalprice != null) {
//            trade.setPaymentAmount("" + totalprice);
//        } else {
//            /**
//             * 现将CycleDialog关闭 ， 再将CycleListFragment置为ErrorFragment页面（下同）
//             */
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        trade.setCycleflag("01");
//        trade.setThirdOrderCode(Util.getTimeBIZService());
//        trade.setCouponId("0");
//        trade.setCouponMoney("0");
//        if (totalDate != null) {
//            trade.setCycledate(totalDate);
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        if (totalnum != null) {
//            trade.setCyclenum(totalnum);
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//
//        /**
//         * 收货人信息
//         */
//        Receiver receiver = new Receiver();
//        receiver.setId(User.getIntance().getMemberId());
//        receiver.setMemberId(User.getIntance().getMemberId());
//        receiver.setName(User.getIntance().getName());
//        receiver.setAddress(User.getIntance().getAddress());
//        receiver.setMobile(User.getIntance().getMobile());
//        receiver.setPhone(User.getIntance().getPhone());
//        receiver.setRegion(User.getIntance().getRegion());
//        receiver.setBuyer_IDcard(User.getIntance().getBuyer_IDcard());
//
//        /**
//         * 商品信息
//         */
//        TradeItem tradeItem = new TradeItem();
//        if (cycleProductInfoDetail.getProductId() != null) {
//            tradeItem.setBsjProductId(cycleProductInfoDetail.getProductId());//商品ID
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        if (cycleProductInfoDetail.getProductCode() != null) {
//            tradeItem.setBsjProductCode(cycleProductInfoDetail.getProductCode());//商品编码
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        if (cycleProductInfoDetail.getProductName() != null) {
//            tradeItem.setBsjProductName(cycleProductInfoDetail.getProductName());//商品名称
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        tradeItem.setNumber(cycleProductInfoDetail.getNumber());//数量
//        if (cycleProductInfoDetail.getPrice() != null) {
//            tradeItem.setPrice(cycleProductInfoDetail.getPrice());//商品单价
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        if (cycleProductInfoDetail.getGrossWeight() != null) {
//            tradeItem.setGrossWeight(cycleProductInfoDetail.getGrossWeight());
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycledetail_dialog_final);
//        }
//        tradeItem.setTotalFee(totalprice + "");//商品总价格
//        tradeItem.setDiscountFee("0");//是否折扣
//        List<TradeItem> tradeItemList = new ArrayList<>();
//        tradeItemList.add(tradeItem);
//
//        trade.setTradeItemList(tradeItemList);
//        trade.setReceiver(receiver);
//        createOrderInfo.setTrade(trade);
//        return createOrderInfo;
//
//    }

    /**
     * 购物车创建订单
     *
//     * @param cartList 购物车列表
     */
//    @SuppressLint("DefaultLocale")
//    public static void createCartOrder(ArrayList<ProductInfoAdd> cartList, String totalMoney, String couponId, String couponMoney, String cycleflag) {
//        CreateOrderInfo createOrderInfo = new CreateOrderInfo();
//        Trade trade = new Trade();
//        trade.setCycleflag(cycleflag);
//        trade.setCouponId(couponId);
//        trade.setCouponMoney(couponMoney);
//        trade.setThirdOrderCode(Util.getTimeBIZService());
//
//        Receiver receiver = new Receiver();
//        /**
//         * 创建订单之前判断 Receiver是否是空
//         */
//        receiver.setId(User.getIntance().getMobile());
//        receiver.setName(User.getIntance().getName());
//        receiver.setMemberId(User.getIntance().getMemberId());
//        receiver.setAddress(User.getIntance().getAddress());
//        receiver.setMobile(User.getIntance().getMobile());
//        receiver.setPhone(User.getIntance().getPhone());
//        receiver.setRegion(User.getIntance().getRegion());
//        receiver.setBuyer_IDcard(User.getIntance().getBuyer_IDcard());
//        Log.i("TAG", receiver.toString());
//        /**
//         * TODO  缺少orderUserName   但是创建订单成功
//         *
//         * 地址列表没有返回orderUserName字段
//         */
//        List<TradeItem> tradeItemList = new ArrayList<>();
//        BigDecimal paymount = new BigDecimal("0.0");
//        for (int i = 0; i < cartList.size(); i++) {
//            TradeItem tradeItem = new TradeItem();
//            if (cartList.get(i).getProductId() != null) {
//                tradeItem.setBsjProductId(cartList.get(i).getProductId());
//            } else {
////                toastErr(ErrorFragment.CartFragment);
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//            }
//            if (cartList.get(i).getProductCode() != null) {
//                tradeItem.setBsjProductCode(cartList.get(i).getProductCode());
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//            }
//            if (cartList.get(i).getProductName() != null) {
//                tradeItem.setBsjProductName(cartList.get(i).getProductName());
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//            }
//            if (cartList.get(i).getCount() != null) {
//                tradeItem.setNumber(cartList.get(i).getCount() + "");
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//            }
//            if (cartList.get(i).getPrice() != null) {
//                tradeItem.setPrice(cartList.get(i).getPrice());
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//            }
//            if (cartList.get(i).getGross_Weight() != null) {
//                tradeItem.setGrossWeight(cartList.get(i).getGross_Weight());
//            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
//            }
//
//            BigDecimal price = new BigDecimal(cartList.get(i).getPrice());
//            BigDecimal count = new BigDecimal(cartList.get(i).getCount());
//            BigDecimal totalfree = price.multiply(count);
//            tradeItem.setTotalFee(com.bingstar.bingmall.base.Util.priceToString(totalfree.doubleValue()));
//            tradeItem.setDiscountFee("0");
//            tradeItemList.add(tradeItem);
//            paymount = paymount.add(totalfree);
//        }
//        BigDecimal totalM = new BigDecimal(totalMoney);
//        BigDecimal couponM = new BigDecimal(couponMoney);
//        trade.setPaymentAmount(com.bingstar.bingmall.base.Util.priceToString(totalM.doubleValue()));
//        trade.setProductAmount(com.bingstar.bingmall.base.Util.priceToString(totalM.add(couponM).doubleValue()));
//        trade.setTradeItemList(tradeItemList);
//        trade.setReceiver(receiver);
//        createOrderInfo.setTrade(trade);
//
//        OrderClient.createOrder(createOrderInfo, new ClientCallback<CreateOrderBeanInfo>() {
//            @Override
//            public void onSuccess(CreateOrderBeanInfo createOrderBeanInfo) {
//                /**
//                 * 支付宝支付查询接口
//                 */
//                LogUtils.Debug_E(BeanTranslater.class, "创建订单成功");
//                OrderFinishEvent finishEvent = new OrderFinishEvent("1");
//                EventBus.getDefault().post(finishEvent);
//                /**
//                 * 支付宝支付查询
//                 */
//                if (createOrderBeanInfo.getZorderinfo().getZorder_no() != null) {
//                    PayEvent event = new PayEvent(createOrderBeanInfo.getZorderinfo().getZorder_no());
//                    EventBus.getDefault().post(event);
//                } else {
//                    onFail(BingNetStates.DATA_ERROR, BingNetStates.DATA_ERROR_MSG);
//                }
//            }
//
//            @Override
//            public void onFail(int code, String error) {
//                OrderFinishEvent finishEvent = new OrderFinishEvent("2");
//                EventBus.getDefault().post(finishEvent);
//                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.CartFragment_final);
//            }
//        });
//
//    }


    /**
     * 订单详情再次购买
     *
     * @param orderListInfo
     * @param totalMoney    订单详情的总金额
     */
//    public static void addCartOrderAgain(OrderListInfo orderListInfo, final String totalMoney) {
//        for (int i = 0; i < orderListInfo.getOrderList().size(); i++) {
//            for (int j = 0; j < orderListInfo.getOrderList().get(i).getOrdergoodsList().size(); j++) {
//                OrderListInfo.OrderMoreInfo.OrderGoodsList orderGoodsList = orderListInfo.getOrderList().get(i).getOrdergoodsList().get(j);
//                if ("2".equals(orderGoodsList.getIsDeleted())) {
//                    continue;
//                }
//                final ProductInfos productInfos = new ProductInfos();
//                ProductInfoAdd productInfoAdd = new ProductInfoAdd();
//                if (User.getIntance().getMemberId() != null) {
//                    productInfoAdd.setMemberId(User.getIntance().getMemberId());
//                } else {
////                    toastErr(ErrorFragment.OrderInfoFragment);
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getPicUrl() != null) {
//                    productInfoAdd.setPicUrl(orderGoodsList.getPicUrl());
//                } else {
//
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getGoods_code() != null) {
//                    productInfoAdd.setProductCode(orderGoodsList.getGoods_code());
//                } else {
//
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getGoods_name() != null) {
//                    productInfoAdd.setProductName(orderGoodsList.getGoods_name());
//                } else {
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//
//                if (orderGoodsList.getPrice() != null) {
//                    productInfoAdd.setPrice(orderGoodsList.getPrice());
//                } else {
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getNumber() != null) {
//                    productInfoAdd.setCount(orderGoodsList.getNumber());
//                } else {
//
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getGrossWeight() != null) {
//                    productInfoAdd.setWeight(orderGoodsList.getGrossWeight());
//                } else {
//
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getUnit() != null) {
//                    productInfoAdd.setUnit(orderGoodsList.getUnit());
//                } else {
//
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getCategoryId() != null) {
//                    productInfoAdd.setCategoryId(orderGoodsList.getCategoryId());
//                } else {
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getGoods_id() != null) {
//                    productInfoAdd.setProductId(orderGoodsList.getGoods_id());
//                } else {
//                    BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//                }
//                if (orderGoodsList.getPicUrl() != null) {
//                    productInfoAdd.setPicUrl(orderGoodsList.getPicUrl());
//                }
//                productInfoAdd.setTotalPrice(orderListInfo.getOrderList().get(i).getOrdergoodsList().get(j).getGoodsTotal_amount());
//                productInfos.setProductInfo(productInfoAdd);
//
//                /**
//                 * TODO Cartlient处理
//                 */
//                CartClient.addCart2(productInfos, new ClientCallback<ShopCar>() {
//                    @Override
//                    public void onSuccess(ShopCar shopCar) {
//                        productInfos.getProductInfo().setShopCartsId(shopCar.getShoppingCartId());
//                        EventBus.getDefault().post(new EventMsg(CartClient.class, EventMsg.CARTADDSUCCESS));
//                    }
//
//                    @Override
//                    public void onFail(int code, String error) {
//                        EventBus.getDefault().post(new EventMsg(CartClient.class, EventMsg.CARTADDERR));
//                    }
//                });
//            }
//        }
//    }

    /**
     * @param userId    用户ID
     * @param ErrorData 当前fragment的标记
     * @return
     */
//    public static String getUserId(String userId, int ErrorData) {
//        String newuserId = "";
//        if (userId != null) {
//            newuserId = userId;
//        } else {
//            toastErr(ErrorData);
//        }
//        return newuserId;
//    }

    /**
     * 物流单号
     *
     * @param logistics_no 物流单号
     * @param orderinfo_id 子订单号
     */
//    public static Map<String, String> getLogistictList(String logistics_no, String orderinfo_id) {
//        Map<String, String> map = new ArrayMap<>();
//        if (logistics_no != null) {
//            map.put(OrderStates.LOGISTICS_NO, logistics_no);
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//        }
//        if (orderinfo_id != null) {
//            map.put(OrderStates.ORDER_INFO_ID, orderinfo_id);
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//        }
//
//        LogUtils.Debug_E(LogisticsPresenter.class, "物流参数：" + logistics_no + " = " + orderinfo_id);
//        return map;
//    }

    /**
     * 订单详情页面
     *
     * @param zorderinfo_id
     * @param orderinfo_no
     * @return
     */
//    public static Map<String, String> getOrderInfoList(String zorderinfo_id, String orderinfo_no) {
//        Map<String, String> map = new ArrayMap<>();
//        if (zorderinfo_id != null) {
//            map.put(com.bingstar.bingmall.order.OrderStates.ZORDERINFO_ID, zorderinfo_id);
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//        }
//        if (orderinfo_no != null) {
//            map.put(com.bingstar.bingmall.order.OrderStates.ZORDER_NO, orderinfo_no);
//        } else {
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//        }
//        return map;
//    }

    /**
     * 订单删除
     *
     * @param zorderinfo_id
     */
//    public static Map<String, String> getDeleteOrder(String zorderinfo_id) {
//        Map<String, String> map = new ArrayMap<>();
//        if (zorderinfo_id != null) {
//            map.put(OrderStates.ZORDERINFO_ID, zorderinfo_id);
//        } else {
////            toastErr(ErrorFragment.OrderApplayAfter);
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderInfoFragment_final);
//        }
//        return map;
//    }

    /**
     * 售后申请
     */
//    public static OrderApplayEditBeanInfo getAfterSaleBeanInfo(List<OrderListInfo.OrderMoreInfo> orderMoreInfoList, String reason, String zstate) {
//        OrderApplayEditBeanInfo afterSaleInfo = new OrderApplayEditBeanInfo();
//        if (orderMoreInfoList.size() == 0) {
//            /**
//             * TODO 错误处理
//             */
//        }
//        afterSaleInfo.setZorderinfo_id(orderMoreInfoList.get(0).getZorderinfo_id());
//        if (OrderStates.BACK_MONEY.equals(zstate)) {
//            afterSaleInfo.setAfterSaleType(OrderStates.BACK_MONEY);
//        }
//        if (OrderStates.BACk_GOODS.equals(zstate) || OrderStates.BACK_GOODS_5.equals(zstate)) {
//            afterSaleInfo.setAfterSaleType(OrderStates.BACk_GOODS);
//        }
//        afterSaleInfo.setAfterSaleReason(reason);
//        List<AfterSaleOrderInfo> afterSaleOrderList = new ArrayList<>();
//        for (int i = 0; i < orderMoreInfoList.size(); i++) {
//            AfterSaleOrderInfo saleOrder = new AfterSaleOrderInfo();
//            saleOrder.setOrderinfo_id(orderMoreInfoList.get(i).getOrderinfo_id());
//            List<AfterSaleGoodsInfo> afterSaleGoodsList = new ArrayList<>();
//            for (int j = 0; j < orderMoreInfoList.get(i).getOrdergoodsList().size(); j++) {
//                AfterSaleGoodsInfo afterSaleGoods = new AfterSaleGoodsInfo();
//                afterSaleGoods.setGoods_code(orderMoreInfoList.get(i).getOrdergoodsList().get(j).getGoods_code());
//                if (orderMoreInfoList.get(i).getOrdergoodsList().get(j).getSelectState() == null) {
//                    orderMoreInfoList.get(i).getOrdergoodsList().get(j).setSelectState("0");
//                }
//                afterSaleGoods.setSelectState(orderMoreInfoList.get(i).getOrdergoodsList().get(j).getSelectState());
//                afterSaleGoodsList.add(afterSaleGoods);
//            }
//            saleOrder.setAfterSaleGoodsList(afterSaleGoodsList);
//            afterSaleOrderList.add(saleOrder);
//        }
//        afterSaleInfo.setAfterSaleOrderList(afterSaleOrderList);
//        return afterSaleInfo;
//    }


//    public static void toastErr(int isFrom) {
//        EventBus.getDefault().post(new EventMsg(BeanTranslater.class, String.valueOf(isFrom)));
//    }

}
