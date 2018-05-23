package com.bingstar.bingmall.order.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.bean.DeleteDialogBean;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.view.DeleteDialog;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.order.LogisticsSearch.LogisticsInfo;
import com.bingstar.bingmall.order.LogisticsSearch.LogisticsPresenter;
import com.bingstar.bingmall.order.LogisticsSearch.LogisticsSearchAdapter;
import com.bingstar.bingmall.order.OrderApplyAfter.OrderApplyAfterDialog;
import com.bingstar.bingmall.order.OrderDelete.OrderDetailDeletePresenter;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.bingstar.bingmall.user.addr.AddrStates;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.view.OnSingleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianhechen on 17/2/8.
 */

public class OrderInfoFragment extends DialogFragment implements IOrderInfoContract.OrderInfoView, IOrderInfoContract.LogisticsView,
        View.OnClickListener, IOrderInfoContract.OrderDeleteView {


    private OrderListInfo.OrderMoreInfo orderMoreInfo;

    private List<OrderListInfo.OrderMoreInfo> order_goodsList;

    private IOrderInfoContract.OrderInfoPresenter presenter;//订单详情presenter

    private IOrderInfoContract.LogisticsPresenter logisticsPresenter;//物流订单查询presenter

    private IOrderInfoContract.OrderDeletePresenter orderDeletePresenter;//删除订单;

    private static String zorderinfo_id;//订单号

    private OrderDetailAdapter orderDetailAdapter;

    private TextView torderDetail_money, torderDetail_postage, tOrderDetail_limitmoney, tOrderdetail_way, torderdetail_preferential;

    private ImageView iOrderdetail_buy;

    private TextView torderDetail_id, torderdetail_name, torderdetail_mobile, torderdetail_place, torderdetail_flug, torderdetail_time;

    private ImageView iOrderdetail_apply;//申请售后按钮

    private LinearLayout logisticsLinear;

    private LinearLayout logisticsLinear_top;

    private ListView logisticsListview;

    private ImageView logistics_image;//物流显示图片

    private TextView logistics_product_name;//商品名称

    private TextView logistics_id;//物流单号

    private TextView logistics_error_text;//当服务器返回物流信息list为null是，显示

    private ImageView order_delete_image;//删除订单按钮

    private String orderinfo_no;

    private ImageView iOrderDetail_pay;

    private SynLinearLayout order_detail_syn;

    private Toast toast;

    private String orderState = "";//用于显示底部按钮的状态

    private String orderLimitMoney = "";//最终价格

    private String ordercouponMoney = "";//优惠金额

    private String orderpay_type;//支付方式
    private String orderLogistics;//支付方式

//    private String product_name;//商品名称

//    private String shipping_name;//物流公司

//    private String logistics_no;//物流单号

    private String zstate;

    public static OrderInfoFragment newInstance(String info, String orderinfo_no, String orderState,
                                                String orderLimitMoney, String orderLogistics, String ordercouponMoney, String pay_type,
                                                String zstate) {
        OrderInfoFragment fragment = new OrderInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OrderStates.ZORDERINFO_ID, info);
        bundle.putString(OrderStates.ORDER_ID, orderinfo_no);
        bundle.putString(OrderStates.ORDERSTSTE, orderState);
        bundle.putString(OrderStates.ORDER_LIMIT_MONEY, orderLimitMoney);
        bundle.putString(OrderStates.ORDER_LOGISTICS, orderLogistics);
        bundle.putString(OrderStates.ORDER_COUPONMONEY, ordercouponMoney);
        bundle.putString(OrderStates.ORDER_PAY_STATE, pay_type);
        bundle.putString(OrderStates.ZSTATE, zstate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT);

        WindowManager wm = getActivity().getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        getDialog().getWindow().setLayout(width / 2 + 30, height);
    }

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.order_infofragment, container);
        zorderinfo_id = getArguments().getString(OrderStates.ZORDERINFO_ID);//获取订单详情Id
        orderinfo_no = getArguments().getString(OrderStates.ORDER_ID);
        orderState = getArguments().getString(OrderStates.ORDERSTSTE);//订单状态
        zstate = getArguments().getString(OrderStates.ZSTATE);
        orderLogistics = getArguments().getString(OrderStates.ORDER_LOGISTICS);

        orderLimitMoney = getArguments().getString(OrderStates.ORDER_LIMIT_MONEY);
        ordercouponMoney = getArguments().getString(OrderStates.ORDER_COUPONMONEY);
        orderpay_type = getArguments().getString(OrderStates.ORDER_PAY_STATE);
        order_detail_syn = (SynLinearLayout) view.findViewById(R.id.order_detail_syn);
        torderdetail_preferential = (TextView) view.findViewById(R.id.order_detail_preferential);
        torderDetail_money = (TextView) view.findViewById(R.id.order_detail_money);
        torderDetail_postage = (TextView) view.findViewById(R.id.order_detail_postage);
        tOrderDetail_limitmoney = (TextView) view.findViewById(R.id.order_detail_limitmoney);
        tOrderdetail_way = (TextView) view.findViewById(R.id.order_way);
        iOrderdetail_buy = (ImageView) view.findViewById(R.id.order_buy);
        iOrderDetail_pay = (ImageView) view.findViewById(R.id.order_pay);
        iOrderdetail_apply = (ImageView) view.findViewById(R.id.order_apply_after);
        logisticsLinear = (LinearLayout) view.findViewById(R.id.logistics_linear);
        logisticsListview = (ListView) view.findViewById(R.id.logistics_detail_listview);
        logisticsLinear_top = (LinearLayout) view.findViewById(R.id.logistics_linear_top);
        logistics_image = (ImageView) view.findViewById(R.id.logistics_image);
        logistics_id = (TextView) view.findViewById(R.id.logistics_id);
        logistics_product_name = (TextView) view.findViewById(R.id.logistics_product_name);
        logistics_error_text = (TextView) view.findViewById(R.id.logistics_error_text);
        order_delete_image = (ImageView) view.findViewById(R.id.order_delete);

