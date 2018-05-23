package com.zyf.bings.bingos.goods;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.AddressFragment;
import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.base.OnSingleClickListener;
import com.zyf.bings.bingos.base.Util;
import com.zyf.bings.bingos.base.WrapLinearLayoutManager;
import com.zyf.bings.bingos.business.UserBusiness;
import com.zyf.bings.bingos.coupon.view.OrderCouponFragment;
import com.zyf.bings.bingos.event.ConfirmOrderEvent;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.event.OrderCouponEvent;
import com.zyf.bings.bingos.goods.bean.GoodsDetailBean;
import com.zyf.bings.bingos.goods.bean.OrderConfirmBean;
import com.zyf.bings.bingos.goods.events.AddressChangeEvent;
import com.zyf.bings.bingos.order.NotificationDialogFragment;
import com.zyf.bings.bingos.order.adapter.OrderConfirmRvAdapter;
import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;
import com.zyf.bings.bingos.order.bean.CreateOrderInfo;
import com.zyf.bings.bingos.order.bean.Receiver;
import com.zyf.bings.bingos.order.bean.Trade;
import com.zyf.bings.bingos.order.bean.TradeItem;
import com.zyf.bings.bingos.pay.PayFragment;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zyf.bings.bingos.R.id.iv_item_order_confirm_decre;
import static com.zyf.bings.bingos.R.id.iv_item_order_confirm_incre;

/**
 * Created by zhangyifei on 17/9/13.
 */

public class OrderConfirmFragment extends Fragment implements IOrderConfirmContract.OrderConfirmView {
    public static final String GOODS_DETAIL_BEAN = "goods_detail_bean";
    public static final String GOODS_SELECT_SPEC = "goods_select_spec";
    public static final String CONFIRM_ORDER_BEAN = "confirm_order_bean";
    public static final String TOTAL_PRICE = "total_price";
    public static final String SPECIFICATION_ID = "specificationId";
    public static final String CYCLE_FLAG = "cycle_flag";
    //    @BindView(R.id.tv_order_confirm_user_name)
    TextView mTvUserName;
    //    @BindView(R.id.tv_order_confirm_user_tel)
    TextView mTvUserTel;
    //    @BindView(R.id.tv_order_confirm_user_idcard)
    TextView mTvUserIdcard;
    //    @BindView(R.id.tv_order_confirm_user_address)
    TextView mTvUserAddress;
    //    @BindView(R.id.tv_order_confirm_address_change)
    TextView mTvAddressChange;
    //    @BindView(R.id.tv_order_confirm_coupon_info)
    TextView mTvOCouponInfo;
    //    @BindView(R.id.tv_order_confirm_coupon_select)
    TextView mTvCouponSelect;
    //    @BindView(R.id.iv_order_confirm_img)
//    ImageView mIvgoodsImg;
    //    @BindView(R.id.tv_order_confirm_title)
//    TextView mTvTitle;
//    @BindView(R.id.flex_order_confirm_spec)
//    FlexboxLayout mFlexSpec;
//    @BindView(R.id.iv_order_confirm_decre)
//    ImageButton mIvDecre;
//    @BindView(R.id.tv_order_confirm_count)
//    TextView mTvCount;
//    @BindView(R.id.iv_order_confirm_incre)
//    ImageButton mIvIncre;
    @BindView(R.id.tv_order_confirm_price)
    TextView mTvPrice;
    //    @BindView(R.id.tv_order_confirm_discountprice)
//    TextView mTvDiscountprice;
//    @BindView(R.id.btn_order_confirm_buy)
//    Button mBtnConfirm;
    @BindView(R.id.order_confirm_rv)
    RecyclerView mOrderRv;
    @BindView(R.id.tv_choose_coupon)
    TextView mTvChooseCoupon;
    @BindView(R.id.tv_shopcart_confirm)
    TextView mOrderConfirm;
    ProgressFrameLayout mProgressFrameLayout;

    IOrderConfirmContract.OrderConfirmPresenter mOrderConfirmPresenter;
    Unbinder unbinder;

