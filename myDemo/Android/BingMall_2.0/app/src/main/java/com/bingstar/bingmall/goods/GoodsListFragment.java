package com.bingstar.bingmall.goods;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.RVScollListener;
import com.bingstar.bingmall.base.WrapGridLayoutManager;
import com.bingstar.bingmall.cart.CartFragment;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.goods.bean.GoodsEntity;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.goods.bean.OneKeyEvent;
import com.bingstar.bingmall.goods.bean.ProductInfoDetail;
import com.bingstar.bingmall.goods.view.GoodsSearchDialog;
import com.bingstar.bingmall.home.HomeTitleView;
import com.bingstar.bingmall.home.bean.TitleBean;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.statistics.StatClient;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.utils.LogUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/8 下午3:38
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/8 下午3:38
 * @modify by reason:{方法名}:{原因}
 */
public class GoodsListFragment extends BaseFragment implements IGoodsListContract.GoodsListView {

    private ArrayList<GoodsEntity> goodsEntities;

    private IGoodsListContract.GoodsListPresenter presenter;

    private static final String CATEGORY_ID = "categoryId";

    private static final String SEARCHNAME = "searchName";

    private GoodsListAdapter listAdapter;

    private int pageSize = 8;

    private int loadSize = 12;

    private int currentPage = 0;

    private String category;
    private String activeFlag;

    private boolean isSearch = false;//是否为搜索状态下分页展示

    private HomeTitleView homeTitleView;

    private String key_word;

    private SynLinearLayout goodlist_syn_linear, goodlist_syn_linear_title;


    private long lastTime = 0;


    final int TIME_SPACE = 3000;

    public boolean mScrolling = false;


