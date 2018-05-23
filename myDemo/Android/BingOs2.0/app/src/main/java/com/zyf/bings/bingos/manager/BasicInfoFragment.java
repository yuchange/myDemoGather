package com.zyf.bings.bingos.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.AddressFragment;
import com.zyf.bings.bingos.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshiqi on 2017/9/4.
 */

public class BasicInfoFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView smallHeadRv;
    List<String> datas = new ArrayList<>();
    String[] strings = {"尼古拉斯赵四", "尼古拉斯赵三", "尼古拉斯赵二", "尼古拉斯赵一"};
    private SmallHeadRvAdapter smallHeadRvAdapter;
    private TextView addressTv;

    @Override
    public void setPresenter() {

    }

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "BasicInfoFragment";
        BasicInfoFragment fragment = (BasicInfoFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new BasicInfoFragment();
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
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof BasicInfoFragment) && frag != null) {
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
        View view = inflater.inflate(R.layout.fragment_basicinfo, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        smallHeadRv = (RecyclerView) view.findViewById(R.id.basic_info_small_rv);
        addressTv = (TextView) view.findViewById(R.id.basic_info_address);
        addressTv.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < 4; i++) {
            datas.add(strings[i]);
        }

        smallHeadRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        smallHeadRvAdapter = new SmallHeadRvAdapter(getContext());
        smallHeadRvAdapter.setDatas(datas);
        smallHeadRv.setAdapter(smallHeadRvAdapter);
        smallHeadRvAdapter.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.item_foot_smallhead, null));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basic_info_address:
                AddressFragment.start(R.id.fl_container, getFragmentManager());
                break;
        }
    }
}
