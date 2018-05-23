package com.zyf.bings.bingos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangshiqi on 2017/9/13.
 */

public class EntracenActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout entranceGoods, entranceManager;
    private TextView entranceTime;
    private ImageView order;
    private Date curDate;
    private TextView month;
    private TextView day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        month = (TextView) findViewById(R.id.entrance_month);
        day = (TextView) findViewById(R.id.entrance_day);
        entranceTime = (TextView) findViewById(R.id.entrance_time);
        entranceGoods = (RelativeLayout) findViewById(R.id.entrance_goods);
        order = (ImageView) findViewById(R.id.entrance_order);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        //获取当前时间
        curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        entranceTime.setText(time);
        entranceManager = (RelativeLayout) findViewById(R.id.entrance_manager);
        initListener();
        initDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initDate() {
        Calendar c = Calendar.getInstance();
        int monthNum = c.get(Calendar.MONTH);
        int dayNum = c.get(Calendar.DAY_OF_MONTH);
        month.setText(monthNum + 1 + "");
        day.setText(dayNum + "");
    }

    private void initListener() {
        entranceGoods.setOnClickListener(this);
        entranceManager.setOnClickListener(this);
        order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.entrance_goods:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("manager", "mall");
                startActivity(intent);
                break;
            case R.id.entrance_manager:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("manager", "manager");
                startActivity(intent);
                break;
            case R.id.entrance_order:
//                ToastUtils.showToast("执行");
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("manager", "order");
                startActivity(intent);
                break;
        }
    }
}