    private GoodsDetailBean.ProductInfoDetailBean mProductInfoDetailBean;
    private OrderConfirmBean.CouponInfo mCouponInfo;
    private ConfirmOrderBean mConfirmOrderBean;
    private String mSelectSpec;
    private OrderConfirmRvAdapter mOrderConfirmRvAdapter;
    private List<ConfirmOrderBean.ItemBean> mBeanList;
    private String mTotalPrice;
    private String mRegion;
    private int mTag;
    private String mSpecificationId;
    private List<ConfirmOrderBean.ItemBean> mItemBeanList;
    private ConfirmOrderBean.ItemBean mItemBean;
    private int mCount;
    private String mCycleFlag;
    private String mCouponId;
    private String mCouponMoney;


    public static void start(FragmentManager manager, int resId, GoodsDetailBean.ProductInfoDetailBean goodsDetail, String spec, List<ConfirmOrderBean.ItemBean> itemBeanList, String totalPrice, String specificationId, String cycleFlag) {
        if (manager == null || resId == 0) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "OrderConfirmFragment";
        OrderConfirmFragment fragment = (OrderConfirmFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof OrderConfirmFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }

        if (fragment == null) {
            fragment = OrderConfirmFragment.newInstance(goodsDetail, spec, itemBeanList, totalPrice, specificationId, cycleFlag);
            transaction.add(resId, fragment, TAG);
            transaction.addToBackStack(null);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                EventBus.getDefault().postSticky(new MainTitleEvent("提交订单"));
                return;
            }
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
        EventBus.getDefault().postSticky(new MainTitleEvent("提交订单"));
    }

    public static OrderConfirmFragment newInstance(GoodsDetailBean.ProductInfoDetailBean goodsDetailBean, String spec, List<ConfirmOrderBean.ItemBean> itemBeanList, String totalPrice, String specificationId, String cycleFlag) {

        Bundle args = new Bundle();
        args.putSerializable(GOODS_DETAIL_BEAN, goodsDetailBean);
        args.putString(GOODS_SELECT_SPEC, spec);
        args.putString(CYCLE_FLAG, cycleFlag);
        args.putParcelableArrayList(CONFIRM_ORDER_BEAN, (ArrayList<? extends Parcelable>) itemBeanList);
        args.putString(TOTAL_PRICE, totalPrice);
        args.putString(SPECIFICATION_ID, specificationId);
        OrderConfirmFragment fragment = new OrderConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mProductInfoDetailBean = (GoodsDetailBean.ProductInfoDetailBean) getArguments().getSerializable(GOODS_DETAIL_BEAN);
        this.mSelectSpec = getArguments().getString(GOODS_SELECT_SPEC);
//        this.mConfirmOrderBean = (ConfirmOrderBean) getArguments().getSerializable(CONFIRM_ORDER_BEAN);
        this.mItemBeanList = getArguments().getParcelableArrayList(CONFIRM_ORDER_BEAN);
        this.mTotalPrice = getArguments().getString(TOTAL_PRICE);
        this.mSpecificationId = getArguments().getString(SPECIFICATION_ID);
        mCycleFlag = getArguments().getString(CYCLE_FLAG);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.order_confirm_fragment, container, false);
        unbinder = ButterKnife.bind(this, mProgressFrameLayout);
        setPresenter();
        if (mOrderConfirmPresenter != null) {
            mOrderConfirmPresenter.subscribe();
        }
        if (mProductInfoDetailBean != null) {
            initData();
        } else {
            initSpec();
        }
        mOrderConfirm.setOnClickListener(new OnSingleClickListener() { // 确认下单
            @Override
            public void onSingleClick(View view) {

                createOrder(mCycleFlag);
            }
        });

