package com.zyf.bings.bingos.brand.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.brand.presenter.BrandShopHotPresenter;
import com.zyf.bings.bingos.brand.adapter.HotListRvAdapter;
import com.zyf.bings.bingos.brand.presenter.IBrandShopListContract;
import com.zyf.bings.bingos.brand.bean.HotListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshiqi on 2017/9/7.
 */

public class BrandShopHotFragment extends BaseFragment implements IBrandShopListContract.BrandShopListView {

    private RecyclerView hotRv;

    private IBrandShopListContract.BrandPresenter presenter;
    private int currentPage = 0;
    private HotListRvAdapter listRvAdapter;
    private List<HotListBean.BrandListBean> listBeen = new ArrayList<>();
    private RefreshLayout refreshLayout;
    private String title;

    public static BrandShopHotFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        BrandShopHotFragment fragment = new BrandShopHotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hot, container, false);
        initView(v);
        setPresenter();
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        return v;
    }

    private void initView(View v) {
        hotRv = (RecyclerView) v.findViewById(R.id.hot_rv);
        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.hot_refresh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getBrandList("10", "0");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5, LinearLayoutManager.VERTICAL, false);
        hotRv.setLayoutManager(gridLayoutManager);
        listRvAdapter = new HotListRvAdapter(R.layout.item_brand_hot);
        hotRv.setAdapter(listRvAdapter);
        refreshLayout.setEnableRefresh(false);
        listRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BrandDetailFragment.start(R.id.fl_container, listBeen.get(position).getBrand_id(), listBeen.get(position).getBrand_url(), getActivity().getSupportFragmentManager());
            }
        });
    }


    @Override
    public void setPresenter() {
        presenter = new BrandShopHotPresenter(this);
    }

    @Override
    public void refreshAdapter(List<HotListBean.BrandListBean> beanList) {
        Log.d("BrandShopHotFragment", "brandList:" + beanList);
        if (beanList != null) {
            listRvAdapter.addData(beanList);
            listBeen.addAll(beanList);
            if (title.equals("hot")) {
                refreshLayout.setEnableLoadmore(false);
            } else {
                if (beanList.size() < 10) {
                    refreshLayout.setEnableLoadmore(false);
                } else {
                    refreshLayout.setEnableLoadmore(true);
                    refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                        @Override
                        public void onLoadmore(RefreshLayout refreshlayout) {
                            currentPage++;
                            presenter.getBrandList("10", String.valueOf(currentPage));
                            refreshlayout.finishLoadmore(2000);
                        }
                    });
                }

            }
        }
    }


}