    public static void start(int resId, String categoryId, String searchName, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "GoodsList";
        GoodsListFragment fragment = (GoodsListFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new GoodsListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            bundle.putString(CATEGORY_ID, categoryId);
            bundle.putString(SEARCHNAME, searchName);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof GoodsListFragment) && frag != null) {
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
        View view = inflater.inflate(R.layout.goodslist_fragment, container, false);
        EventBus.getDefault().register(this);
        category = getArguments().getString(CATEGORY_ID, "");
        key_word = getArguments().getString(SEARCHNAME, "");
        setPresenter();
        RecyclerView goodsListView = (RecyclerView) view.findViewById(R.id.goodslist_rv);
        goodlist_syn_linear = (SynLinearLayout) view.findViewById(R.id.goodlist_syn_linear);
        goodlist_syn_linear_title = (SynLinearLayout) view.findViewById(R.id.goodlist_syn_linear_title);
        WrapGridLayoutManager gridLayoutManager = new WrapGridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        goodsListView.setLayoutManager(gridLayoutManager);
        goodsEntities = new ArrayList<>();
        listAdapter = new GoodsListAdapter(goodsEntities, ErrorFragment.GoodsListFragment);
        goodsListView.setAdapter(listAdapter);
        goodsListView.addOnScrollListener(new RVScollListener(loadSize, 4) {
            @Override
            public void onBottom() {
                super.onBottom();
                if (listAdapter.getItemCount() % loadSize == 0) {
                    LogUtils.Debug_I(GoodsListFragment.class, "" + listAdapter.getItemCount());
                    currentPage++;
                    if (!isSearch) {
                        presenter.getGoodsList(goodsEntities, category, String.valueOf(currentPage), activeFlag, false);
                    } else {
                        presenter.seachGoods(goodsEntities, category, key_word, String.valueOf(currentPage));
                    }
                }
            }
        });

        goodsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mScrolling = false;
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        mScrolling = true;
                        break;
                }

            }
        });


        listAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (listAdapter.getList().size() > 0) {
                    GoodsDetailFragment.start(getChildFragmentManager(), listAdapter.getItem(position).getProductInfoDetail().getBsjProductId(), GoodsDetailFragment.GOODSLISTFRAGMENT);
                    //单品统计
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > TIME_SPACE) {
                        GoodsDetailFragment.setModelFrom("商品列表");
                        lastTime = System.currentTimeMillis();//单品点击统计
                        ProductInfoDetail productInfoDetail = listAdapter.getItem(position).getProductInfoDetail();
                        StatClient.upLoadClickCount(productInfoDetail.getBsjProductId(), productInfoDetail.getBsjProductName());
                    }
                }

            }
        });

        homeTitleView = (HomeTitleView) view.findViewById(R.id.home_title);
        homeTitleView.setOnItemClickListener(new HomeTitleView.OnTitleItemClickListener() {
            @Override
            public void onClick(TitleBean titleBean, int position) {

                goodsEntities.clear();
                isSearch = false;
                currentPage = 0;
                if ("全部".equals(titleBean.getCategoryName())) {
                    category = null;
                    activeFlag = null;
                } else {
                    category = titleBean.getCategoryId();
                    activeFlag = titleBean.getActiveKbn();
                }
                key_word = "";
                presenter.getGoodsList(goodsEntities, category, String.valueOf(currentPage), activeFlag, true);

            }
        });

        homeTitleView.addInternalTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (mScrolling) {
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        homeTitleView.setSearchOnClickListener(getChildFragmentManager(), ErrorFragment.GoodsListFragment);
        homeTitleView.setFrom(ErrorFragment.GoodsListFragment);
        homeTitleView.setErrorListener(new HomeTitleView.ViewErrorListener() {
            @Override
            public void onError() {
                goodlist_syn_linear_title.showError();
            }

            @Override
            public void onSucceed() {
                if (goodlist_syn_linear_title != null) {
                    goodlist_syn_linear_title.showSuccess();
                }
            }
        });


        goodlist_syn_linear.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                if (!key_word.equals("")) {
                    presenter.seachGoods(goodsEntities, "", key_word, "0");
                } else {
                    presenter.getGoodsList(goodsEntities, category, String.valueOf(currentPage), activeFlag, true);
                }
            }
        });
        goodlist_syn_linear_title.setReloadListener(new SynLinearLayout.OnReloadListener() {
            @Override
            public void reload() {
                if (category != null && !category.equals("")) {
                    homeTitleView.getTitle(true, category);
                } else if (category == null) {  //全部类目
                    homeTitleView.getTitle(true, "");
                } else if (!key_word.equals("")) {
                    presenter.seachGoods(goodsEntities, "", key_word, "0");
                    homeTitleView.getTitle(false);
                } else {
                    homeTitleView.getTitle(true, null);
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        currentPage = 0;
        if (category != null && !category.equals("")) {
            homeTitleView.getTitle(true, category);
        } else if (category == null) {  //全部类目
            homeTitleView.getTitle(true, "");
        } else if (!key_word.equals("")) {
            presenter.seachGoods(goodsEntities, "", key_word, "0");
            homeTitleView.getTitle(false);
        } else {
            homeTitleView.getTitle(true, null);
        }
    }

    @Override
    public void setPresenter() {
        presenter = new GoodsListPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.unBind();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void refreshAdapter() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void listErr() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.GoodsListFragment);
    }

    @Override
    public void showView(int kind) {
        switch (kind) {
            case SynLinearLayout.SHOW_SUCCESS:
                goodlist_syn_linear.showSuccess();
                break;
            case SynLinearLayout.SHOW_LOAD:
                goodlist_syn_linear.showLoad();
                break;
            case SynLinearLayout.SHOW_EMPTY:
                if (isSearch) {
                    goodlist_syn_linear.showEmpty(getResources().getString(R.string.no_search_good));
                } else {
                    goodlist_syn_linear.showEmpty(getResources().getString(R.string.no_goods));
                }
                break;
            case SynLinearLayout.SHOW_ERROR:
                goodlist_syn_linear.showError();
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(EventMsg msg) {
        String content = msg.getMsg();
        LogUtils.Debug_I(GoodsListFragment.class, "event:" + content);
        if (msg.getClassName().equals(GoodsSearchDialog.class)) {
            key_word = msg.getMsg();
            LogUtils.Debug_I(GoodsListFragment.class, "event" + category);
            currentPage = 0;
            isSearch = true;
            presenter.seachGoods(goodsEntities, category, key_word, String.valueOf(currentPage));
        }
        if (msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(String.valueOf(BingstarErrorParser.GoodsListFragment_final))) {
            goodlist_syn_linear.showError();
        }
        if (msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(BingstarErrorParser.GoodsDetailFragment_final)) {
            goodlist_syn_linear.showError();
        }
        if ((msg.getClassName().equals(GoodsDetailFragment.class)) && msg.getMsg().equals(GoodsDetailFragment.GOODSLISTFRAGMENT)) {
            goodlist_syn_linear.showError();
        }
        if (msg.getClassName().equals(HomeTitleView.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.GoodsListFragment))) {
            goodlist_syn_linear.showError();
        }
    }

}