        return mProgressFrameLayout;
    }

    /**
     * 接收优惠券信息
     */
    @Subscribe
    public void getCouponInfo(OrderCouponEvent couponEvent) {
        mCouponId = couponEvent.getCouponId();
        mCouponMoney = couponEvent.getCouponMoney();
    }
    /**
     * 创建订单
     */
    private void createOrder(String cycleFlag) {
        if (TextUtils.isEmpty(mCouponId)) {
            mCouponId = "0";
        }
        if (TextUtils.isEmpty(mCouponMoney)) {
            mCouponMoney = "0";
        }
        CreateOrderInfo createOrderInfo = new CreateOrderInfo();
        Trade trade = new Trade();
        trade.setCycleflag(cycleFlag);
        trade.setCouponId(mCouponId);
        trade.setCouponMoney(mCouponMoney);
        trade.setThirdOrderCode(TimeUtil.getTimeBIZService());
        Receiver receiver = new Receiver();
        receiver.setId(mTvUserTel.getText().toString());
        receiver.setName(mTvUserName.getText().toString());
        receiver.setMemberId(SPUtil.getString(getContext(), Config.MEMBER_ID));
        receiver.setAddress(mTvUserAddress.getText().toString());
        receiver.setMobile(mTvUserTel.getText().toString());
//        receiver.setPhone(User.getIntance().getPhone());
//        receiver.setRegion(User.getIntance().getRegion());
        receiver.setRegion(mRegion);
        receiver.setBuyer_IDcard("");
        List<TradeItem> tradeItemList = new ArrayList<>();
//        BigDecimal paymount = new BigDecimal("0.0");
        for (int i = 0; i < mBeanList.size(); i++) {
            TradeItem tradeItem = new TradeItem();
            if (mBeanList.get(i).getSpecificationId() != null) {
                tradeItem.setSpecificationId(mBeanList.get(i).getSpecificationId());
            }
            if (mBeanList.get(i).getProductId() != null) {
                tradeItem.setBsjProductId(mBeanList.get(i).getProductId());
            } else {
//                toastErr(ErrorFragment.CartFragment);
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
            }
            if (mBeanList.get(i).getProductCode() != null) {
                tradeItem.setBsjProductCode(mBeanList.get(i).getProductCode());
            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
            }
            if (mBeanList.get(i).getTitle() != null) {
                tradeItem.setBsjProductName(mBeanList.get(i).getTitle());
            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
            }
            if (mBeanList.get(i).getCount() != 0) {
                tradeItem.setNumber(mBeanList.get(i).getCount() + "");
            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
            }
            if (mBeanList.get(i).getPrice() != null) {
                tradeItem.setPrice(mBeanList.get(i).getPrice());
            } else {
//                BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.CartFragment_final);
            }
            if (mBeanList.get(i).getGrossWeight() != null) {
                tradeItem.setGrossWeight(mBeanList.get(i).getGrossWeight());
            } else {
                tradeItem.setGrossWeight("0.00");
            }
            BigDecimal price = new BigDecimal(mBeanList.get(i).getPrice());
            BigDecimal count = new BigDecimal(mBeanList.get(i).getCount());
            BigDecimal totalfree = price.multiply(count);
            tradeItem.setTotalFee(Util.priceToString(totalfree.doubleValue()));
            tradeItem.setDiscountFee("0");
            tradeItemList.add(tradeItem);
//            paymount = paymount.add(totalfree);
        }
        BigDecimal totalM = new BigDecimal(mTvPrice.getText().toString());
        BigDecimal coupouM = new BigDecimal("0"); // 优惠券
        trade.setPaymentAmount(Util.priceToString(totalM.doubleValue()));
//        trade.setProductAmount(Util.priceToString(coupouM.doubleValue()));
        trade.setTradeItemList(tradeItemList);
        trade.setReceiver(receiver);
        createOrderInfo.setTrade(trade);
        mOrderConfirmPresenter.creatOrder(createOrderInfo);
    }

    private void initData() {
        mProgressFrameLayout.showLoading();
        mOrderConfirmPresenter.getOrderConfirmData(mProductInfoDetailBean.getBsjProductId(), mProductInfoDetailBean.getCount() + "");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("OrderConfirmFragment", "onResume: " + "");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("OrderConfirmFragment", "onPause: " + "");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("OrderConfirmFragment", "onHiddenChanged: " + "");
        if (!hidden) {
            EventBus.getDefault().postSticky(new MainTitleEvent("提交订单"));
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("OrderConfirmFragment", "setUserVisibleHint: " + "");
    }


    private void initCoupon(OrderConfirmBean orderConfirmBean) {
        OrderConfirmBean.CouponInfo couponInfo = orderConfirmBean.getCouponInfo();
        if (couponInfo != null) {
            mTvOCouponInfo.setText(couponInfo.getCouponDetail() + ",  -￥" + couponInfo.getCouponMoney());
        } else {
            mTvOCouponInfo.setText("暂无优惠劵");
        }
    }

    private void initAddress(OrderConfirmBean orderConfirmBean) {
        OrderConfirmBean.MemberAddress memberAddress = orderConfirmBean.getMemberAddress();
        if (!TextUtils.isEmpty(memberAddress.getName())) {
            mTvUserName.setText(memberAddress.getName());
        }
        if (!TextUtils.isEmpty(memberAddress.getDetailed())) {
            mTvUserAddress.setText(memberAddress.getDetailed());
        }

        if (!TextUtils.isEmpty(memberAddress.getIdCard())) {
            mTvUserIdcard.setText(memberAddress.getIdCard());
        }

        if (!TextUtils.isEmpty(memberAddress.getMobile())) {
            mTvUserTel.setText(memberAddress.getMobile());
        }
    }


    //初始化右侧商品参数
    private void initSpec() {
        mBeanList = new ArrayList<>();
        if (mProductInfoDetailBean != null) {
            mTag = 1;// 立即购买进入
            mItemBean = new ConfirmOrderBean.ItemBean();
            mItemBean.setCount(mProductInfoDetailBean.getCount());
            mItemBean.setImgUrl(mProductInfoDetailBean.getProductPicList().get(0).getPicUrl());
            mItemBean.setSpec(mSelectSpec);
            mItemBean.setGrossWeight(mProductInfoDetailBean.getGrossWeight());
            mItemBean.setTitle(mProductInfoDetailBean.getBsjProductname());
            mItemBean.setProductCode(mProductInfoDetailBean.getBsjProductCode());
            mItemBean.setProductId(mProductInfoDetailBean.getBsjProductId());
            mItemBean.setPrice(mProductInfoDetailBean.getPrice());
            mItemBean.setSpecificationId(mSpecificationId);
            mBeanList.add(mItemBean);
            changePrice(mItemBean);
            mCount = mItemBean.getCount();
        } else {
            mTag = 2;// 从购物车进入
            mRegion = UserBusiness.getUserRegion();
            mTvPrice.setText(mTotalPrice);
            mBeanList = mItemBeanList;
        }
        WrapLinearLayoutManager wrapLinearLayoutManager = new WrapLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mOrderRv.setLayoutManager(wrapLinearLayoutManager);
        mOrderConfirmRvAdapter = new OrderConfirmRvAdapter(R.layout.item_order_confirm, mTag);
        mOrderConfirmRvAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case iv_item_order_confirm_decre:
                        if (mCount == 1) {
                            return;
                        }
                        mCount--;
                        mItemBean.setCount(mCount);
                        mOrderConfirmRvAdapter.notifyDataSetChanged();
                        changePrice(mItemBean);
                        break;
                    case iv_item_order_confirm_incre:
                        mCount++;
                        mItemBean.setCount(mCount);
                        mOrderConfirmRvAdapter.notifyDataSetChanged();
                        changePrice(mItemBean);
                        break;
                    default:
                }
            }
        });
        View header = LayoutInflater.from(getContext()).inflate(R.layout.order_confirm_rv_head, null);
        mOrderConfirmRvAdapter.addHeaderView(header);
        mTvUserName = (TextView) header.findViewById(R.id.tv_order_confirm_user_name);
        mTvUserTel = (TextView) header.findViewById(R.id.tv_order_confirm_user_tel);
        mTvUserIdcard = (TextView) header.findViewById(R.id.tv_order_confirm_user_idcard);
        mTvUserAddress = (TextView) header.findViewById(R.id.tv_order_confirm_user_address);
        mTvAddressChange = (TextView) header.findViewById(R.id.tv_order_confirm_address_change);
        mTvUserName.setText(UserBusiness.getUserName());
        mTvUserAddress.setText(UserBusiness.getUserAddress());
        mTvUserIdcard.setText(UserBusiness.getUserIdCard());
        mTvUserTel.setText(UserBusiness.getUserMobile());
        mTvAddressChange.setOnClickListener(view -> AddressFragment.start(R.id.fl_container, getFragmentManager()));
        mTvCouponSelect = (TextView) header.findViewById(R.id.tv_order_confirm_coupon_select);
        mTvCouponSelect.setOnClickListener(view -> goToOrderCoupon());
        mTvChooseCoupon.setOnClickListener(view -> goToOrderCoupon());
        mTvOCouponInfo = (TextView) header.findViewById(R.id.tv_order_confirm_coupon_info);
        mOrderConfirmRvAdapter.setNewData(mBeanList);
        mOrderRv.setAdapter(mOrderConfirmRvAdapter);
