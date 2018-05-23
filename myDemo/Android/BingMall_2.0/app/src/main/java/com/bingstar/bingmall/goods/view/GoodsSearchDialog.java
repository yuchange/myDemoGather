package com.bingstar.bingmall.goods.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.transition.Transition;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.goods.GoodsListFragment;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qianhechen on 17/2/15.
 */

public class GoodsSearchDialog extends DialogFragment {

    private static final String SEARCH_KEY = "seach_key";
    private static final String KEY_WORD = "key_word";
    private static final String KEY_SIZE = "key_size";
    private static final String IS_FROM = "is_from";
    private static final String CATEGORYID = "categoryid";

    private String key_word;
    private EditText search_et;
    private FlowLayout flowLayout;
    private TextView seach_tv;
    static final String TAG = "GoodsSearchDialog";
    private List<String> historyList;
    private String isFrom, categoryId;


    public static void start(FragmentManager fragmentManager, String categoryId, int isFrom) {

        GoodsSearchDialog fragment = (GoodsSearchDialog) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new GoodsSearchDialog();
        }
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORYID, categoryId);
        bundle.putString(IS_FROM, String.valueOf(isFrom));
        fragment.setArguments(bundle);
        fragment.show(fragmentManager, TAG);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.goods_search_fragment, container, false);
        flowLayout = (FlowLayout) view.findViewById(R.id.goods_search_flow);
        seach_tv = (TextView) view.findViewById(R.id.goods_search_clear);
        historyList = new ArrayList<>();
        isFrom = getArguments().getString(IS_FROM);
        categoryId = getArguments().getString(CATEGORYID);
        final SharedPreferences preferences = getActivity().getSharedPreferences(SEARCH_KEY, Context.MODE_PRIVATE);
        int size = preferences.getInt(KEY_SIZE, 0);
        size += 1;
        historyList.clear();
        for (int i = 0; i < size; i++) {
            historyList.add(preferences.getString(KEY_WORD + i, ""));
            if (historyList.get(i).equals("")) {
                historyList.remove(i);
            }
        }
        removeAllList(historyList);
        seach_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowLayout.removeAllViews();
                historyList.clear();
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
            }
        });
        LogUtils.Debug_E(GoodsSearchDialog.class, preferences.getString(KEY_WORD, ""));
        if (historyList.size() != 0) {
            for (int i = 0; i < historyList.size(); i++) {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 30));
                lp.setMargins(10, 10, 10, 10);
                TextView tv = new TextView(getContext());
                tv.setPadding(15, 0, 15, 0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(getResources().getColor(R.color.black));
                tv.setLines(1);
                tv.setBackground(getResources().getDrawable(R.drawable.goods_search_history_bg));
                tv.setText(historyList.get(i));
                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search_et.setText(historyList.get(finalI));
                    }
                });
                flowLayout.addView(tv, lp);
            }
        }
        search_et = (EditText) view.findViewById(R.id.goods_search_et);
        ImageView search_iv = (ImageView) view.findViewById(R.id.gooods_search_iv);
        search_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key_word = search_et.getText().toString();
                LogUtils.Debug_E(GoodsSearchDialog.class, key_word);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SEARCH_KEY, Context.MODE_APPEND);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_SIZE, historyList.size());
                if (key_word == null || key_word.trim().equals("")) {
                    dismiss();
                    return;
                }
                key_word = key_word.trim();
                if (historyList.size() == 0) {
                    editor.putString(KEY_WORD + historyList.size(), key_word);
                } else {
                    for (int i = 0; i < historyList.size(); i++) {
                        editor.putString(KEY_WORD + i, historyList.get(i));
                    }
                    editor.putString(KEY_WORD + historyList.size(), key_word);
                }

                editor.apply();
                EventBus.getDefault().postSticky(new EventMsg(GoodsSearchDialog.class, key_word, isFrom));
                dismiss();
            }
        });
        return view;
    }

    public List removeAllList(List<String> list) {
        for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
        {
            for (int j = list.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
            {
                if (list.get(i).equals(list.get(j))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
