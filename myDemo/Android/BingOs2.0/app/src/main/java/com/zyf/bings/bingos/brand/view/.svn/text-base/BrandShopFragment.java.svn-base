package com.zyf.bings.bingos.brand.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zyf.bings.bingos.R;

/**
 * Created by wangshiqi on 2017/9/7.
 */

public class BrandShopFragment extends Fragment {

    private RadioGroup brandRg;
    private RadioButton hotRb;
    private RadioButton allRb;
    private Drawable drawable;
    private Drawable drawableNull;

    public static BrandShopFragment newInstance() {

        Bundle args = new Bundle();
        BrandShopFragment fragment = new BrandShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brandshop, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        brandRg = (RadioGroup) view.findViewById(R.id.brand_shop_rg);
        hotRb = (RadioButton) view.findViewById(R.id.brand_shop_hot);
        allRb = (RadioButton) view.findViewById(R.id.brand_shop_all);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawable = getResources().getDrawable(R.mipmap.brandshop_left_title);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawableNull = getResources().getDrawable(R.mipmap.brandshop_left_title_null);
        drawableNull.setBounds(0,0,drawableNull.getIntrinsicWidth(),drawableNull.getIntrinsicHeight());
        brandRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                FragmentManager manager = getChildFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (i) {
                    case R.id.brand_shop_hot:
                        allRb.setCompoundDrawables(drawableNull, null, null, null);
                        hotRb.setCompoundDrawables(drawable, null, null, null);
                        transaction.replace(R.id.brand_shop_replaceview, BrandShopHotFragment.newInstance("hot"));
                        break;
                    case R.id.brand_shop_all:
                        hotRb.setCompoundDrawables(drawableNull, null, null, null);
                        allRb.setCompoundDrawables(drawable, null, null, null);
                        transaction.replace(R.id.brand_shop_replaceview, BrandShopHotFragment.newInstance("all"));
                        break;
                }
                transaction.commit();
            }
        });
        brandRg.check(R.id.brand_shop_hot);

    }
}
