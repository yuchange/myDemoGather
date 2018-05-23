package com.bingstar.bingmall.cart;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;
import com.bingstar.bingmall.cart.bean.OrderFinishEvent;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.event.FinishOneKeyFragmentEvent;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.cart.view.PayWarnFragment;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.goods.view.DelayTextWatcher;
import com.bingstar.bingmall.goods.view.MutableGoodsViewPager;
import com.bingstar.bingmall.order.OrderFragment;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.LogUtils;
import com.yunzhi.lib.utils.UIUtils;
import com.yunzhi.lib.view.OnSingleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 功能：
 * Created by yaoyafeng on 17/2/6 下午3:49
 * <p/>
 * 一键下单页面
 */
public class OneKeyCartFragment extends BaseFragment implements ICartContract.CartListView, View.OnClickListener {


    public static String PRODUCT_ID = "PRODUCT_ID"; //商品ID
    public static String GOODS_COUNT = "GOODS_COUNT";  //商品数量

    private ArrayList<Coupon.CouponInfo> couponInfoList;
    private ICartContract.CartPresenter presenter;
    private CouponListAdapter couponListAdapter;
    private double totalPrice;
    private LinearLayout cart_order_layout, cart_discount_linear, cartfragment_poprv_linear;
    private RelativeLayout cartfragment_discountpop_rela;
    private TextView one_addr_name_tv, cart_addr_tv, cart_addr_name_tv, cart_addr_phone_tv, cart_addr_addr_tv, cart_discountprice_tv, carditem_price_tv, carditem_couponrange, carditem_coupondetail;

    private RecyclerView cartfragment_discount_rv;
    private View carditem_view;
    private String card_id, card_price;//优惠券的id和优惠券的价格
    private int position;
    TextView address_location_log;
    private TextView tvProductMainTitle;
    private TextView tvProductSubtitle;
    private TextView tvGoodsDiscountPrice;
    private TextView tvGoodsOrignalPrice;
    private TextView tvGoodsActiveContent;
    private TextView tActivityEndTime;
    private TextView tvGoodsSpecifications;
    private ImageButton ivGoodsDetailNumDecrease;
    private EditText etGoodsNum;
    private ImageButton ivGoodsDetailNumIncrease;
    private ImageView ivWeixin;
    private RelativeLayout cartfragmentAddaddrText;

    private TextView cartTotalpriceTv;
    private static final String GOODS_ID = "goods_id";
    private static final String COUNT = "count";
    private static String mBsjProductId;
    private static String mCount;
    private MutableGoodsViewPager mGoodImgVp;
    private TextView mTvPreferen;
    private int stockNumm;
    private ArrayList<ProductInfoAdd> mGoodsPayList;
    private ProductInfoAdd mProductInfoAdd;
    private TextView mTvQuan;
    private TextView mTvCuponsContent;
    private TextView mTvCouponRemindw;
    private View mView;


    public static String getBsjProductId() {
        return mBsjProductId;
    }

    public static void setBsjProductId(String bsjProductId) {
        mBsjProductId = bsjProductId;
    }

    public static String getCount() {
        return mCount;
    }

