package com.zyf.bings.bingos_libnet.interceptor;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.zyf.bings.bingos_libnet.BuildConfig;
import com.zyf.bings.bingos_libnet.utils.EncryptUtils;
import com.zyf.bings.bingos_libnet.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by zhangyifei on 17/9/1.
 */

public class RequestInterceptor implements Interceptor {

    private static final String TAG = "RxResponTransform";


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Response response = null;
        try {
            Request newRequest = createNewRequest(original);
            response = chain.proceed(newRequest);
        } catch (SocketTimeoutException e) {
            Log.e(TAG, "intercept: " + e.toString());
            return createErrorRespon(original);
        }
        return createNewRespon(original, response.body().string());
    }

    private Response createNewRespon(Request request, String body) {
        MediaType mediaType = MediaType.parse("application/json");
        ResponseBody responseBody = ResponseBody.create(mediaType, body);
        return new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .body(responseBody)
                .build();
    }


    private Response createErrorRespon(Request request) {
        Response response = null;
        String method = request.method();
        if ("POST".equals(method)) {
            response = doPostErrorResponse(request);
        } else if ("GET".equals(method)) {
            response = doGetErrorResponse(request);
        }
        return response;
    }

    private Response doGetErrorResponse(Request request) {
        URL url = request.url().url();
        return new Response.Builder()
                .code(408)
                .message("网络请求超时" + "action==>")
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .build();
    }

    private Response doPostErrorResponse(Request request) {
        FormBody formBody = (FormBody) request.body();
        String action = "";
        for (int i = 0; i < formBody.size(); i++) {
            if (formBody.name(i).equals("method")) {
                action = formBody.value(i);
            }
        }
        JSONObject error = new JSONObject();
        try {
            error.put("code", "408");
            error.put("msg", "网络请求超时" + "action==>" + action);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        ResponseBody responseBody = ResponseBody.create(formBody.contentType(), error.toString());
        return new Response.Builder()
                .code(408)
                .message("网络请求超时" + "action==>" + action)
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .body(responseBody)
                .build();
    }

    private Request createNewRequest(Request oldRequest) {
        Request newRequest = null;
        String method = oldRequest.method();
        if ("POST".equals(method)) {
            newRequest = doPost(oldRequest);
        } else if ("GET".equals(method)) {
            newRequest = doGet(oldRequest);
        }
        if (BuildConfig.DEBUG)
            L.t(TAG).d(String.format("\nrequest===> %s\n%s", newRequest, newRequest.headers()));

        return newRequest;
    }

    private Request doGet(Request oldRequest) {
        String totalUrl = "";
        String url = oldRequest.url().toString();
        String a = url.substring(url.indexOf("?") + 1, url.length());
        String[] split = a.split("&");
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < split.length; i++) {
            String str = split[i];
            String[] split1 = str.split("=");
            if ("requestBizData".equals(split1[0])) {
                String s = split1[1];
                s = s.replace("%22", "\"");
                L.t(TAG).d("doGet: requestBizData 替换后  " + s);
                params.put(split1[0], s);
            } else {
                params.put(split1[0], split1[1]);
            }
            L.t(TAG).d("doGet: 添加前  " + params.toString());
        }
        String timestamp = getTimestamp();
        params.put("timestamp", timestamp);
        String sign = getSign(params);
        params.put("sign", sign);

        L.t(TAG).d("doGet: 添加后 " + params.toString());

        for (Map.Entry<String, String> entry : params.entrySet()) {
            totalUrl += entry.getKey() + "=" + entry.getValue() + "&";
        }
        totalUrl = totalUrl.substring(0, totalUrl.length() - 1);

        totalUrl = url.substring(0, url.indexOf("?") + 1) + totalUrl;
        try {
            String encode = URLEncoder.encode(totalUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        L.t(TAG).d("doGet: 总的URL " + totalUrl);
        Request newRequest = oldRequest.newBuilder()
                .url(totalUrl)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        return newRequest;
    }


    private Request doPost(Request oldRequest) {
        FormBody oldFormBody = (FormBody) oldRequest.body();

        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < oldFormBody.size(); i++) {
            params.put(oldFormBody.name(i), oldFormBody.value(i));
        }
        String timestamp = getTimestamp();
        params.put("timestamp", timestamp);
        params.put("sign", getSign(params));

        if (BuildConfig.DEBUG) {
            L.t(TAG).d("========= Post请求参数Start=====================");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                L.t(TAG).d(entry.getKey() + "  : " + entry.getValue());
            }
            L.t(TAG).d("========= Post请求参数END=====================");
        }
        String content = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            content += entry.getKey() + "=" + entry.getValue() + "&";
        }
        content = content.substring(0, content.length() - 1);
        if (BuildConfig.DEBUG)
            L.t(TAG).d("构造post参数===>>  " + content);

        RequestBody body = RequestBody.create(oldFormBody.contentType(), content);
        Request newRequest = new Request.Builder()
                .url(oldRequest.url().toString())
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        return newRequest;
    }

    private String getSign(Map<String, String> params) {
        String sign = "";
        String timestamp = params.get("timestamp");
        String customeCode = params.get("customeCode");
        String bizSource = params.get("bizSource");
        String method = params.get("method");
        String requestBizData = "";
        if (!TextUtils.isEmpty(params.get("requestBizData"))) {
            requestBizData = params.get("requestBizData");
            sign = EncryptUtils.getMd5("customeCode:" + customeCode + ",bizSource:" + bizSource + ",timestamp:" + timestamp + ",method:" + method + ",requestBizData:" + requestBizData);
        } else {
            sign = EncryptUtils.getMd5("customeCode:" + customeCode + ",bizSource:" + bizSource + ",timestamp:" + timestamp + ",method:" + method);
        }
        return sign;
    }

    private String getTimestamp() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date curDate = new Date(System.currentTimeMillis());
        String date = sDateFormat.format(curDate);
        return date;
    }
}