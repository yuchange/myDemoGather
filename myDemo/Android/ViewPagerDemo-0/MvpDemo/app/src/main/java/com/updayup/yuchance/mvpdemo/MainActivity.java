package com.updayup.yuchance.mvpdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.updayup.yuchance.mvpdemo.presenter.LoginPresenter;
import com.updayup.yuchance.mvpdemo.presenter.LoginView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoginView{

    private EditText accountEt;
    private EditText pswEt;
    private AppCompatButton loginBtn;
    private AppCompatButton clearBtn;
    private LoginPresenter loginPresenter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();



    }

    private void initListener() {
        loginBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
    }

    private void initView() {
        accountEt = findViewById(R.id.activity_main_account_et);
        pswEt = findViewById(R.id.activity_main_psw_et);
        loginBtn = findViewById(R.id.btn_login);
        clearBtn = findViewById(R.id.btn_clear);
        loginPresenter = new LoginPresenter(this,this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                loginPresenter.login(accountEt.getText().toString(),pswEt.getText().toString());
                break;
            case R.id.btn_clear:
                accountEt.setText("");
                pswEt.setText("");
                break;
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void success() {
        Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT);
    }

    @Override
    public void failed() {
        Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT);
    }
}
