package com.zyf.bings.bingos.address;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.address.bean.CityJsonBean;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.DensityUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos.utils.SoftKeyboardStateHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * Created by zhangyifei on 17/9/10.
 */

public class AddAddressPopupWindow extends PopupWindow implements IAddAddressContract.AddAddressView {


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


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void show(Activity context) {
        new AddAddressPopupWindow(context).showAtLocation(context.getWindow().getDecorView(), 17, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public AddAddressPopupWindow(Context context) {


        super(context);

        mActivity = (Activity) context;

        View inflate = LayoutInflater.from(context).inflate(R.layout.address_add_fragment, null);

        setWindowLayoutType(1002);
        setSoftInputMode(16);
        unbinder = ButterKnife.bind(this, inflate);
        setPresenter();
        if (mAddAddressPresenter != null) mAddAddressPresenter.subscribe();
        initData();
        initObserver();
        setOutsideTouchable(false);
        setContentView(inflate);

        int dialogWidth = DensityUtil.px2dp(context, 900);
        int dialogHeight = DensityUtil.px2dp(context, 650);
        setWidth(dialogWidth);
        setHeight(dialogHeight);
        setFocusable(true);
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
        mMemberID = SPUtil.getString(mActivity, Config.MEMBER_ID);
        if (TextUtils.isEmpty(mMemberID)) {
            mMemberID = "15869153840";
        }

    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void insertSuccess() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void showCityPicker(ArrayList<CityJsonBean> options1Items, ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items) {

    }

    @Override
    public Context getViewContext() {
        return null;
    }
}
