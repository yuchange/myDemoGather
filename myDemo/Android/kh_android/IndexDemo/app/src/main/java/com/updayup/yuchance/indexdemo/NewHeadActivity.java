package com.updayup.yuchance.indexdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/4/10.
 */

public class NewHeadActivity extends AppCompatActivity {

    private RecyclerView rv;
    LxAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_head);
        rv = findViewById(R.id.new_head_rv);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i <40 ; i++) {
            datas.add("我是"+i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        adapter = new LxAdapter(R.layout.item_lx,datas);
        rv.setAdapter(adapter);
        addHeader();
    }

    private void addHeader() {
        View view = getLayoutInflater().inflate(R.layout.view_head,null);
        RecyclerView photoRv = view.findViewById(R.id.recycler_photo);
        TextView headTv = view.findViewById(R.id.view_head_tv);
        List<String> phoList = new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            phoList.add("12"+i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        photoRv.setLayoutManager(manager);
        PhotoAdapter Xadapter = new PhotoAdapter(R.layout.item,phoList);
        photoRv.setAdapter(Xadapter);
        headTv.setText("nihao");
        adapter.addHeaderView(view);


    }


}
