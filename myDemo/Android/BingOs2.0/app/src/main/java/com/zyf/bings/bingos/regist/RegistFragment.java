package com.zyf.bings.bingos.regist;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseFragment;

import java.util.List;

/**
 * Created by wangshiqi on 2017/8/31.
 */

public class RegistFragment extends BaseFragment implements ISmsContract.SmsView, View.OnClickListener {
    private EditText registPhoneNumber;
    private EditText registSmsNumber;
    private TextView registSmsSend;
    private Button registButton;
    private ISmsContract.SmsPresenter smsPresenter;


    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "RegistFragment";
        RegistFragment fragment = (RegistFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragment == null) {
            fragment = new RegistFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
        } else {
            if (!fragment.isHidden()) {
                transaction.commit();
                return;
            }
            transaction.show(fragment);
        }
        transaction.addToBackStack(null);
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof RegistFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setPresenter();
        View view = inflater.inflate(R.layout.fragment_regist, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        registPhoneNumber = (EditText)view.findViewById(R.id.regist_phone_number);
        registSmsNumber = (EditText) view.findViewById(R.id.regist_sms_number);
        registSmsSend = (TextView) view.findViewById(R.id.regist_sms_send);
        registButton = (Button) view.findViewById(R.id.regist_btn);
        registSmsSend.setOnClickListener(this);
        registButton.setOnClickListener(this);
    }

    @Override
    public void setPresenter() {
        smsPresenter = new SmsPresenter(this);
    }

    @Override
    public void registSuccess() {

        Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_sms_send:
                timer.start();
                registSmsSend.setEnabled(false);
                if (isMobile(registPhoneNumber.getText().toString())) {
                    smsPresenter.getSmsCode(registPhoneNumber.getText().toString());
                }
                break;
            case R.id.regist_btn:
                if (!TextUtils.isEmpty(registPhoneNumber.getText().toString()) && isMobile(registPhoneNumber.getText().toString())) {

                    smsPresenter.regist(registPhoneNumber.getText().toString());
                } else {
                    Toast.makeText(getContext(), "号码不正确", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return super.onTouch(v, event);
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
    /**
     * 计时器
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            registSmsSend.setText(millisUntilFinished / 1000 + "秒");
            registSmsSend.setBackgroundResource(R.mipmap.waiting_sms);
        }

        @Override
        public void onFinish() {
            registSmsSend.setEnabled(true);
            registSmsSend.setText("重新发送");
        }
    };
}
