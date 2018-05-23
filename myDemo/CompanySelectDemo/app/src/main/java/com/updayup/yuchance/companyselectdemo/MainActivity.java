package com.updayup.yuchance.companyselectdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private String editString;


    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            getSearchResult(editString);
        }
    };

    private void getSearchResult(String editString) {
        String token = ShareUtils.getString(MainActivity.this,"token","");
        OkHttpUtils.get().url("http://api.qianzhan.com/OpenPlatformService/CompanyInputTips")
                .addParams("token",token)
                .addParams("type","JSON")
                .addParams("input",editString)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        });




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText ed = findViewById(R.id.mainEt);
        RecyclerView recyclerView = findViewById(R.id.main_recycler);



        OkHttpUtils.get().url("http://api.qianzhan.com/OpenPlatformService/GetToken")
                .addParams("type","JSON").addParams("appkey","3d4f8662f53c95d9")
                .addParams("seckey","17691c25fad6ec1b")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(response);
                    CityTokenBean bean = gson.fromJson(response,CityTokenBean.class);
                    String token = bean.getResult().getToken();
                    ShareUtils.setString(MainActivity.this,"token",token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        ed.addTextChangedListener(new TextWatcher() {



            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(delayRun!=null){
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();

                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 800);

            }
        });


    }

}
