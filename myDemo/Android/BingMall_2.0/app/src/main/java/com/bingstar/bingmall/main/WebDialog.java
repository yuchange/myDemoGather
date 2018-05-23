package com.bingstar.bingmall.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseActivity;
import com.bingstar.bingmall.cart.view.DiscountCardFragment;
import com.bingstar.bingmall.main.http.MainClient;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.UIUtils;
import com.yunzhi.lib.view.OnSingleClickListener;

import java.util.Map;

/**
 * Created by zhangyifei on 17/7/27.
 */

public class WebDialog extends DialogFragment {


    Dialog mDialog;

    String mCouponIds;
    String url;
    BaseActivity mBaseActivity;

    public static final String COUPON_ID = "coupon_id";
    public static final String COUPON_URL = "coupon_url";


    public static void start(FragmentManager fragmentManager, String couponIds, String url) {
        final String TAG = "WebDialog";
        WebDialog fragment = (WebDialog) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new WebDialog();
            Bundle bundle = new Bundle();
            bundle.putString(COUPON_ID, couponIds);
            bundle.putString(COUPON_URL, url);
            fragment.setArguments(bundle);
        }
        if (!fragment.isAdded()) {
            fragment.show(fragmentManager, TAG);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = UIUtils.dp2px(getContext(), 900);
        int dialogHeight = UIUtils.dp2px(getContext(), 500);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity(), R.style.Dialog);
        Bundle arguments = getArguments();
        mCouponIds = arguments.getString(COUPON_ID);
        url = arguments.getString(COUPON_URL);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_coupon_dialog, null);
        final WebView webView = (WebView) view.findViewById(R.id.home_coupon_webview);
        LinearLayout close = (LinearLayout) view.findViewById(R.id.home_coupon_close);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setInitialScale(1);// webView网页缩放 参数是百分比
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("bsj")) {
                    if (uri.getAuthority().equals("webView")) {
                        if (TextUtils.isEmpty(User.getIntance().getMemberId())) {
                            mBaseActivity.showToast(getString(R.string.no_memeber_coupon));
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                        } else {
                            acquireCoupon(mCouponIds);
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.loadUrl(url);

        close.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView != null) {
                        webView.destroy();
                    }
                    return true;
                }
                return false;
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(view);

        return mDialog;
    }

    private void acquireCoupon(String couponId) {
        final Map<String, String> map = new ArrayMap<>();
        map.put(MainStates.MEMBER_ID, User.getIntance().getMemberId());
        map.put(MainStates.COUPON_ID, couponId);
        MainClient.acquireCoupon(map, new ClientCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                JSONObject jsonObject = JSON.parseObject(s);
                String flag = jsonObject.getString("flag");
                if (!TextUtils.isEmpty(flag)) {
                    switch (flag) {
                        case "0":
                            mBaseActivity.showToast(getResources().getString(R.string.discount_acquire_coupon0));
                            DiscountCardFragment.start(R.id.main_fragment, getActivity().getSupportFragmentManager());
                            break;
                        case "1":
                            mBaseActivity.showToast(getResources().getString(R.string.discount_acquire_coupon1));
                            break;
                        case "2":
                            mBaseActivity.showToast(getResources().getString(R.string.discount_acquire_coupon2));
                            break;
                    }
                } else {
                    mBaseActivity.showToast(getResources().getString(R.string.discount_acquire_coupon3));
                }
            }

            @Override
            public void onFail(int code, String error) {
                mBaseActivity.showToast(getResources().getString(R.string.discount_acquire_coupon3));
            }
        });
    }
}
