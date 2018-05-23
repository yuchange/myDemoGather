package com.yunzhi.lib.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunzhi.lib.R;


/**
 * 功能：
 * Created by yaoyafeng on 17/5/10 下午6:14
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/5/10 下午6:14
 * @modify by reason:{方法名}:{原因}
 */

public class SynLinearLayout extends FrameLayout {

    private LinearLayout errorView;
    private LinearLayout emptyView;
    private LinearLayout loadView;
    private OnReloadListener reloadListener;

    public final static int SHOW_SUCCESS = 1;
    public final static int SHOW_LOAD = 2;
    public final static int SHOW_EMPTY = 3;
    public final static int SHOW_ERROR = 4;


    public SynLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showError() {
        showError(R.layout.syn_linear_error);
    }

    public void showError(@LayoutRes int res) {
        if (errorView == null) {
            errorView = (LinearLayout) LayoutInflater.from(getContext()).inflate(res,this,false);
            errorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (reloadListener!=null){
                        reloadListener.reload();
                    }
                }
            });
        }
        if (loadView != null) {
            loadView.setVisibility(GONE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
        }
        if (indexOfChild(errorView) == -1) {
            addView(errorView);
            errorView.setVisibility(VISIBLE);
        } else {
            errorView.setVisibility(VISIBLE);
        }
    }

    public void showSuccess() {
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
        if (loadView != null) {
            loadView.setVisibility(GONE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
        }
    }

    public void showEmpty(@LayoutRes int res) {
        if (emptyView == null) {
            emptyView = (LinearLayout) LayoutInflater.from(getContext()).inflate(res,this,false);
        }
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
        if (loadView != null) {
            loadView.setVisibility(GONE);
        }
        if (indexOfChild(emptyView) == -1) {
            addView(emptyView);
            emptyView.setVisibility(VISIBLE);
        } else {
            emptyView.setVisibility(VISIBLE);
        }
    }

    public void showEmpty() {
        showEmpty(null);
    }

    public void showEmpty(String text) {
        if (emptyView == null) {
            emptyView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.syn_linear_empty,this,false);
        }
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
        if (loadView != null) {
            loadView.setVisibility(GONE);
        }
        if (indexOfChild(emptyView) == -1) {
            addView(emptyView);
            emptyView.setVisibility(VISIBLE);
        } else {
            emptyView.setVisibility(VISIBLE);
        }
        if (text!=null){
            ((TextView)emptyView.findViewById(R.id.syn_empty_text)).setText(text);
        }
    }

    public void showLoad(@LayoutRes int res) {
        if (loadView == null) {
            loadView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.syn_linear_load,this,false);
        }
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
        }
        if (indexOfChild(loadView) == -1) {
            addView(loadView);
            loadView.setVisibility(VISIBLE);
        } else {
            loadView.setVisibility(VISIBLE);
        }
    }

    public void showLoad() {
        showLoad(R.layout.syn_linear_load);
    }

    public void setReloadListener(OnReloadListener reloadListener){
        this.reloadListener = reloadListener;
    }

    public interface OnReloadListener{
        void reload();
    }
}
