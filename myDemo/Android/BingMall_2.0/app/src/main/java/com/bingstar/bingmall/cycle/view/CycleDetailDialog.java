package com.bingstar.bingmall.cycle.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.http.CycleStates;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.order.view.OrderInfoFragment;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.video.lib.SynLinearLayout;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;

/**
 * Created by qianhechen on 17/2/14.
 */

public class CycleDetailDialog extends DialogFragment implements View.OnClickListener, ICycleDialogContract.CycleDialogView {
    //    商品ID
    private static CycleProductInfo.CycleProduct.CycleProductInfoDetail cycleProductInfoDetailBundle;

    private EditText cycle_edit_day;//天数day

    private EditText cycle_edit_num;// 数量Edit

    private ImageView cycle_dialog_ordertv;//确认按钮

    private TextView textMoney;//总金额Textview

    private ICycleDialogContract.CycleDialogPresenter presenter;

    private Toast toast;

    private SynLinearLayout cycle_synlinearlayout;

    public static void start(FragmentManager fragmentManager, CycleProductInfo.CycleProduct.CycleProductInfoDetail cycleProductInfoDetail) {
        final String TAG = "CycleDetailDialog";
        CycleDetailDialog fragment = (CycleDetailDialog) fragmentManager.findFragmentByTag(TAG);
        if (fragment==null) {
            fragment = new CycleDetailDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CycleStates.DETAIL_INFO, cycleProductInfoDetail);
            fragment.setArguments(bundle);
        }
        fragment.show(fragmentManager,TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = 667;
        int dialogHeight = 413;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.cycle_detail_dialog, container);
        cycle_synlinearlayout = (SynLinearLayout) view.findViewById(R.id.cycle_synlinearlayout);
        cycleProductInfoDetailBundle = (CycleProductInfo.CycleProduct.CycleProductInfoDetail) getArguments().getSerializable(CycleStates.DETAIL_INFO);//获取Dialog所需信息
        cycle_dialog_ordertv = (ImageView) view.findViewById(R.id.cycle_dialog_ordertv);
        cycle_edit_day = (EditText) view.findViewById(R.id.cycle_edit_day);
        cycle_edit_num = (EditText) view.findViewById(R.id.cycle_eidt_number);
        textMoney = (TextView) view.findViewById(R.id.cycle_dialog_money);
        cycle_dialog_ordertv.setOnClickListener(this);
        setListener();
        setPresenter();
//        EventBus.getDefault().register(this);
        return view;
    }

    private void setListener() {
        cycle_edit_day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cycle_edit_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String textAfterNum = cycle_edit_num.getText().toString();
                if (!textAfterNum.equals("")) {
                    float price = Float.parseFloat(cycleProductInfoDetailBundle.getPrice());
                    int number = Integer.parseInt(textAfterNum);
                    int count = Integer.parseInt(cycleProductInfoDetailBundle.getNumber());
                    textMoney.setText(Util.priceToString(price * count * number));
                }else {
                    textMoney.setText("0.00");
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cycle_dialog_ordertv) {
            if (cycle_edit_day.getText().toString().equals("") || cycle_edit_num.getText().toString().equals("")) {
                showToast(R.string.cycle_dialog_data_null);
                return;
            }
            if (Integer.parseInt(cycle_edit_day.getText().toString())==0 || Integer.parseInt(cycle_edit_num.getText().toString())==0) {
                showToast(R.string.cycle_dialog_data_null);
                return;
            }
            /**
             * 判断地址是否存在，不存在就跳转到AddrManageFragment页面
             */
            if (User.checkAddr()) {
                /**
                 * 创建订单
                 */
                cycle_synlinearlayout.showLoad();
                setCancelable(false);
                presenter.createOrder(cycleProductInfoDetailBundle, textMoney.getText().toString(), cycle_edit_day.getText().toString()
                        , cycle_edit_num.getText().toString());

//                dismiss();
            }else {
                dismiss();
                AddrManageFragment.start(R.id.main_fragment, getFragmentManager());
            }

        }
    }

    @Override
    public void setDialog(int type) {
        /**
         * 创建订单成功
         */
        cycle_synlinearlayout.showSuccess();
        switch (type){
            case SynLinearLayout.SHOW_SUCCESS:{

                break;
            }
            case SynLinearLayout.SHOW_ERROR:{
                showToast(R.string.cycle_dialog_create_error);
                break;
            }
        }
        dismiss();
    }

//    @Override
//    public void error() {
////        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.CycleDialog);
//        dismiss();
//    }

    @Override
    public void setPresenter() {
        presenter = new CycleDialogPresenter(this);
    }

    @Override
    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext().getApplicationContext(), str, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    @Override
    public void showToast(@StringRes int strId) {
        if (toast == null) {
            toast = Toast.makeText(getContext().getApplicationContext(), strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }

//    @Subscribe
//    public void onEvent(EventMsg msg) {
//        if (msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.cycledetail_dialog_final.equals(msg.getMsg())) {
//            cycle_synlinearlayout.showError();
//            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "", BingstarErrorParser.cycleFragment_final);//发送消息，让cycleFragment处理消息，
//            dismiss();//关闭dialog
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
        if (null != presenter) {
            presenter.unBind();
        }
    }

    private void setToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(50);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
