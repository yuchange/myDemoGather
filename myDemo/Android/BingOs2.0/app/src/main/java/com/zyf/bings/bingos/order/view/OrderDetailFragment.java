package com.zyf.bings.bingos.order.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.cart.DeleteDialog;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.event.NotifyAllOrderRvEvent;
import com.zyf.bings.bingos.goods.OrderConfirmFragment;
import com.zyf.bings.bingos.goods.bean.AddCartProductInfo;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.order.CustomerServiceDialogFragment;
import com.zyf.bings.bingos.order.NotificationDialogFragment;
import com.zyf.bings.bingos.order.adapter.OrderDetailAdpter;
import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;
import com.zyf.bings.bingos.order.bean.OrderInfoBean;
import com.zyf.bings.bingos.order.http.OrderClient;
import com.zyf.bings.bingos.pay.PayFragment;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos.utils.PriceUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.progressDialog.MyProgressDialog;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

import static com.zyf.bings.bingos.R.mipmap.immediate_payment;
import static com.zyf.bings.bingos.cart.http.CartClient.CART_ADD_METHOD;
import static com.zyf.bings.bingos.cart.http.CartClient.CART_ADD_URL;

/**
 * Created by Administrator on 2017/9/27.
 * 订单详情的fragment
 */

public class OrderDetailFragment extends Fragment implements View.OnClickListener {

    private TextView mTvOrderNum;
    private TextView mTvOrderTime;
    private TextView mTvOrderState;
    private TextView mTvRecipientName;
    private TextView mTvPhone;
    private TextView mTvReceivingAdress;
    private RecyclerView mRvOrderProduct;
    private TextView mTvTotalAmount;
    private TextView mTvFreigt;
    private TextView mTvTreatment;
    private TextView mTvPaymentMethod;
    private TextView mTvRelPayemnt;
    private TextView mTvCustomerPhone;
    private TextView mTvBottomOrderState;
    private static final String ZORDER_ID = "zorderinfo_id";
    private static final String ZORDER_NUM = "zorder_no";
    private static final String ZORDER_CUPON_MONEY = "couponMoney";
    private static final String REL_PAYMENT = "zorder_total_money";
    private static final String ZORDER_STATE = "zorder_state";
    private static final String ZORDER_FREIGHT = "zorder_freight";
    private String mZorderId;
    private String mZorderNum;
    private String TAG = "OrderDetailsFragment";
    private String mCuponMoney;
    private TextView mTvPaymentMethodTitle;
    private String mRelPayment;
    private TextView mTvBottomOrderLeftState;
    private String mOrderState;
    private List<OrderInfoBean.OrderListBean> mOrderInfoList;
    private ProgressFrameLayout mProgressFrameLayout;

