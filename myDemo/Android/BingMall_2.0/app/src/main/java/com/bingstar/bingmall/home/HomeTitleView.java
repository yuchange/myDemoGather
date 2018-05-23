package com.bingstar.bingmall.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.WarpLinearLayoutManager;
import com.bingstar.bingmall.goods.view.GoodsSearchDialog;
import com.bingstar.bingmall.home.bean.TitleBean;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/7 下午8:18
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/7 下午8:18
 * @modify by reason:{方法名}:{原因}
 */

public class HomeTitleView extends LinearLayout implements IHomeContract.HomeView {

    private ArrayList<TitleBean> categoryInfos;

    private HomeTitleAdapter titleAdapter;

    private IHomeContract.HomePresenter presenter;

    private OnTitleItemClickListener itemClickListener;
    private ViewErrorListener errorListener;
    private ImageView search_iv;

    private boolean defaultSelect = false;
    private String selectId;
    private Toast toast;
    private RecyclerView titleView;


    private View view;

    private int isFrom = 1;//标题页面标示


    private final WarpLinearLayoutManager mLayoutManager;

    public HomeTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = inflate(context, R.layout.home_title_layout, this);
        titleView = (RecyclerView) findViewById(R.id.home_title_nav);
        search_iv = (ImageView) findViewById(R.id.home_search);
        mLayoutManager = new WarpLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        titleView.setLayoutManager(mLayoutManager);
        categoryInfos = new ArrayList<>();
        titleAdapter = new HomeTitleAdapter(categoryInfos);
        titleView.setAdapter(titleAdapter);
        setPresenter();
    }

    public void getTitle() {
        getTitle(true);
    }

    public void setFrom(int from) {
        isFrom = from;
    }

    public void addInternalTouchListener(RecyclerView.OnItemTouchListener onItemTouchListener) {
        titleView.addOnItemTouchListener(onItemTouchListener);
    }

    public void setSearchOnClickListener(final FragmentManager fragmentManager, final int isFrom) {
        search_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsSearchDialog.start(fragmentManager, getDefaultCategoryId(), isFrom);
            }
        });

    }

    public void getTitle(boolean defaultSelect) {
        this.defaultSelect = defaultSelect;
        presenter.getTitleList(categoryInfos);
    }

    public void getTitle(boolean defaultSelect, String selectId) {
        this.defaultSelect = defaultSelect;
        this.selectId = selectId;
        presenter.getTitleList(categoryInfos);
    }


    public HomeTitleAdapter getTitleAdapter() {
        return titleAdapter;
    }

    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }


    public void setOnItemClickListener(OnTitleItemClickListener listener) {
        if (listener == null) {
            return;
        }
        itemClickListener = listener;
        titleAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemClickListener.onClick(titleAdapter.getItem(position), position);
            }
        });
    }

    @Override
    public void notifyTitleRefresh() {
        if (categoryInfos.size() > 0) {
            if (errorListener != null) {
                errorListener.onSucceed();
            }
            titleAdapter.notifyDataSetChanged();
            if (defaultSelect) {
                if (selectId != null) {
                    for (int i = 0, size = categoryInfos.size(); i < size; i++) {
                        if (categoryInfos.get(i).getCategoryId().equals(selectId)) {
                            categoryInfos.get(i).setSelected(true);
                            titleAdapter.notifyItemChanged(i);
                            mLayoutManager.scrollToPosition(i);
                            //titleView.smoothScrollToPosition(i);
                            if (itemClickListener != null) {
                                itemClickListener.onClick(categoryInfos.get(i), i);
                            }
                        }
                    }
                } else {
                    categoryInfos.get(0).setSelected(true);
                    titleAdapter.notifyItemChanged(0);
                    if (itemClickListener != null) {
                        itemClickListener.onClick(categoryInfos.get(0), 0);
                    }
                }
            }
        }
    }

    @Override
    public void listErr() {
        showToast(R.string.navigation_error);
        if (errorListener != null) {
            errorListener.onError();
        }
    }

    @Override
    public void setPresenter() {
        presenter = new HomePresenter(this);
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    @Override
    public void showToast(@StringRes int strId) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }
//
//    @Override
//    public void onViewError() {
//
//    }

    public void clearState() {
        if (categoryInfos != null) {
            for (TitleBean titleBean : categoryInfos) {
                titleBean.setSelected(false);
            }
        }
    }

    public String getDefaultCategoryId() {
        return titleAdapter.getDefaultCategoryId();
    }

    public void setTitleIcon(Drawable drawable) {
        ImageView imageView = (ImageView) findViewById(R.id.home_title_icon);
        imageView.setImageDrawable(drawable);
    }

    public interface OnTitleItemClickListener {
        void onClick(TitleBean titleBean, int position);
    }

    public void setViewBack(int color) {
        view.setBackgroundResource(color);
    }

    public void getTitle(int type, boolean defaultSelect) {
        this.defaultSelect = defaultSelect;
        presenter.getTitleList(categoryInfos, type);
    }

    public void getTitle(int type) {
        getTitle(type, true);
    }

    public void hiddenSearchBar(boolean isHidden) {
        if (isHidden) {
            search_iv.setVisibility(GONE);
        }
    }

    private void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setErrorListener(ViewErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public interface ViewErrorListener {
        void onError();

        void onSucceed();
    }
}
