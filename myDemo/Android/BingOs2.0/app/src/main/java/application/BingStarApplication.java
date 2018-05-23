package application;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.zyf.bings.bingos_libnet.interceptor.LoggingInterceptor;
import com.zyf.bings.bingos_libnet.utils.ApplicationHolder;
import com.zyf.bings.bingos_libnet.utils.L;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/9/1.
 */

public class BingStarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOkGo();
        initLog();
        ApplicationHolder.instance = this;

    }

    private void initLog() {
        L.init();
    }

    private void initOkGo() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        OkGo.getInstance().init(this)
                .setOkHttpClient(okHttpClient);

    }
}
