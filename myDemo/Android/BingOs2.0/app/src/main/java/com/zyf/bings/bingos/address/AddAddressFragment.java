package com.zyf.bings.bingos.address;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.address.bean.CityJsonBean;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos.utils.SoftKeyboardStateHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;

import static com.zyf.bings.bingos_libnet.utils.ToastUtils.showToast;

/**
 * Created by zhangyifei on 17/9/1.
 */

public class AddAddressFragment extends DialogFragment implements IAddAddressContract.AddAddressView {


    Activity mActivity;
    @BindView(R.id.iv_addaddress_close)
    ImageView mIvClose;
    @BindView(R.id.et_address_add_name)
    EditText mEtName;
    @BindView(R.id.et_address_add_tel)
    EditText mEtTel;
    @BindView(R.id.et_address_add_location)
    EditText mEtLocation;
    @BindView(R.id.et_address_add_detaillocation)
    EditText mEtDetailLocation;
    @BindView(R.id.et_address_add_code)
    EditText mEtCode;
    Unbinder unbinder;
    @BindView(R.id.btn_address_add_confirm)
    Button mBtnConfirm;
    @BindView(R.id.linear_address_select)
    LinearLayout mLinearAddressSelect;

    IAddAddressContract.AddAddressPresenter mAddAddressPresenter;
    public static final String ADDRESS_BEAN = "address_bean";
    private AddressList.MemberAddressListBean mMemberAddressListBean;
    private String mMemberID;
    private SoftKeyboardStateHelper mHelper;

    public static void start(FragmentManager manager, AddSuccess addSuccess, AddressList.MemberAddressListBean memberAddressListBean) {
        if (manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "AddAddressFragment";
        AddAddressFragment fragment = newInstance(memberAddressListBean);
        fragment.setAddSuccess(addSuccess);
        if (!fragment.isAdded()) {
            fragment.show(manager, TAG);
        }

    }

    public static AddAddressFragment newInstance(AddressList.MemberAddressListBean memberAddressListBean) {
        Bundle args = new Bundle();
        if (memberAddressListBean != null) {
            args.putSerializable(ADDRESS_BEAN, memberAddressListBean);
        }
        AddAddressFragment fragment = new AddAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        int dialogWidth = DensityUtil.px2dp(getActivity(), 900);
        int dialogHeight = DensityUtil.px2dp(getActivity(), 800);
        //getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置dialog的背景为透明
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.ANIMATION_CHANGED);
        View inflate = inflater.inflate(R.layout.address_add_fragment, container, false);
        mHelper = new SoftKeyboardStateHelper(inflate);
        mHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                Log.e("AddAddressFragment", "onSoftKeyboardOpened:  55 ");


            }