//        mTvTitle.setText(mProductInfoDetailBean.getGoodsTitle());
//        mTvCount.setText(mProductInfoDetailBean.getCount() + "");

//        String unit = mProductInfoDetailBean.getUnit();
//        String weight = mProductInfoDetailBean.getWeight();

//        if (mFlexSpec != null) {
//            mFlexSpec.removeAllViews();
//        }

//        mFlexSpec.addView(getButton(R.mipmap.goods_detail_spec_select, getResources().getColor(R.color.tv_goods_detail_spec),
//                mSelectSpec);


    }

    /**
     * 优惠券
     */
    List<String> mGoodsIdList;
    List<String> mGoodCountList;
    private void goToOrderCoupon() {
        if (mGoodsIdList == null) {
            mGoodsIdList = new ArrayList<>();
        }
        if (mGoodCountList == null) {
            mGoodCountList = new ArrayList<>();
        }
        mGoodsIdList.clear();
        mGoodCountList.clear();
        for (int i = 0; i < mBeanList.size(); i++) {
            mGoodsIdList.add(mBeanList.get(i).getProductId());
            mGoodCountList.add(String.valueOf(mBeanList.get(i).getCount()));
        }
        if (mBeanList.size() > 1) {
            OrderCouponFragment.start(R.id.fl_container, getFragmentManager(), mGoodsIdList, mGoodCountList, "1");
        } else {
            OrderCouponFragment.start(R.id.fl_container, getFragmentManager(), mGoodsIdList, mGoodCountList, "0");
        }
    }

    //获取规格布局
