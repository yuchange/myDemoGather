package com.zyf.bings.bingos.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseActivity;
import com.zyf.bings.bingos.user.User;

/**
 * 功能：
 * Created by yaoyafeng on 17/5/3 下午1:55
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/3 下午1:55
 * @modify by reason:{方法名}:{原因}
 */

public class WebActivity extends BaseActivity {

    private final static String URL_KEY = "url_key";

    public static final String FLAG = "WebActivity";
    public static final String CUPONS_ID = "couponIds";
    public static final String CUPONS_METHOD = "coupon.receive";
    public static final String HOME_ID = "home_id";

    public static void start(Context context, String url, String cuponsId, String homeId) {
        if (url == null) {
            return;
        }
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(URL_KEY, url);
        intent.putExtra(CUPONS_ID, cuponsId);
        intent.putExtra(HOME_ID, homeId);
        Activity activity = (Activity) context;
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_web_activity);
        WebView webView = (WebView) findViewById(R.id.base_web);
        String url = getIntent().getStringExtra(URL_KEY);
        final String cuponsId = getIntent().getStringExtra(CUPONS_ID);
        final String homeId = getIntent().getStringExtra(HOME_ID);
        Log.e("WebActivity", url);

        ImageView base_web_close = (ImageView) findViewById(R.id.base_web_close);
        base_web_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebSettings settings = webView.getSettings();
        webView.setInitialScale(1);// webView网页缩放 参数是百分比
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(false);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //信任https
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受证书
                super.onReceivedSslError(view, handler, error);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("WebActivity", "URL" + url);
                final Uri uri = Uri.parse(url);
                Log.e("WebActivity", "Scheme" + uri.getAuthority());
                if (uri.getScheme().equals("bsj")) {
                    if (uri.getAuthority().equals("webView")) {
                        final String goodsId = uri.getQueryParameter("goodsId");
                    //  GoodsDetailFragment.start(getSupportFragmentManager(), goodsId, "");
                    } else if (uri.getAuthority().equals("oneKey")) {//一键购买页面
                        User.checkMemberId(WebActivity.this, new User.CheckMemberCallback() {
                            @Override
                            public void hasLogin() {
                                String goodsId = uri.getQueryParameter("goodsId");
                                String count = uri.getQueryParameter("count");
                                Log.e("WebActivity", "goodsId" + goodsId);
                                Log.e("WebActivity", "count" + count);
                            //    EventBus.getDefault().post(new OneKeyEvent(goodsId, count));
                                finish();
                            }
                        });


                    } else if (uri.getAuthority().equals("cupons")) {//领取优惠券

//                        User.checkMemberId(WebActivity.this, new User.CheckMemberCallback() {
//                            @Override
//                            public void hasLogin() {
//                                Log.e("WebActivity", "Author" + uri.getAuthority());
//
//                                Map<String, String> map = new ArrayMap<String, String>();
//                                map.put("member_id", User.getIntance().getMemberId());
//                                map.put(CUPONS_ID, cuponsId);
//                                map.put(HOME_ID, homeId);
//                                map.put("mac_id", Util.getWifiMac());
//                                WebActivityCliet.getCuponsState(JSON.toJSONString(map), new ClientCallback<String>() {
//                                    @Override
//                                    public void onSuccess(String s) {
//                                        Log.e("WebActivity", "优惠券" + s);
//                                        JSONObject jsonObject = JSON.parseObject(s);
//                                        String flag = jsonObject.getString("flag");
//                                        switch (flag) {
//                                            case "0":
//                                                showToast("恭喜！ 领券成功~");
//                                                break;
//                                            case "1":
//                                                showToast("每个用户限领1张，不能重复领取哦~");
//                                                break;
//                                            case "2":
//                                                showToast("该优惠券已抢光");
//                                                break;
//                                            case "3":
//                                                showToast(" 超过领取时间");
//                                                break;
//                                            case "4":
//                                                showToast("该冰箱已参与过该活动，不能重复参加~");
//                                                break;
//                                            default:
//                                                break;
//
//                                        }
//
//
//                                    }
//
//                                    @Override
//                                    public void onFail(int code, String error) {
//                                        showToast("领取失败");
//                                    }
//                                });
//                            }
//                        });


                    }

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(url);
    }


}
