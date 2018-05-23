package com.bingstar.bingmall.goods;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseActivity;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.base.WebActivity;
import com.bingstar.bingmall.goods.bean.GoodsShowInfo;
import com.bingstar.bingmall.goods.bean.OneKeyEvent;
import com.bingstar.bingmall.goods.bean.ProductDetailEntity;
import com.bingstar.bingmall.goods.http.GoodsClient;
import com.bingstar.bingmall.goods.http.GoodsStates;
import com.bingstar.bingmall.goods.view.MutableGoodsViewPager;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.statistics.StatClient;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.bean.UserStates;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.view.OnSingleClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能：
 * Created by yaoyafeng on 17/2/21 下午7:51
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/21 下午7:51
 * @modify by reason:{方法名}:{原因}
 */

public class GoodsDetailFragment extends DialogFragment {

    private ImageView goods_detail_support;
    private MutableGoodsViewPager goods_detail_img;
    private TextView goods_detail_name;
    //    private TextView goods_detail_specs;
    private TextView goods_detail_price;
    private TextView goods_detail_price_d;
    //    private TextView goods_detail_freight;
    private TextView goods_detail_describe;
    private ImageButton goods_detail_num_decrease;
    private EditText goods_detail_num;
    private ImageButton goods_detail_num_increase;
    private ImageView goods_detail_add_cart;
    private SynLinearLayout goods_detail_syn_linear;

    private static final String GOODS_ID = "goodsId";

    private static final String COMEFROME = "comeFrom";

    public static final String GOODSLISTFRAGMENT = "0";
    public static final String CARTFRAGMENT = "1";
    public static final String COLLECTFRAGMENT = "2";

    private final String SUPPORT_FLAG_TRUE = "Y";
    private final String SUPPORT_FLAG_FALSE = "N";
    private final String SUPPORT_FLAG_KEY = "flag";

    private GoodsShowInfo goodsShowInfo;

    private String comeFrom;

    private String count;
    private String goodsId;
    private TextView mPromotionInfo;   //促销信息
    private TextView mPromotionTime;

    private BaseActivity mActivity;

    private long lastTime = 0;

    final int TIME_SPACE = 3000;


    private static String modelFrom;//打开的模块来源(该模块多处引用，无法修改，添加set方法设置来源)
    private ImageView mTypeFlag, mOne_key_buy;
    private TextView mGoods_small_name;


    private static String sCartCount;
    private TextView mDiscountExplain_img;
    private TextView mDiscountExplain_tv;

    public static void setCartCount(String cartCount) {
        sCartCount = cartCount;
    }