//    public Button getButton(int resource, int textColor, String text) {
//        规格
//        Button button = new Button(getActivity());
//        button.setText(text);
//        button.setTextColor(textColor);
//        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//        button.setAllCaps(false); //防止自动转换为大写
//        button.setBackgroundResource(resource);
//        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(DensityUtil.dp2px(getActivity(), 62), DensityUtil.dp2px(getActivity(), 40));
//        params.setMargins(0, 0, DensityUtil.dp2px(getActivity(), 44), 0);
//        button.setLayoutParams(params);
//        button.setOnClickListener(v ->
//                changeSelectSpec(v)
//        );
//        return button;
//    }


    public void changePrice(ConfirmOrderBean.ItemBean itemBean) {
        Double totalPrice = 0d;
//
        BigDecimal count = new BigDecimal(itemBean.getCount());
        BigDecimal itemPrice;
//
//
//        if (!TextUtils.isEmpty(itemBean.getLimitPrice())) {
//            itemPrice = new BigDecimal(itemBean.getLimitPrice());
//        } else {
        itemPrice = new BigDecimal(itemBean.getPrice());
//        }
        totalPrice = itemPrice.multiply(count).doubleValue();

//        if (mCouponInfo != null) {
//            mTvDiscountprice.setVisibility(View.VISIBLE);
//            String couponMoney = mCouponInfo.getCouponMoney();
//            BigDecimal coupon = new BigDecimal(couponMoney);
//            totalPrice = new BigDecimal(totalPrice).subtract(coupon).doubleValue();
//            mTvPrice.setText("合计: " + getResources().getString(R.string.money) + totalPrice);
//            BigDecimal actualPrice = new BigDecimal(totalPrice).add(new BigDecimal(couponMoney));
//            mTvDiscountprice.setText("(￥" + (actualPrice.doubleValue()) + " - ￥" + couponMoney + ")");
//        } else {
//            mTvDiscountprice.setVisibility(View.GONE);
        mTvPrice.setText(totalPrice + "");
//        }
    }