            @Override
            public void onSoftKeyboardClosed() {
                Log.e("AddAddressFragment", "onSoftKeyboardClosed: 66 " + "");
                //  ((NestedScrollView) inflate).smoothScrollTo(0, 100);
            }
        });

        unbinder = ButterKnife.bind(this, inflate);
        mEtName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        setPresenter();
        if (mAddAddressPresenter != null) mAddAddressPresenter.subscribe();
        initData();
        initObserver();
        return inflate;
    }

    //监听颜色变化
    private void initObserver() {
        Observable<CharSequence> nameObservable = RxTextView.textChanges(mEtName);
        Observable<CharSequence> telObservable = RxTextView.textChanges(mEtTel);
        Observable<CharSequence> locObservable = RxTextView.textChanges(mEtLocation);
        Observable<CharSequence> locDetailObservable = RxTextView.textChanges(mEtDetailLocation);
        Observable<CharSequence> codeObservable = RxTextView.textChanges(mEtCode);
        Observable.combineLatest(nameObservable, telObservable, locObservable, locDetailObservable, codeObservable, (charSequence, charSequence2, charSequence3, charSequence4, charSequence5) -> {
            if (!TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(charSequence2)
                    && !TextUtils.isEmpty(charSequence3)
                    && !TextUtils.isEmpty(charSequence4)
                    && !TextUtils.isEmpty(charSequence5)) {
                return 2;
            }
            return 1;
        }).subscribe(integer -> {
            if (integer == 1) {
                mBtnConfirm.setBackgroundResource(R.mipmap.address_btn_bgb);
            } else if (integer == 2) {
                mBtnConfirm.setBackgroundResource(R.mipmap.address_btn_bgo);
            }
        }, throwable -> {
            mBtnConfirm.setBackgroundResource(R.mipmap.address_btn_bgb);
        });


    }


    private void initData() {
        mMemberID = SPUtil.getString(getActivity(), Config.MEMBER_ID);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    Context context = activity.getApplicationContext();
                    if (context != null) {
                        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (im != null && getDialog().getCurrentFocus() != null) {
                            boolean b = im.hideSoftInputFromWindow(getDialog().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            Log.e("AddAddressFragment", "onDismiss: " + "隐藏软键盘 " + b);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        Serializable serializable = getArguments().getSerializable(ADDRESS_BEAN);
        if (serializable instanceof AddressList.MemberAddressListBean) {
            mMemberAddressListBean = (AddressList.MemberAddressListBean) serializable;
            mEtName.setText(mMemberAddressListBean.getName());
            mEtTel.setText(mMemberAddressListBean.getMobile());
            mEtLocation.setText(mMemberAddressListBean.getRegion());
            mEtDetailLocation.setText(mMemberAddressListBean.getDetailed());
            mEtCode.setText(mMemberAddressListBean.getPostCode());
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAddSuccess != null) mAddSuccess = null;
        if (mMemberAddressListBean != null) mMemberAddressListBean = null;
        if (mAddAddressPresenter != null) {
            mAddAddressPresenter.unsubscribe();
            mAddAddressPresenter = null;
        }
        unbinder.unbind();

    }

    @OnClick({R.id.btn_address_add_confirm, R.id.iv_addaddress_close,
            R.id.linear_address_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_address_add_confirm:
                checkInput();
                break;
            case R.id.iv_addaddress_close:
                if (getDialog() != null && getDialog().isShowing())
                    dismiss();
                break;
            case R.id.linear_address_select:
                Log.e("AddAddressFragment", "onClick: " + "点击了 城市选择");
                mAddAddressPresenter.handleCityPicker();
                break;
            default:

        }
    }

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );

    @Override
    public void showCityPicker(final ArrayList<CityJsonBean> optionsOne, final ArrayList<ArrayList<String>> optionsTwo,
                               final ArrayList<ArrayList<ArrayList<String>>> optionsThree) {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsOne.get(options1).getPickerViewText() +
                        optionsTwo.get(options1).get(options2) +
                        optionsThree.get(options1).get(options2).get(options3);

                if (mEtLocation != null) mEtLocation.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .isDialog(true)
                .build();


        ViewGroup contentContainer = (ViewGroup) pvOptions.findViewById(com.bigkoo.pickerview.R.id.content_container);
        //设置对话框 左右间距屏幕30
        this.params.leftMargin = DensityUtil.dp2px(getActivity(), 350);
        this.params.rightMargin = DensityUtil.dp2px(getActivity(), 350);
        contentContainer.setLayoutParams(this.params);
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(optionsOne, optionsTwo, optionsThree);//三级选择器
        pvOptions.show();
    }

    private void checkInput() {
        if (mEtName.getText().toString().trim().equals("") ||
                mEtTel.getText().toString().trim().equals("") ||
                mEtLocation.getText().toString().trim().equals("") ||
                mEtCode.getText().toString().trim().equals("") ||
                mEtDetailLocation.getText().toString().trim().equals("")
                ) {
            showToast("请完善您的个人信息！");
            return;
        }

        String name = mEtName.getText().toString();
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(name);
        if (m.find()) {
            showToast("输入姓名不合法,请重新输入.");
            return;
        }

        String phone = mEtTel.getText().toString();
        Pattern p = Pattern.compile("^[1]([2-5]|[7-8])\\d{9}$");
        Matcher matcher = p.matcher(phone);
        if (!matcher.find()) {
            showToast("手机号输入不合法,请检查.");
            return;
        }
        String fax = mEtCode.getText().toString().trim();
        String reg = "^([0-9])\\d{5}";
        Pattern pattern2 = Pattern.compile(reg);
        if (!pattern2.matcher(fax).matches()) {
            showToast("邮编不合法,请检查.");
            return;
        }


        if (mMemberAddressListBean == null) {
            mAddAddressPresenter.insertAddress(mMemberID, mEtName.getText().toString().trim(),
                    mEtDetailLocation.getText().toString().trim(),
                    mEtTel.getText().toString().trim(),
                    mEtLocation.getText().toString().trim(),
                    mEtCode.getText().toString().trim());
        } else {
            mAddAddressPresenter.updateAddress(mMemberAddressListBean.getAddressid(), mMemberID, mEtName.getText().toString().trim(),
                    mEtDetailLocation.getText().toString().trim(),
                    mEtTel.getText().toString().trim(),
                    mEtLocation.getText().toString().trim(),
                    mEtCode.getText().toString().trim());
        }


    }


    @Override
    public void setPresenter() {
        mAddAddressPresenter = new AddAddressPresenter(this);
    }

    @Override
    public void insertSuccess() {
        if (mAddSuccess != null) {
            mAddSuccess.addSuccess();
            dismissDialog();
        }
    }

    @Override
    public void updateSuccess() {
        if (mAddSuccess != null) {
            mAddSuccess.addSuccess();
            dismissDialog();
        }
    }

    public void setAddSuccess(AddSuccess addSuccess) {
        mAddSuccess = addSuccess;
    }

    public AddSuccess mAddSuccess;

    public interface AddSuccess {
        void addSuccess();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        hideSoft();
        super.onDismiss(dialog);
    }

    private void hideSoft() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Context context = activity.getApplicationContext();
            if (context != null) {
                InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null && getActivity().getCurrentFocus() != null) {
                    boolean b = im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Log.e("AddAddressFragment", "onDismiss: " + "隐藏软键盘 " + b);

                }
            }
        }
    }

    private void dismissDialog() {
        if (getDialog() != null && getDialog().isShowing()) {
            dismiss();
        }
    }


    @Override
    public Context getViewContext() {
        return getActivity();
    }
}
