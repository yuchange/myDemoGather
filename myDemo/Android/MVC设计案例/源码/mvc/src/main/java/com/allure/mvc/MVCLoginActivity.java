package com.allure.mvc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.allure.mvc.model.UserLoginListener;
import com.allure.mvc.model.UserLoginModel;
import com.allure.mvc.model.UserLoginModelImpl;


public class MVCLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editAccount;
    private EditText editPassWord;
    private AppCompatButton btnLogin;
    private AppCompatButton btnClear;

    private ProgressDialog progressDialog;

    private UserLoginModel loginModel;

    private  String account="";
    private  String passWord="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {
        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    private void initView() {
        loginModel=new UserLoginModelImpl();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("登陆中,请稍后");
        progressDialog.setCancelable(false);


        editAccount= (EditText) findViewById(R.id.edit_account);
        editPassWord= (EditText) findViewById(R.id.edit_pwd);
        btnLogin= (AppCompatButton) findViewById(R.id.btn_login);
        btnClear= (AppCompatButton) findViewById(R.id.btn_clear);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                //这里是一个业务逻辑的控制
                account=editAccount.getText().toString();
                passWord=editPassWord.getText().toString();

                if(TextUtils.isEmpty(account)|| TextUtils.isEmpty(passWord)){
                    Toast.makeText(MVCLoginActivity.this,"请输入你的用户名和密码",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.show();
                    loginModel.login(account, passWord, new UserLoginListener() {
                        @Override
                        public void loginSuccess() {
                            progressDialog.dismiss();
                            Log.d("登陆成功","登陆成功");
                            Toast.makeText(MVCLoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void loginFailed() {
                            progressDialog.dismiss();
                            Log.d("登陆失败","登陆失败");
                            Toast.makeText(MVCLoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.btn_clear:
                editAccount.setText("");
                editPassWord.setText("");

                break;
        }

    }
}