//        logistics_product_name.setText(product_name);
//        logistics_id.setText(shipping_name + "：" + logistics_no);


        logisticsLinear.setOnClickListener(this);//给物流layout设置监听事件
        logisticsLinear_top.setOnClickListener(this);
        order_delete_image.setOnClickListener(this);//删除订单
        iOrderdetail_apply.setOnClickListener(this);//售后申请查询
        iOrderdetail_buy.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                BeanTranslater.addCartOrderAgain(orderListInfo, "" + torderDetail_money.getText());
            }
        });//立即支付？再次购买
        iOrderDetail_pay.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                presenter.getPayMoney(orderMoreInfo.getZorder_no());
                dismiss();
            }
        });

        listView = (ListView) view.findViewById(R.id.order_detail_list);
        order_detail_syn.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                order_detail_syn.showLoad();
                presenter.getOrderInfo(zorderinfo_id, orderinfo_no, orderMoreInfo);
            }
        });
        setPresenter();

        EventBus.getDefault().register(this);

        /**
         * 设置底部数据
         */
        if ("0".equals(orderState)) {
            iOrderdetail_buy.setVisibility(View.GONE);
            iOrderDetail_pay.setVisibility(View.VISIBLE);
            order_delete_image.setVisibility(View.VISIBLE);
            iOrderdetail_apply.setVisibility(View.GONE);
        }
        if (ordercouponMoney == null) {
            torderdetail_preferential.setText(getResources().getString(R.string.cycle_money) + getResources().getString(R.string.order_default) + "");//优惠
        } else {
            torderdetail_preferential.setText(getResources().getString(R.string.cycle_money) + ordercouponMoney + "");//优惠
        }
        //        支付方式0:余额支付，1支付宝支付
        if (orderpay_type == null) {
           tOrderdetail_way.setText(R.string.order_info_unpay);
        } else if ("0".equals(orderpay_type)) {
            tOrderdetail_way.setText(R.string.order_info_balance);
        } else if ("1".equals(orderpay_type)) {
            tOrderdetail_way.setText(R.string.order_info_pay_ali);
        } else if ("2".equals(orderpay_type)){
            tOrderdetail_way.setText(R.string.order_info_pay_wx);
        }else {
            tOrderdetail_way.setText(R.string.order_info_pay_other);
        }


        /**
         * 判断是否是周期购购买的 yes：不显示order_buy ？ NO :显示order_buy；
         */


        View headView;
        headView = View.inflate(getContext(), R.layout.order_detail_heard, null);
      //  torderdetail_flug = (TextView) headView.findViewById(R.id.order_detail_flug);
        torderdetail_name = (TextView) headView.findViewById(R.id.order_detail_name);
        torderdetail_place = (TextView) headView.findViewById(R.id.order_detail_place);
        torderdetail_mobile = (TextView) headView.findViewById(R.id.order_detail_mobile);
        torderDetail_id = (TextView) headView.findViewById(R.id.order_detail_id);
        torderdetail_time = (TextView) headView.findViewById(R.id.order_detail_time);

        order_goodsList = new ArrayList<>();
        orderDetailAdapter = new OrderDetailAdapter(order_goodsList);
        listView.addHeaderView(headView);
        listView.setAdapter(orderDetailAdapter);


        order_detail_syn.showLoad();
        presenter.getOrderInfo(zorderinfo_id, orderinfo_no, orderMoreInfo);



        return view;

    }

    @Override
    public void setPresenter() {
        presenter = new OrderInfoPresenter(this);
        logisticsPresenter = new LogisticsPresenter(this);
        orderDeletePresenter = new OrderDetailDeletePresenter(this);
    }

    @Override
    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    @Override
    public void showToast(@StringRes int strId) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }

    /**
     * 物流信息执行的回调
     */
    @Override
    public void setnotifyDate(LogisticsInfo logisticsInfo) {
        /**
         *  更新物流信息 logistics_image;
         */
        List<LogisticsInfo.ExpressInfoList> list = logisticsInfo.getExpressInfoList();

        if(list == null || list.size() == 0){//当物流信息返回null时，
            logisticsListview.setVisibility(View.GONE);
            logistics_error_text.setVisibility(View.VISIBLE);
        }else{
            logisticsListview.setVisibility(View.VISIBLE);
            logistics_error_text.setVisibility(View.GONE);
            LogisticsSearchAdapter adapter = new LogisticsSearchAdapter();
            adapter.setList(list);
            logisticsListview.setAdapter(adapter);
        }

    }

    private OrderListInfo orderListInfo;

    @Override
    public void setView(OrderListInfo orderListInfo) {

        this.orderListInfo = orderListInfo;

        if (orderListInfo.getOrderList() == null || orderListInfo.getOrderList().size() == 0) {
            return;
        }
        order_goodsList.clear();
        for (int i = 0; i < orderListInfo.getOrderList().size(); i++) {
            /**
             * 获取数据之后在 初始化view
             */
            order_goodsList.add(orderListInfo.getOrderList().get(i));
            this.orderMoreInfo = orderListInfo.getOrderList().get(0);
        }
        if (orderLogistics != null) {
            torderDetail_postage.setText(getResources().getString(R.string.cycle_money) + orderLogistics + "");//邮费
        } else {
            torderDetail_postage.setText(getResources().getString(R.string.cycle_money) + getResources().getString(R.string.order_default) + "");//邮费
        }
        torderDetail_money.setText(getResources().getString(R.string.cycle_money) + orderLimitMoney + "");//订单总价
        tOrderDetail_limitmoney.setText(getResources().getString(R.string.cycle_money) + Util.priceToString(Float.parseFloat(orderLimitMoney)
                - Float.parseFloat(orderLogistics) + Float.parseFloat(ordercouponMoney)) + "");//商品总价

        /**
         * 设置listview头部数据
         */
//        2017-03-24 11:55:23.0
        if (null == orderMoreInfo.getPayTime()) {
            torderdetail_time.setText(orderMoreInfo.getAddTime().substring(0, 19));
        } else {
            torderdetail_time.setText(orderMoreInfo.getPayTime().substring(0, 19));
        }
        torderDetail_id.setText(orderMoreInfo.getZorder_no());
        torderdetail_name.setText(orderMoreInfo.getReceiver_name());
        torderdetail_mobile.setText(orderMoreInfo.getContact_mobile());
        torderdetail_place.setText(orderMoreInfo.getRegion()+orderMoreInfo.getDetailed_address()+"");
     //   torderdetail_flug.setText(orderMoreInfo.getState());
//        00:周期购未激活订单0:待支付1:待发货 2:已发货5:已收货99:失败

//        00:周期购未激活订单0:待支付1:待发货 2:已发货5:已收货99:失败12:申请退款中13:退款审核中14:退款成功22:申请退货中23:退货审核中24:退货成功
//        if (OrderStates.ORDER_LIST_QUERY_UNPAY.equals(orderMoreInfo.getState())) {
//          //  torderdetail_flug.setText(getResources().getString(R.string.order_info_unpay));
//        } else if (OrderStates.ORDER_LIST_QUERY_PAY.equals(orderMoreInfo.getState())) {
//         //   torderdetail_flug.setText(getResources().getString(R.string.order_info_textpay));
//        } else if (OrderStates.ORDER_LIST_QUERY_DEVILERY.equals(orderMoreInfo.getState())) {
//       //     torderdetail_flug.setText(getResources().getString(R.string.order_info_delivery));
//        } else if (OrderStates.ORDER_LIST_QUERY_GET_GOODS.equals(orderMoreInfo.getState())) {
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_goods));
//        } else if (OrderStates.ORDER_LIST_QUERY_APPLAY_BACK_MONEY.equals(orderMoreInfo.getState())) {
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_applay_back_money));
//        } else if (OrderStates.ORDER_LIST_QUERY_CHECK_BACK_MONEY.equals(orderMoreInfo.getState())) {//申请售后页面是不能够点击的
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_back_money));
//        } else if (OrderStates.ORDER_LIST_QUERY_BACK_MONEY_SUCCESS.equals(orderMoreInfo.getState())) {
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_back_money_success));
//        } else if (OrderStates.ORDER_LIST_QUERY_APPLAY_BACK_GOODS.equals(orderMoreInfo.getState())) {
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_applay_back_goods));
//        } else if (OrderStates.ORDER_LIST_QUERY_CHECK_BACK_GOODS.equals(orderMoreInfo.getState())) {//申请售后页面是不能够点击的
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_back_goods));
//        } else if (OrderStates.ORDER_LIST_QUERY_BACK_GOODS_SUCCESS.equals(orderMoreInfo.getState())) {
//            torderdetail_flug.setText(getResources().getString(R.string.order_info_back_goods_success));
//        }

        order_detail_syn.showSuccess();
    }

    @Override
    public void loadListError() {
        order_detail_syn.showError();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.unBind();
        }
        if (logisticsPresenter != null) {
            logisticsPresenter.unBind();
        }
        if (orderDeletePresenter != null) {
            orderDeletePresenter.unBind();
        }
    }

    @Subscribe
    public void deleteDialogEvent(DeleteDialogBean deleteDialogBean){
        if(AddrStates.DELETE_CONFIRM.equals(deleteDialogBean.getConfirmOrCancel()) && OrderStates.DELETE_ORDER.equals(deleteDialogBean.getDeleteFlug())){
            orderDeletePresenter.getHttpDelete(orderMoreInfo.getZorderinfo_id());
        }
    }

    private int parentPosition = 0;
    private int position = 0;

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(OrderDetailAdapter.class) && OrderStates.LOGISTICS_CLICK.equals(msg.getMsg())) {
            parentPosition = Integer.parseInt(msg.getName());
            position = Integer.parseInt(msg.getPhone());
            logistics_id.setText(order_goodsList.get(parentPosition).getShipping_name() + ": " + order_goodsList.get(parentPosition).getLogistics_no());//物流信息
            logistics_product_name.setText(getResources().getString(R.string.order_detail_adapter_title));//商品名称
            /**
             * 给物流订单设置图片
             */
            Util.imageTransform(getContext(), logistics_image,order_goodsList.get(parentPosition).getOrdergoodsList().get(position).getPicUrl());
            /**
             * 物流查询
             */
            logisticsPresenter.getLogisticsList(order_goodsList.get(parentPosition).getLogistics_no(), order_goodsList.get(parentPosition).getOrderinfo_id());
            logisticsLinear.setVisibility(View.VISIBLE);
        } else if (msg.getClassName().equals(CartClient.class) && msg.getMsg().equals(String.valueOf(OrderStates.OrderInfoFragment))) {//再次购买成功；
            dismiss();
        } else if (msg.getClassName().equals(OrderApplyAfterDialog.class)) {//售后申请成功 关闭Dialog
            EventBus.getDefault().post(new EventMsg(OrderInfoFragment.class, com.bingstar.bingmall.order.OrderStates.FLUSH_ORDER_LIST));//刷新订单列表
            dismiss();//关闭Dialog
        } else if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.OrderInfoFragment_final.equals(msg.getMsg())) {
            /**
             * 订单详情错误处理  重新请求数据
             */
            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderFragment_final);
            dismiss();
        } else if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.OrderInfo_logistics_final.equals(msg.getMsg())) {
            /**
             * 物流查询错误处理
             */
            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderFragment_final);
            dismiss();
