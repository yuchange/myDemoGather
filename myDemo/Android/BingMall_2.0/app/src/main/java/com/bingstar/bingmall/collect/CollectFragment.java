package com.bingstar.bingmall.collect;

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

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.cart.CartTitleAdapter;
import com.bingstar.bingmall.cart.bean.CartTitle;
import com.bingstar.bingmall.cart.view.CartItemDecoration;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.goods.GoodsListAdapter;
import com.bingstar.bingmall.goods.bean.GoodsEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：收藏（点赞）界面
 * Created by yaoyafeng on 17/3/23 下午2:46
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/23 下午2:46
 * @modify by reason:{方法名}:{原因}
 */

public class CollectFragment extends BaseFragment implements ICollectionContract.CollectionListView {

    private ArrayList<GoodsEntity> goodsEntities;

    private ICollectionContract.CollectionPresenter presenter;

    private String category;//类目（title）id

    private static final String CATEGORY_ID = "categoryId";

    private GoodsListAdapter listAdapter;
    private ArrayList<CartTitle.CartCategoryInfo> cartTitleList;

    private CartTitleAdapter cartTitleAdapter;
    private boolean defaultSelect = false;



    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "CollectFragment";
        CollectFragment fragment = (CollectFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new CollectFragment();
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
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof CollectFragment) && frag != null) {
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
        View view = inflater.inflate(R.layout.collect_fragment, container, false);
        setPresenter();
        cartTitleList = new ArrayList<>();

        RecyclerView title_rv = (RecyclerView) view.findViewById(R.id.goods_good_titlerv);
        RecyclerView bottom_rv = (RecyclerView) view.findViewById(R.id.goods_good_bottomrv);
        WarpLinearLayoutManager bottomRVManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        WarpLinearLayoutManager linearLayoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        title_rv.setLayoutManager(linearLayoutManager);
        title_rv.addItemDecoration(new CartItemDecoration(getContext(), CartItemDecoration.VERTICAL_LIST));
        bottom_rv.setLayoutManager(bottomRVManager);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        goodsEntities = new ArrayList<>();
        cartTitleAdapter = new CartTitleAdapter(cartTitleList);
        listAdapter = new GoodsListAdapter(goodsEntities, ErrorFragment.CollectFragment);
        title_rv.setAdapter(cartTitleAdapter);
        bottom_rv.setAdapter(listAdapter);

        listAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GoodsDetailFragment.setModelFrom("收藏（点赞）界面");
                GoodsDetailFragment.start(getChildFragmentManager(), goodsEntities.get(position).getProductInfoDetail().getBsjProductId(), GoodsDetailFragment.COLLECTFRAGMENT);

            }
        });

        cartTitleAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                category = cartTitleAdapter.getItem(position).getCategoryId();//获得titleＩＤ
                presenter.getGoodsList(goodsEntities, category);//通过titleID获取数据
            }
        });
        return view;
    }

    @Override
    public void setPresenter() {
        presenter = new CollectionPresenter(this);
    }

    @Override
    public void refreshAdapter(String categoryId) {
        if (TextUtils.isEmpty(category)) {
            listAdapter.notifyDataSetChanged();
        }
        if (!TextUtils.isEmpty(category)) {
            for (int i = 0; i < cartTitleList.size(); i++) {
                CartTitle.CartCategoryInfo categoryInfo = cartTitleList.get(i);
                if (categoryInfo.isSelected() && categoryInfo.getCategoryId().equals(categoryId)) {
                    listAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void notifyTitle() {
        cartTitleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getTitle(true);
    }

    public void getTitle(boolean defaultSelect) {
        this.defaultSelect = defaultSelect;
        presenter.getCollectTittle(cartTitleList, "1");
    }

    @Override
    public void notifyTitleRefresh() {
        if (cartTitleList.size() > 0) {
            cartTitleAdapter.notifyDataSetChanged();
            if (defaultSelect) {
                cartTitleList.get(0).setSelected(true);
                presenter.getGoodsList(goodsEntities, null);
                cartTitleAdapter.notifyItemChanged(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void listErr() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CollectFragment);
    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(GoodsDetailFragment.class) && msg.getMsg().equals(GoodsDetailFragment.COLLECTFRAGMENT)) {
            listErr();
        }

    }
}
