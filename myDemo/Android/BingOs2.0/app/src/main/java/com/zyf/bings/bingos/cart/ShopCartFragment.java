package com.zyf.bings.bingos.cart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.AddressFragment;
import com.zyf.bings.bingos.brand.view.BrandFragment;
import com.zyf.bings.bingos.business.UserBusiness;
import com.zyf.bings.bingos.cart.bean.Coupon;
import com.zyf.bings.bingos.cart.bean.ShopCartGoodsBean;
import com.zyf.bings.bingos.event.ConfirmOrderEvent;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.goods.OrderConfirmFragment;
import com.zyf.bings.bingos.order.NotificationDialogFragment;
import com.zyf.bings.bingos.order.bean.ConfirmOrderBean;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zyf.bings.bingos.base.BaseFragment.KEY_RESID;

/**
 * Created by zhangyifei on 17/8/30.
 */

public class ShopCartFragment extends Fragment implements CartGoodsAdapter.OnNumberChange, CartGoodsAdapter.OnDelete,
        ICartContract.CartView {

    @BindView(R.id.shop_cart_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_shopcart_selectAll)
    TextView mTvShopcartSelectAll;
    @BindView(R.id.tv_shopcart_delete)
    TextView mTvDeleteAll;
    @BindView(R.id.tv_shopcart_confirm)
    TextView mTvShopcartConfirm;
    @BindView(R.id.rv_shopcart_goodslist)
    RecyclerView mRvShopcartGoodslist;
    @BindView(R.id.shop_cart_empty)
    LinearLayout cartEmpty;
    @BindView(R.id.shop_cart_go)
    TextView cartGo;
    ICartContract.CartPresenter mCartPresenter;

    public String mPageSize = "10";
    public int mCurrentIndex = 0;
    public String mCategory = "";

    ArrayList<ShopCartGoodsBean.ProductInfoListBean> mShopCartGoodsItems;
    @BindView(R.id.tv_shopcart_totalprice)
    TextView mTvTotalprice;
    @BindView(R.id.tv_shopcart_real_price)
    TextView mTvRealPrice;
    private CartGoodsAdapter mAdapter;
    private ProgressFrameLayout mProgressFrameLayout;
    private List<ConfirmOrderBean.ItemBean> mBeanList;
    private double mDouble;
    private int mSelectCount = 0;
    private int mOffShelfCount = 0;


    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "ShopCartFragment";
        ShopCartFragment fragment = (ShopCartFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();

        if (fragment == null) {
            fragment = new ShopCartFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            if (!fragment.isHidden()) {
                transaction.commit();
                EventBus.getDefault().postSticky(new MainTitleEvent("购物车"));
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof ShopCartFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }

        transaction.commit();
        EventBus.getDefault().postSticky(new MainTitleEvent("购物车"));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProgressFrameLayout = (ProgressFrameLayout) inflater.inflate(R.layout.shopcart_fragmnet, container, false);
        mShopCartGoodsItems = new ArrayList<>();
        ButterKnife.bind(this, mProgressFrameLayout);
        setPresenter();
        initView();
        initData();
        return mProgressFrameLayout;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("ShopCartFragment", "onHiddenChanged: " + "");
        if (!hidden) {
            EventBus.getDefault().postSticky(new MainTitleEvent("购物车"));
            mCurrentIndex = 0;
            mSelectCount = 0;
            mShopCartGoodsItems.clear();
            initView();
            initData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCartPresenter != null) {
            mCartPresenter.subscribe();
        }
    }

    private void initView() {
        mRefreshLayout.setEnableRefresh(false);
        mAdapter = new CartGoodsAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvShopcartGoodslist.setLayoutManager(layoutManager);
        mRvShopcartGoodslist.setAdapter(mAdapter);
        mTvShopcartSelectAll.setTag(2);
        mTvShopcartSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag = (int) v.getTag();
                if (tag == 1) {
                    for (int i = 0; i < mShopCartGoodsItems.size(); i++) {
                        ShopCartGoodsBean.ProductInfoListBean shopCartGoodsItem = mShopCartGoodsItems.get(i);
                        shopCartGoodsItem.setSelected(2);
                    }
                    mTvShopcartSelectAll.setText("全选");
                    mTvShopcartSelectAll.setTag(2);
                } else if (tag == 2) {
                    for (int i = 0; i < mShopCartGoodsItems.size(); i++) {
                        ShopCartGoodsBean.ProductInfoListBean shopCartGoodsItem = mShopCartGoodsItems.get(i);
                        Log.d("ShopCartFragment", "选中");
                        shopCartGoodsItem.setSelected(1);
                    }
                    mTvShopcartSelectAll.setText("取消全选");
                    mTvShopcartSelectAll.setTag(1);
                }

                mAdapter.notifyDataSetChanged();
                changePrice();

                Log.e("ShopCartFragment", "onClick: " + "删除事件....");
            }
        });

        mTvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = 0;
                for (int i = 0; i < mShopCartGoodsItems.size(); i++) {
                    ShopCartGoodsBean.ProductInfoListBean shopCartGoodsItem = mShopCartGoodsItems.get(i);
                    if (shopCartGoodsItem.getIsSelected() == 1) {
                        count++;
                    }
                }
                if (count == 0) {
                    ToastUtils.showToast("请选择要删除的商品");
                    return;
                }

                final DeleteDialog deleteDialog = DeleteDialog.newInstance();
                if (!deleteDialog.isAdded()) {
                    deleteDialog.show(getActivity().getSupportFragmentManager(), "Delete");
                    deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                        @Override
                        public void onDeleteItem() {
                            ArrayList<String> goodsId = new ArrayList<String>();
                            for (int i = 0, len = mShopCartGoodsItems.size(); i < len; i++) {
                                if (mShopCartGoodsItems.get(i).getIsSelected() == 1) {
                                    goodsId.add(mShopCartGoodsItems.get(i).getProductId());
                                }
                            }
                            mCartPresenter.batchDeleteCart(goodsId);

                        }
                    });
                }


            }
        });

        mAdapter.setOnDelete(this);
        mAdapter.setOnNumberChange(this);

    }


    private void initData() {
        mTvShopcartConfirm.setBackgroundResource(R.mipmap.shopcart_confirm_order_bgb);
        mTvShopcartSelectAll.setTag(2);
        mTvShopcartSelectAll.setText("全选");
        mTvTotalprice.setText("0.00");
        mTvRealPrice.setText("总额: " + getResources().getString(R.string.money) + "12000-" + getResources().getString(R.string.money) + "99999");
        mProgressFrameLayout.showLoading();
        mCartPresenter.getCartList(mCategory, mPageSize, String.valueOf(mCurrentIndex));
    }

    public int mDeleteItemPosition;

    @Override
    public void onDelete(final ShopCartGoodsBean.ProductInfoListBean cartBean, int position) {
        DeleteDialog deleteDialog = DeleteDialog.newInstance();
        if (!deleteDialog.isAdded()) {
            deleteDialog.show(getActivity().getSupportFragmentManager(), "Delete");
            deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                @Override
                public void onDeleteItem() {
                    mCartPresenter.deleteItemCart(cartBean);
                    mDeleteItemPosition = position;
                }
            });
        }

    }

    @Override
    public void onNumberChange() {
        changePrice();
    }

    private void changePrice() {
        ConfirmOrderBean confirmOrderBean = new ConfirmOrderBean();
        mBeanList = new ArrayList<>();
        int selectCount = 0;
        int offShelfCount = 0;
        for (int i = 0; i < mShopCartGoodsItems.size(); i++) {
            ShopCartGoodsBean.ProductInfoListBean shopCartGoodsItem = mShopCartGoodsItems.get(i);
            if (shopCartGoodsItem.getIsSelected() == 1) {
                ConfirmOrderBean.ItemBean itemBean = new ConfirmOrderBean.ItemBean();
                itemBean.setCount(Integer.parseInt(shopCartGoodsItem.getCount()));
                itemBean.setImgUrl(shopCartGoodsItem.getPicUrl());
                itemBean.setSpec(shopCartGoodsItem.getSpecification());
                itemBean.setTitle(shopCartGoodsItem.getProductName());
                itemBean.setProductId(shopCartGoodsItem.getProductId());
                itemBean.setProductCode(shopCartGoodsItem.getProductCode());
                itemBean.setPrice(shopCartGoodsItem.getPrice());
                itemBean.setGrossWeight(shopCartGoodsItem.getGross_Weight());
                itemBean.setSpecificationId(shopCartGoodsItem.getSpecificationId());
                mBeanList.add(itemBean);
                selectCount++;
                if (shopCartGoodsItem.getIf_show().equals("4") || shopCartGoodsItem.getScreenrePertory() == null || shopCartGoodsItem.getScreenrePertory().equals("0")) {
                    offShelfCount++;
                }
            }
        }
        confirmOrderBean.setItemBeanList(mBeanList);

        if (selectCount > 0) {
            mTvShopcartConfirm.setBackgroundResource(R.mipmap.shopcart_confirm_order_bgr);
        } else {
            mTvShopcartConfirm.setBackgroundResource(R.mipmap.shopcart_confirm_order_bgb);
        }

        if (selectCount != mShopCartGoodsItems.size()) {
            mTvShopcartSelectAll.setText("全选");
            mTvShopcartSelectAll.setTag(2);
        } else if (selectCount == mShopCartGoodsItems.size() && selectCount != 0) {
            mTvShopcartSelectAll.setText("取消全选");
            mTvShopcartSelectAll.setTag(1);
        }

        BigDecimal all = new BigDecimal("0");
        for (int i = 0; i < mShopCartGoodsItems.size(); i++) {
            ShopCartGoodsBean.ProductInfoListBean shopCartGoodsItem = mShopCartGoodsItems.get(i);
            if (shopCartGoodsItem.getIsSelected() == 1) {
                BigDecimal itemPrice = new BigDecimal(shopCartGoodsItem.getPrice());
                BigDecimal count = new BigDecimal(shopCartGoodsItem.getCount());
                BigDecimal price = itemPrice.multiply(count);
                all = all.add(price);
            }
        }


        mDouble = 0.00d;
        if (all.doubleValue() < mDouble) {
            all = new BigDecimal("0");

        }

        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format(all.doubleValue());
        mTvTotalprice.setText(format);
        BigDecimal finalAll = all;
        int finalOffShelfCount = offShelfCount;
        int finalSelectCount = selectCount;
        mTvShopcartConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double totalNum = Double.valueOf(mTvTotalprice.getText().toString());
                if (totalNum > mDouble) {
                    if (finalOffShelfCount > 0) {
                        NotificationDialogFragment notificationDialogFragment = NotificationDialogFragment.newInstance("请删除已下架商品");
                        notificationDialogFragment.show(getActivity().getSupportFragmentManager(), "shopCart");
                        return;
                    } else {
                        if (!UserBusiness.getUserAddress().equals("") && finalSelectCount > 0) {
                            OrderConfirmFragment.start(getFragmentManager(), R.id.fl_container, null, "", mBeanList, finalAll.doubleValue() + "", null, "00");
                            EventBus.getDefault().post(new ConfirmOrderEvent(mBeanList, finalAll.doubleValue() + ""));
                        } else {
                            ToastUtils.showToast("请填写收货地址");
                            AddressFragment.start(R.id.fl_container, getFragmentManager());
                        }
                    }
                } else {
                    return;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCartPresenter != null) {
            mCartPresenter.unsubscribe();
        }
    }


    @Override
    public void setPresenter() {
        mCartPresenter = new CartPresenter(this);
    }

    @Override
    public void notifyCartList(ShopCartGoodsBean shopCartGoods) {
        mProgressFrameLayout.showContent();
        if (!"0".equals(shopCartGoods.getTotalCount())) {
            cartEmpty.setVisibility(View.GONE);
            this.mShopCartGoodsItems.addAll(shopCartGoods.getProductInfoList());
            mAdapter.setShopCartGoodsItems(mShopCartGoodsItems);
            mAdapter.notifyDataSetChanged();
            if (shopCartGoods.getProductInfoList().size() == 10) {
                mRefreshLayout.setEnableLoadmore(true);
                mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                    @Override
                    public void onLoadmore(RefreshLayout refreshlayout) {
                        mCurrentIndex++;
                        mCartPresenter.getCartList(mCategory, mPageSize, String.valueOf(mCurrentIndex));
                        refreshlayout.finishLoadmore(2000);
                    }
                });
            } else {
                mRefreshLayout.setEnableLoadmore(false);

            }
        } else {
            mTvShopcartSelectAll.setTag(2);
            mTvShopcartSelectAll.setText("全选");
            mTvShopcartSelectAll.setClickable(false);
            cartEmpty.setVisibility(View.VISIBLE);
            cartGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BrandFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                }
            });
        }
    }

    @Override
    public void batchDeleteCartError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void batchDeleteCartSuccess() {
        for (int i = 0, len = mShopCartGoodsItems.size(); i < len; i++) {
            if (mShopCartGoodsItems.get(i).getIsSelected() == 1) {
                mShopCartGoodsItems.remove(i);
                mAdapter.notifyItemRemoved(i);
                len--;
                i--;
            }
        }
        changePrice();
        if (mShopCartGoodsItems.size() == 0) {
            cartEmpty.setVisibility(View.VISIBLE);
//            mTvShopcartSelectAll.setTag(2);
//            mTvShopcartSelectAll.setText("全选");
            cartGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BrandFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                }
            });
        }
    }

    @Override
    public void listErr(String errMsg) {
        mCurrentIndex = 0;
        mProgressFrameLayout.showError(R.mipmap.syn_error_icon, "加载失败...", errMsg, "点击重试", v -> {
            mProgressFrameLayout.showLoading();
            mCartPresenter.getCartList("", mPageSize, String.valueOf(mCurrentIndex));
        });
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void getCouponSuccess(Coupon coupon) {

    }

    @Override
    public void deleteItemCartError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void deleteItemCartSuccess() {
        mShopCartGoodsItems.remove(mDeleteItemPosition);
        mAdapter.notifyItemRemoved(mDeleteItemPosition);
        Log.e("ShopCartFragment", "onDeleteItem: " + "这里的数据");
        changePrice();
        if (mShopCartGoodsItems.size() == 0) {
            mTvShopcartSelectAll.setTag(2);
            mTvShopcartSelectAll.setText("全选");
            mTvShopcartSelectAll.setClickable(false);
            cartEmpty.setVisibility(View.VISIBLE);
            cartGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BrandFragment.start(R.id.fl_container, getActivity().getSupportFragmentManager());
                }
            });
        }
    }
}