//    public void changeSelectSpec(View v) {
//        for (int i = 0; i < mFlexSpec.getChildCount(); i++) {
//            View childAt = mFlexSpec.getChildAt(i);
//            Button btn = (Button) childAt;
//            if (v.equals(childAt)) {
//                btn.setTextColor(getResources().getColor(R.color.tv_goods_detail_spec));
//                btn.setBackgroundResource(R.mipmap.goods_detail_spec_select);
//            } else {
//                btn.setTextColor(getResources().getColor(R.color.tv_goods_detail_black));
//                btn.setBackgroundResource(R.mipmap.goods_detail_spec_unselect);
//            }
//        }
//    }


//    public void onClick(View view) {
//        switch (view.getId()) {
////            case R.id.btn_order_confirm_buy:
////                ToastUtils.showToast("去支付页面");
////                break;
//            case R.id.tv_order_confirm_address_change:
//                AddressFragment.start(R.id.fl_container, getFragmentManager());
//                break;
//            case R.id.tv_order_confirm_coupon_select:
//                ToastUtils.showToast("优惠劵选择");
//                break;
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mOrderConfirmPresenter != null) {
            mOrderConfirmPresenter.unsubscribe();
            mOrderConfirmPresenter = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setPresenter() {
        mOrderConfirmPresenter = new OrderConfirmPresenter(this);
    }

    @Override
    public void notifyOrderConfirmRefresh(OrderConfirmBean orderConfirmBean) {
        mProgressFrameLayout.showContent();
        if (orderConfirmBean.getMemberAddress() != null) {
            mCouponInfo = orderConfirmBean.getCouponInfo();
            mRegion = orderConfirmBean.getMemberAddress().getRegion();
            initSpec();  //初始化属性
//        changePrice(mProductInfoDetailBean);   //初始化价格
            initCoupon(orderConfirmBean);  // 优惠劵信息
            initAddress(orderConfirmBean);  //地址信息
        }
//        ImageLoader.load(mIvgoodsImg, mProductInfoDetailBean.getProductPicList().get(0).getPicUrl());
    }

    @Subscribe
    public void confirmChang(ConfirmOrderEvent confirmOrderEvent) {
        mItemBeanList = confirmOrderEvent.getBeanList();
        Log.d("OrderConfirmFragment", confirmOrderEvent.getTotalPrice());

        mTotalPrice = confirmOrderEvent.getTotalPrice();
        initSpec();
    }
    @Subscribe(sticky = true)
    public void changeAddress(AddressChangeEvent addressChangeEvent) {
        AddressList.MemberAddressListBean memberAddressListBean = addressChangeEvent.getMemberAddressListBean();
        if (mTvUserName != null && !TextUtils.isEmpty(memberAddressListBean.getName())) {
            mTvUserName.setText(memberAddressListBean.getName());
        }
        if (mTvUserAddress != null && !TextUtils.isEmpty(memberAddressListBean.getDetailed())) {
            mTvUserAddress.setText(memberAddressListBean.getDetailed());
        }

        if (mTvUserIdcard != null && !TextUtils.isEmpty(memberAddressListBean.getIdCard())) {
            mTvUserIdcard.setText(memberAddressListBean.getIdCard());
        }
        if (mTvUserTel != null && !TextUtils.isEmpty(memberAddressListBean.getMobile())) {
            mTvUserTel.setText(memberAddressListBean.getMobile());
        }
    }


    @Override
    public void listErr(String errMsg) {
        mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "加载失败...", errMsg, "点击重试", v -> {
            mProgressFrameLayout.showLoading();
            mOrderConfirmPresenter.getOrderConfirmData(mProductInfoDetailBean.getBsjProductId(), mProductInfoDetailBean.getCount() + "");
        });
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void createOrderSuccess(String zorder_no,String zorder_id) {
        PayFragment.start(getActivity().getSupportFragmentManager(), zorder_no,zorder_id);
    }

    @Override
    public void createOrderFailed(String errMsg) {
        final NotificationDialogFragment notificationDialog = NotificationDialogFragment.newInstance(errMsg);
        notificationDialog.show(getActivity().getSupportFragmentManager(), "orderConfirm");
    }
}