//            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.OrderInfoFragment);
        } else if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.OrderInfo_delete_final.equals(msg.getMsg())) {
            /**
             * 订单删除错误处理
             */
            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.OrderFragment_final);//OrderFragment弹出错误页面
            dismiss();
//            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.OrderInfoFragment);
        }
    }


    @Override
    public void onClick(View v) {

        if (orderMoreInfo == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.order_apply_after) {
            if (OrderStates.ORDER_LIST_QUERY_BACK_MONEY_SUCCESS.equals(orderMoreInfo.getState())) {
                showToast(R.string.order_detail_error);
                return;
            }
            if (order_goodsList == null || order_goodsList.size() == 0) {
                return;
            }
            OrderApplyAfterDialog orderApplyAfterDialog =
                    OrderApplyAfterDialog.newInstance(orderListInfo, order_goodsList.get(0).getZorderinfo_id(), orderMoreInfo.getState(), zstate,orderLimitMoney);
            orderApplyAfterDialog.show(getChildFragmentManager(), "OrderApplyAfterDialog");

        } else if (i == R.id.logistics_linear) {
            /**
             * 物流查询（点击屏幕外，让物流页面消失）
             */
            logisticsLinear.setVisibility(View.GONE);
        } else if (i == R.id.logistics_linear_top) {
            /**
             * 删除订单
             */
        } else if (i == R.id.order_delete) {
            DeleteDialog deleteDialog = DeleteDialog.newInstance(getResources().getString(R.string.order_info_title_delete), OrderStates.DELETE_ORDER);
            deleteDialog.show(getChildFragmentManager(), "DeleteDialog");
        } else if (i == R.id.order_buy) {
            String soldOut = checkSoldOut();
            if (!soldOut.equals("")) {
                new AlertDialog.Builder(getContext()).setMessage("该订单中的商品 " + soldOut + "已下架，无法加入购物车，您仍可以继续添加其他商品进入购物车")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BeanTranslater.addCartOrderAgain(orderListInfo, "" + torderDetail_money.getText());
                                dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            } else {
                BeanTranslater.addCartOrderAgain(orderListInfo, "" + torderDetail_money.getText());
                dismiss();
            }
        } else if (i == R.id.order_pay) {
            presenter.getPayMoney(orderMoreInfo.getZorder_no());
            dismiss();
        }
    }

    /**
     * 订单删除。关闭Dialog 刷新订单列表
     */
    @Override
    public void setEventBus() {
        EventBus.getDefault().post(new EventMsg(OrderInfoFragment.class, OrderStates.FLUSH_ORDER_LIST));
        dismiss();
    }

    private String checkSoldOut() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < orderListInfo.getOrderList().size(); i++) {
            for (int j = 0; j < orderListInfo.getOrderList().get(i).getOrdergoodsList().size(); j++) {
                OrderListInfo.OrderMoreInfo.OrderGoodsList orderGoodsList = orderListInfo.getOrderList().get(i).getOrdergoodsList().get(j);
                if ("2".equals(orderGoodsList.getIsDeleted())) {
                    stringBuilder.append(orderGoodsList.getGoods_name());
                    stringBuilder.append(" ");
                }
            }
        }
        return stringBuilder.toString().trim();
    }

    private void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
