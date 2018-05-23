package com.updayup.yuchance.indexdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.activity_main_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(linearLayoutManager);

        List<Bean.DataBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
           Bean.DataBean dataBean = new Bean.DataBean("我的",i,"ali");
           data.add(dataBean);
        }
        IndexAdapter adapter = new IndexAdapter(R.layout.item_index_recycler,data);

        recycler.setAdapter(adapter);




    }
}
