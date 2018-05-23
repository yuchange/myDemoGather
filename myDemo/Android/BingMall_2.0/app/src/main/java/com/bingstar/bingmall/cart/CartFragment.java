package com.bingstar.bingmall.cart;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.Utils.ACache;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.bean.Coupon;
import com.bingstar.bingmall.cart.bean.DeleteDialogBean;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;
import com.bingstar.bingmall.cart.bean.OrderFinishEvent;
import com.bingstar.bingmall.cart.bean.ProductInfoAdd;
import com.bingstar.bingmall.cart.bean.ProductInfoUpdate;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.cart.view.CartItemDecoration;
import com.bingstar.bingmall.cart.view.CouponPopupWindow;
import com.bingstar.bingmall.cart.view.CustomLinearLayoutManager;
import com.bingstar.bingmall.cart.view.DeleteDialog;
import com.bingstar.bingmall.cart.view.PayWarnFragment;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.sdk.pay.haier.HaierPayActivity;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bingstar.bingmall.user.addr.AddrStates;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.pay.PayStates;
import com.yunzhi.lib.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 功能：
 * Created by yaoyafeng on 17/2/6 下午3:49
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/6 下午3:49
 * @modify by reason:{方法名}:{原因}
 */
public class CartFragment extends BaseFragment implements ICartContract.CartListView {

    private ArrayList<ProductInfoAdd> mCartList;  //购物车所有商品
    private ArrayList<ProductInfoAdd> mCartOrderList; //下单时选择的商品
    private ArrayList<ProductInfoAdd> mCartCurrentList; //当前类目下的商品

    private ArrayList<CartTitle.CartCategoryInfo> cartTitleList;

    private ArrayList<Coupon.CouponInfo> couponInfoList;

    private ICartContract.CartPresenter presenter;

    private CartListAdapter listAdapter;
    private CartTitleAdapter cartTitleAdapter;
    private CouponListAdapter couponListAdapter;

    private TextView cart_totalprice_tv;
    private BigDecimal totalPrice;


    private int currentPage = 0;//当前页下标


    private boolean isPause = false;

    private String category;//类目（title）id

    private LinearLayout cart_order_layout, cart_discount_linear, cartfragment_poprv_linear;

    private RelativeLayout cartfragment_discountpop_rela;

    private boolean defaultSelect = false;

    private TextView cart_addr_tv, cart_addr_name_tv, cart_addr_phone_tv, cart_addr_addr_tv, cart_discountprice_tv, carditem_price_tv, carditem_couponrange, carditem_coupondetail;
    private TextView cart_coupon_remind;
    private RelativeLayout addrLinear;
    private RecyclerView cartfragment_discount_rv;
    private LinearLayout cart_nogoods_txt_linear;//购物车还没有商品
    private View carditem_view;
    private String card_id, card_price;


    private int position;

    private RecyclerView mCartListView;
    private RecyclerView mTitleListView;
    private CouponPopupWindow mPopupWindow;

    private boolean mRefresh = true;

