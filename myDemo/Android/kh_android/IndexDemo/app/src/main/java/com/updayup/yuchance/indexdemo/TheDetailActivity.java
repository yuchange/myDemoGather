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
 * Created by yuchance on 2018/4/4.
 */

public class TheDetailActivity extends AppCompatActivity {

    RecyclerView photoRy;
    RecyclerView keyRy;
    TextView btn;
    private List<String> datax;
    private LxAdapter lxAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thedetail);
        photoRy = findViewById(R.id.recycler_photo);
        keyRy = findViewById(R.id.detail_lx_recycler);
        btn = findViewById(R.id.close_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datax.clear();
                for (int i = 0; i <4 ; i++) {
                    datax.add("没啦"+i);
                }
                lxAdapter.notifyDataSetChanged();
            }
        });
        List<String> data = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            data.add("x");
        }
        datax = new ArrayList<>();
        for (int i = 0; i <30 ; i++) {
            datax.add("wode"+i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        photoRy.setLayoutManager(manager);
        PhotoAdapter photoAdapter = new PhotoAdapter(R.layout.item,data);
        photoRy.setAdapter(photoAdapter);
        LinearLayoutManager manager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        keyRy.setLayoutManager(manager1);
        lxAdapter = new LxAdapter(R.layout.item_lx, datax);
        keyRy.setAdapter(lxAdapter);

    }
}
