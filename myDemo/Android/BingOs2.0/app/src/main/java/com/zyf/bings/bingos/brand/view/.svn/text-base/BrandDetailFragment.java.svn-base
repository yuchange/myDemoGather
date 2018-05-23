package com.zyf.bings.bingos.brand.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.brand.adapter.BrandDetailAdapter;
import com.zyf.bings.bingos.brand.presenter.BrandDetailPresenter;
import com.zyf.bings.bingos.brand.adapter.GoodsItemDecoration;
import com.zyf.bings.bingos.brand.presenter.IBrandDetailContract;
import com.zyf.bings.bingos.brand.bean.BrandDetailBean;
import com.zyf.bings.bingos.event.SetMainTitleEvent;
import com.zyf.bings.bingos.goods.GoodsDetailFragment;
import com.zyf.bings.bingos.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangshiqi on 2017/9/11.
 */

public class BrandDetailFragment extends BaseFragment implements IBrandDetailContract.BrandDetailListView {

    private String brandid;
    private String logoUrl;
    private ImageView logoImg;
    private RecyclerView detailRv;
    private IBrandDetailContract.BrandPresenter presenter;
    private int currentPage = 0;
    private BrandDetailAdapter listRvAdapter;
    private List<BrandDetailBean.ProductInfoListBean> listBeen = new ArrayList<>();
    private RefreshLayout refreshLayout;

    @Override
    public void setPresenter() {
        presenter = new BrandDetailPresenter(this);
    }

    public static void start(int resId, String brandId, String logoUrl, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "BrandDetailFragment";
        BrandDetailFragment fragment = (BrandDetailFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new BrandDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("brand_id", brandId);
            bundle.putString("logoUrl", logoUrl);
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
                if (!(frag instanceof BrandDetailFragment) && frag != null) {
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
        View view = inflater.inflate(R.layout.fragment_brand_detail, container, false);
        initView(view);
        setPresenter();
        return view;
    }

    private void initView(View view) {
//        logoImg = (ImageView) view.findViewById(R.id.detail_logo);
        detailRv = (RecyclerView) view.findViewById(R.id.detail_rv);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.detail_refresh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        brandid = bundle.getString("brand_id");
        logoUrl = bundle.getString("logoUrl");
        presenter.getBrandList("12", "0", brandid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        detailRv.setLayoutManager(gridLayoutManager);
        listRvAdapter = new BrandDetailAdapter(R.layout.item_brand_goods);
        View viewHead = LayoutInflater.from(getContext()).inflate(R.layout.detail_head, null);
        listRvAdapter.addHeaderView(viewHead);
        ImageLoader.load((ImageView) viewHead.findViewById(R.id.detail_logo), logoUrl);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
//        stringIntegerHashMap.put(GoodsItemDecoration.TOP_DECORATION,50);//top间距

        stringIntegerHashMap.put(GoodsItemDecoration.BOTTOM_DECORATION,20);//底部间距

        stringIntegerHashMap.put(GoodsItemDecoration.LEFT_DECORATION,29);//左间距

//        stringIntegerHashMap.put(GoodsItemDecoration.RIGHT_DECORATION,100);//右间距

        detailRv.addItemDecoration(new GoodsItemDecoration(stringIntegerHashMap));
        detailRv.setAdapter(listRvAdapter);
        refreshLayout.setEnableRefresh(false);
        listRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager(), listRvAdapter.getItem(position).getBsjProductId());
            }
        });
    }

    @Override
    public void refreshAdapter(List<BrandDetailBean.ProductInfoListBean> beanList) {

//        listRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                BrandDetailFragment.start(R.id.fl_container, listBeen.get(position).getBrand_id(), listBeen.get(position).getBrand_url(), getActivity().getSupportFragmentManager());
//            }
//        });
        if (beanList != null) {
            listRvAdapter.addData(beanList);
            listBeen.addAll(beanList);
            if (beanList.size() == 12) {
                refreshLayout.setEnableLoadmore(true);
                refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                    @Override
                    public void onLoadmore(RefreshLayout refreshlayout) {
                        currentPage++;
                        presenter.getBrandList("12", String.valueOf(currentPage), brandid);
                        refreshlayout.finishLoadmore(2000);
                    }
                });
            } else {
                refreshLayout.setEnableLoadmore(false);
            }
        }

    }

    @Override
    public void listErr() {
        detailRv = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            EventBus.getDefault().post(new SetMainTitleEvent());
        }
    }


}
