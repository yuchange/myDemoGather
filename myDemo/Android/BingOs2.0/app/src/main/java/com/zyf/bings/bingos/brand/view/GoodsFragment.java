package com.zyf.bings.bingos.brand.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseRecycleAdapter;
import com.zyf.bings.bingos.base.RVScollListener;
import com.zyf.bings.bingos.brand.adapter.GoodsItemDecoration;
import com.zyf.bings.bingos.brand.adapter.GoodsListAdapter;
import com.zyf.bings.bingos.brand.bean.GoodsEntity;
import com.zyf.bings.bingos.brand.presenter.BrandGoodsListPresenter;
import com.zyf.bings.bingos.brand.presenter.IBrandGoodsListContract;
import com.zyf.bings.bingos.goods.GoodsDetailFragment;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.WrapGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangshiqi on 2017/9/8.
 */

public class
GoodsFragment extends Fragment implements IBrandGoodsListContract.GoodsListView {
    private ProgressFrameLayout mProgressFrameLayout;
    private RecyclerView goodsRv;
    private TextView goodsTitle;
    private IBrandGoodsListContract.GoodsListPresenter presenter;
    private ArrayList<GoodsEntity> goodsEntities;
    private static final String CATEGORY_ID = "categoryId";
    private boolean isSearch = false;//是否为搜索状态下分页展示
    private int pageSize = 8;

    private int loadSize = 12;

    private int currentPage = 0;

    private String category;
    private String activeFlag;

    private long lastTime = 0;


    final int TIME_SPACE = 3000;
    private GoodsListAdapter listRvAdapter;
    private String title;
    private LinearLayout mGoodsEmpty;

    public static GoodsFragment newInstance(String categoryId, String title) {

        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, categoryId);
        args.putString("title", title);
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.fragment_goods, container, false);
        initView(mProgressFrameLayout);
        setPresenter();
        category = getArguments().getString(CATEGORY_ID, "");
        title = getArguments().getString("title", "");
        return mProgressFrameLayout;
    }

    private void initView(View view) {
        goodsRv = (RecyclerView) view.findViewById(R.id.goods_rv);
        mGoodsEmpty = (LinearLayout) view.findViewById(R.id.goods_empty);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressFrameLayout.showLoading();
        WrapGridLayoutManager gridLayoutManager = new WrapGridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false) {

        };
        goodsRv.setLayoutManager(gridLayoutManager);
        goodsEntities = new ArrayList<>();
        presenter.getGoodsList(goodsEntities, category, String.valueOf(0), activeFlag, false);
        listRvAdapter = new GoodsListAdapter(goodsEntities, 1);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
//        stringIntegerHashMap.put(GoodsItemDecoration.TOP_DECORATION,50);//top间距

        stringIntegerHashMap.put(GoodsItemDecoration.BOTTOM_DECORATION,20);//底部间距

        stringIntegerHashMap.put(GoodsItemDecoration.LEFT_DECORATION,29);//左间距

//        stringIntegerHashMap.put(GoodsItemDecoration.RIGHT_DECORATION,100);//右间距

        goodsRv.addItemDecoration(new GoodsItemDecoration(stringIntegerHashMap));
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.goods_head, null);
        goodsTitle = (TextView) headView.findViewById(R.id.goods_title_tv);
        goodsTitle.setText(title);
        listRvAdapter.addHeaderView(headView);
        goodsRv.setAdapter(listRvAdapter);
        listRvAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                    GoodsDetailFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), goodsEntities.get(position - 1).getProductInfoDetail().getBsjProductId());
            }
        });
        goodsRv.addOnScrollListener(new RVScollListener(loadSize, 4) {
            @Override
            public void onBottom() {
                super.onBottom();
                if (listRvAdapter.getItemCount() % loadSize > 0) {
//                    LogUtils.Debug_I(GoodsListFragment.class, "" + listAdapter.getItemCount());
                    currentPage++;
                    if (!isSearch) {
//                        ToastUtils.showToast("加载");
                        presenter.getGoodsList(goodsEntities, category, String.valueOf(currentPage), activeFlag, false);
                    }
// else {
////                        presenter.seachGoods(goodsEntities, category, key_word, String.valueOf(currentPage));
//                    }
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
        currentPage = 0;
    }


    @Override
    public void setPresenter() {
        presenter = new BrandGoodsListPresenter(this);
    }

    @Override
    public void refreshAdapter() {
        mProgressFrameLayout.showContent();
        mGoodsEmpty.setVisibility(View.GONE);
        listRvAdapter.notifyDataSetChanged();
    }

    @Override
    public void listErr() {

//        mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "暂无商品", "点击重试", v -> {
//            mProgressFrameLayout.showLoading();
//        });
//        mProgressFrameLayout.showError(R.mipmap.syn_search_empty_icon, "暂无商品", "点击重试", "", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mProgressFrameLayout.showLoading();
//            }
//        });
    }

    @Override
    public void listEmpty() {
        mProgressFrameLayout.showContent();
        mGoodsEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showView(int kind) {

    }
}
