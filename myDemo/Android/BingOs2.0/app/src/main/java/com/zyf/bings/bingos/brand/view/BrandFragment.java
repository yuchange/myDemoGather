package com.zyf.bings.bingos.brand.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.brand.bean.TitleBean;
import com.zyf.bings.bingos.brandshome.BrandsHomeFragment;
import com.zyf.bings.bingos.event.GoBrandZoneFragmentEvent;
import com.zyf.bings.bingos.event.SetMainTitleEvent;
import com.zyf.bings.bingos.manager.ManagerAdapter;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshiqi on 2017/9/7.
 */

public class BrandFragment extends BaseFragment {

    private TabLayout mTab;
    private ViewPager mBrandVp;
    private ManagerAdapter managerAdapter;
    private String[] titles;
    private int i;
    private static final String TITLE = "/categoryInfoQuery.shtml";
    private static final String TITLE_METHOD = "category.info.query";
    private List<TitleBean.CategoryListBean> titleBeanList;

    @Override
    public void setPresenter() {

    }

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "BrandFragment";
        BrandFragment fragment = (BrandFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new BrandFragment();
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
                if (!(frag instanceof BrandFragment) && frag != null) {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBrandVp = (ViewPager) view.findViewById(R.id.brand_vp);
        mTab = (TabLayout) view.findViewById(R.id.brand_tab);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titles = getResources().getStringArray(R.array.brand_tab);
        getBrandTtitle();
        mBrandVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(mTab.getTabAt(position).getText());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取品牌馆标题
     */
    private void getBrandTtitle() {
        Map<String, String> map = new ArrayMap<>();
        map.put(BingNetStates.METHOD, TITLE_METHOD);
        RxOkClient.doPost(map, TITLE, getClass().getSimpleName(), null).subscribe(new WebAction() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                TitleBean titleBean = gson.fromJson(data, TitleBean.class);
                if (titleBean.getCategoryList() != null && titleBean.getCategoryList().size() > 0) {
                    titleBeanList = titleBean.getCategoryList();
                    Log.d("BrandFragment", "titleBeanList:" + titleBeanList);
                    List<Fragment> fragmentList = new ArrayList<>();
                    fragmentList.add(new BrandsHomeFragment());
                    fragmentList.add(BrandShopFragment.newInstance());
                    fragmentList.add(BrandShopFragment.newInstance());
                    for (int i = 0; i < titleBeanList.size(); i++) {
                        fragmentList.add(GoodsFragment.newInstance(titleBeanList.get(i).getCategoryId(), titleBeanList.get(i).getCategoryName()));
                    }
                    managerAdapter = new ManagerAdapter(getChildFragmentManager(), fragmentList);
                    mBrandVp.setCurrentItem(0);
                    mBrandVp.setAdapter(managerAdapter);
//                    mBrandVp.setOffscreenPageLimit(); // 设置预加载页数
                    mTab.setupWithViewPager(mBrandVp);
                    for (i = 0; i < 3; i++) {
                        mTab.getTabAt(i).setText(titles[i]);
                    }
                    for (int i = 3; i< titleBeanList.size() + 3; i++) {
                        mTab.getTabAt(i).setText(titleBeanList.get(i - 3).getCategoryName());
                    }
                    reflex(mTab);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });
    }

    /**
     * 处理标签下划线长度
     */

    public void reflex(final TabLayout tabLayout) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);

                    int dp10 = dip2px(tabLayout.getContext(), 30);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Subscribe()
    public void goBrandZone(GoBrandZoneFragmentEvent goBrandZoneFragmentEvent) {
        if (mBrandVp != null) {
            mBrandVp.setCurrentItem(2);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("Brans", "执行了"+hidden);
        if(!hidden){
            EventBus.getDefault().post(new SetMainTitleEvent());
        }

    }


}




