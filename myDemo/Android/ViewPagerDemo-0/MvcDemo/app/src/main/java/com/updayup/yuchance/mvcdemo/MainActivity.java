package com.updayup.yuchance.mvcdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.updayup.yuchance.mvcdemo.model.LoginModel;
import com.updayup.yuchance.mvcdemo.model.LoginResultListener;
import com.updayup.yuchance.mvcdemo.model.logininterface;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText accountEt;
    private EditText pswEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn_login);
        accountEt = findViewById(R.id.activity_main_account_et);
        pswEt = findViewById(R.id.activity_main_psw_et);
        AppCompatButton clearBtn = findViewById(R.id.btn_clear);




        final LoginModel mLoginModel = new LoginModel();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String accountStr = accountEt.getText().toString();
                final String pswStr = pswEt.getText().toString();
                mLoginModel.logininterface(accountStr, pswStr, new LoginResultListener() {
                    @Override
                    public void successReslt() {
                        Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failedResult() {
                        Toast.makeText(MainActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountEt.setText("");
                pswEt.setText("");
            }
        });


    }

}
