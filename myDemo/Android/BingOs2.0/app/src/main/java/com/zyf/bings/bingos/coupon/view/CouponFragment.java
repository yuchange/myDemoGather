package com.zyf.bings.bingos.coupon.view;

import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.coupon.CouponItemDecoration;
import com.zyf.bings.bingos.coupon.adapter.CouponListAdapter;
import com.zyf.bings.bingos.coupon.bean.CouponCountBean;
import com.zyf.bings.bingos.coupon.bean.CouponListBean;
import com.zyf.bings.bingos.coupon.presenter.CouponListPresenter;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * @author wangshiqi
 * @date 2017/10/25
 */

public class CouponFragment extends BaseFragment implements View.OnClickListener, ICouponContract.CouponListView {

    private RelativeLayout mRlCouponNotUse, mRlCouponWillExpired, mRlCouponHaveFailed, mRlCouponUsed;
    private TextView mTvNotUse, mTvWillExpired, mTvHaveFailed, mTvUsed;
    private TextView mTvNotUseNum;
    private TextView mTvWillExpireNum;
    private TextView mTvHaveFailedNum;
    private TextView mTvUsedNum;
    private Drawable mDrawable;
    private Drawable mDrawableNull;
    private RecyclerView mRvCoupon;
    private CouponListAdapter mCouponListAdapter;
    private ICouponContract.CouponPresenter mCouponListPresenter;
    private static final String NOT_USE = "01";
    private static final String HAVE_FAILED = "03";
    private static final String WILL_EXPIRED = "04";
    private static final String USED = "02";
    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "CouponFragment";
        CouponFragment fragment = (CouponFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof CouponFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        if (fragment == null) {
            fragment = new CouponFragment();
            transaction.add(resId, fragment, TAG);
            transaction.addToBackStack(null);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                postTitle();
                return;
            }
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
        postTitle();
    }

