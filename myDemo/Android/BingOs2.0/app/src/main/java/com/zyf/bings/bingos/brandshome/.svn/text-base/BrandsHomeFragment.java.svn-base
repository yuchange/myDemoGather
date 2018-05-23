package com.zyf.bings.bingos.brandshome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.activity.WebActivity;
import com.zyf.bings.bingos.brand.view.BrandDetailFragment;
import com.zyf.bings.bingos.brandshome.adapter.AreBuyingAdapter;
import com.zyf.bings.bingos.brandshome.adapter.AreBuyingItemDecoration;
import com.zyf.bings.bingos.brandshome.adapter.BrandsAdapter;
import com.zyf.bings.bingos.brandshome.adapter.BrandsRvItemDecoration;
import com.zyf.bings.bingos.brandshome.bean.AreBuyingProductInfo;
import com.zyf.bings.bingos.brandshome.bean.BannerInfo;
import com.zyf.bings.bingos.event.GoBrandZoneFragmentEvent;
import com.zyf.bings.bingos.event.SetMainTitleEvent;
import com.zyf.bings.bingos.goods.GoodsDetailFragment;
import com.zyf.bings.bingos.ui.ScrollBottomScrollView;
import com.zyf.bings.bingos.ui.banner.Banner;
import com.zyf.bings.bingos.ui.banner.BannerConfig;
import com.zyf.bings.bingos.ui.banner.listener.OnBannerListener;
import com.zyf.bings.bingos.ui.banner.loader.GlideImageLoader;
import com.zyf.bings.bingos.ui.banner.transformer.DefaultTransformer;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos_libnet.MainBuildConfigure;
import com.zyf.bings.bingos_libnet.OkGoUtils;
import com.zyf.bings.bingos_libnet.callback.NetResultCallback;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.progressDialog.MyProgressDialog;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/9/7.
 */


