package com.zyf.bings.bingos.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.cart.DeleteDialog;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.utils.DensityUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created wangshiqi sd on 2017/9/4.
 */

public class ManagerFragment extends BaseFragment {

    private ViewPager mManagerVp;
    private TabLayout mTab;
    private ManagerAdapter managerAdapter;
    private String[] titles;
    private TextView mLogOut;

    @Override
    public void setPresenter() {

    }

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "ManagerFragment";
        ManagerFragment fragment = (ManagerFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new ManagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof ManagerFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mManagerVp = (ViewPager) view.findViewById(R.id.manager_vp);
        mTab = (TabLayout) view.findViewById(R.id.title_tab);
        mLogOut = (TextView) view.findViewById(R.id.manager_log_out);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mLogOut.setOnClickListener(view -> {
            DeleteDialog deleteDialog = DeleteDialog.newInstance();
            deleteDialog.show(getActivity().getSupportFragmentManager(), "logOut");
            deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                @Override
                public void onDeleteItem() {
                    LogOutClient.logOut();
                }
            });
        });
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(AccountFragment.newInstance());
        fragmentList.add(AccountFragment.newInstance());
        fragmentList.add(AccountFragment.newInstance());
        managerAdapter = new ManagerAdapter(getChildFragmentManager(), fragmentList);
        mManagerVp.setCurrentItem(0);
        mManagerVp.setAdapter(managerAdapter);
        mTab.setupWithViewPager(mManagerVp);
        titles = getResources().getStringArray(R.array.manager_tab);
        for (int i = 0; i < titles.length; i++) {
            mTab.getTabAt(i).setText(titles[i]);
        }
//        reduceMarginsInTabs(mTab, 70/3);
        setTabLine(mTab, 70 / 3, 70 / 3);
    }


    /**
     * 对tabLayout的指示器的长度的处理
     */
//    public void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {
//
//        View tabStrip = tabLayout.getChildAt(0);
//        if (tabStrip instanceof ViewGroup) {
//            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
//            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
//                View tabView = tabStripGroup.getChildAt(i);
//                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
//                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
//                }
//            }
//
//            tabLayout.requestLayout();
//        }
//    }
    public void setTabLine(TabLayout tab, int left, int right) {
        try {
            Class<?> tablayout = tab.getClass();
            Field tabStrip = tablayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(mTab);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                //修改两个tab的间距
                params.setMarginStart(DensityUtil.dp2px(getContext(), left));
                params.setMarginEnd(DensityUtil.dp2px(getContext(), right));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().postSticky(new MainTitleEvent("冰果管家"));

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            EventBus.getDefault().postSticky(new MainTitleEvent("冰果管家"));
        }
    }
}
