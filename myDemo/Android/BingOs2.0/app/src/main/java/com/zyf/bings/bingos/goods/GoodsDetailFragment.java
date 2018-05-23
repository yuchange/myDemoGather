package com.zyf.bings.bingos.goods;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.AddressFragment;
import com.zyf.bings.bingos.business.UserBusiness;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.goods.bean.GoodsDetailBean;
import com.zyf.bings.bingos.login.view.SweepCodeFragment;
import com.zyf.bings.bingos.ui.TableView;
import com.zyf.bings.bingos.ui.banner.Banner;
import com.zyf.bings.bingos.ui.banner.BannerConfig;
import com.zyf.bings.bingos.ui.banner.listener.OnBannerListener;
import com.zyf.bings.bingos.ui.banner.loader.GlideImageLoader;
import com.zyf.bings.bingos.ui.banner.transformer.DefaultTransformer;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.ImageLoader;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangyifei on 17/9/7.
 */

public class GoodsDetailFragment extends Fragment implements IGoodsDetailContract.GoodsDetailView {

    @BindView(R.id.banner_goods_detail)
    Banner mBanner;
    Unbinder unbinder;
    @BindView(R.id.tv_goodsdetail_title)
    TextView mTvTitle;
    @BindView(R.id.tv_goodsdetail_subtitle)
    TextView mTvlSubtitle;
    @BindView(R.id.flex_goods_detail_spec)
    FlexboxLayout mFlexDetailSpec;
    @BindView(R.id.iv_goodsdetail_decre)
    ImageButton mIvDecre;
    @BindView(R.id.tv_goods_detail_count)
    TextView mTvCount;
    @BindView(R.id.iv_goodsdetail_incre)
    ImageButton mIvIncre;
    @BindView(R.id.tv_goods_detail_stock)
    TextView mTvStock;
    @BindView(R.id.tv_goods_detail_price)
    TextView mTvPrice;
    @BindView(R.id.tv_goods_detail_discountprice)
    TextView mTvDiscountprice;
    @BindView(R.id.btn_goods_detail_buy)
    Button mBtnBuy;
    @BindView(R.id.iv_goods_bottom1_divider)
    ImageView mIvBottomDivider1;
    @BindView(R.id.tv_goods_bottom_spec)
    TextView mTvBottomSpec;
    @BindView(R.id.linear_goods_bottom_spec)
    LinearLayout mLinearBottomSpec;
    @BindView(R.id.iv_goods_bottom2_divider)
    ImageView mIvBottomDivider2;
    @BindView(R.id.tv_goods_bottom_pic)
    TextView mTvBottomPic;
    @BindView(R.id.linear_goods_bottom_img)
    LinearLayout mLinearBottomImg;
    @BindView(R.id.flex_goods_detail_bottom)
    FlexboxLayout mFlexboxLayoutBottom;
    @BindView(R.id.linear_goods_detail_picshow)
    LinearLayout mLinearPicshow;
    @BindView(R.id.linear_goods_detail_specshow)
    LinearLayout mLinearSpecshow;
    @BindView(R.id.flex_goods_detail_special)
    FlexboxLayout mFlexSpecial;
    @BindView(R.id.tableview_custom)
    TableView mCustomTableview;
    @BindView(R.id.tv_goods_detail_spec_nodate)
    TextView mTvSpecNodate;

    @BindView(R.id.tv_goods_detail_picdetail_nodate)
    TextView mPicDetailNoData;

    private ImageView mShare;
    private ImageView mLove;
    private ImageView mTest;
    private ImageView mAddCart;


    IGoodsDetailContract.GoodsDetailPresenter mGoodsDetailPresenter;
    private ProgressFrameLayout mProgressFrameLayout;
    private GoodsDetailBean mGoodsDetailBean;
    public static final String GOODS_ID = "goods_id";


    public String mGoodsId;
    private Button mActivityButton;  //每日特惠图标