public class BrandsHomeFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "BrandsHomeFragment";
    private LinearLayout mLlForABatch;
    private RecyclerView mRlBrands;
    private RecyclerView mRlAreBuying;
    private TextView mTvSeeMore;
    private static final String AD_INFO_QUERY_URL = "/adInfoQuery.shtml";
    private static final String FOR_A_BATCH = "/everyBodyBuy.shtml";
    private Banner mBanner;

    public final static String TYPE_VIDEO = "1";
    public final static String TYPE_IMG = "2";

    public final static String TYPE_UNKNOW = "0";
    public final static String TYPE_WEB = "3";
    public final static String TYPE_GOODS = "4";
    private RecyclerView mRvAreBuying;

    private List<BannerInfo.ProductInfoBean.ProductInfoListBean> mAreBuyingInfoList;
    private AreBuyingAdapter mAreBuyingAdapter;
    private TextView mTvBackToTop;
    private ScrollBottomScrollView mScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initListener();
        initData();
        Log.e(TAG, "onCreateView执行了");
        return view;
    }

    private void initListener() {
        mLlForABatch.setOnClickListener(this);
        mTvSeeMore.setOnClickListener(this);
        mTvBackToTop.setOnClickListener(this);
        mScrollView.setScrollBottomListener(new ScrollBottomScrollView.ScrollBottomListener() {
            @Override
            public void scrollBottom(int t, int oldt) {

                if (t > 800) {
                    mTvBackToTop.setVisibility(VISIBLE);
                } else {
                    mTvBackToTop.setVisibility(GONE);
                }

            }
        });


    }

    private void initData() {
        MyProgressDialog progressDialog = PBUtil.getPD(getActivity());
        progressDialog.show();
        ArrayMap<String, String> map = new ArrayMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("homepage_id", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("method", "ad.info.query");
        map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
        OkGoUtils.doStringPostRequest(map, AD_INFO_QUERY_URL, TAG, new NetResultCallback() {
            @Override
            public void onSuccess(String str) {
                progressDialog.dismiss();
                Log.i(TAG, "轮播数据" + str);
                BannerInfo bannerInfo = GsonFactory.getGson().fromJson(str, BannerInfo.class);
                List<BannerInfo.BannerListBean> bannerList = bannerInfo.getBannerList();
                mAreBuyingInfoList = bannerInfo.getProductInfo().getProductInfoList();

                initBanner(bannerList);//初始化轮播部分
                initBannerListener(bannerList);
                initRlBrands(bannerInfo.getBrandList());//初始化品牌购部分
                initRvAreBuying(mAreBuyingInfoList);//初始化大家都在买模块


            }

            @Override
            public void onFail(int code, String error) {
                progressDialog.dismiss();
                Log.i(TAG, "错误" + code);
            }
        });


    }

    private void initRvAreBuying(List<BannerInfo.ProductInfoBean.ProductInfoListBean> productList) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        mRvAreBuying.setLayoutManager(gridLayoutManager);
        mRvAreBuying.addItemDecoration(new AreBuyingItemDecoration(29));
        mAreBuyingInfoList = productList;
        mAreBuyingAdapter = new AreBuyingAdapter(getActivity(), mAreBuyingInfoList);
        mRvAreBuying.setAdapter(mAreBuyingAdapter);
        mAreBuyingAdapter.setOnItemClickListener(new AreBuyingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.i(TAG, "onItemClick执行了");
                GoodsDetailFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), productList.get(position).getBsjProductId());

            }
        });


    }

    private void initRlBrands(List<BannerInfo.BrandListBean> brandList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlBrands.setLayoutManager(gridLayoutManager);
        mRlBrands.addItemDecoration(new BrandsRvItemDecoration(30));
        BrandsAdapter brandsAdapter = new BrandsAdapter(getActivity(), brandList);
        mRlBrands.setAdapter(brandsAdapter);
        brandsAdapter.setOnItemClickListener(new BrandsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {//品牌

                BrandDetailFragment.start(R.id.fl_container, brandList.get(position).getBrand_id(), brandList.get(position).getBrand_url(), getActivity().getSupportFragmentManager());

            }
        });


    }

    private void initBannerListener(List<BannerInfo.BannerListBean> bannerList) {


        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.showToast(position + "");
                if (bannerList.size() != 0 && bannerList != null) {
                    Log.i(TAG, "bannner点击类型" + bannerList.get(position).getFileType());
                    switch (bannerList.get(position).getFileType()) {
                        case TYPE_GOODS: //商品详情
                            GoodsDetailFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), bannerList.get(position).getGoods_id());


                            break;
                        case TYPE_WEB:
                            WebActivity.start(getContext(), MainBuildConfigure.HTML_URL, "", "");


                            break;
                        case TYPE_VIDEO:


                            break;
                        case TYPE_IMG:


                            break;
                        case TYPE_UNKNOW:
                            break;
                        default:
                            break;


                    }

                }


            }
        });


    }

    private void initBanner(List<BannerInfo.BannerListBean> bannerList) {
        List<String> imgsList = new ArrayList<>();
        if (bannerList != null && bannerList.size() != 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                imgsList.add(bannerList.get(i).getImgUrl());
            }
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImages(imgsList);
        mBanner.setImageLoader(new GlideImageLoader());

        mBanner.setBannerAnimation(new DefaultTransformer());
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();

    }


    private void initView(View view) {
        mRlBrands = (RecyclerView) view.findViewById(R.id.rl_brands);
        mTvSeeMore = (TextView) view.findViewById(R.id.tv_see_more);
        mLlForABatch = (LinearLayout) view.findViewById(R.id.ll_for_a_batch);
        mBanner = (Banner) view.findViewById(R.id.banner_carusel);
        mRvAreBuying = (RecyclerView) view.findViewById(R.id.rv_are_buying);
        mTvBackToTop = (TextView) view.findViewById(R.id.tv_back_to_top);
        mScrollView = (ScrollBottomScrollView) view.findViewById(R.id.scroll);

    }


    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "BrandsHome";
        BrandsHomeFragment fragment = (BrandsHomeFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof BrandsHomeFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }


        if (fragment == null) {
            fragment = new BrandsHomeFragment();
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
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume执行了");
        EventBus.getDefault().post(new SetMainTitleEvent());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_for_a_batch:
                Map<String, String> map = new ArrayMap<String, String>();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pageSize", "12");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("method", "every.body.buy");
                map.put(BingNetStates.REQUEST_DATA, jsonObject.toString());
                OkGoUtils.doStringPostRequest(map, FOR_A_BATCH, TAG, new NetResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        if (mAreBuyingInfoList == null) {
                            return;
                        }
                        mAreBuyingInfoList.clear();
                        AreBuyingProductInfo areBuyingProductInfo = GsonFactory.fromJson(str, AreBuyingProductInfo.class);
                        if (mAreBuyingInfoList == null) {
                            mAreBuyingInfoList = new ArrayList<>();
                        }
                        if (areBuyingProductInfo != null && areBuyingProductInfo.getProductInfo() != null) {
                            List<AreBuyingProductInfo.ProductInfoBean.ProductInfoListBean> areProductInfoList = areBuyingProductInfo.getProductInfo().getProductInfoList();

                            for (int i = 0; i < areProductInfoList.size(); i++) {
                                //  BannerInfo.ProductInfoBean.ProductInfoListBean bannerProductList = new BannerInfo.ProductInfoBean.ProductInfoListBean(areProductInfoList.get(i).getWeight(), areProductInfoList.get(i).getPicUrl(), areProductInfoList.get(i).getCategoryId(), areProductInfoList.get(i).getBsjProductName(), areProductInfoList.get(i).getPicId(), areProductInfoList.get(i).getIsDeleted(), areProductInfoList.get(i).getGoodsCat(), areProductInfoList.get(i).getBrandName(), areProductInfoList.get(i).getProductType(), areProductInfoList.get(i).getCategoryName(), areProductInfoList.get(i).getUnit(), areProductInfoList.get(i).getPrice(), areProductInfoList.get(i).getStock(), areProductInfoList.get(i).getIsDefault(), areProductInfoList.get(i).getTotalCount(), areProductInfoList.get(i).getPicType(), areProductInfoList.get(i).getLimitPrice(), areProductInfoList.get(i).getCategorySearchName(), areProductInfoList.get(i).getBsjProductCode(), areProductInfoList.get(i).getBsjProductId(), areProductInfoList.get(i).getActivity());
                                BannerInfo.ProductInfoBean.ProductInfoListBean bannerProductList = new BannerInfo.ProductInfoBean.ProductInfoListBean();
                                bannerProductList.setActivity(areProductInfoList.get(i).getActivity());
                                bannerProductList.setTotalCount(areProductInfoList.get(i).getTotalCount());
                                bannerProductList.setBrandName(areProductInfoList.get(i).getBrandName());
                                bannerProductList.setBsjProductCode(areProductInfoList.get(i).getBsjProductCode());
                                bannerProductList.setBsjProductId(areProductInfoList.get(i).getBsjProductId());
                                bannerProductList.setBsjProductName(areProductInfoList.get(i).getBsjProductName());
                                bannerProductList.setCategoryId(areProductInfoList.get(i).getCategoryId());
                                bannerProductList.setCategoryName(areProductInfoList.get(i).getCategoryName());
                                bannerProductList.setGoodsCat(areProductInfoList.get(i).getGoodsCat());
                                bannerProductList.setIsDefault(areProductInfoList.get(i).getIsDefault());
                                bannerProductList.setCategorySearchName(areProductInfoList.get(i).getCategorySearchName());
                                bannerProductList.setLimitPrice(areProductInfoList.get(i).getLimitPrice());
                                bannerProductList.setPicId(areProductInfoList.get(i).getPicId());
                                bannerProductList.setPicType(areProductInfoList.get(i).getPicType());
                                bannerProductList.setPicUrl(areProductInfoList.get(i).getPicUrl());
                                bannerProductList.setStock(areProductInfoList.get(i).getStock());
                                bannerProductList.setPrice(areProductInfoList.get(i).getPrice());
                                bannerProductList.setUnit(areProductInfoList.get(i).getUnit());
                                bannerProductList.setWeight(areProductInfoList.get(i).getWeight());
                                bannerProductList.setIsDefault(areProductInfoList.get(i).getIsDefault());
                                bannerProductList.setProductType(areProductInfoList.get(i).getProductType());
                                mAreBuyingInfoList.add(bannerProductList);
                            }

                        }


                        if (mAreBuyingAdapter != null) {
                            mAreBuyingAdapter.notifyDataSetChanged();
                        } else {
                            mAreBuyingAdapter = new AreBuyingAdapter(getActivity(), mAreBuyingInfoList);
                            mRvAreBuying.setAdapter(mAreBuyingAdapter);
                        }


                    }

                    @Override
                    public void onFail(int code, String error) {
                        ToastUtils.showToast(error);

                    }
                });
                break;
            case R.id.tv_see_more://品牌专区
                EventBus.getDefault().post(new GoBrandZoneFragmentEvent());

                break;
            case R.id.tv_back_to_top:

                mScrollView.fullScroll(View.FOCUS_UP);


                break;
            default:
                break;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