    //缓存管理
    ACache mACache;
    public static final String GOODS_ID_KEY = "goods_id_key";
    public static final String CATEGORY_ID = "CategoryId";


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "Cart";
        CartFragment fragment = (CartFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new CartFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            if (!fragment.isHidden()) {
                transaction.commit();
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof CartFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commit();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setPresenter();
        mCartList = new ArrayList<>();
        cartTitleList = new ArrayList<>();
        couponInfoList = new ArrayList<>();
        mCartOrderList = new ArrayList<>();
        mCartCurrentList = new ArrayList<>();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        mCartListView = (RecyclerView) view.findViewById(R.id.cartfragment_rv);
        mTitleListView = (RecyclerView) view.findViewById(R.id.cartfragment_title_rv);
        mTitleListView.addItemDecoration(new CartItemDecoration(getContext(), CartItemDecoration.VERTICAL_LIST));
        cartfragment_discount_rv = (RecyclerView) view.findViewById(R.id.cartfragment_discount_rv);
        cart_order_layout = (LinearLayout) view.findViewById(R.id.cart_order);
        cart_totalprice_tv = (TextView) view.findViewById(R.id.cart_totalprice_tv);
        cart_addr_tv = (TextView) view.findViewById(R.id.cart_addr_tv);
        cart_addr_name_tv = (TextView) view.findViewById(R.id.cart_addr_name_tv);
        cart_addr_phone_tv = (TextView) view.findViewById(R.id.cart_addr_phone_tv);
        cart_addr_addr_tv = (TextView) view.findViewById(R.id.cart_addr_addr_tv);
        cart_coupon_remind = (TextView) view.findViewById(R.id.card_coupon_remindw);
        cart_discount_linear = (LinearLayout) view.findViewById(R.id.cart_discount_linear);
        cartfragment_poprv_linear = (LinearLayout) view.findViewById(R.id.cartfragment_poprv_linear);
        cartfragment_discountpop_rela = (RelativeLayout) view.findViewById(R.id.cartfragment_discountpop_rela);
        cart_discountprice_tv = (TextView) view.findViewById(R.id.cart_discountprice_tv);
        carditem_price_tv = (TextView) view.findViewById(R.id.carditem_price_tv);
        carditem_couponrange = (TextView) view.findViewById(R.id.carditem_couponrange);
        carditem_coupondetail = (TextView) view.findViewById(R.id.carditem_coupondetail);
        carditem_view = view.findViewById(R.id.carditem_view);
        cart_nogoods_txt_linear = (LinearLayout) view.findViewById(R.id.cart_nogoods_txt_linear);
        cart_nogoods_txt_linear.setVisibility(View.GONE);
        addrLinear = (RelativeLayout) view.findViewById(R.id.cartfragment_addaddr_text);
        mPopupWindow = new CouponPopupWindow(getActivity());
    }

    private void initListener() {
        cart_discount_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (couponInfoList.size() > 0) {
                    cartfragment_discountpop_rela.setVisibility(View.VISIBLE);
                    //mPopupWindow.showAtLocation(cart_discount_linear, Gravity.BOTTOM, UIUtils.px2dp(getActivity(), 10), UIUtils.px2dp(getActivity(), 250));
                    //mPopupWindow.setNewData(couponInfoList);
                }
                cartfragment_discountpop_rela.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartfragment_discountpop_rela.setVisibility(View.GONE);
                    }
                });
            }
        });

        //mCartListView.addItemDecoration(new SpaceItemDecoration(10));

        WarpLinearLayoutManager linearLayoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTitleListView.setLayoutManager(linearLayoutManager);


        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        CustomLinearLayoutManager cartLayoutManager = new CustomLinearLayoutManager(getContext());
        cartLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCartListView.setLayoutManager(cartLayoutManager);

        WarpLinearLayoutManager couponLayoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cartfragment_discount_rv.setLayoutManager(couponLayoutManager);


        listAdapter = new CartListAdapter(mCartList);//购物车适配器
        cartTitleAdapter = new CartTitleAdapter(cartTitleList);//title适配器
        couponListAdapter = new CouponListAdapter(couponInfoList);

        cartfragment_discount_rv.setAdapter(couponListAdapter);
        mCartListView.setAdapter(listAdapter);
        mTitleListView.setAdapter(cartTitleAdapter);

        mCartListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideSoft();
                }
                return false;
            }
        });
        /**
         * 删除购物车商品
         */
        listAdapter.setOnDeleteItem(new CartListAdapter.OnDeleteItem() {
            @Override
            public void delete(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("pro",cartList.get(position));
                setPosition(position);
                DeleteDialog deleteDialog = DeleteDialog.newInstance(getResources().getString(R.string.delete_tv), CartStates.DELETE_CART);
                deleteDialog.show(getChildFragmentManager(), "delete");
            }
        });
        listAdapter.setOnNumberChangeListener(new CartListAdapter.OnNumberChange() {
            @Override
            public void numberChange() {
                clearCoupon();
                notifyPrice();
            }
        });
        /**
         * 购物车title点击
         */
        cartTitleAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // category = cartTitleAdapter.getItem(position).getCategoryId();//获得titleＩＤ
                listAdapter.setCategoryId(category);
                listAdapter.notifyDataSetChanged();
            }
        });

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
                    cart_coupon_remind.setVisibility(View.GONE);
                    carditem_couponrange.setVisibility(View.GONE);
                    carditem_coupondetail.setVisibility(View.GONE);
                    cart_discountprice_tv.setVisibility(View.GONE);
                    carditem_view.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < couponInfoList.size(); i++) {
                        couponInfoList.get(i).setSelected(false);
                    }
                    couponListAdapter.getItem(position).setSelected(true);
                    carditem_price_tv.setVisibility(View.VISIBLE);
                    cart_coupon_remind.setVisibility(View.VISIBLE);
                    carditem_couponrange.setVisibility(View.VISIBLE);
                    carditem_coupondetail.setVisibility(View.VISIBLE);
                    cart_discountprice_tv.setVisibility(View.VISIBLE);
                    carditem_view.setVisibility(View.VISIBLE);
                    String remindText = couponListAdapter.getItem(position).getRemindText();
                    if (!TextUtils.isEmpty(remindText)) {
                        cart_coupon_remind.setText(remindText);
                    }
                    String remindFlag = couponListAdapter.getItem(position).getRemindFlag();
                    if (!TextUtils.isEmpty(remindFlag) && remindFlag.equals("1")) {
                        cart_coupon_remind.setBackgroundResource(R.drawable.coupon_remind_bg);
                    } else {
                        cart_coupon_remind.setBackground(null);
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

        /**
         * 显示购物车商品详情
         */
        listAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                listAdapter.notifyDataSetChanged();
                GoodsDetailFragment.setCartCount(listAdapter.getItem(position).getCount());
                rememberCategory();
                GoodsDetailFragment.start(getChildFragmentManager(), listAdapter.getItem(position).getProductId(), GoodsDetailFragment.CARTFRAGMENT);
            }
        });

        /**
         * 收货地址管理页面
         */

        addrLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                在MainActivity中注册
                org.greenrobot.eventbus.EventBus.getDefault().post(CartFragment.class);
            }
        });

        /**
         * 下单（创建订单）
         */
        cart_order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartfragment_discountpop_rela.setVisibility(View.INVISIBLE);
