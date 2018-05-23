package com.updayup.yuchance.textisdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.updayup.yuchance.textisdemo.cardview.CardView;

import java.security.spec.EllipticCurve;

import static java.lang.StrictMath.log;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private CardView cardView;
    String x  ="123";
    String y = "rong";
    private int z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.maintv);
        Button zkBtn = findViewById(R.id.main_zk);
        Button zxBtn = findViewById(R.id.main_sx);
        Button jzBtn = findViewById(R.id.main_jz);
        Button szBtn = findViewById(R.id.main_sz);
        cardView = findViewById(R.id.card);
        zkBtn.setOnClickListener(this);
        zxBtn.setOnClickListener(this);
        jzBtn.setOnClickListener(this);
        szBtn.setOnClickListener(this);
        cardView.changeTheme(0xff91bbeb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_zk:
                textView.setMaxLines(Integer.MAX_VALUE);
                textView.setEllipsize(null);
                break;
            case R.id.main_sx:
                textView.setMaxLines(5);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                break;
            case R.id.main_jz:
                textView.setText(Integer.valueOf(x)+"");
                break;
            case R.id.main_sz:
                try{
                    z = Integer.parseInt(y);
                }catch (NumberFormatException x){
                    Log.d("MainActivity", x.getMessage());
                }finally {
                    textView.setText(z+"");
                    break;
                }



        }
    }








}