    private String currentPrice;   //当前选择规格的价格
    private String currentStock;  //当前选择规格的库存
    private String currentSpec;  //当前选择规格的名字
    private GoodsDetailBean.NormsBean normsBean;
    private String mSpec;
    private int count;
    private Button mSelect;
    private String mSpecId;


    public static void start(int resId, FragmentManager manager, String goodsId) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "GoodsDetailFragment";
        GoodsDetailFragment fragment = (GoodsDetailFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof GoodsDetailFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        if (fragment == null) {
            fragment = new GoodsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(GOODS_ID, goodsId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
            transaction.addToBackStack(null);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                postTitle();
                return;
            }
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
        postTitle();
    }


    //修改主页面标题
    public static void postTitle() {
        EventBus.getDefault().postSticky(new MainTitleEvent("商品详情"));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.goods_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, mProgressFrameLayout);
        mGoodsId = getArguments().getString(GOODS_ID);
        setPresenter();
        if (mGoodsDetailPresenter != null) mGoodsDetailPresenter.subscribe();
        initView();
        initData();
        initStock();
        return mProgressFrameLayout;
    }

    private void initStock() {
        count = Integer.parseInt(mTvCount.getText().toString());
        mIvIncre.setOnClickListener(v -> {
            String stock = mTvStock.getText().toString();
            if (!TextUtils.isEmpty(stock)) {
                int stockNum = Integer.parseInt(stock);
                if (count < stockNum) {
                    count++;
                    mGoodsDetailBean.getProductInfoDetail().setCount(count);
                    mTvCount.setText(count + "");
                }
            }

        });

        mIvDecre.setOnClickListener(v -> {
            if (count > 1) {
                count--;
                mGoodsDetailBean.getProductInfoDetail().setCount(count);
                mTvCount.setText(count + "");
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

        } else {
            postTitle();
            initData();
        }
    }

    private void initView() {
        mShare = (ImageView) mBanner.getContainerView().findViewById(R.id.iv_goodsdetail_banner_share);
        mLove = (ImageView) mBanner.getContainerView().findViewById(R.id.iv_goodsdetail_banner_love);
        mAddCart = (ImageView) mBanner.getContainerView().findViewById(R.id.iv_goodsdetail_banner_addcart);
        mTest = (ImageView) mBanner.getContainerView().findViewById(R.id.iv_goodsdetail_banner_test);
        mActivityButton = (Button) mBanner.getContainerView().findViewById(R.id.btn_banner_activity);
        setBottom(1);
    }

    //获取规格布局
    public Button getButton(int resource, int textColor, String text) {
        //规格
        Button button = new Button(getActivity());
        button.setText(text);
        button.setTextColor(textColor);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        button.setAllCaps(false); //防止自动转换为大写
        button.setTypeface(Typeface.MONOSPACE);
        button.setBackgroundDrawable(getResources().getDrawable(resource));
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dp2px(getContext(), 40));
        params.setMargins(0, 0, DensityUtil.dp2px(getActivity(), 44), 0);
        button.setLayoutParams(params);
        button.setPadding(20, 0, 20, 0);
        button.setOnClickListener(v ->
                changeSelectSpec(v)
        );

        return button;
    }


    public void changeSelectSpec(View v) {
        for (int i = 0; i < mFlexDetailSpec.getChildCount(); i++) {
            View childAt = mFlexDetailSpec.getChildAt(i);
            Button btn = (Button) childAt;
            if (v.equals(childAt)) {
                mSpec = btn.getText().toString();
                btn.setTextColor(getResources().getColor(R.color.tv_goods_detail_spec));
//                btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.goods_detail_spec_select));
//                btn.setBackgroundResource(R.mipmap.goods_detail_spec_select);
                btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_rectangle_goods_detail_select));
                Object tag = btn.getTag();
                if (tag instanceof GoodsDetailBean.NormsBean) {
                    GoodsDetailBean.NormsBean bean = (GoodsDetailBean.NormsBean) tag;
                    currentPrice = bean.getPrice();
                    currentStock = bean.getStock();
                    currentSpec = bean.getSpec();
                    mSpecId = bean.getSpecificationId();
                    changeSpec();
                }
            } else {
                btn.setTextColor(getResources().getColor(R.color.tv_goods_detail_black));
//                btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.goods_detail_spec_unselect));
//                btn.setBackgroundResource(R.mipmap.goods_detail_spec_unselect);
                btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_rectangle_goods_detail_unselect));
            }
        }
    }


    private void changeSpec() {
        mTvPrice.setText(getResources().getString(R.string.money) + currentPrice);
        mTvStock.setText(currentStock);
        mTvCount.setText("1");
        count = 1;
        mGoodsDetailBean.getProductInfoDetail().setCount(1);
        mGoodsDetailBean.getProductInfoDetail().setPrice(currentPrice);
        mGoodsDetailBean.getProductInfoDetail().setStock(currentStock);
    }


    //获取配送布局
    public LinearLayout getLinearSpecial(int resource, String text) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, DensityUtil.dp2px(getActivity(), 2), 0, 0);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //文字
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTextColor(getResources().getColor(R.color.tv_goods_detail_black));
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.setMargins(DensityUtil.dp2px(getActivity(), 10), 0, DensityUtil.dp2px(getActivity(), 10), 0);
        tvParams.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(tvParams);

        //圆形图标
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(resource);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivParams.height = DensityUtil.dp2px(getActivity(), 10);
        ivParams.height = DensityUtil.dp2px(getActivity(), 10);
        ivParams.gravity = Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(ivParams);


        linearLayout.setLayoutParams(layoutParams);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        return linearLayout;
    }

    //设置底部点击颜色
    public void setBottom(int flag) {
        if (flag == 1) {
            mIvBottomDivider2.setVisibility(View.INVISIBLE);
            mIvBottomDivider1.setVisibility(View.VISIBLE);
            mTvBottomSpec.setTextColor(getResources().getColor(R.color.tv_goods_detail_spec));
            mTvBottomPic.setTextColor(getResources().getColor(R.color.tv_goods_detail_gray));
        } else {
            mIvBottomDivider2.setVisibility(View.VISIBLE);
            mIvBottomDivider1.setVisibility(View.INVISIBLE);
            mTvBottomSpec.setTextColor(getResources().getColor(R.color.tv_goods_detail_gray));
            mTvBottomPic.setTextColor(getResources().getColor(R.color.tv_goods_detail_spec));
        }
    }

    @OnClick({R.id.linear_goods_bottom_spec, R.id.linear_goods_bottom_img
            , R.id.btn_goods_detail_buy})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.linear_goods_bottom_spec:
                setBottom(1);
                mLinearSpecshow.setVisibility(View.VISIBLE);
                mLinearPicshow.setVisibility(View.GONE);
                break;
            case R.id.linear_goods_bottom_img:
                setBottom(2);
                mLinearSpecshow.setVisibility(View.GONE);
                mLinearPicshow.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_goods_detail_buy:
                if (mTvStock.getText().equals("0")) {
                    ToastUtils.showToast("当前规格商品库存为0");
                } else {
                    mGoodsDetailBean.getProductInfoDetail().setCount(Integer.parseInt(mTvCount.getText().toString()));
                    if (TextUtils.isEmpty(SPUtil.getString(getContext(), Config.MEMBER_ID))) {
                        SweepCodeFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), Config.GOODS_DETAIL);
                        ToastUtils.showToast("您尚未登录,请先登录");
                    } else {
                        if (!UserBusiness.getUserAddress().equals("")) {
                            OrderConfirmFragment.start(getFragmentManager(),
                                    R.id.fl_container, mGoodsDetailBean.getProductInfoDetail(), mSpec, null, "", mSpecId, "03");
                        } else {
                            ToastUtils.showToast("请填写收货地址");
                            AddressFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                        }
                    }
                }

                break;
            default:
        }

    }


    public static final String JSON = "{\n" +
            "        \"norms\": [\n" +
            "            {\n" +
            "                \"spec\": \"500克\",\n" +
            "                \"price\": \"190\",\n" +
            "                \"stock\": \"10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"spec\": \"500克\",\n" +
            "                \"price\": \"150\",\n" +
            "                \"stock\": \"10\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"picAattr\": {\n" +
            "            \"picdetail\": \"/uploadImage/2017092109/1505958654576.png\",\n" +
            "            \"attributeStr\": \"7天无理由退换,包邮\"\n" +
            "        },\n" +
            "        \"productInfoDetail\": {\n" +
            "            \"goodsJsonStr\": \"\",\n" +
            "            \"activity\": null,\n" +
            "            \"freight\": \"01\",\n" +
            "            \"bsjProductCode\": \"5\",\n" +
            "            \"sellerName\": null,\n" +
            "            \"categoryName\": \"婴儿用品\",\n" +
            "            \"isDeleted\": \"0\",\n" +
            "            \"price\": \"6.00\",\n" +
            "            \"activity_type\": null,\n" +
            "            \"bsjProductId\": \"208\",\n" +
            "            \"stock\": \"56546\",\n" +
            "            \"productPicList\": [\n" +
            "                {\n" +
            "                    \"picUrl\": \"/uploadImage/2017092109/1505958652105.jpg\",\n" +
            "                    \"isDefault\": null,\n" +
            "                    \"picType\": null,\n" +
            "                    \"bsjProductId\": \"208\",\n" +
            "                    \"picId\": \"684\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"productType\": \"1\",\n" +
            "            \"bsjProductname\": \"6456\",\n" +
            "            \"discountExplain\": \"\",\n" +
            "            \"brandName\": \"伊利\",\n" +
            "            \"productVideoList\": [],\n" +
            "            \"limitPrice\": null,\n" +
            "            \"weight\": \"6\",\n" +
            "            \"tax\": \"0.00\",\n" +
            "            \"goodsCat\": null,\n" +
            "            \"agencyName\": \"杭州经销商\",\n" +
            "            \"unit\": \"6\",\n" +
            "            \"grossWeight\": null,\n" +
            "            \"goodsTitle\": \"6\",\n" +
            "            \"endTime\": null,\n" +
            "            \"countryName\": \"中国\",\n" +
            "            \"categoryId\": \"38\"\n" +
            "        },\n" +
            "        \"spec\": {\n" +
            "            \"64\": \"634\"\n" +
            "        }\n" +
            "    }";

    //初始化数据
    private void initData() {
        mProgressFrameLayout.showLoading();
        mGoodsDetailPresenter.getGoodsDetailList(mGoodsId);
      /*  GoodsDetailBean goodsDetailBean = GsonFactory.fromJson(JSON, GoodsDetailBean.class);
        notifyGoodsDetailList(goodsDetailBean);*/
    }


    @Override
    public void notifyGoodsDetailList(GoodsDetailBean goodsDetailBean) {
        Log.i("GodsDeta", "初始化执行了");
        mSpec = goodsDetailBean.getNorms().get(0).getSpec();
        mProgressFrameLayout.showContent();
        Log.i("GodsDeta", "notifyGoodsDetailList执行了");
        goodsDetailBean.getProductInfoDetail().setCount(1);
        this.mGoodsDetailBean = goodsDetailBean;
        GoodsDetailBean.ProductInfoDetailBean productInfoDetail = goodsDetailBean.getProductInfoDetail();
        //商品参数
        if (productInfoDetail != null) {
            initSpec();
        }
        List<GoodsDetailBean.ProductInfoDetailBean.ProductPicListBean> productPicList = productInfoDetail.getProductPicList();
        //轮播图
        if (productPicList != null) {
            initBanner(productPicList);
        }
        //商品规格
        List<GoodsDetailBean.NormsBean> norms = goodsDetailBean.getNorms();
        initSpecDetail(norms);

        GoodsDetailBean.PicAattrBean picAattr = goodsDetailBean.getPicAattr();
        //底部商品图文详情和商品规格
        if (picAattr != null) {
            initBottomPic(picAattr.getPicdetail());
            if (!TextUtils.isEmpty(picAattr.getAttributeStr())) {
                initMail(picAattr.getAttributeStr());
            }
        } else {
            mFlexboxLayoutBottom.setVisibility(View.GONE);
            mPicDetailNoData.setVisibility(View.VISIBLE);
        }
        initBottom(mGoodsDetailBean.getSpec());
    }


    //初始化右侧商品参数
    private void initSpec() {
        GoodsDetailBean.ProductInfoDetailBean productInfoDetail = this.mGoodsDetailBean.getProductInfoDetail();
        mTvTitle.setText(productInfoDetail.getGoodsTitle());
        mTvlSubtitle.setText(productInfoDetail.getBsjProductname());

        if (!TextUtils.isEmpty(productInfoDetail.getLimitPrice())) {
            mTvDiscountprice.setVisibility(View.VISIBLE);
            mTvPrice.setText(getResources().getString(R.string.money) + productInfoDetail.getLimitPrice());
            mTvDiscountprice.setText(getResources().getString(R.string.money) + productInfoDetail.getPrice());
            mTvDiscountprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            mTvDiscountprice.setVisibility(View.GONE);
            mTvPrice.setText(getResources().getString(R.string.money) + productInfoDetail.getPrice());
        }
        if (!TextUtils.isEmpty(productInfoDetail.getStock())) {
            mTvStock.setVisibility(View.VISIBLE);
            mTvStock.setText(productInfoDetail.getStock());
        } else {
            mTvStock.setVisibility(View.GONE);
        }
        //每日特惠判断
        if (!TextUtils.isEmpty(productInfoDetail.getActivity_type()) && productInfoDetail.getActivity_type().equals("1")) {
            mActivityButton.setVisibility(View.VISIBLE);
        } else {
            mActivityButton.setVisibility(View.GONE);
        }

        //当前参数初始化
        String unit = productInfoDetail.getUnit();
        String weight = productInfoDetail.getWeight();
        currentSpec = weight + unit;


    }

    //初始化轮播图
    private void initBanner(List<GoodsDetailBean.ProductInfoDetailBean.ProductPicListBean> productPicList) {
        ArrayList<String> imgs = new ArrayList<>();
        for (int i = 0; i < productPicList.size(); i++) {
            imgs.add(productPicList.get(i).getPicUrl());
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(imgs);
        mBanner.setBannerAnimation(new DefaultTransformer());
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        mShare.setOnClickListener(v ->
                ToastUtils.showToast("share")
        );
        mLove.setOnClickListener(v ->
                ToastUtils.showToast("love")
        );
        mAddCart.setOnClickListener(v -> {
                    Log.d("GoodsDetailFragment", mSpecId);
                    if (TextUtils.isEmpty(SPUtil.getString(getContext(), Config.MEMBER_ID))) {
                        SweepCodeFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), Config.GOODS_DETAIL);
                        ToastUtils.showToast("您尚未登录,请先登录");
                    } else {

                        if (mTvStock.getText().equals("0")) {
                            ToastUtils.showToast("当前规格商品库存为0");
                        } else {
                            mGoodsDetailPresenter.addCart(mGoodsDetailBean, mSpecId);
                        }
                    }
                }
        );
        mTest.setOnClickListener(v ->
                ToastUtils.showToast("Test")
        );
    }

    private void initSpecDetail(List<GoodsDetailBean.NormsBean> norms) {

        if (mFlexDetailSpec != null) {
            mFlexDetailSpec.removeAllViews();
        }

        //应该取norms的 后台有坑 判断下
        if (norms != null && norms.size() > 0) {
            mGoodsDetailBean.getProductInfoDetail().setPrice(norms.get(0).getPrice());
            for (int i = 0; i < norms.size(); i++) {
                normsBean = norms.get(i);
                if (i == 0) {
                    mSelect = getButton(R.drawable.shape_rectangle_goods_detail_select, getResources().getColor(R.color.tv_goods_detail_spec),
                            normsBean.getSpec());
                    mSelect.setTag(normsBean);
                    mFlexDetailSpec.addView(mSelect);
                    mTvPrice.setText(getResources().getString(R.string.money) + normsBean.getPrice());
                    mTvStock.setText(normsBean.getStock());
                    mSpecId = normsBean.getSpecificationId();
                    continue;
                }
                Button normal = getButton(R.drawable.shape_rectangle_goods_detail_unselect, getResources().getColor(R.color.tv_goods_detail_black),
                        normsBean.getSpec());
                normal.setTag(normsBean);
                mFlexDetailSpec.addView(normal);

            }
        } else {
            mSelect = getButton(R.drawable.shape_rectangle_goods_detail_select, getResources().getColor(R.color.tv_goods_detail_spec),
                    currentSpec);
            mFlexDetailSpec.addView(mSelect);
        }
    }


    //初始化底部规格参数
    private void initBottom(JsonObject spec) {
        mBanner.setFocusable(false);
        ArrayList<String> specList = new ArrayList<>();
        if (spec == null || spec.keySet().size() == 0) {
            mCustomTableview.setVisibility(View.GONE);
            mTvSpecNodate.setVisibility(View.VISIBLE);
            return;
        }
        Iterator<String> iterator = spec.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            JsonElement jsonElement = spec.get(key);
            String value = "";
            if (jsonElement instanceof JsonNull) {
                value = "暂无";
            } else {
                value = jsonElement.getAsString();
            }
            if (value.equals("")) {
                continue;
            }
            specList.add(key + ": " + value);
        }
        Collections.reverse(specList);
        TableView tableView = mCustomTableview.clearTableContents();
        List<List<String>> lists = CommonUtils.groupListByQuantity(specList, 2);  //以2个分组
        for (int i = 0; i < lists.size(); i++) {
            List<String> item = lists.get(i);
            Integer size = item.size();
            String[] strings = item.toArray(new String[size]);
            tableView.addContent(strings);
        }
        tableView.refreshTable();

    }


    private void initBottomPic(String bottomPic) {

        if (mFlexboxLayoutBottom != null) {
            mFlexboxLayoutBottom.removeAllViews();
        }

        if (!TextUtils.isEmpty(bottomPic)) {
            mPicDetailNoData.setVisibility(View.GONE);
            String[] split = bottomPic.split(",");
            List<String> strings = Arrays.asList(split);
            for (int i = 0; i < strings.size(); i++) {
                ImageView bottomImg = getBottomImg(strings.get(i));
                mFlexboxLayoutBottom.addView(bottomImg);
            }

        } else {
            mFlexboxLayoutBottom.setVisibility(View.GONE);
            mPicDetailNoData.setVisibility(View.VISIBLE);
        }
    }

    public ImageView getBottomImg(String url) {
        //商品详情图片
        ImageView imageView = new ImageView(getActivity());
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(DensityUtil.dp2px(getActivity(), 920), ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.load(imageView, url);
        return imageView;

    }

    private void initMail(String mailMsg) {

        if (mFlexSpecial != null) {
            mFlexSpecial.removeAllViews();
        }
        if (!TextUtils.isEmpty(mailMsg)) {
            String[] split = mailMsg.split(",");
            List<String> strings = Arrays.asList(split);
            for (int i = 0; i < strings.size(); i++) {
                String text = strings.get(i);
//                if (text.equals("包邮")) {
//                    mFlexSpecial.addView(getLinearSpecial(R.mipmap.dot_red, text));
//                    continue;
//                }
                mFlexSpecial.addView(getLinearSpecial(R.mipmap.dog_blue, text));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mGoodsDetailPresenter != null) mGoodsDetailPresenter.unsubscribe();
    }

    @Override
    public void setPresenter() {
        mGoodsDetailPresenter = new GoodsDetailPresenter(this);
    }


    @Override
    public void listErr(String errMsg) {
        mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "加载失败...", errMsg, "点击重试", v -> {
            mProgressFrameLayout.showLoading();
            mGoodsDetailPresenter.getGoodsDetailList(mGoodsId);
        });
    }

    @Override
    public void addCartMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void increaseMsg(String msg) {

    }

    @Override
    public void decreaseMsg(String msg) {

    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }
}