//                if(cartList.size() == 0){
//                    showToast(R.string.cart_no_goods);
//                }else{
                if (totalPrice.doubleValue() == 0.0d) {
                    return;
                }
                if (card_id == null) {
                    card_id = "0";
                }
                if (card_price == null) {
                    card_price = "0";
                }


                if (checkeUser(User.getIntance())) {
                    cart_order_layout.setClickable(false);
                    ArrayList<ProductInfoAdd> cartListReal = new ArrayList<ProductInfoAdd>(); //去除库存为零和已下架的商品
                    for (int i = 0; i < mCartList.size(); i++) {
                        ProductInfoAdd productInfoAdd = mCartList.get(i);
                        String stock = productInfoAdd.getStock();
                        if (!TextUtils.isEmpty(stock) && stock.equalsIgnoreCase("0")                      //库存是0
                                || !TextUtils.isEmpty(productInfoAdd.getIf_show()) && productInfoAdd.getIf_show().equals("4") //4 代表已下架
                                || productInfoAdd.getIsSelected() == 2 //未勾选商品
                                ) {

                        } else {
                            cartListReal.add(productInfoAdd);
                        }
                    }

                    for (int i = 0; i < cartListReal.size(); i++) {  //跨境商品提示 跨境商品需要身份证号码
                        ProductInfoAdd productInfoAdd = cartListReal.get(i);
                        if (!TextUtils.isEmpty(productInfoAdd.getProductType()) && CartStates.CROSS_GOODS.equals(productInfoAdd.getProductType())) {
                            if (TextUtils.isEmpty(User.getIntance().getBuyer_IDcard())) {
                                PayWarnFragment.start(getFragmentManager());
                                return;
                            }
                        }
                    }
                    BeanTranslater.createCartOrder(cartListReal, "" + totalPrice.doubleValue(), card_id, card_price, "00");
                } else {
                    AddrManageFragment.start(R.id.main_fragment, getFragmentManager());
                }
            }
        });
    }

    //获取优惠劵使用参数
    LinkedList<String> mGoodsIdList; //商品ID
    LinkedList<String> mGoodCountList; //商品数量

    public void getCouponData() {

        if (null == mGoodsIdList) mGoodsIdList = new LinkedList<>();
        if (null == mGoodCountList) mGoodCountList = new LinkedList<>();

        mGoodsIdList.clear();
        mGoodCountList.clear();
        for (int i = 0; i < mCartList.size(); i++) {
            ProductInfoAdd productInfoAdd = mCartList.get(i);
            if (productInfoAdd.getIsSelected() == 1) {
                mGoodsIdList.add(productInfoAdd.getProductId());
                mGoodCountList.add(productInfoAdd.getCount());
            }

        }
    }

    private void initData() {
        // getCouponData();
        //  presenter.getCouponList(couponInfoList, mGoodsIdList, mGoodCountList);
    }


    //记住当前类目
    private void rememberCategory() {
        for (int i = 0; i < cartTitleList.size(); i++) {
            CartTitle.CartCategoryInfo categoryInfo = cartTitleList.get(i);
            if (categoryInfo.isSelected()) {
                category = categoryInfo.getCategoryId();
            }
        }
    }

    private void hideSoft() {
        FragmentActivity activity = getActivity();
        if (null != activity) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null) {
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                listAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /**
         * 触摸屏幕关闭软键盘
         */
        hideSoft();
        return super.onTouch(v, event);

    }


    @Subscribe
    public void payEventFinish(OrderFinishEvent finishEvent) {
        cart_order_layout.setClickable(true);
        if (!TextUtils.isEmpty(finishEvent.flag) && finishEvent.flag.equals("2")) { //1 下单成功  2 下单失败
            saveData(mCartList);
            mRefresh = false;
            presenter.getCartList(mCartList, category, String.valueOf(currentPage)); //创建订单失败 刷新购物车
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (cart_order_layout != null) {
                cart_order_layout.setClickable(true);
            }
            rememberCategory();
            hideSoft();
            if (cartfragment_discountpop_rela != null && cartfragment_discountpop_rela.getVisibility() == View.VISIBLE) {
                cartfragment_discountpop_rela.setVisibility(View.GONE);
            }
            saveData(mCartList);
        }
    }

    public void getTitle(boolean defaultSelect) {
        this.defaultSelect = defaultSelect;
        presenter.getCartTitle(cartTitleList, "1");
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isPause) {

        }

        ACache aCache = getACache();
        if (aCache != null) {
            //category = aCache.getAsString(CATEGORY_ID);
        }
        presenter.getCartList(mCartList, category, String.valueOf(currentPage));
        category = "";
        listAdapter.setCategoryId(category);
        getTitle(true);
        restoreData(mCartList);
        listAdapter.notifyDataSetChanged();
        if (checkeUser(User.getIntance())) {
            cart_addr_name_tv.setVisibility(View.VISIBLE);
            cart_addr_phone_tv.setVisibility(View.VISIBLE);
            cart_addr_addr_tv.setVisibility(View.VISIBLE);
            cart_addr_tv.setVisibility(View.GONE);
            cart_addr_name_tv.setText(User.getIntance().getName());
            String phone = User.getIntance().getMobile();
            if (phone == null || phone.equals("")) {
                phone = User.getIntance().getPhone();
                if (phone == null) {
                    phone = "";
                }
            }
            cart_addr_phone_tv.setText(phone);
            cart_addr_addr_tv.setText(User.getIntance().getAddress());
        } else {
            cart_addr_name_tv.setVisibility(View.INVISIBLE);
            //address_location_log.setVisibility(View.INVISIBLE);
            cart_addr_phone_tv.setVisibility(View.INVISIBLE);
            cart_addr_addr_tv.setVisibility(View.INVISIBLE);
            cart_addr_tv.setVisibility(View.VISIBLE);
            cart_addr_name_tv.setText("");
            cart_addr_phone_tv.setText("");
            cart_addr_addr_tv.setText("");
        }
        isPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        if (cart_order_layout != null) {
            cart_order_layout.setClickable(true);
        }
        saveData(mCartList);
    }


    //记录购物车选中状态
    private void saveData(List<ProductInfoAdd> productInfosList) {
        StringBuffer cartIds = new StringBuffer();
        for (int i = 0; i < productInfosList.size(); i++) {
            ProductInfoAdd productInfoAdd = productInfosList.get(i);
            if (productInfoAdd.getIsSelected() == 1) {
                cartIds.append("," + productInfoAdd.getProductId());
            }
        }

        ACache aCache = getACache();
        if (aCache != null) {
            aCache.clear();
            if (cartIds.length() > 1) {
                mACache.put(GOODS_ID_KEY, cartIds.toString().substring(1));//去掉第一个逗号
            }
            // aCache.put(CATEGORY_ID, category);
        }
    }


    public ACache getACache() {
        if (mACache == null) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                mACache = ACache.get(activity);
                return mACache;
            }
        } else {
            return mACache;
        }
        return null;
    }

    //恢复购物车选中状态
    private void restoreData(List<ProductInfoAdd> list) {


        if (mRefresh) {
            for (int i = 0; i < list.size(); i++) {
                ProductInfoAdd productInfoAdd = list.get(i);
                productInfoAdd.setSelected(2);
            }
        } else {
            mRefresh = true;
            ACache aCache = getACache();
            if (aCache != null) {
                String cacheIds = aCache.getAsString(GOODS_ID_KEY);
                if (!TextUtils.isEmpty(cacheIds)) {
                    String[] split = cacheIds.split(",");
                    for (int i = 0; i < list.size(); i++) {
                        ProductInfoAdd productInfoAdd = list.get(i);
                        for (int j = 0; j < split.length; j++) {
                            String s = split[j];
                            Log.e("CartPresenter555", "notifyCartList: nnn   " + s);
                            if (productInfoAdd.getProductId().equals(s)) {
                                productInfoAdd.setSelected(1);
                            }
                        }
                    }
                }
            }
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.unBind();
    }

    @Override
    public void setPresenter() {
        presenter = new CartPresenter(this);
    }

    @Override
    public void notifyCartList() {
        List<ProductInfoAdd> list = listAdapter.getList();
        if (list.size() == 0) {
            mCartListView.setVisibility(View.GONE);
            cart_nogoods_txt_linear.setVisibility(View.VISIBLE);
        } else {
            cart_nogoods_txt_linear.setVisibility(View.GONE);
            mCartListView.setVisibility(View.VISIBLE);
        }
        restoreData(list);
        refreshTitle();
        listAdapter.notifyDataSetChanged();
        clearCoupon();
        notifyPrice();
    }


    ArrayList<String> titleCategoryId;
    ArrayList<String> cartCategoryId;

    //没有该类目商品时 刷新类目头
    private void refreshTitle() {
        if (null == titleCategoryId) {
            titleCategoryId = new ArrayList<>();
        }
        if (null == cartCategoryId) {
            cartCategoryId = new ArrayList<>();
        }
        titleCategoryId.clear();
        cartCategoryId.clear();
        for (int j = 1; j < cartTitleList.size(); j++) {
            titleCategoryId.add(cartTitleList.get(j).getCategoryId());
        }

        for (int i = 0; i < mCartList.size(); i++) {
            String categoryId = mCartList.get(i).getCategoryId();
            if (!cartCategoryId.contains(categoryId)) {
                cartCategoryId.add(categoryId);
            }
        }
        if (!cartCategoryId.containsAll(titleCategoryId)) {
            getTitle(true);
            listAdapter.setCategoryId("");
        }
    }


    @Override
    public void notifyTitle() {
        cartTitleAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyTitleRefresh() {
        if (cartTitleList.size() > 0) {
            cartTitleAdapter.notifyDataSetChanged();
            if (defaultSelect) {
                cartTitleList.get(0).setSelected(true);
                cartTitleAdapter.notifyItemChanged(0);
                notifyPrice();
            }
            if (category != null) {
                for (int i = 0; i < cartTitleList.size(); i++) {  //记住当前类目 并且返回时依旧在该类目下
                    CartTitle.CartCategoryInfo categoryInfo = cartTitleList.get(i);
                    if (categoryInfo.getCategoryId().equalsIgnoreCase(category)) {
                        listAdapter.setCategoryId(category);
                        listAdapter.notifyDataSetChanged();
                        if (defaultSelect) {
                            cartTitleList.get(0).setSelected(false);
                            cartTitleAdapter.notifyItemChanged(0);
                        }
                        categoryInfo.setSelected(true);
                        cartTitleAdapter.notifyItemChanged(i);
                        notifyPrice();
                    }
                }
            }
        }
    }

    /**
     * 错误处理
     */
    @Override
    public void listErr() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CartFragment);
    }

    @Override
    public void notifyCard() {
        if (couponInfoList.size() > 0) {
            cart_discount_linear.setBackgroundResource(R.drawable.cart_discount_choose);
            couponListAdapter.notifyDataSetChanged();
        } else {
            cart_discount_linear.setBackgroundResource(R.drawable.cart_nodiscount_bg);
        }
        if (couponInfoList.size() > 3) { // 优惠劵布局最大高度是420dp
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) cartfragment_poprv_linear.getLayoutParams();
            linearParams.height = 420;
            linearParams.setMargins(UIUtils.dp2px(getContext(), 440), 0, 0, UIUtils.dp2px(getContext(), 165));
            cartfragment_poprv_linear.setLayoutParams(linearParams);
        } else {
            //恢复原来布局样式
            RelativeLayout.LayoutParams mLp = (RelativeLayout.LayoutParams) cartfragment_poprv_linear.getLayoutParams(); //优惠卷原始布局参数
            mLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mLp.setMargins(UIUtils.dp2px(getContext(), 440), 0, 0, UIUtils.dp2px(getContext(), 170));
            cartfragment_poprv_linear.setLayoutParams(mLp);
        }
    }

    @Override
    public void notifyOneCart(OneKeyCartOrder oneKeyCartOrder) {

    }

    @Override
    public void notifyPrice() {
        totalPrice = new BigDecimal("0.00");
        for (int i = 0; i < mCartList.size(); i++) {
            ProductInfoAdd productInfoAdd = mCartList.get(i);
            if (!TextUtils.isEmpty(productInfoAdd.getStock()) && !productInfoAdd.getStock().equalsIgnoreCase("0")  //库存不是0
                    && !TextUtils.isEmpty(productInfoAdd.getIf_show()) && !productInfoAdd.getIf_show().equals("4")  //没有下架
                    && productInfoAdd.getIsSelected() == 1) {  //选中
                BigDecimal bigDecimal = new BigDecimal(productInfoAdd.getPrice());
                BigDecimal count = new BigDecimal(productInfoAdd.getCount());
                BigDecimal price = bigDecimal.multiply(count);
                totalPrice = totalPrice.add(price);
            }
        }
        try {
            if (card_price != null) {
                totalPrice = totalPrice.subtract(new BigDecimal(card_price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (totalPrice.doubleValue() <= 0.0d) {
            totalPrice = new BigDecimal("0.00");
        }

        cart_totalprice_tv.setText("¥ " + Util.priceToString(totalPrice.doubleValue()) + "");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        card_price = "0";
        carditem_price_tv.setVisibility(View.GONE);
        cart_coupon_remind.setVisibility(View.GONE);
        carditem_couponrange.setVisibility(View.GONE);
        carditem_coupondetail.setVisibility(View.GONE);
        cart_discountprice_tv.setVisibility(View.GONE);
        carditem_view.setVisibility(View.GONE);
        getCouponData();
        presenter.getCouponList(couponInfoList, mGoodsIdList, mGoodCountList, "1");
    }


    @Subscribe
    public void deleteDialogEvent(DeleteDialogBean deleteDialogBean) {
        if (AddrStates.DELETE_CONFIRM.equals(deleteDialogBean.getConfirmOrCancel()) && CartStates.DELETE_CART.equals(deleteDialogBean.getDeleteFlug())) {
            ProductInfoUpdate productInfoUpdate = new ProductInfoUpdate();
            ProductInfoAdd infoAdd = listAdapter.getItem(position);
            infoAdd.setCount("0");
            productInfoUpdate.setMemberId(infoAdd.getMemberId());
            productInfoUpdate.setProductInfo(infoAdd);
            saveData(mCartList);
            mRefresh = false;
            CartClient.updateCart(productInfoUpdate, new ProductInfoAdd.UpdateNumCallback() {
                @Override
                public void update() {
                    clearCoupon();
                    presenter.getCartList(mCartList, category, String.valueOf(currentPage));
                }

                @Override
                public void rollback() {

                }
            });
        }
        if (AddrStates.DELETE_CANCEL.equals(deleteDialogBean.getConfirmOrCancel()) && CartStates.DELETE_CART.equals(deleteDialogBean.getDeleteFlug())) {

        }
    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        /**
         * 删除购物车商品 刷新购物车列表
         */
//        if (msg.getClassName().equals(DeleteDialog.class) && AddrStates.DELETE_CONFIRM.equals(msg.getMsg())) {
//            ProductInfoUpdate productInfoUpdate = new ProductInfoUpdate();
//            ProductInfoAdd infoAdd = listAdapter.getItem(position);
//            infoAdd.setCount("0");
//            productInfoUpdate.setMemberId(infoAdd.getMemberId());
//            productInfoUpdate.setProductInfo(infoAdd);
//            CartClient.updateCart(productInfoUpdate, new ProductInfoAdd.UpdateNumCallback() {
//                @Override
//                public void update() {
//                    clearCoupon();
//                    presenter.getCartList(cartList, category, String.valueOf(currentPage));
//                }
//
//                @Override
//                public void rollback() {
//
//                }
//            });
//        } else if (msg.getClassName().equals(DeleteDialog.class) && AddrStates.DELETE_CANCEL.equals(msg.getMsg())) {
////            ProductInfoUpdate productInfoUpdate = new ProductInfoUpdate();
////            ProductInfoAdd infoAdd = listAdapter.getItem(position);
////            infoAdd.setCount("1");
////            productInfoUpdate.setMemberId(infoAdd.getMemberId());
////            productInfoUpdate.setProductInfo(infoAdd);
////            CartClient.updateCart(productInfoUpdate,);
//        }


        /**
         * 选择收货地址
         */
        if (msg.getClassName().equals(AddrManageFragment.class)) {
            cart_addr_name_tv.setVisibility(View.VISIBLE);
            //address_location_log.setVisibility(View.VISIBLE);
            cart_addr_phone_tv.setVisibility(View.VISIBLE);
            cart_addr_addr_tv.setVisibility(View.VISIBLE);
            cart_addr_tv.setVisibility(View.INVISIBLE);
            cart_addr_name_tv.setText(msg.getName());
            cart_addr_phone_tv.setText(msg.getPhone());
            cart_addr_addr_tv.setText(msg.getMsg());
        } else if ((msg.getClassName().equals(CartClient.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.CartFragment))) || (msg.getClassName().equals(GoodsDetailFragment.class) && msg.getMsg().equals(GoodsDetailFragment.CARTFRAGMENT))) {
            listErr();
        }
        /**
         * 添加购物车成功
         */
        else if (msg.getClassName().equals(CartClient.class) && msg.getMsg().equals(EventMsg.CARTADDSUCCESS)) {
            mRefresh = false;
            presenter.getCartList(mCartList, category, String.valueOf(currentPage));
            getTitle(false);
        } else if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.CartFragment_final.equals(msg.getMsg())) {
            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CartFragment);
        } else if ((msg.getClassName().equals(MainActivity.class) || msg.getClassName().equals(HaierPayActivity.class)) && (PayStates.PAY_SUCCESS.equals(msg.getMsg()) || PayStates.PAY_FAIL.equals(msg.getMsg()))) {
            presenter.getCartList(mCartList, category, String.valueOf(currentPage));
            getTitle(true);
        }
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


    /*public void getCheckData() {
        if (null != mCartList && mCartList.size() > 0) {
            for (int i = 0; i < mCartList.size(); i++) {
                ProductInfoAdd productInfoAdd = mCartList.get(i);
                if (productInfoAdd.getIsSelected()) {
                    mCartOrderList.add(productInfoAdd);
                }
            }
        }
    }*/


    /*public void changeBtnText() {
        getCurrentCategoryList();
        int count = 0;
        for (int i = 0; i < mCartCurrentList.size(); i++) {
            ProductInfoAdd productInfoAdd = mCartCurrentList.get(i);
            if (productInfoAdd.getIsSelected()) {
                count++;
            }
        }
        if (count == mCartCurrentList.size()) {

        } else {

        }
    }*/

    public void getCurrentCategoryList() {
        if (null != category) {
            mCartCurrentList.clear();
            if (category.equals("")) {
                mCartCurrentList.addAll(mCartList);
                Log.e("CartFragment", "getCurrentCategoryList: " + mCartCurrentList.size());
                return;
            }
            for (int i = 0; i < mCartList.size(); i++) {
                ProductInfoAdd productInfoAdd = mCartList.get(i);
                if (productInfoAdd.getCategoryId().equals(category)) {
                    mCartCurrentList.add(productInfoAdd);
                }
            }
        }
        Log.e("CartFragment", "getCurrentCategoryList: " + mCartCurrentList.size());
    }

 /*   public void selectBtnClick() {
        if (null != category) {
            getCurrentCategoryList();
            int count = 0;
            for (int i = 0; i < mCartCurrentList.size(); i++) {
                ProductInfoAdd productInfoAdd = mCartCurrentList.get(i);
                if (productInfoAdd.getIsSelected()) {
                    count++;
                }
            }
            if (count == mCartCurrentList.size()) {
                for (int i = 0; i < mCartList.size(); i++) {
                    ProductInfoAdd productInfoAdd = mCartList.get(i);
                    if (productInfoAdd.getCategoryId().equals(category)) {
                        productInfoAdd.setSelected(false);
                    }
                }
            } else {
                for (int i = 0; i < mCartList.size(); i++) {
                    ProductInfoAdd productInfoAdd = mCartList.get(i);
                    if (productInfoAdd.getCategoryId().equals(category)) {
                        productInfoAdd.setSelected(true);
                    }
                }
            }
            listAdapter.notifyDataSetChanged();
        }

    }*/
}