    public synchronized static void start(FragmentManager fragmentManager, String goodsId, String comeFrom) {
        final String TAG = "GoodsDetailFragment";
        GoodsDetailFragment fragment = (GoodsDetailFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new GoodsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(GOODS_ID, goodsId);
            bundle.putString(COMEFROME, comeFrom);
            fragment.setArguments(bundle);
        }
        if (!fragment.isAdded()) {
            fragment.show(fragmentManager, TAG);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = 1048;
        int dialogHeight = 590;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.goods_fragment_detail, container, false);
        goods_detail_support = (ImageView) view.findViewById(R.id.goods_detail_support);
        goods_detail_img = (MutableGoodsViewPager) view.findViewById(R.id.goods_detail_img);
        mTypeFlag = (ImageView) view.findViewById(R.id.goods_detail_flag);
        mGoods_small_name = (TextView) view.findViewById(R.id.goods_detail_smallName);
        goods_detail_name = (TextView) view.findViewById(R.id.goods_detail_name);
//        mNumlinear = (LinearLayout) view.findViewById(R.id.goods_detail_numlinear);
//        mDetailOp = (LinearLayout) view.findViewById(R.id.goods_detail_op);
//        goods_detail_specs = (TextView) view.findViewById(R.id.goods_detail_specs);
        goods_detail_price = (TextView) view.findViewById(R.id.goods_detail_price);
        goods_detail_price_d = (TextView) view.findViewById(R.id.goods_detail_price_d);
        goods_detail_num_decrease = (ImageButton) view.findViewById(R.id.goods_detail_num_decrease);
        goods_detail_num = (EditText) view.findViewById(R.id.goods_detail_num);
        goods_detail_num_increase = (ImageButton) view.findViewById(R.id.goods_detail_num_increase);
        goods_detail_add_cart = (ImageView) view.findViewById(R.id.goods_detail_add_cart);
        mOne_key_buy = (ImageView) view.findViewById(R.id.goods_detail_just_buy);
//        goods_detail_freight = (TextView) view.findViewById(R.id.goods_detail_freight);
        goods_detail_describe = (TextView) view.findViewById(R.id.goods_detail_describe);
        mPromotionInfo = (TextView) view.findViewById(R.id.goods_detail_promotion);
        goods_detail_syn_linear = (SynLinearLayout) view.findViewById(R.id.goods_detail_syn_linear);
        //goods_detail_price_d.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mPromotionTime = (TextView) view.findViewById(R.id.goods_detail_promotiontime);
        mDiscountExplain_img = (TextView) view.findViewById(R.id.goods_detail_discountExplain_img);
        mDiscountExplain_tv = (TextView) view.findViewById(R.id.goods_detail_discountExplain_tv);
        goodsShowInfo = new GoodsShowInfo();
        goodsId = getArguments().getString(GOODS_ID);
        comeFrom = getArguments().getString(COMEFROME);
        goods_detail_num_increase.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                try {
                    int num = Integer.parseInt(goodsShowInfo.getChoosNum());
                    String stockStr = goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getStock();
                    Integer stock = Integer.parseInt(stockStr);
                    if (!TextUtils.isEmpty(stockStr) && sCartCount != null) {  //如果是从购物车加进来的 还要计算购物车已有的库存
                        Integer orCount = Integer.parseInt(sCartCount);
                        if (orCount + num >= Integer.parseInt(stockStr)) {
                            Integer cartNum = Integer.parseInt(stockStr) - Integer.parseInt(sCartCount);
                            if (cartNum < 0) {
                                cartNum = 0;
                            }
                            mActivity.showToast("库存不足,剩余库存" + stock + "最多添加" + (cartNum + "件商品."));
                            goodsShowInfo.setChoosNum(cartNum);
                            goods_detail_num.setText(goodsShowInfo.getChoosNum());
                            Selection.setSelection(goods_detail_num.getText(), goods_detail_num.getText().length());
                            return;
                        }
                    }
                    if (num < stock) {
                        goodsShowInfo.setChoosNum(++num);
                    } else {
                        mActivity.showToast(getString(R.string.goods_detail_num_warnH1) + stock);
                        goodsShowInfo.setChoosNum(stock);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    goodsShowInfo.setChoosNum(1);
                }
                goods_detail_num.setText(goodsShowInfo.getChoosNum());
                Selection.setSelection(goods_detail_num.getText(), goods_detail_num.getText().length());
            }
        });
        goods_detail_num_decrease.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                goodsShowInfo.decreaseNum();
                goods_detail_num.setText(goodsShowInfo.getChoosNum());
                Selection.setSelection(goods_detail_num.getText(), goods_detail_num.getText().length());
            }
        });

        goods_detail_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        if (validateNum()) {
                            return true;
                        }
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    goods_detail_num.clearFocus();
                    return true;
                }
                return false;
            }
        });
        goods_detail_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    try {
                        if (goodsShowInfo.getChoosNum().equals(s.toString())) {
                            return;
                        }
                        int num = Integer.parseInt(s.toString());
                        String stock = goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getStock();

                        if (!TextUtils.isEmpty(stock) && sCartCount != null) {  //如果是从购物车加进来的 还要计算购物车已有的库存
                            Integer orCount = Integer.parseInt(sCartCount);
                            if (orCount + num >= Integer.parseInt(stock)) {
                                Integer cartNum = Integer.parseInt(stock) - Integer.parseInt(sCartCount);
                                if (cartNum < 0) {
                                    cartNum = 0;
                                }
                                mActivity.showToast("库存不足,剩余库存" + stock + "最多添加" + (cartNum + "件商品."));
                                goodsShowInfo.setChoosNum(cartNum);
                                goods_detail_num.setText(goodsShowInfo.getChoosNum());
                                Selection.setSelection(goods_detail_num.getText(), goods_detail_num.getText().length());
                                return;
                            }
                        }

                        if (!TextUtils.isEmpty(stock) && num > Integer.parseInt(stock)) {
                            num = Integer.parseInt(stock);
                            mActivity.showToast(getString(R.string.goods_detail_num_warnH1) + num);
                        }


                        goodsShowInfo.setChoosNum(num);
                    } catch (Exception e) {
                        e.printStackTrace();
                        goodsShowInfo.setChoosNum(1);
                        mActivity.showToast(R.string.goods_detail_num_warnE);
                    }
                    goods_detail_num.setText(goodsShowInfo.getChoosNum());
                    Selection.setSelection(goods_detail_num.getText(), goods_detail_num.getText().length());
                }
            }
        });


        goods_detail_add_cart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                String memberId = User.getIntance().getMemberId();
                User.checkMemberId(getActivity(), new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        if (validateNum()) {
                            return;
                        }
                        String goodsNum = goods_detail_num.getText().toString();
                        String stock = goodsShowInfo.getProductDetailEntity().getProductInfoDetail().getStock();
                        int num = Integer.parseInt(goodsNum);
                        if (!TextUtils.isEmpty(stock) && sCartCount != null) {  //如果是从购物车加进来的 还要计算购物车已有的库存
                            Integer orCount = Integer.parseInt(sCartCount);
                            if (orCount + num > Integer.parseInt(stock)) {
                                Integer cartNum = Integer.parseInt(stock) - Integer.parseInt(sCartCount);
                                if (cartNum < 0) {
                                    cartNum = 0;
                                }
                                mActivity.showToast("库存不足,剩余库存" + stock + "最多添加" + (cartNum + "件商品."));
                                return;
                            }
                        }
                        goodsShowInfo.addToCart(getContext(), goodsShowInfo);
                        EventBus.getDefault().post(new EventMsg(GoodsDetailFragment.class, "refresh", comeFrom));
                    }
                });
                if (memberId == null || memberId.equals("")) {
                    dismiss();
                }
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime > TIME_SPACE) {
                    //百度添加购物车统计
                    ProductDetailEntity.ProductInfoDetail productInfoDetail = goodsShowInfo.getProductDetailEntity().getProductInfoDetail();
                    StatClient.upLoadClickCount(productInfoDetail.getBsjProductId(), productInfoDetail.getBsjProductName());
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put("modelFrom", modelFrom);
                    Util.BaiduEvent(getContext(), "modelFrom", modelFrom);
                }
            }
        });


        mOne_key_buy.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                String memberId = User.getIntance().getMemberId();
                final ProductDetailEntity.ProductInfoDetail productInfoDetail = goodsShowInfo.getProductDetailEntity().getProductInfoDetail();
                User.checkMemberId(getActivity(), new User.CheckMemberCallback() {
                            @Override
                            public void hasLogin() {
                                if (validateNum()) {
                                    return;
                                }
//                                if (getDialog() != null && getDialog().isShowing()) {
//                                    getDialog().dismiss();
//                                }
                                String bsjProductId = productInfoDetail.getBsjProductId();
                                String trim = goods_detail_num.getText().toString().trim();
                                FragmentActivity activity = getActivity();
                                if (activity != null) {
                                    String simpleName = activity.getClass().getSimpleName();
                                    if (!TextUtils.isEmpty(simpleName) && simpleName.equals(WebActivity.FLAG)) {
                                        getActivity().finish();  //如果是h5页面finish调
                                    }
                                }
                                EventBus.getDefault().postSticky(new OneKeyEvent(bsjProductId, trim, GoodsDetailFragment.this));
                            }
                        }
                );
                StatClient.upLoadClickCount(productInfoDetail.getBsjProductId(), productInfoDetail.getBsjProductName());
                if (memberId == null || memberId.equals("")) {
                    dismiss();
                }
            }
        });

        goods_detail_support.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(final View view) {
                if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        activity.showToast(R.string.user_no_login);
                    }
                    return;
                }
                User.checkMemberId(getContext(), new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        view.setClickable(false);
                        view.setSelected(true);
                        goodsShowInfo.supportAdd();
                    }
                });

            }
        });
        //goods_detail_support.setClickable(false);
        goods_detail_syn_linear.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                getData();
            }
        });
        getData();
        getSupport();
        clickCount();
        return view;
    }

    //校验输入数量是否合法
    private boolean validateNum() {
        try {
            String goodsNum = goods_detail_num.getText().toString();
            int num = Integer.parseInt(goodsNum);


            if (num < 1) {
                mActivity.showToast(R.string.goods_detail_num_warnL);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            goodsShowInfo.setChoosNum(1);
            mActivity.showToast(R.string.goods_detail_num_warnE);
            return true;
        }
        return false;
    }


    private void getData() {
        if (goodsId != null) {
            goods_detail_syn_linear.showLoad();
            GoodsClient.getGoodsDetail(goodsId, new ClientCallback<ProductDetailEntity>() {
                @Override
                public void onSuccess(ProductDetailEntity productDetailEntity) {
                    goods_detail_syn_linear.showSuccess();
                    goodsShowInfo.setProductDetailEntity(productDetailEntity);
                    GoodsImgAdapter goodsImgAdapter = new GoodsImgAdapter(productDetailEntity.getProductInfoDetail().getProductPicList(), getContext());
                    goods_detail_img.setAdapter(goodsImgAdapter);
                    goods_detail_img.setDot(goodsImgAdapter.getCount());
                    String activity_type = productDetailEntity.getProductInfoDetail().getActivity_type();
                    if (!TextUtils.isEmpty(activity_type)) {
                        switch (activity_type) {
                            case "1":  //每日特惠
                                mTypeFlag.setImageResource(R.drawable.goods_daily_deal);
                                break;
                            case "2":
                                mTypeFlag.setImageResource(R.drawable.goods_limite_discount);
                                break;
                            case "3":
                                mTypeFlag.setImageResource(R.drawable.goods_limited_timeprice);
                                break;
                            default:
                                mTypeFlag.setVisibility(View.INVISIBLE);
                                break;
                        }
                    } else {
                        mTypeFlag.setVisibility(View.INVISIBLE);
                    }

                    String discountExplain = productDetailEntity.getProductInfoDetail().getDiscountExplain();
                    if (!TextUtils.isEmpty(discountExplain)) {
                        if (TextUtils.isEmpty(activity_type)) {
                            mTypeFlag.setVisibility(View.GONE);
                        } else {
                            mTypeFlag.setVisibility(View.VISIBLE);
                        }
                        mDiscountExplain_img.setVisibility(View.VISIBLE);
                        mDiscountExplain_tv.setText(discountExplain);
                    } else {
                        mDiscountExplain_img.setVisibility(View.INVISIBLE);
                        mDiscountExplain_tv.setText("");
                    }
                    mGoods_small_name.setText(productDetailEntity.getProductInfoDetail().getGoodsTitle());
                    goods_detail_name.setText((productDetailEntity.getProductInfoDetail().getBsjProductName()
//                            +productDetailEntity.getProductInfoDetail().getWeight() + productDetailEntity.getProductInfoDetail().getUnit()
                    ).trim());
                    if (productDetailEntity.getProductInfoDetail().getLimitPrice() == null || productDetailEntity.getProductInfoDetail().getLimitPrice().equals("")) {
                        /**
                         * liumengqiang 修改 “¥”统一到string文件中
                         */
                        goods_detail_price.setText(getResources().getString(R.string.goods_detail_money) + productDetailEntity.getProductInfoDetail().getPrice() + "");
                        goods_detail_price_d.setVisibility(View.GONE);
                    } else {
                        goods_detail_price.setText(getResources().getString(R.string.goods_detail_money) + productDetailEntity.getProductInfoDetail().getLimitPrice() + "");
                        goods_detail_price_d.setText(getResources().getString(R.string.goods_detail_money) + productDetailEntity.getProductInfoDetail().getPrice());
                        goods_detail_price_d.setVisibility(View.VISIBLE);
                        goods_detail_price_d.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    }

                    if (!TextUtils.isEmpty(productDetailEntity.getProductInfoDetail().getActivity())) {
                        mPromotionInfo.setVisibility(View.VISIBLE);
                        mPromotionInfo.setText(productDetailEntity.getProductInfoDetail().getActivity());
                    } else {
                        mPromotionInfo.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(productDetailEntity.getProductInfoDetail().getEndTime())) {
                        mPromotionTime.setVisibility(View.VISIBLE);
                        mPromotionTime.setText("活动截止时间: " + productDetailEntity.getProductInfoDetail().getEndTime());
                    } else {
                        mPromotionTime.setVisibility(View.INVISIBLE);
                    }
                    goodsShowInfo.setChoosNum(1);
                    //goods_detail_num.setText(goodsShowInfo.getChoosNum());
                    String weight = productDetailEntity.getProductInfoDetail().getWeight();
                    String unit = productDetailEntity.getProductInfoDetail().getUnit();

                    if (!TextUtils.isEmpty(weight) && !TextUtils.isEmpty(unit)) {
                        goods_detail_describe.setText("规格: " + weight + unit);
                    } else {
                        goods_detail_describe.setText(R.string.goods_fragment_detail_detail);
                    }
                }

                @Override
                public void onFail(int code, String error) {
                    if (code > 1000) {
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                    goods_detail_syn_linear.showError();
                }
            });
        }

    }

    private void getSupport() {
        if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
            return;
        }
        Map<String, String> map = new ArrayMap<>();
        map.put(UserStates.MEMBER_ID, User.getIntance().getMemberId());
        map.put(GoodsStates.PRODUCT_ID_DOWN, goodsId);
        //是否点赞
        GoodsClient.supportInfoQuery(map, new ClientCallback<String>() {
            @Override
            public void onSuccess(String s) {
                JSONObject jsonObject = JSON.parseObject(s);
                if (jsonObject == null) {
                    return;
                }
                String flag = jsonObject.getString(SUPPORT_FLAG_KEY);
                if (SUPPORT_FLAG_TRUE.equals(flag)) {
                    goods_detail_support.setClickable(false);
                    goods_detail_support.setSelected(true);
                } else if (SUPPORT_FLAG_FALSE.equals(flag)) {
                    goods_detail_support.setClickable(true);
                    goods_detail_support.setSelected(false);
                }
            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    /**
     * 商品点击统计
     */
    private void clickCount() {
        if (goodsId == null) {
            return;
        }
        //友盟统计查看详情数量
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("modelFrom", modelFrom);
        Util.BaiduEvent(getContext(), "showDetailModel", modelFrom);
    }

    public static void setModelFrom(String modelFrom) {
        GoodsDetailFragment.modelFrom = modelFrom;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        destroyVideo();
        sCartCount = null;
    }


    public void destroyVideo() {
        if (goods_detail_img != null) {
            goods_detail_img.onDestory();
        }
    }

}
