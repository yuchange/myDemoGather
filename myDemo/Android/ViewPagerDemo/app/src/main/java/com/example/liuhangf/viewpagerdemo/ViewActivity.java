package com.example.liuhangf.viewpagerdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhangf on 2017/9/18.
 */

public class ViewActivity extends AppCompatActivity implements FinishactivityInterface{

    private ArrayList<String> datas = new ArrayList<>();
    private ViewAdapter adapter;
    private ViewPager viewPager;
    private LinearLayout pointLl;
    private boolean isStart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        datas = bundle.getStringArrayList("list");
        int position = bundle.getInt("position");

        viewPager = (ViewPager) findViewById(R.id.activity_view_pager);
        pointLl = (LinearLayout) findViewById(R.id.discover_point);
        adapter = new ViewAdapter(ViewActivity.this);
        adapter.setDatas(datas);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        addPoint(datas.size());
        changePoint(datas.size());

    }

    //下面这里是添加那个小圆点的代码  你可以自己改动 我这个点有点小 你可以自己设置

    private void addPoint(int size) {
        for (int i = 0; i <size ; i++) {
            ImageView pointImg = new ImageView(this);
            pointImg.setPadding(5,5,5,5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25,25);
            pointImg.setLayoutParams(params);
            pointLl.addView(pointImg);

        }




    }
    private void changePoint(final int size) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                    for (int i = 0; i < size; i++) {
                        ImageView pointIv = (ImageView) pointLl.getChildAt(i);
                        pointIv.setImageResource(R.mipmap.lunpo_dangqian);
                    }
                    ImageView imageView = (ImageView) pointLl.getChildAt(position);
                    imageView.setImageResource(R.mipmap.lunpo);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }



    @Override
    public void setFinishactivityInterface() {
        finish();
    }
}
