package com.bingstar.bingmall.cycle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cycle.bean.CycleEventBus;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;

import com.bingstar.bingmall.cycle.view.CycleDetailDialog;
import com.bingstar.bingmall.cycle.view.CycleDialogPresenter;
import com.bingstar.bingmall.cycle.view.ICycleDialogContract;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.home.HomeTitleView;
import com.bingstar.bingmall.home.IHomeContract;
import com.bingstar.bingmall.home.TitleStates;
import com.bingstar.bingmall.home.bean.TitleBean;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.order.view.OrderInfoFragment;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.utils.LogUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianhechen on 17/2/14.
 */

public class CycleFragment extends BaseFragment implements ICycleContract.CycleListView {

    private ArrayList<CycleProductInfo.CycleProduct.CycleProductInfoDetail> cycleProductList;

    private ArrayList<CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail> cycleProductQList;

    private ICycleContract.CyclePresenter presenter;

    private ICycleDialogContract.CycleDialogPresenter createOrderPresenter;

    private CycleLeftRVAdapter cycleLeftrvAdapter;
    private CycleRightRVAdapter cycleRightrvAdapter;

    private SynLinearLayout synLinearLayout;
    private SynLinearLayout synLinearLayoutQ;

    //    //  总共的列表的参数
//    private int sumCountZhouqi = 0;
//    private int sumCountQuik = 0;
//    //  最后可见的条目数
//    private int lastVisibleItemZhouqi =  0;
//    private int lastVisibleItemQuik = 0;
    //   条目布局的管理器
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager LinearLayoutRightManager;

    private HomeTitleView homeTitleView;
    private String category;

