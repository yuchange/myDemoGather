package com.zyf.bings.bingos.order;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zyf.bings.bingos.R;

import java.util.List;

/**
 * Created by wangshiqi on 2017/9/21.
 */

public class OrderAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<TabItemInfo> mTabItems;

    public OrderAdapter(Context context, FragmentManager fm, List<TabItemInfo> tabItems) {
        super(fm);
        mContext = context;
        mTabItems = tabItems;
    }

    @Override
    public Fragment getItem(int position) {
            Fragment fragment = mTabItems.get(position).getFragmentClass();
            return fragment;
    }

    @Override
    public int getCount() {
        return mTabItems.size();
    }

    public View getTabView(int position) {
        TabItemInfo itemInfo = mTabItems.get(position);
        // 字体颜色在自定义布局中设置
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_tab_text, null);
        TextView tabTv = (TextView) view.findViewById(R.id.order_tab_tv);
        TextView tabNum = (TextView) view.findViewById(R.id.order_tab_num);
        tabTv.setText(itemInfo.getNameResource());
        if (position == 0) {
            tabNum.setVisibility(View.GONE);
        } else {
            if (itemInfo.getNumResource() == 0) {
                tabNum.setVisibility(View.GONE);
            } else {
                tabNum.setText(itemInfo.getNumResource() + "");
            }
        }
        return view;
    }
}
