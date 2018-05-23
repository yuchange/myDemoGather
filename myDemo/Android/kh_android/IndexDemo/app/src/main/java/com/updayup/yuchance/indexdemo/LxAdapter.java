package com.updayup.yuchance.indexdemo;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/4/4.
 */

public class LxAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    List<String> data = new ArrayList<>();

    public LxAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
     helper.setText(R.id.xxxx,item+"")  ;
        (helper.getView(R.id.xxxx)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "woshi" + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
//        int index = position - getHeaderLayoutCount();
//        convert(holder,data.get(index));
//    }
//
//    @Override
//    public int getItemCount() {
////        int i =  ? 1 : 0;
//        return data.size()  + getHeaderLayoutCount();
//    }
}
