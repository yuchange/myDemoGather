package com.bingstar.bingmall.ads;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.bean.AdsInfoBean;
import com.bingstar.bingmall.ads.bean.HpgProductAds;
import com.bingstar.bingmall.ads.view.MutableViewPager;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.base.WebActivity;
import com.bingstar.bingmall.discount.DiscountFragment;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.goods.GoodsListFragment;
import com.bingstar.bingmall.goods.view.GoodsSearchDialog;
import com.bingstar.bingmall.home.HomeTitleAdapter;
import com.bingstar.bingmall.home.HomeTitleView;
import com.bingstar.bingmall.home.bean.TitleBean;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.video.lib.SynLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能：
 * Created by yaoyafeng on 17/2/8 上午9:37
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/8 上午9:37
 * @modify by reason:{方法名}:{原因}
 */
public class AdsFragment extends BaseFragment implements IAdsContract.AdsView {

    private IAdsContract.AdsPresenter presenter;

    private AdsPagerAdapter pagerAdapter;

    private ImgVideoViewAdapter bannerAdapter;

    private List<HpgProductAds> productList;

    private List<AdsInfoBean> bannerList;

    private HomeTitleView homeTitleView;
    private View view;

    private MutableViewPager itemPager;
    private MutableViewPager bannerPager;

    private SynLinearLayout ads_syn_linear_title;

    private ImageView ads_info_dis, ads_info_cycle, ads_info_left_first, ads_info_left_second, ads_info_right;

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "Ads";
        AdsFragment fragment = (AdsFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new AdsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof AdsFragment) && frag != null) {
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
        view = inflater.inflate(R.layout.ads_fragment, container, false);
        setParentResId(getArguments().getInt(KEY_RESID));
        productList = new ArrayList<>();
        bannerList = new ArrayList<>();
        itemPager = (MutableViewPager) view.findViewById(R.id.ads_viewpager);
        pagerAdapter = new AdsPagerAdapter(productList, getContext());
        itemPager.setAdapter(pagerAdapter);
        ads_info_dis = (ImageView) view.findViewById(R.id.ads_info_dis);
        // ads_info_cycle = (ImageView) view.findViewById(R.id.ads_info_cycle);
        bannerPager = (MutableViewPager) view.findViewById(R.id.ads_info_main);
        ads_syn_linear_title = (SynLinearLayout) view.findViewById(R.id.ads_syn_linear_title);
        bannerAdapter = new ImgVideoViewAdapter(bannerList, getContext());
        bannerAdapter.setOnClickListener(onAdsItemClickListener);
        bannerPager.setAdapter(bannerAdapter);
        pagerAdapter.setOnChildClickListener(onAdsItemClickListener);
        EventBus.getDefault().register(this);
        homeTitleView = (HomeTitleView) view.findViewById(R.id.home_title);
        homeTitleView.setOnItemClickListener(new HomeTitleView.OnTitleItemClickListener() {

            @Override
            public void onClick(TitleBean titleBean, int position) {
                GoodsListFragment.start(getParentResId(), titleBean.getCategoryId(), "", getFragmentManager());
            }
        });
        homeTitleView.setSearchOnClickListener(getChildFragmentManager(), ErrorFragment.AdsFragment);
        homeTitleView.setFrom(ErrorFragment.AdsFragment);
        homeTitleView.setErrorListener(new HomeTitleView.ViewErrorListener() {
            @Override
            public void onError() {
                ads_syn_linear_title.showError();
            }

            @Override
            public void onSucceed() {
                if (ads_syn_linear_title != null) {
                    ads_syn_linear_title.showSuccess();
                }
            }
        });
        ads_syn_linear_title.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                homeTitleView.getTitle(false);
                presenter.getAdsInfo(bannerList, productList);
            }
        });
        setPresenter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Ads", "onResume执行了");
        homeTitleView.getTitle(false);
        homeTitleView.clearState();
        HomeTitleAdapter titleAdapter = homeTitleView.getTitleAdapter();
        if (null != titleAdapter) titleAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = homeTitleView.getLayoutManager();
        if (null != layoutManager) layoutManager.scrollToPosition(0);
        presenter.getAdsInfo(bannerList, productList);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Ads", "onPause执行了");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("Ads", "fragment" + hidden);
        if (hidden) {
            if (bannerPager != null) {
                bannerPager.onDestory();
            }
        }
    }


    @Override
    public void setPresenter() {
        presenter = new AdsPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unBind();
        bannerPager.onDestory();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setChildView(AdsInfoBean kid, AdsInfoBean cycle) {
        Util.imageLoader(ads_info_dis, kid.getUrl());
        // Util.imageLoader(ads_info_cycle, BingstarNet.IMG_SERVICE + cycle.getUrl());
        ads_info_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFragment.start(getParentResId(), getFragmentManager());
            }
        });
        /*ads_info_cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.checkMemberId(getActivity(), new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        CycleFragment.start(getParentResId(), getFragmentManager());
                    }
                });
            }
        });*/
    }

    @Override
    public void refreshPager() {
        itemPager.setDot(productList.size());
        bannerPager.setDot(bannerList.size(), true);
        pagerAdapter.notifyDataSetChanged();
        bannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void listErr() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.AdsFragment);
    }
//
//    @Override
//    public void onViewError() {
//        super.onViewError();
//    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(AdsFragment.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.AdsFragment))) {
            ads_syn_linear_title.showError();
        }
        if (msg.getClassName().equals(HomeTitleView.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.AdsFragment))) {
            ads_syn_linear_title.showError();
        }
        if (msg.getClassName().equals(GoodsSearchDialog.class) && msg.getIsFrom().equals(String.valueOf(ErrorFragment.AdsFragment))) {
            /**
             * liumengqiang 修改：将“关键字”提出到string文件中
             */
            GoodsListFragment.start(getParentResId(), "", msg.getMsg(), getFragmentManager());
        }

        if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.AdsFragment_final.equals(msg.getMsg())) {
            /**
             * User初始化错误处理
             */
            ads_syn_linear_title.showError();
        }

    }

    com.bingstar.bingmall.ads.view.OnAdsItemClickListener onAdsItemClickListener = new com.bingstar.bingmall.ads.view.OnAdsItemClickListener() {
        @Override
        public void onItemClick(View view, AdsInfoBean infoBean) {
            Log.i("TYPE", infoBean.getClickType() + "");
            if (infoBean.getUrlType() == AdsInfoBean.TYPE_VIDEO) {

            } else {
                Log.i("Ads", "地址" + infoBean.toString());
                if (infoBean.getClickType() == AdsInfoBean.TYPE_WEB) {
                    WebActivity.start(getContext(), BingstarNet.HTML_URL + infoBean.getClickContent(), infoBean.getCouponIds(), infoBean.getHomeId());
                }
                //BingstarNet.IMG_SERVICE
// else if (infoBean.getClickType() == AdsInfoBean.TYPE_WEB && !TextUtils.isEmpty(infoBean.getCouponIds())) {
//                    //"http://192.168.1.138:8099/bingstar-web"
//                    WebActivity.start(getContext(),"http://192.168.1.138:8099/bingstar-web" + infoBean.getClickContent(), infoBean.getCouponIds(),infoBean.getHomeId());
//
//                }
                else if (infoBean.getClickType() == AdsInfoBean.TYPE_GOODS) {
                    GoodsDetailFragment.setModelFrom("首页下部分");
                    GoodsDetailFragment.start(getFragmentManager(), infoBean.getClickContent(), "");
                }
            }
        }
    };


}

