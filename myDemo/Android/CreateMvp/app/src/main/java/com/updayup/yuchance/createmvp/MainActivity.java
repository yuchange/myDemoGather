package com.updayup.yuchance.createmvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.updayup.yuchance.createmvp.Model.LoginBean;
import com.updayup.yuchance.createmvp.contract.NewContact;
import com.updayup.yuchance.createmvp.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements NewContact.View{

    private NewContact.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv_main);
        new LoginPresenter(this);
        presenter.start();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });

    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "正在加载", Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissLoading() {
        Toast.makeText(this, "加载完成", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserInfo(LoginBean userInfoModel) {
       String token = userInfoModel.getBody().getAccess_token();
       Toast.makeText(this,token,Toast.LENGTH_SHORT).show();


    }

    @Override
    public String loadUserId() {
        return "1000";
    }

    @Override
    public void setPresenter(NewContact.Presenter presenter) {
        this.presenter = presenter;
    }
}
