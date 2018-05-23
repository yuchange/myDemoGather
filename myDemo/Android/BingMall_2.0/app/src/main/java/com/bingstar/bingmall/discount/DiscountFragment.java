package com.bingstar.bingmall.discount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.view.MutableViewPager;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.discount.bean.KidProductInfo;
import com.bingstar.bingmall.discount.bean.KidShowInfo;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.home.HomeTitleView;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.user.bean.User;
import com.bumptech.glide.Glide;
import com.yunzhi.lib.view.OnSingleClickListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/23 下午1:37
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/23 下午1:37
 * @modify by reason:{方法名}:{原因}
 */

public class DiscountFragment extends BaseFragment implements IDiscountContract.DiscountView {

    private IDiscountContract.DiscountPresenter presenter;

    private ArrayList<KidShowInfo> timeList;

    private List<View> viewList;

    private MutableViewPager viewPager;

    private DiscountAdapter discountAdapter;

    private ArrayList<KidProductInfo> kidModelList;

    private MViewPagerAdapter pagerAdapter;

    private ImageView iv_left1, iv_left2, iv_right;

    private boolean defaultSelect = false;

    private boolean isStart = false;


    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "Discount";
        DiscountFragment fragment = (DiscountFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new DiscountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        }else {
            if (!fragment.isHidden()) {
                transaction.commit();
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof DiscountFragment) && frag != null) {
                    if(!frag.isHidden()){
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discount_fragment, container, false);
        viewPager = (MutableViewPager) view.findViewById(R.id.discount_vp);
        RecyclerView time_rv = (RecyclerView) view.findViewById(R.id.discount_rv);
        iv_left1 = (ImageView) view.findViewById(R.id.discount_left1_iv);
        iv_left2 = (ImageView) view.findViewById(R.id.discount_left2_iv);
        iv_right = (ImageView) view.findViewById(R.id.discount_right_iv);
        iv_left1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (!isStart) {
                    showToast(R.string.discount_not_start);
                    return;
                }
                if (kidModelList.size() >= 1) {
                    if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                        showToast(getString(R.string.no_memeber_id));
                        return;
                    }
                    presenter.addToCart(kidModelList.get(0));
                }
            }
        });
        iv_left2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (!isStart) {
                    showToast(R.string.discount_not_start);
                    return;
                }
                if (kidModelList.size() >= 2) {
                    if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                        showToast(getString(R.string.no_memeber_id));
                        return;
                    }
                    presenter.addToCart(kidModelList.get(1));
                    umengSum();
                }
            }
        });
        iv_right.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (!isStart) {
                    showToast(R.string.discount_not_start);
                    return;
                }
                if (kidModelList.size() >= 3) {
                    if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                        showToast(getString(R.string.no_memeber_id));
                        return;
                    }
                    presenter.addToCart(kidModelList.get(2));
                    umengSum();
                }
            }
        });
        WarpLinearLayoutManager layoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        time_rv.setLayoutManager(layoutManager);
        timeList = new ArrayList<>();
        kidModelList = new ArrayList<>();
        discountAdapter = new DiscountAdapter(timeList);
        discountAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isStart = timeList.get(position).isStart();
                presenter.getKidInfo(timeList.get(position).getKidId(), timeList.get(position).getKidTime(), kidModelList);
            }
        });
        viewList = new ArrayList<>();
        setPresenter();
        EventBus.getDefault().register(this);
        pagerAdapter = new MViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);

        time_rv.setAdapter(discountAdapter);
        return view;
    }

    public void getTitle(boolean defaultSelect) {
        this.defaultSelect = defaultSelect;
        presenter.getKidList(timeList);
    }


    //初始化底部viewpager
    private void initVp(final ArrayList<KidProductInfo> kidModelList) {
        viewList.clear();
        viewPager.clearChildView();
        if (kidModelList.size() > 3) {
            for (int i = 3; i < kidModelList.size(); i = i + 2) {
                View view;
                view = LayoutInflater.from(getContext()).inflate(R.layout.discount_view, null);
                ImageView discountview_left_iv = (ImageView) view.findViewById(R.id.discountview_letf_iv);
                ImageView discountview_right_iv = (ImageView) view.findViewById(R.id.discountview_right_iv);
                Util.imageLoader(discountview_left_iv,kidModelList.get(i).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
                if (i + 1 < kidModelList.size()) {
                    Util.imageLoader(discountview_right_iv,kidModelList.get(i + 1).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));

                    final int finalI = i;
                    discountview_right_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isStart) {
                                showToast(R.string.discount_not_start);
                                return;
                            }
                            if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                                showToast(getString(R.string.no_memeber_id));
                                return;
                            }
                            presenter.addToCart(kidModelList.get(finalI + 1));
                            umengSum();
                        }
                    });
                    view.setTag(kidModelList.get(i).getKidUrl() + kidModelList.get(i + 1).getKidUrl());
                } else {
                    view.setTag(kidModelList.get(i).getKidUrl());
                }

                final int finalI1 = i;
                discountview_left_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isStart) {
                            showToast(R.string.discount_not_start);
                            return;
                        }
                        if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                            showToast(getString(R.string.no_memeber_id));
                            return;
                        }
                        presenter.addToCart(kidModelList.get(finalI1));
                        umengSum();
                    }
                });
                viewList.add(view);
            }
           // viewPager.setDot(viewList.size());
            if (viewPager.getCurrentIndex() > 0) {
                viewPager.changeDot(viewPager.getCurrentIndex());
            }
        }
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter() {
        presenter = new DiscountPresent(this);
    }

    @Override
    public void notisfyImg() {
        int i = kidModelList.size();
        if (i < 3) {
            switch (i) {
                case 2:
                    Util.imageLoader(iv_left2, kidModelList.get(1).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
                    Util.imageLoader(iv_left1, kidModelList.get(0).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
                    Glide.clear(iv_right);
                    break;
                case 1:
                    Glide.clear(iv_left2);
                    Glide.clear(iv_right);
                    Util.imageLoader(iv_left1, kidModelList.get(0).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
                    break;
                case 0:
                    Glide.clear(iv_left1);
                    Glide.clear(iv_left2);
                    Glide.clear(iv_right);
                    break;
            }
        } else {
            Util.imageLoader(iv_right, kidModelList.get(2).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
            Util.imageLoader(iv_left2, kidModelList.get(1).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
            Util.imageLoader(iv_left1, kidModelList.get(0).getKidUrl(), getResources().getDrawable(R.drawable.img_error_bg));
        }
        initVp(kidModelList);

    }

    @Override
    public void notifyTitleRefresh() {
        if (timeList.size() > 0) {
            discountAdapter.notifyDataSetChanged();
            if (defaultSelect) {
                timeList.get(0).setSelected(true);
                isStart = discountAdapter.getItem(0).isStart();
                discountAdapter.notifyItemChanged(0);
                presenter.getKidInfo(timeList.get(0).getKidId(), timeList.get(0).getKidTime(), kidModelList);
            }
        }
    }

    @Override
    public void listErr() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.DisCountFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        getTitle(true);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.unBind();
    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(HomeTitleView.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.DisCountFragment))) {
            listErr();
        }
        if (msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(BingstarErrorParser.DisCountFragment_final)) {
            listErr();
        }

    }

    private void umengSum() {
        //友盟添加购物车统计
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("modelFrom", "每日特惠");
        Util.BaiduEvent(getContext(), "modelFrom", "每日特惠");
    }



}