    /**
     * 修改主页面标题
     */
    public static void postTitle() {
        EventBus.getDefault().postSticky(new MainTitleEvent("优惠券"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        setPresenter();
        mRlCouponNotUse = (RelativeLayout) view.findViewById(R.id.coupon_notuse);
        mRlCouponWillExpired = (RelativeLayout) view.findViewById(R.id.coupon_will_expire);
        mRlCouponHaveFailed = (RelativeLayout) view.findViewById(R.id.coupon_have_failed);
        mRlCouponUsed = (RelativeLayout) view.findViewById(R.id.coupon_used);
        mTvNotUse = (TextView) view.findViewById(R.id.tv_coupon_notuse);
        mTvHaveFailed = (TextView) view.findViewById(R.id.tv_coupon_have_failed);
        mTvWillExpired = (TextView) view.findViewById(R.id.tv_coupon_will_expire);
        mTvUsed = (TextView) view.findViewById(R.id.tv_coupon_used_txt);
        mTvNotUseNum = (TextView) view.findViewById(R.id.tv_notuse_num);
        mTvWillExpireNum = (TextView) view.findViewById(R.id.tv_will_expire_num);
        mTvHaveFailedNum = (TextView) view.findViewById(R.id.tv_have_failed_num);
        mTvUsedNum = (TextView) view.findViewById(R.id.tv_used_num);
        mRvCoupon = (RecyclerView) view.findViewById(R.id.rv_coupon);
        mRlCouponWillExpired.setOnClickListener(this);
        mRlCouponHaveFailed.setOnClickListener(this);
        mRlCouponUsed.setOnClickListener(this);
        mRlCouponNotUse.setOnClickListener(this);
        mDrawable = getResources().getDrawable(R.mipmap.coupong_left_title);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        mDrawableNull = getResources().getDrawable(R.mipmap.brandshop_left_title_null);
        mDrawableNull.setBounds(0, 0, mDrawableNull.getIntrinsicWidth(), mDrawableNull.getIntrinsicHeight());
        mTvNotUse.setCompoundDrawables(mDrawable, null, null, null);
        mTvWillExpired.setCompoundDrawables(mDrawableNull, null, null, null);
        mTvUsed.setCompoundDrawables(mDrawableNull, null, null, null);
        mTvHaveFailed.setCompoundDrawables(mDrawableNull, null, null, null);
        mTvNotUse.setTextColor(getResources().getColor(R.color.ff7300));
        mTvWillExpired.setTextColor(getResources().getColor(R.color.c_919191));
        mTvHaveFailed.setTextColor(getResources().getColor(R.color.c_919191));
        mTvUsed.setTextColor(getResources().getColor(R.color.c_919191));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mCouponListPresenter.getCouponTitleData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        mRvCoupon.setLayoutManager(gridLayoutManager);
        mCouponListPresenter.getCouponList(NOT_USE);
        mCouponListAdapter = new CouponListAdapter(R.layout.item_coupon,getActivity());
        mCouponListAdapter.setFlag("0");
        mRvCoupon.setAdapter(mCouponListAdapter);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
//        stringIntegerHashMap.put(CouponItemDecoration.TOP_DECORATION,50);//top间距

        stringIntegerHashMap.put(CouponItemDecoration.BOTTOM_DECORATION,30);//底部间距

//        stringIntegerHashMap.put(CouponItemDecoration.LEFT_DECORATION,30);//左间距

        stringIntegerHashMap.put(CouponItemDecoration.RIGHT_DECORATION,30);//右间距

        mRvCoupon.addItemDecoration(new CouponItemDecoration(stringIntegerHashMap));

        mCouponListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(position + "");
                    mCouponListPresenter.deleteCoupon(mCouponListAdapter.getItem(position).getCouponId(), position);
            }
        });
    }

    @Override
    public void setPresenter() {
        mCouponListPresenter = new CouponListPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coupon_notuse: // 未使用
                    mCouponListPresenter.getCouponList(NOT_USE);
                    mCouponListAdapter.setFlag("0");
                    mRvCoupon.setAdapter(mCouponListAdapter);
                    mTvNotUse.setCompoundDrawables(mDrawable, null, null, null);
                    mTvWillExpired.setCompoundDrawables(mDrawableNull, null, null, null);
                    mTvUsed.setCompoundDrawables(mDrawableNull, null, null, null);
                    mTvHaveFailed.setCompoundDrawables(mDrawableNull, null, null, null);
                    mTvNotUse.setTextColor(getResources().getColor(R.color.ff7300));
                    mTvWillExpired.setTextColor(getResources().getColor(R.color.c_919191));
                    mTvHaveFailed.setTextColor(getResources().getColor(R.color.c_919191));
                    mTvUsed.setTextColor(getResources().getColor(R.color.c_919191));
                break;
            case R.id.coupon_will_expire: //即将过期
                mCouponListAdapter.setFlag("1");
                mCouponListPresenter.getCouponList(WILL_EXPIRED);
                mRvCoupon.setAdapter(mCouponListAdapter);
                mTvWillExpired.setCompoundDrawables(mDrawable, null, null, null);
                mTvNotUse.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvUsed.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvHaveFailed.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvWillExpired.setTextColor(getResources().getColor(R.color.ff7300));
                mTvNotUse.setTextColor(getResources().getColor(R.color.c_919191));
                mTvHaveFailed.setTextColor(getResources().getColor(R.color.c_919191));
                mTvUsed.setTextColor(getResources().getColor(R.color.c_919191));
                break;
            case R.id.coupon_have_failed: // 已失效
                mCouponListPresenter.getCouponList(HAVE_FAILED);
                mCouponListAdapter.setFlag("2");
                mRvCoupon.setAdapter(mCouponListAdapter);
                mTvHaveFailed.setCompoundDrawables(mDrawable, null, null, null);
                mTvNotUse.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvUsed.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvWillExpired.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvHaveFailed.setTextColor(getResources().getColor(R.color.ff7300));
                mTvNotUse.setTextColor(getResources().getColor(R.color.c_919191));
                mTvWillExpired.setTextColor(getResources().getColor(R.color.c_919191));
                mTvUsed.setTextColor(getResources().getColor(R.color.c_919191));
                break;
            case R.id.coupon_used: // 已使用
                mCouponListAdapter.setFlag("3");
                mCouponListPresenter.getCouponList(USED);
                mRvCoupon.setAdapter(mCouponListAdapter);
                mTvUsed.setCompoundDrawables(mDrawable, null, null, null);
                mTvNotUse.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvWillExpired.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvHaveFailed.setCompoundDrawables(mDrawableNull, null, null, null);
                mTvUsed.setTextColor(getResources().getColor(R.color.ff7300));
                mTvNotUse.setTextColor(getResources().getColor(R.color.c_919191));
                mTvHaveFailed.setTextColor(getResources().getColor(R.color.c_919191));
                mTvWillExpired.setTextColor(getResources().getColor(R.color.c_919191));
                break;
            default:
                break;
        }
    }

    @Override
    public void notifyListData(List<CouponListBean.CouponInfoListBean> couponInfoListBeen) {
            mCouponListAdapter.setNewData(couponInfoListBeen);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mCouponListPresenter.getCouponTitleData();
//            mRlCouponNotUse.performClick();
            postTitle();
        }
    }

    @Override
    public void notifyListTitleData(CouponCountBean couponCountBean) {
        if (couponCountBean.getAlreadyUsed() == 0) {
            mTvUsedNum.setVisibility(View.GONE);
        } else {
            mTvUsedNum.setText(couponCountBean.getAlreadyUsed() + "");
        }
        if (couponCountBean.getExpiring() == 0) {
            mTvWillExpireNum.setVisibility(View.GONE);
        } else {

            mTvWillExpireNum.setText(couponCountBean.getExpiring()+ "");
        }
        if (couponCountBean.getExpired() == 0) {
            mTvHaveFailedNum.setVisibility(View.GONE);
        } else {
            mTvHaveFailedNum.setText(couponCountBean.getExpired() + "");
        }
        if (couponCountBean.getNoUsed() == 0) {
            mTvNotUseNum.setVisibility(View.GONE);
        } else {
            mTvNotUseNum.setText(couponCountBean.getNoUsed() + "");
        }
    }

    @Override
    public void deleteSuccess(int position) {
        mCouponListAdapter.remove(position);
        mCouponListAdapter.notifyDataSetChanged();
    }

    @Override
    public void listEmpty() {

    }
}