    private SynLinearLayout home_synLinearlayout;

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "Cycle";
        CycleFragment fragment = (CycleFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new CycleFragment();
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
                if (!(frag instanceof CycleFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
//        presenter.getCycleProductList(cycleProductList,category);
//        presenter.getCycleProductQList(cycleProductQList,category);
    }

    @Override
    public void setPresenter() {
        presenter = new CyclePresenter(this);
        createOrderPresenter = new CycleDialogPresenter(this);
    }

    @Override
    public void notifyQList(int type) {
        switch (type) {
            case SynLinearLayout.SHOW_SUCCESS: {
                home_synLinearlayout.showSuccess();
                synLinearLayoutQ.showSuccess();
                cycleRightrvAdapter.notifyDataSetChanged();
                break;
            }
            case SynLinearLayout.SHOW_EMPTY: {
                synLinearLayoutQ.showEmpty(R.layout.cycle_no_goods);
                break;
            }
            case SynLinearLayout.SHOW_ERROR: {
                synLinearLayoutQ.showError();
                break;
            }
            case SynLinearLayout.SHOW_LOAD: {
                break;
            }
        }
    }

    @Override
    public void notifyList(int type) {
        switch (type) {
            case SynLinearLayout.SHOW_SUCCESS: {
                home_synLinearlayout.showSuccess();
                synLinearLayout.showSuccess();
                cycleLeftrvAdapter.notifyDataSetChanged();
                break;
            }
            case SynLinearLayout.SHOW_EMPTY: {
                cycleLeftrvAdapter.notifyDataSetChanged();
                synLinearLayout.showEmpty(R.layout.cycle_no_goods);
                break;
            }
            case SynLinearLayout.SHOW_ERROR: {
                synLinearLayout.showError();
                break;
            }
            case SynLinearLayout.SHOW_LOAD: {
                break;
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cycle_fragment, container, false);
        home_synLinearlayout = (SynLinearLayout) view.findViewById(R.id.home_synLinearlayout);
        synLinearLayout = (SynLinearLayout) view.findViewById(R.id.synLinearlayout_recycleview);
        synLinearLayoutQ = (SynLinearLayout) view.findViewById(R.id.synLinearlayout_recycleviewQ);
        RecyclerView cycle_leftrv = (RecyclerView) view.findViewById(R.id.cyclefragment_left_rv);
        RecyclerView cycle_rightrv = (RecyclerView) view.findViewById(R.id.cyclefragment_right_rv);
        //注册接收categoriesId事件;
        EventBus.getDefault().register(this);

        setParentResId(getArguments().getInt(KEY_RESID));
        setPresenter();

        cycleProductList = new ArrayList<>();
        cycleProductQList = new ArrayList<>();


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutRightManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        cycle_leftrv.setLayoutManager(linearLayoutManager);
        cycle_rightrv.setLayoutManager(LinearLayoutRightManager);
        cycleLeftrvAdapter = new CycleLeftRVAdapter(cycleProductList);
        cycleRightrvAdapter = new CycleRightRVAdapter(cycleProductQList);

        cycle_leftrv.setAdapter(cycleLeftrvAdapter);
        cycle_rightrv.setAdapter(cycleRightrvAdapter);
        cycleLeftrvAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                User.checkMemberId(getActivity(), new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        CycleDetailDialog.start(getFragmentManager(), cycleProductList.get(position));
                    }
                });

            }
        });
        cycleRightrvAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                /**
                 * 直接创建订单(目前缺少参数)
                 */
                User.checkMemberId(getActivity(), new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        if (User.checkAddr()) {
                            createOrderPresenter.createQListOrder(cycleProductQList.get(position));
                            return;
                        }
                        AddrManageFragment.start(R.id.main_fragment, getFragmentManager());
                    }
                });
                /**
                 * 判断User是否为空，是空的话就跳转到AddrManageFragment页面
                 */


            }
        });
        /**
         *  TODO 标题
         */
        homeTitleView = (HomeTitleView) view.findViewById(R.id.home_title);
        homeTitleView.hiddenSearchBar(true);
        homeTitleView.setOnItemClickListener(new HomeTitleView.OnTitleItemClickListener() {
            @Override
            public void onClick(TitleBean titleBean, int position) {
                category = titleBean.getCategoryId();
                cycleProductList.clear();
                cycleProductQList.clear();
                if (category != null && !"".equals(category)) {
                    synLinearLayout.showLoad();
                    synLinearLayoutQ.showLoad();
                    presenter.getCycleProductList(cycleProductList, category);
                    presenter.getCycleProductQList(cycleProductQList, category);
                }
            }
        });
        homeTitleView.setViewBack(R.color.cycle_back);
        homeTitleView.setTitleIcon(getResources().getDrawable(R.drawable.home_title_cycle));
        homeTitleView.getTitle(TitleStates.TYPE_CYCLE, true);
        homeTitleView.setErrorListener(new HomeTitleView.ViewErrorListener() {
            @Override
            public void onError() {
                home_synLinearlayout.showError();
//                ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CycleListFragment);
            }

            @Override
            public void onSucceed() {
                if (home_synLinearlayout != null) {
                    home_synLinearlayout.showSuccess();
                }
            }
        });
        home_synLinearlayout.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                homeTitleView.getTitle(TitleStates.TYPE_CYCLE, true);
                cycleProductList.clear();
                cycleProductQList.clear();
                if (category != null && !"".equals(category)) {
                    synLinearLayout.showLoad();
                    synLinearLayoutQ.showLoad();
                    presenter.getCycleProductList(cycleProductList, category);
                    presenter.getCycleProductQList(cycleProductQList, category);
                }
            }
        });
        synLinearLayout.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                synLinearLayout.showLoad();
                presenter.getCycleProductList(cycleProductList, category);
            }
        });
        synLinearLayoutQ.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                synLinearLayoutQ.showLoad();
                presenter.getCycleProductQList(cycleProductQList, category);
            }
        });

        return view;
    }

    @Subscribe
    public void onSetChildEvent(CycleEventBus cycleEventBus) {
//        /**
//         * 接收点击title某一个条目的categoriesId
//         */
//        Toast.makeText(getContext(), "接受title的ID", Toast.LENGTH_SHORT).show();
    }

    /**
     * 参数错误处理
     *
     * @param msg
     */
    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.CycleListFragment))) {
//            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CycleListFragment);
            home_synLinearlayout.showError();
        } else if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.cycleFragment_final.equals(msg.getMsg())) {
            /**
             * 周期购错误处理 重新请求数据
             *
             */
            home_synLinearlayout.showError();
//            ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CycleListFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.unBind();
        }
    }
}