    public static void setCount(String count) {
        mCount = count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private static OneKeyCartOrder mOneKeyCartOrder;//一键下单信息集合


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setPresenter();
        Log.i("OneKeyCart", "onCreate执行了");
        EventBus.getDefault().register(this);
        mGoodsPayList = new ArrayList<>();//支付信息集合
        couponInfoList = new ArrayList<>();//优惠券信息集合
        mView = inflater.inflate(R.layout.cart_fragment_onekey, container, false);
        initView(mView);
        initListener();
        cart_discount_linear.setOnClickListener(new View.OnClickListener() {//优惠券列表
            @Override
            public void onClick(View v) {
                if (couponInfoList.size() > 0) {
                    if (cartfragment_discountpop_rela.isShown()) {
                        cartfragment_discountpop_rela.setVisibility(View.GONE);
                    } else {
                        cartfragment_discountpop_rela.setVisibility(View.VISIBLE);//相对布局
                    }
                }
                cartfragment_discountpop_rela.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartfragment_discountpop_rela.setVisibility(View.GONE);
                        Log.i("TAG", "GONE" + "  ");
                    }
                });
                cartfragment_poprv_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                if (couponInfoList.size() > 3) {
                    RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) cartfragment_poprv_linear.getLayoutParams();
                    linearParams.height = 420;
                    cartfragment_poprv_linear.setLayoutParams(linearParams);
                } else {
                    //恢复原来布局样式
                    RelativeLayout.LayoutParams mLp = (RelativeLayout.LayoutParams) cartfragment_poprv_linear.getLayoutParams(); //优惠卷原始布局参数
                    mLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mLp.setMargins(UIUtils.dp2px(getContext(), 440), 0, 0, UIUtils.dp2px(getContext(), 165));
                    cartfragment_poprv_linear.setLayoutParams(mLp);
                }
            }
        });

        WarpLinearLayoutManager cardLayoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cartfragment_discount_rv.setLayoutManager(cardLayoutManager);
        couponListAdapter = new CouponListAdapter(couponInfoList);
        cartfragment_discount_rv.setAdapter(couponListAdapter);

        couponListAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(View view, int position) {
                if (couponListAdapter.getItem(position).isSelected()) {
                    couponListAdapter.getItem(position).setSelected(false);
                    if (couponInfoList.size() > 0) {
                        cart_discount_linear.setBackgroundResource(R.drawable.cart_discount_choose);
                    } else {
                        cart_discount_linear.setBackground(getResources().getDrawable(R.drawable.cart_nodiscount_bg));
                    }
                    carditem_price_tv.setText("");
                    card_id = "0";
                    card_price = "0";
                    carditem_price_tv.setVisibility(View.GONE);
                    carditem_couponrange.setVisibility(View.GONE);
                    carditem_coupondetail.setVisibility(View.GONE);
                    mTvCouponRemindw.setVisibility(View.GONE);
                    cart_discountprice_tv.setVisibility(View.GONE);
                    carditem_view.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < couponInfoList.size(); i++) {
                        couponInfoList.get(i).setSelected(false);
                    }
                    couponListAdapter.getItem(position).setSelected(true);
                    carditem_price_tv.setVisibility(View.VISIBLE);
                    carditem_couponrange.setVisibility(View.VISIBLE);
                    carditem_coupondetail.setVisibility(View.VISIBLE);
                    mTvCouponRemindw.setVisibility(View.VISIBLE);
                    cart_discountprice_tv.setVisibility(View.VISIBLE);
                    carditem_view.setVisibility(View.VISIBLE);
                    String remindText = couponListAdapter.getItem(position).getRemindText();

                    if (!TextUtils.isEmpty(remindText)) {
                        mTvCouponRemindw.setText(remindText);
                    }
                    String remindFlag = couponListAdapter.getItem(position).getRemindFlag();
                    if (!TextUtils.isEmpty(remindFlag) && remindFlag.equals("1")) {
                        mTvCouponRemindw.setBackgroundResource(R.drawable.coupon_remind_bg);
                    } else {
                        mTvCouponRemindw.setBackground(null);
                    }


                    carditem_price_tv.setText(couponListAdapter.getItem(position).getCouponMoney());
                    carditem_couponrange.setText(couponListAdapter.getItem(position).getCouponRange());
                    carditem_coupondetail.setText(couponListAdapter.getItem(position).getCouponDetail());
                    cart_discount_linear.setBackground(getResources().getDrawable(R.drawable.cart_discount));
                    card_id = couponListAdapter.getItem(position).getCouponId();
                    card_price = couponListAdapter.getItem(position).getCouponMoney();
                }
                notifyPrice();
                couponListAdapter.notifyDataSetChanged();
            }
        });
        return mView;
    }

    //获取优惠劵使用参数
    LinkedList<String> mGoodsIdList; //商品ID
    LinkedList<String> mGoodCountList; //商品数量

    public void getCouponData() {
        if (null == mGoodsIdList) mGoodsIdList = new LinkedList<>();
        if (null == mGoodCountList) mGoodCountList = new LinkedList<>();
        mGoodsIdList.clear();
        mGoodCountList.clear();
        if (!TextUtils.isEmpty(mBsjProductId)) {
            mGoodsIdList.add(mBsjProductId);
        }
        String count = etGoodsNum.getText().toString().trim();
        if (!TextUtils.isEmpty(count)) {
            mGoodCountList.add(count);
        }
    }

    private void initListener() {
        cartfragmentAddaddrText.setOnClickListener(this);
        cart_order_layout.setOnClickListener(this);
        ivGoodsDetailNumDecrease.setOnClickListener(new OnSingleClickListener() {//减
            @Override
            public void onSingleClick(View view) {
                if (!(TextUtils.isEmpty(etGoodsNum.getText().toString())) && Integer.valueOf(etGoodsNum.getText().toString()) > 1) {
                    etGoodsNum.setText(Integer.valueOf(etGoodsNum.getText().toString()) - 1 + "");
                    totalPrice = Double.valueOf(tvGoodsDiscountPrice.getText().toString()) * Integer.valueOf(etGoodsNum.getText().toString());
                    cartTotalpriceTv.setText("¥ " + Util.priceToString(totalPrice));
                    clearCoupon();
                }
            }
        });
        ivGoodsDetailNumIncrease.setOnClickListener(new OnSingleClickListener() {//加
            @Override
            public void onSingleClick(View view) {
                if (!TextUtils.isEmpty(etGoodsNum.getText().toString())) {
                    if ((Integer.valueOf(etGoodsNum.getText().toString()) + 1 <= stockNumm)) {
                        etGoodsNum.setText(Integer.valueOf(etGoodsNum.getText().toString()) + 1 + "");
                        clearCoupon();
                    } else {
                        showToast("库存不足，剩余库存" + stockNumm);
                        return;
                    }
                    if (!TextUtils.isEmpty(cartTotalpriceTv.getText().toString()) && !TextUtils.isEmpty(tvGoodsDiscountPrice.getText().toString())) {
                        totalPrice = Double.valueOf(tvGoodsDiscountPrice.getText().toString()) * Integer.valueOf(etGoodsNum.getText().toString());
                        cartTotalpriceTv.setText("¥ " + Util.priceToString(totalPrice));
                    }
                } else {
                    etGoodsNum.setText(1 + "");

                }

            }
        });


        etGoodsNum.addTextChangedListener(new DelayTextWatcher() {
            @Override
            public void onDelayFinish(String s) {
                if (!TextUtils.isEmpty(etGoodsNum.getText().toString().trim())) {
                    boolean mIsLess = Integer.valueOf(etGoodsNum.getText().toString()) <= stockNumm;//库存
                    if (s.startsWith("0") && !(Integer.valueOf(etGoodsNum.getText().toString()) == 0)) {
                        etGoodsNum.setText(Integer.valueOf(etGoodsNum.getText().toString()) + "");
                        etGoodsNum.setSelection(etGoodsNum.getText().length());

                    }
                    if (mIsLess && !TextUtils.isEmpty(tvGoodsDiscountPrice.getText().toString()) && !(Integer.valueOf(etGoodsNum.getText().toString()) == 0)) {
                        totalPrice = Double.valueOf(tvGoodsDiscountPrice.getText().toString()) * Integer.valueOf(etGoodsNum.getText().toString());
                        cartTotalpriceTv.setText("¥ " + Util.priceToString(totalPrice));
                        clearCoupon();
                        getCouponData();
                        presenter.getCouponList(couponInfoList, mGoodsIdList, mGoodCountList, "0");

                    } else if (Integer.valueOf(etGoodsNum.getText().toString()) == 0) {
                        showToast(R.string.goods_no_less);
                        totalPrice = 0;
                        cartTotalpriceTv.setText("¥ " + Util.priceToString(totalPrice));
                    } else if (stockNumm != 0) {//  商品大于库存的
                        etGoodsNum.setText(stockNumm + "");
                        Log.i("TAG", "执行了空" + etGoodsNum.getText().toString());
                        showToast("库存不足，剩余库存" + stockNumm);
                    }

                } else {
                    showToast("输入为空");
                    totalPrice = 0;
                    cartTotalpriceTv.setText("¥ " + Util.priceToString(totalPrice));
                }

            }

        });


    }

    private void initData(final String goodsId, final String count) {
        etGoodsNum.setText(getSplit(count));
        Log.i("OneKeyCart", "goodsID" + goodsId);
        Log.i("OneKeyCart", "count" + count);
        Log.i("TAG", "COUNT" + getSplit(count));
        Map<String, String> map = new ArrayMap<String, String>();
        map.put(GOODS_ID, goodsId);
        map.put(COUNT, count);
        if (mOneKeyCartOrder != null) {
            mProductInfoAdd = new ProductInfoAdd();
            mProductInfoAdd.setProductId(goodsId);
            mProductInfoAdd.setProductCode(mOneKeyCartOrder.getProductInfo().getBsjProductCode());
            mProductInfoAdd.setProductName(mOneKeyCartOrder.getProductInfo().getBsjProductname());
            mProductInfoAdd.setPrice(mOneKeyCartOrder.getProductInfo().getLimitPrice());
            mProductInfoAdd.setProductType(mOneKeyCartOrder.getProductInfo().getProductType());//商品类型
            mProductInfoAdd.setGross_Weight("0.00");//快递重量

            stockNumm = Integer.valueOf(mOneKeyCartOrder.getProductInfo().getStock());//库存
            setActivityType(mOneKeyCartOrder.getProductInfo().getActivity_type());
            cartTotalpriceTv.setText("¥ " + Util.priceToString(Double.valueOf(mOneKeyCartOrder.getProductInfo().getTotalPrice()))); //商品总价

            tvProductMainTitle.setText(mOneKeyCartOrder.getProductInfo().getBsjProductname());
            tvProductSubtitle.setText(mOneKeyCartOrder.getProductInfo().getGoodsTitle());
            tvGoodsDiscountPrice.setText(mOneKeyCartOrder.getProductInfo().getLimitPrice());//折后价格

            if (!TextUtils.isEmpty(mOneKeyCartOrder.getProductInfo().getDiscountExplain())) {//顶部标题优惠内容
                mTvCuponsContent.setText(mOneKeyCartOrder.getProductInfo().getDiscountExplain());
                mTvQuan.setText("券");
            } else {
                mTvCuponsContent.setVisibility(View.GONE);
                mTvQuan.setVisibility(View.GONE);
            }


            if (!TextUtils.isEmpty(mOneKeyCartOrder.getProductInfo().getPrice())) {//原价
                tvGoodsOrignalPrice.setText("¥ " + mOneKeyCartOrder.getProductInfo().getPrice());
                tvGoodsOrignalPrice.setPaintFlags((Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG));
            } else {
                tvGoodsOrignalPrice.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mOneKeyCartOrder.getProductInfo().getActivity())) {//优惠内容
                tvGoodsActiveContent.setText(mOneKeyCartOrder.getProductInfo().getActivity());
            } else {
                tvGoodsActiveContent.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mOneKeyCartOrder.getProductInfo().getEndTime())) {
                tActivityEndTime.setText("活动截止时间：" + mOneKeyCartOrder.getProductInfo().getEndTime());

            } else {
                tActivityEndTime.setVisibility(View.GONE);
            }

            tvGoodsSpecifications.setText("规格： " + mOneKeyCartOrder.getProductInfo().getWeight() + mOneKeyCartOrder.getProductInfo().getUnit());

            List<OneKeyCartOrder.ProductInfoBean.ProductPicListBean> productPicList = mOneKeyCartOrder.getProductInfo().getProductPicList();
            OneKeyImgAdapter oneKeyImgAdapter = new OneKeyImgAdapter(productPicList, getContext());
            mGoodImgVp.setAdapter(oneKeyImgAdapter);
            mGoodImgVp.setDot(oneKeyImgAdapter.getCount());
            Util.imageLoader(ivWeixin, mOneKeyCartOrder.getProductInfo().getQRcode(), ContextCompat.getDrawable(getContext(), R.drawable.img_error_bg));
            getCouponData();
            presenter.getCouponList(couponInfoList, mGoodsIdList, mGoodCountList, "0");//请求优惠券接口

        }


    }

    private void setActivityType(String activity_type) {
        switch (activity_type) {
            case "0":
                mTvPreferen.setVisibility(View.GONE);
                tvGoodsOrignalPrice.setVisibility(View.GONE);
                break;
            case "1":
                mTvPreferen.setVisibility(View.VISIBLE);
                mTvPreferen.setText("每日特惠");
                break;
            case "2":
                mTvPreferen.setVisibility(View.VISIBLE);
                mTvPreferen.setText("限时折扣");
                break;
            case "3":
                mTvPreferen.setVisibility(View.VISIBLE);
                mTvPreferen.setText("限时特价");
                break;
            default:
                mTvPreferen.setVisibility(View.GONE);
                break;


        }


    }

    private void initView(View view) {
        mGoodImgVp = (MutableGoodsViewPager) view.findViewById(R.id.goods_detail_img);
        mTvPreferen = (TextView) view.findViewById(R.id.tv_preferential);
        tvProductMainTitle = (TextView) view.findViewById(R.id.tv_product_main_title);
        tvProductSubtitle = (TextView) view.findViewById(R.id.tv_product_subtitle);
        tvGoodsDiscountPrice = (TextView) view.findViewById(R.id.tv_goods_discount_price);
        tvGoodsOrignalPrice = (TextView) view.findViewById(R.id.tv_goods_orignal_price);
        tvGoodsActiveContent = (TextView) view.findViewById(R.id.tv_goods_active_content);
        tActivityEndTime = (TextView) view.findViewById(R.id.t_activity_end_time);
        tvGoodsSpecifications = (TextView) view.findViewById(R.id.tv_goods_specifications);
        ivGoodsDetailNumDecrease = (ImageButton) view.findViewById(R.id.iv_goods_detail_num_decrease);
        etGoodsNum = (EditText) view.findViewById(R.id.et_goods_num);
        ivGoodsDetailNumIncrease = (ImageButton) view.findViewById(R.id.iv_goods_detail_num_increase);
        ivWeixin = (ImageView) view.findViewById(R.id.iv_weixin);
        cartfragmentAddaddrText = (RelativeLayout) view.findViewById(R.id.one_fragment_addaddr_text);
        cartTotalpriceTv = (TextView) view.findViewById(R.id.cart_totalprice_tv);
        cartfragment_discount_rv = (RecyclerView) view.findViewById(R.id.cartfragment_discount_rv);//优惠券列表recyclerview
        address_location_log = (TextView) view.findViewById(R.id.one_addr_addr_logo);
        cart_order_layout = (LinearLayout) view.findViewById(R.id.cart_order);
        cart_addr_tv = (TextView) view.findViewById(R.id.one_addr_tv);
        one_addr_name_tv = (TextView) view.findViewById(R.id.one_addr_name_tv);
        cart_addr_phone_tv = (TextView) view.findViewById(R.id.one_addr_phone_tv);
        cart_addr_addr_tv = (TextView) view.findViewById(R.id.one_addr_addr_tv);
        cart_discount_linear = (LinearLayout) view.findViewById(R.id.cart_discount_linear);
        cartfragment_poprv_linear = (LinearLayout) view.findViewById(R.id.cartfragment_poprv_linear);
        cartfragment_discountpop_rela = (RelativeLayout) view.findViewById(R.id.cartfragment_discountpop_rela);
        cart_discountprice_tv = (TextView) view.findViewById(R.id.cart_discountprice_tv);
        carditem_price_tv = (TextView) view.findViewById(R.id.carditem_price_tv);
        carditem_couponrange = (TextView) view.findViewById(R.id.carditem_couponrange);
        carditem_coupondetail = (TextView) view.findViewById(R.id.carditem_coupondetail);
        carditem_view = view.findViewById(R.id.carditem_view);
        mTvCouponRemindw = (TextView) view.findViewById(R.id.card_coupon_remindw);//商品即将过期
        mTvQuan = (TextView) view.findViewById(R.id.tv_quan);
        mTvCuponsContent = (TextView) view.findViewById(R.id.tv_cupons_content);//youhuineiron

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (cart_order_layout != null) {
                cart_order_layout.setClickable(true);
            }
            if (cartfragment_discountpop_rela != null && cartfragment_discountpop_rela.getVisibility() == View.VISIBLE) {
                cartfragment_discountpop_rela.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void onResume() {
        super.onResume();


        Log.i("OneKeyCart", "传过来" + mOneKeyCartOrder.getProductInfo().getBsjProductId());
        mBsjProductId = OneKeyCartFragment.getBsjProductId();
        mCount = OneKeyCartFragment.getCount();
        Log.i("OneKeyCart", "onResume执行了");
        Log.i("OneKeyCart", "商品id" + mBsjProductId);
        Log.i("OneKeyCart", "数量" + mCount);

        if (checkeUser(User.getIntance())) {
            address_location_log.setVisibility(View.VISIBLE);
            one_addr_name_tv.setVisibility(View.VISIBLE);
            cart_addr_phone_tv.setVisibility(View.VISIBLE);
            cart_addr_addr_tv.setVisibility(View.VISIBLE);
            cart_addr_tv.setVisibility(View.INVISIBLE);
            one_addr_name_tv.setText(User.getIntance().getName());

            String phone = User.getIntance().getMobile();
            Log.i("OneKeyCart", "不空执行了" + User.getIntance().getName() + User.getIntance().getPhone());
            if (phone == null || phone.equals("")) {
                phone = User.getIntance().getPhone();
                if (phone == null) {
                    phone = "";
                }
            }
            cart_addr_phone_tv.setText(phone);
            cart_addr_addr_tv.setText(User.getIntance().getAddress());
        } else {
            one_addr_name_tv.setVisibility(View.INVISIBLE);
            cart_addr_phone_tv.setVisibility(View.INVISIBLE);
            cart_addr_addr_tv.setVisibility(View.INVISIBLE);
            cart_addr_tv.setVisibility(View.VISIBLE);
            one_addr_name_tv.setText("");
            cart_addr_phone_tv.setText("");
            cart_addr_addr_tv.setText("");
            Log.i("OneKeyCart", "为空执行了");
        }
        initData(mBsjProductId, mCount);
    }

    @Override
    public void onPause() {
        super.onPause();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("OneKeyCart", "onDestroy: ");
        EventBus.getDefault().unregister(this);
        presenter.unBind();
    }

    @Override
    public void setPresenter() {
        presenter = new CartPresenter(this);
    }

    @Override
    public void notifyCartList() {


    }

    @Override
    public void notifyTitle() {

    }

    @Override
    public void notifyTitleRefresh() {

    }

    /**
     * 错误处理
     */
    @Override
    public void listErr() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.OneKeyCartFragment);
    }

    @Override
    public void notifyCard() {//获取优惠列表回调
        Log.i("OneKey", couponInfoList.toString());
        Log.i("OneKey", "s" + couponInfoList.size());
        if (couponInfoList.size() > 0) {
            cart_discount_linear.setBackgroundResource(R.drawable.cart_discount_choose);
            couponListAdapter.notifyDataSetChanged();
        } else {
            cart_discount_linear.setBackgroundResource(R.drawable.cart_nodiscount_bg);
        }
    }

    @Override
    public void notifyOneCart(OneKeyCartOrder oneKeyCartOrder) {


        //clearCoupon();
        // notifyPrice();
    }

    @Override
    public void notifyPrice() {

        totalPrice = Double.valueOf(tvGoodsDiscountPrice.getText().toString()) * Integer.valueOf(etGoodsNum.getText().toString());

        if (card_price != null) {
            totalPrice = totalPrice - Double.valueOf(card_price);
        }
        if (totalPrice <= 0) {
            totalPrice = 0;
        }
        cartTotalpriceTv.setText("¥ " + Util.priceToString(totalPrice) + "");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.unBind();
        }
    }


    private void clearCoupon() {
        if (couponInfoList.size() > 0) {
            cart_discount_linear.setBackgroundResource(R.drawable.cart_discount_choose);
        } else {
            cart_discount_linear.setBackgroundResource(R.drawable.cart_nodiscount_bg);
        }
        carditem_price_tv.setText("");
        card_id = "0";
        card_price = "0";//优惠券的价格
        carditem_price_tv.setVisibility(View.GONE);
        carditem_couponrange.setVisibility(View.GONE);
        carditem_coupondetail.setVisibility(View.GONE);
        mTvCouponRemindw.setVisibility(View.GONE);
        cart_discountprice_tv.setVisibility(View.GONE);
        carditem_view.setVisibility(View.GONE);
    }


    @Subscribe
    public void onEvent(EventMsg msg) {
        /**
         * 选择收货地址
         */
        if (msg.getClassName().equals(AddrManageFragment.class)) {
//              one_addr_name_tv.setVisibility(View.VISIBLE);
//            //  address_location_log.setVisibility(View.VISIBLE);
//            cart_addr_phone_tv.setVisibility(View.VISIBLE);
//            cart_addr_addr_tv.setVisibility(View.VISIBLE);
//            cart_addr_tv.setVisibility(View.INVISIBLE);
//             one_addr_name_tv.setText(msg.getName());
//            cart_addr_phone_tv.setText(msg.getPhone());
            //   cart_addr_addr_tv.setText(msg.getMsg());
            //Log.e("OneKeyCart", "onEvent: 66666  " + msg.getName());
        } else if ((msg.getClassName().equals(CartClient.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.CartFragment))) || (msg.getClassName().equals(GoodsDetailFragment.class) && msg.getMsg().equals(GoodsDetailFragment.CARTFRAGMENT))) {
            listErr();
        }

    }


    @Subscribe
    public void payEventFinish(OrderFinishEvent finishEvent) {
        cart_order_layout.setClickable(true);
    }


    private boolean checkeUser(User user) {
        if (user.getAddress() == null || user.getAddress().equals("")) {
            return false;
        }
        if (user.getAddressId() == null || user.getAddressId().equals("")) {
            return false;
        }
        if (user.getName() == null || user.getName().equals("")) {
            return false;
        }
        if (user.getMobile() == null || user.getMobile().equals("")) {
            return false;
        }
        if (user.getRegion() == null || user.getRegion().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.one_fragment_addaddr_text) {
            // 在MainActivity中注册
            EventBus.getDefault().post(OneKeyCartFragment.class);
        } else if (v.getId() == R.id.cart_order) {
            cartfragment_discountpop_rela.setVisibility(View.INVISIBLE);
            if (TextUtils.isEmpty(etGoodsNum.getText().toString()) || Integer.valueOf(etGoodsNum.getText().toString()) == 0) {
                showToast("请输入正确的商品数量");
                return;
            }
            if (totalPrice <= 0) {
                return;
            }
            if (card_id == null) {//优惠券id
                card_id = "0";
            }
            if (card_price == null) {//优惠券价格
                card_price = "0";
            }


            if (checkeUser(User.getIntance())) {
                if (mProductInfoAdd != null) {//设置商品数量和总价格
                    mProductInfoAdd.setCount(etGoodsNum.getText().toString());
                    mProductInfoAdd.setTotalPrice(Util.priceToString(totalPrice));
                    mGoodsPayList.add(mProductInfoAdd);
                }

                //判断商品是否为跨境商品
                if (mProductInfoAdd != null && !TextUtils.isEmpty(mProductInfoAdd.getProductType()) && CartStates.CROSS_GOODS.equals(mProductInfoAdd.getProductType())) {
                    if (TextUtils.isEmpty(User.getIntance().getBuyer_IDcard())) {
                        PayWarnFragment.start(getFragmentManager());
                        return;
                    }
                }


                if (mGoodsPayList.size() == 0) {
                    return;
                }
                cart_order_layout.setClickable(false);
                BeanTranslater.createCartOrder(mGoodsPayList, "" + totalPrice, card_id, card_price, "03");//一键下单传03
                mGoodsPayList.clear();
            } else {
                AddrManageFragment.start(R.id.main_fragment, getFragmentManager());
            }
        }


    }


    public static void start(int resId, FragmentManager manager, String productId, String count, OneKeyCartOrder oneKeyCartOrder) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "OneKey";
        mOneKeyCartOrder = oneKeyCartOrder;
        OneKeyCartFragment fragment = (OneKeyCartFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new OneKeyCartFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            bundle.putString(PRODUCT_ID, productId);
            bundle.putString(GOODS_COUNT, count);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
            Log.i("OneKeyCart", "fragemt为空");
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                return;
            }
            Log.i("OneKeyCart", "fragemt不为空");
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof OneKeyCartFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                        LogUtils.Debug_E(OrderFragment.class, frag.getClass().getSimpleName());
                    }
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }


    public String getSplit(String data) {
        if (data == null || "null".equals(data.trim()) || "".equals(data.trim())) {
            return "1";
        } else {
            return data;
        }
    }


    @Subscribe()
    public void onFinishOneKeyFragment(FinishOneKeyFragmentEvent finishOneKeyFragmentEvent) {
        getActivity().onBackPressed();//销毁自己
    }


}