    private OrderApplyDialogFragment mOrderApplyDialogFragment;
    private TextView mTvOrderRemaingTime;
    private CountDownTimer timer;
    private LinearLayout mLlAfterSalesStatus;
    private LinearLayout mLlBottomNormal;
    private TextView mTvStimatedEreAmount;
    private TextView mTvAfterSalesNum;
    private static final String RETURN_METHOD = "refund.of.return";
    private static final String RETURN_URL = "/refundOfReturn.shtml";
    private static final String GOODS_ID = "order_goods_id";
    private static final String AFTER_SALE_TYPE = "after_sale_type";
    private static final String USER_REMARK = "user_remark";
    private LinearLayout mLlOnlineService;
    private String mFreight;
    private List<OrderInfoBean.OrderListBean.OrdergoodsListBean> mOrdergoodsList;
    private List<ConfirmOrderBean.ItemBean> mItemBeanArrayList;
    private String mTime_expire;
    private TextView mTvPayMethodTitle;
    private OrderInfoBean.ZorderPayinfo mZorderPayinfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.fragment_order_detail, container, false);
        mZorderId = getArguments().getString(ZORDER_ID);
        mZorderNum = getArguments().getString(ZORDER_NUM);
        initView(mProgressFrameLayout);
        initListener();
        initData();
        Log.e("Fragment", "OrderDetail" + "onCreate执行了");
        return mProgressFrameLayout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.e("Fragment", "OrderDetail" + isVisibleToUser);
    }


    private void initListener() {
        mTvBottomOrderState.setOnClickListener(this);
        mTvBottomOrderLeftState.setOnClickListener(this);
        mLlOnlineService.setOnClickListener(this);
    }

    ;

    private void initData() {
        mProgressFrameLayout.showLoading();
        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ZORDER_ID, mZorderId);
            jsonObject.put(ZORDER_NUM, mZorderNum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.ORDERINFO_QUERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());


        OkGoUtils.doStringPostRequest(map, OrderClient.INFO_TITLE, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {

                mProgressFrameLayout.showContent();
                OrderInfoBean orderInfoBean = GsonFactory.fromJson(str, OrderInfoBean.class);
                //订单金额的信息
                mZorderPayinfo = orderInfoBean.getZorderPayinfo();
                mOrderInfoList = orderInfoBean.getOrderList();
                //适配器的集合
                mOrdergoodsList = new ArrayList<OrderInfoBean.OrderListBean.OrdergoodsListBean>();//商品的list
                for (int i = 0; i < mOrderInfoList.size(); i++) {

                    mOrdergoodsList.addAll(mOrderInfoList.get(i).getOrdergoodsList());

                }
                if (mOrderInfoList != null && mOrderInfoList.size() != 0) {


                    initProductListAdapter(mOrdergoodsList);
                    String addTime = mOrderInfoList.get(0).getAddTime();
                    String zorder_no = mOrderInfoList.get(0).getZorder_no();
                    String state = mOrderInfoList.get(0).getState();
                    String receiver_name = mOrderInfoList.get(0).getReceiver_name();
                    String contact_mobile = mOrderInfoList.get(0).getContact_mobile();
                    String detailed_address = mOrderInfoList.get(0).getDetailed_address();

                    mTvRecipientName.setText(receiver_name);
                    mTvPhone.setText(contact_mobile);
                    mTvOrderNum.setText(zorder_no);
                    mTvOrderTime.setText(addTime);
                    mTvReceivingAdress.setText(detailed_address);
                    mTvRelPayemnt.setText("¥  " + mZorderPayinfo.getZorder_total_money());//实付款

                    if (TextUtils.isEmpty(mZorderPayinfo.getPost_money())) {
                        mFreight = "0.00";
                    }
                    mTvFreigt.setText("¥  " + mZorderPayinfo.getPost_money());//运费
                    mTvTotalAmount.setText("¥  " + mZorderPayinfo.getZorderGoodsPriceCount());//商品总额
                    //待支付订单剩余时间
                    mTime_expire = orderInfoBean.getTime_expire();
                    mOrderState = mZorderPayinfo.getZorderState();
                    setOrderState(mZorderPayinfo.getZorderState(), mTime_expire, mOrdergoodsList);//设置订单的状态


                    setOrderPayMethod(mZorderPayinfo.getPay_type());//支付方式

                    if (mCuponMoney != null) {
                        mTvTreatment.setText("¥  " + mCuponMoney);
                    } else {
                        mCuponMoney = "0.00";
                        mTvTreatment.setText("¥  " + "0.00");
                    }

                }


            }

            @Override
            public void onFail(int code, String error) {
                mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "加载失败...", error, "点击重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initData();
                    }
                });
            }
        });


    }

    private void initProductListAdapter(List<OrderInfoBean.OrderListBean.OrdergoodsListBean> ordergoodsList) {

        OrderDetailAdpter orderDetailAdpter = new OrderDetailAdpter(getActivity(), R.layout.item_order_detail, ordergoodsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }//让滑动不卡顿
        };

        mRvOrderProduct.setLayoutManager(linearLayoutManager);
        mRvOrderProduct.setAdapter(orderDetailAdpter);

        orderDetailAdpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_add_to_cart: //加入购物车
                        OrderInfoBean.OrderListBean.OrdergoodsListBean ordergoodsBean = ordergoodsList.get(position);
                        addCart(ordergoodsBean);
                        break;
                    case R.id.tv_order_refund://退款 类型：1
                        startOrderApplyDialogFragment(ordergoodsList.get(position).getOrdergoodsid(), "1");

                        break;
                    case R.id.tv_return_of_goods://退货 退款 类型 ：2
                        startOrderApplyDialogFragment(ordergoodsList.get(position).getOrdergoodsid(), "2");

                        break;
                    case R.id.tv_upload_logistics:  //上传物流单号
                        String ordergoodsid = ordergoodsList.get(position).getOrdergoodsid();
                        UpLogisticsNumFragment.start(getActivity().getSupportFragmentManager(), ordergoodsid);

                        break;
                    case R.id.tv_order_tracking://订单跟踪  已完成订单订单跟踪



                        SuborderLogisticsFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), mOrderInfoList, mZorderId, "", "OrderDetailFragment");





                        break;
                    case R.id.tv_cancel_request://取消申请售后

                        final DeleteDialog deleteDialog = DeleteDialog.newInstance();
                        deleteDialog.show(getActivity().getSupportFragmentManager(), "cancelSales");
                        deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                            @Override
                            public void onDeleteItem() {

                                cancelAfterSales(ordergoodsList.get(position).getGoods_id());


                            }
                        });


                        break;
                    default:
                        break;


                }


            }
        });

    }

    private void startOrderApplyDialogFragment(String orderGoodsId, String type) {

        mOrderApplyDialogFragment = OrderApplyDialogFragment.newInstance(orderGoodsId
                , type);
        if (!mOrderApplyDialogFragment.isAdded()) {
            mOrderApplyDialogFragment.show(getActivity().getSupportFragmentManager(), "orderApply");
        }

    }

    private void cancelAfterSales(String goods_id) {//取消申请售后


        Map<String, String> params = new ArrayMap<>();
        params.put(GOODS_ID, goods_id);
        params.put(USER_REMARK, "");
        params.put(AFTER_SALE_TYPE, "3");
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, RETURN_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));
        RxOkClient.doPost(map, RETURN_URL, TAG, null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showToast("申请成功");

                getActivity().onBackPressed();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });

    }

    private void addCart(OrderInfoBean.OrderListBean.OrdergoodsListBean ordergoodsBean) {
        AddCartProductInfo addCartProductInfo = new AddCartProductInfo();
        AddCartProductInfo.ProductInfo productInfo = new AddCartProductInfo.ProductInfo();
        productInfo.setMemberId(SPUtil.getString(getActivity(), Config.MEMBER_ID));
        productInfo.setCategoryId(ordergoodsBean.getCategoryId());
        productInfo.setProductId(ordergoodsBean.getGoods_id());
        productInfo.setProductCode(ordergoodsBean.getGoods_code());
        productInfo.setProductName(ordergoodsBean.getGoods_name());
        productInfo.setGross_Weight("");  //快递重量 暂时无用
        productInfo.setSpecificationId(ordergoodsBean.getSpecificationId());
        productInfo.setWeight(ordergoodsBean.getWeight());
        productInfo.setUnit(ordergoodsBean.getUnit());
        productInfo.setPrice(ordergoodsBean.getPrice());
        productInfo.setCount(ordergoodsBean.getNumber());
        productInfo.setPicUrl(ordergoodsBean.getPicUrl());
        BigDecimal price = new BigDecimal(ordergoodsBean.getPrice());
        BigDecimal count = new BigDecimal(ordergoodsBean.getNumber());
        BigDecimal totalPrice = price.multiply(count);
        double v = totalPrice.doubleValue();
        productInfo.setTotalPrice(v + "");

        addCartProductInfo.setProductInfo(productInfo);
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, CART_ADD_METHOD);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.getGson().toJson(addCartProductInfo));
        OkGoUtils.doStringPostRequest(map, CART_ADD_URL, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                ToastUtils.showToast("加入购物车成功");
                CartCountClient.getCartCount();
            }

            @Override
            public void onFail(int code, String error) {
                ToastUtils.showToast(error);

            }
        });

    }

    private void setOrderPayMethod(String pay_type) {
        if (TextUtils.isEmpty(pay_type)) {
            mTvPaymentMethod.setVisibility(View.GONE);
            mTvPaymentMethodTitle.setVisibility(View.GONE);
            return;
        }
        mTvPaymentMethod.setText(pay_type);
    }


    private void setOrderState(String state, String timeExpire, List<OrderInfoBean.OrderListBean.OrdergoodsListBean> ordergoodsList) {
        if (TextUtils.isEmpty(state)) {
            return;
        }
        switch (state) {
            case "0"://待付款
                mTvOrderState.setText("待付款");
                mTvBottomOrderState.setText("立即付款");
                mTvBottomOrderState.setBackgroundResource(immediate_payment);
                mTvBottomOrderState.setTextColor(Color.WHITE);
                mTvBottomOrderLeftState.setVisibility(View.VISIBLE);
                mTvBottomOrderLeftState.setText("取消订单");
                mTvOrderRemaingTime.setVisibility(View.VISIBLE);


                int timeExpre = Integer.parseInt(timeExpire);
                timer = new CountDownTimer(timeExpre * 1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        int minute = (int) (millisUntilFinished / (1000 * 60));
                        int second = (int) (millisUntilFinished / 1000 % 60);
                        mTvOrderRemaingTime.setText("订单将在: " + new Formatter().format("%02d", minute).toString() + ":"
                                + new Formatter().format("%02d", second).toString() + "后关闭");
                    }

                    @Override
                    public void onFinish() {
                        mTvOrderRemaingTime.setText(R.string.order_time_out);

                    }
                };
                timer.start();

                break;
            case "1"://待发货
                mTvOrderState.setText("待发货");
                mTvBottomOrderState.setText("提醒发货");
                mTvBottomOrderLeftState.setVisibility(View.GONE);
                mTvBottomOrderState.setBackgroundResource(R.mipmap.transparency);
                mTvBottomOrderState.setTextColor(getResources().getColor(R.color.tv_common_red));
                break;
            case "2"://已发货
                mTvOrderState.setText("待收货");
                mTvBottomOrderLeftState.setVisibility(View.GONE);
                mTvBottomOrderState.setText("订单跟踪");
                mTvBottomOrderState.setBackgroundResource(R.mipmap.gray_box);
                if (getActivity() != null) {
                    mTvBottomOrderState.setTextColor(ContextCompat.getColor(getActivity(), R.color.c_919191));
                    // mTvBottomOrderState.setTextColor(getResources().getColor(R.color.c_919191));
                }

                break;
            case "5"://已完成
                mTvOrderState.setText("已完成");
                mTvBottomOrderLeftState.setVisibility(View.VISIBLE);
                mTvBottomOrderLeftState.setVisibility(View.GONE);
                mTvBottomOrderState.setText("删除订单");
                mTvBottomOrderState.setBackgroundResource(R.mipmap.delete_order);
                mTvBottomOrderState.setTextColor(getResources().getColor(R.color.c_919191));
                break;
            case "6"://已取消
                mTvOrderState.setText("已取消");
                mTvBottomOrderLeftState.setVisibility(View.VISIBLE);
                mTvBottomOrderState.setVisibility(View.VISIBLE);
                mTvBottomOrderState.setText("再次购买");
                mTvBottomOrderState.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                mTvBottomOrderState.setBackgroundResource(R.mipmap.immediate_payment);
                mTvBottomOrderLeftState.setText("删除订单");
                break;
            case "7"://售后中
                mTvOrderState.setText("售后中");
                mLlAfterSalesStatus.setVisibility(View.VISIBLE);
                mLlBottomNormal.setVisibility(View.GONE);
                mTvBottomOrderLeftState.setVisibility(View.GONE);
                mTvBottomOrderState.setText("订单跟踪");
                mTvBottomOrderState.setBackgroundResource(R.mipmap.delete_order);
                mTvBottomOrderState.setTextColor(getResources().getColor(R.color.c_919191));
                double refundAmount = 0;//预计退款金额
                int afterGdNum = 0;//售后商品的数量
                for (int i = 0; i < ordergoodsList.size(); i++) {
                    String goodsState = ordergoodsList.get(i).getGoodsState();

                    if ("1".equals(goodsState) || "2".equals(goodsState) || "3".equals(goodsState) || "4".equals(goodsState) || "5".equals(goodsState) || "6".equals(goodsState) || "7".equals(goodsState) || "8".equals(goodsState) | "18".equals(goodsState)) {
                        refundAmount = refundAmount + Double.valueOf(ordergoodsList.get(i).getGoodsTotal_amount());
                        afterGdNum = afterGdNum + Integer.valueOf(ordergoodsList.get(i).getNumber());
                    }

                    mTvStimatedEreAmount.setText("¥" + PriceUtil.priceToString(refundAmount));
                    mTvAfterSalesNum.setText("X" + afterGdNum);

                }

                break;
            case "99":

                mTvOrderState.setText("失败");


                break;
            default:
                break;

        }

    }

    private void initView(ProgressFrameLayout view) {
        mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        mTvOrderTime = (TextView) view.findViewById(R.id.tv_order_time);
        mTvOrderState = (TextView) view.findViewById(R.id.tv_order_state);
        mTvRecipientName = (TextView) view.findViewById(R.id.tv_recipient_name);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvReceivingAdress = (TextView) view.findViewById(R.id.tv_receiving_adress);
        mRvOrderProduct = (RecyclerView) view.findViewById(R.id.rv_order_product);
        mTvTotalAmount = (TextView) view.findViewById(R.id.tv_total_amount);
        mTvFreigt = (TextView) view.findViewById(R.id.tv_freigt);
        mTvTreatment = (TextView) view.findViewById(R.id.tv_treatment);
        mTvPaymentMethod = (TextView) view.findViewById(R.id.tv_payment_method);
        mTvRelPayemnt = (TextView) view.findViewById(R.id.tv_rel_payment);
        mTvCustomerPhone = (TextView) view.findViewById(R.id.tv_customer_phone);
        mTvBottomOrderState = (TextView) view.findViewById(R.id.tv_bottom__order_state);
        mTvPaymentMethodTitle = (TextView) view.findViewById(R.id.tv_payment_method_title);
        mTvBottomOrderLeftState = (TextView) view.findViewById(R.id.tv_bottom__order_left_state);
        mTvOrderRemaingTime = (TextView) view.findViewById(R.id.tv_order_remaining_time);
        mLlAfterSalesStatus = (LinearLayout) view.findViewById(R.id.ll_after_sales_status);
        mLlBottomNormal = (LinearLayout) view.findViewById(R.id.ll_bottom_normal);
        mLlOnlineService = (LinearLayout) view.findViewById(R.id.ll_online_service);
        mTvStimatedEreAmount = (TextView) view.findViewById(R.id.tv_stimated_erefund_amount);//预计退款金额

        mTvAfterSalesNum = (TextView) view.findViewById(R.id.tv_after_sales_num); //售后商品数量

    }


    public static void start(int resId, FragmentManager manager, String zorderinfo_id, String zorder_num) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "OrderDetails";
        OrderDetailFragment fragment = (OrderDetailFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof OrderDetailFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }


        if (fragment == null) {

            fragment = new OrderDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ZORDER_ID, zorderinfo_id);
            bundle.putString(ZORDER_NUM, zorder_num);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);

        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                return;
            }
            Log.i("OneKeyCart", "fragemt不为空");
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);

        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_bottom__order_state:
                if (TextUtils.isEmpty(mOrderState)) {
                    return;
                }
                switch (mOrderState) {
                    case "0"://待付款
                        if (!TextUtils.isEmpty(mTime_expire) && mTime_expire.equals("0") && !TextUtils.isEmpty(mTvOrderRemaingTime.getText().toString()) && mTvOrderRemaingTime.getText().toString().equals(R.string.order_time_out)) {
                            ToastUtils.showToast("订单支付超时");

                        } else {
                            PayFragment.start(getFragmentManager(), mZorderNum, mZorderId);
                        }

                        break;
                    case "1"://待发货
                        if (getResources().getString(R.string.has_remind_the_delivery).equals(mTvBottomOrderState.getText().toString())) {
                            return;
                        }

                        remindTheDelivery();


                        break;
                    case "2"://待收货   去物流页面

                        SuborderLogisticsFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), mOrderInfoList, mZorderId, "", "OrderDetailFragment");


                        break;
                    case "5"://已完成  //删除订单
                        final DeleteDialog deleteDialog = DeleteDialog.newInstance();
                        deleteDialog.show(getActivity().getSupportFragmentManager(), "deleteOrder");
                        deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                            @Override
                            public void onDeleteItem() {

                                deleteOrder(mZorderId);//

                            }
                        });
                        break;

                    case "6": //已取消  再次购买
                        toOrderConfirmFragment();//去提交订单页


                        //ddd
                        break;
                    case "7":// 售后中 订单跟踪
                        Log.e(TAG, "订单号" + mZorderId);
                        SuborderLogisticsFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), mOrderInfoList, mZorderId, "", "OrderDetailFragment");


                        break;
                    default:
                        break;


                }


                break;

            case R.id.ll_online_service:

                CustomerServiceDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "");

                break;
            case R.id.tv_bottom__order_left_state:
                if (mOrderState == null) {
                    return;
                }
                switch (mOrderState) {
                    case "0"://待付款 调取消订单接口、

                        if (!TextUtils.isEmpty(mTime_expire) && mTime_expire.equals("0") && !TextUtils.isEmpty(mTvOrderRemaingTime.getText().toString()) && mTvOrderRemaingTime.getText().toString().equals(R.string.order_time_out)) {
                            ToastUtils.showToast("订单支付超时");

                        } else {
                            final DeleteDialog deleteDialog = DeleteDialog.newInstance();
                            deleteDialog.show(getActivity().getSupportFragmentManager(), "cancelOrder");
                            deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                                @Override
                                public void onDeleteItem() {

                                    cancelOrder(mZorderId);//

                                }
                            });
                        }


                        break;


                    case "6"://已取消  删除订单
                        showDeleteDialog("deleteOrder");


                        break;

                    default:
                        break;

                }


                break;


            default:
                break;
        }


    }

    private void toOrderConfirmFragment() {

        ConfirmOrderBean confirmOrderBean = new ConfirmOrderBean();
        ConfirmOrderBean.ItemBean itemBean = new ConfirmOrderBean.ItemBean();
        mItemBeanArrayList = new ArrayList<>();
        if (mOrdergoodsList != null && mOrdergoodsList.size() != 0) {
            for (OrderInfoBean.OrderListBean.OrdergoodsListBean ordergoodsListBean : mOrdergoodsList) {

                itemBean.setCount(Integer.parseInt(ordergoodsListBean.getNumber()));
                itemBean.setGrossWeight(ordergoodsListBean.getGrossWeight());
                itemBean.setTitle(ordergoodsListBean.getGoods_name());
                itemBean.setSpec(ordergoodsListBean.getSpecification());
                itemBean.setImgUrl(ordergoodsListBean.getPicUrl());
                itemBean.setSpecificationId(ordergoodsListBean.getSpecificationId());
                itemBean.setProductCode(ordergoodsListBean.getGoods_code());
                itemBean.setProductId(ordergoodsListBean.getGoods_id());
                itemBean.setPrice(ordergoodsListBean.getPrice());
                mItemBeanArrayList.add(itemBean);


            }

            confirmOrderBean.setItemBeanList(mItemBeanArrayList);
            if (mZorderPayinfo != null) {
                mRelPayment = mZorderPayinfo.getZorder_total_money();
            }
            OrderConfirmFragment.start(getActivity().getSupportFragmentManager(), R.id.fl_container, null, "", mItemBeanArrayList, mRelPayment, null, "00");
        }


    }

    private void showDeleteDialog(String tag) {


        final DeleteDialog deleteDialog = DeleteDialog.newInstance();
        deleteDialog.show(getActivity().getSupportFragmentManager(), tag);
        deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
            @Override
            public void onDeleteItem() {

                deleteOrder(mZorderId);//

            }
        });
    }

    private void remindTheDelivery() {
        MyProgressDialog pd = PBUtil.getPD(getActivity());
        pd.show();
        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ZORDER_ID, mZorderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.ORDER_REMAIN_DELIVERY_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        OkGoUtils.doStringPostRequest(map, OrderClient.ORDER_REMAIN_DELIVERY, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                pd.dismiss();
                NotificationDialogFragment.newInstance(getString(R.string.shipment_request)).show(getActivity().getSupportFragmentManager(), "");
                mTvBottomOrderState.setText(R.string.has_remind_the_delivery);
            }

            @Override
            public void onFail(int code, String error) {
                ToastUtils.showToast("提醒失败");
                pd.dismiss();
            }
        });

    }

    //删除订单
    public void deleteOrder(String orderId) {
        MyProgressDialog pd = PBUtil.getPD(getActivity());
        pd.show();
        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ZORDER_ID, orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.ORDER_DELETE_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        OkGoUtils.doStringPostRequest(map, OrderClient.ORDER_DELETE, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                pd.dismiss();
                ToastUtils.showToast("删除成功");
                getActivity().onBackPressed();
                EventBus.getDefault().postSticky(new NotifyAllOrderRvEvent(orderId));
                //OrderCountClient.getOrderCount();
            }

            @Override
            public void onFail(int code, String error) {
                pd.dismiss();
                ToastUtils.showToast("删除失败");

            }
        });


    }

    //取消订单
    public void cancelOrder(String orderId) {
        MyProgressDialog pd = PBUtil.getPD(getActivity());
        pd.show();
        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ZORDER_ID, orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", OrderClient.ORDER_CANCEL_METHOD);
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        OkGoUtils.doStringPostRequest(map, OrderClient.ORDER_CANCEL, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                pd.dismiss();
                ToastUtils.showToast("取消成功");
                getActivity().onBackPressed();
                EventBus.getDefault().postSticky(new NotifyAllOrderRvEvent(orderId));
                // OrderCountClient.getOrderCount();
            }

            @Override
            public void onFail(int code, String error) {
                pd.dismiss();
                ToastUtils.showToast("取消失败");

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new MainTitleEvent("订单详情"));

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            EventBus.getDefault().post(new MainTitleEvent("订单详情"));
        }


    }
}
