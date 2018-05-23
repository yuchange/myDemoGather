package com.updayup.yuchance.iconlistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TagFlowLayout flowLayout;
    private List<String> datas;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = findViewById(R.id.mainFl);
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("握手啊"+i);
            if (i%3-i==0) {
                datas.add("昨天做了一个梦");
            }
        }
        flowLayout.setAdapter(new TagAdapter<String>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                tv = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.tv,flowLayout,false);
                tv.setText(o);

                return tv;
            }
        });





    }
}
