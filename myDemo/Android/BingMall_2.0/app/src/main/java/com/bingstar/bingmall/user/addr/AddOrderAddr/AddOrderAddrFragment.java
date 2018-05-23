package com.bingstar.bingmall.user.addr.AddOrderAddr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.sdk.map.AutoArrayAdapter;
import com.bingstar.bingmall.sdk.map.BaiduMapManager;
import com.bingstar.bingmall.sdk.map.IMapController;
import com.bingstar.bingmall.sdk.map.MapAddrBean;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageInfoBean;
import com.bingstar.bingmall.user.addr.AddrStates;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.view.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liumengqiang on 2017/3/13.
 */

public class AddOrderAddrFragment extends BaseFragment implements IAddOrderAddrContract.IAddAddrView {

    private View view;
    private IAddOrderAddrContract.IAddAddrPresenter presenter;
    private ImageView save_image;//保存按钮
    /**
     * contactsEdit(联系人姓名) idEdit（身份证） phoneEdit（电话号码） areaEdit（地区） detailEdit（详细地址）
     */
    private EditText contactsEdit, idEdit, phoneEdit, areaEdit;
    private AutoCompleteTextView detailEdit;
    private TextView updateTitletext;

    private ArrayAdapter<MapAddrBean> arrayAdapter;
    private List<MapAddrBean> arr;
    private IMapController<BaiduMap> controller;
    private TextureMapView mMapView;

    private static AddrManageInfoBean.MemberAddress memberAddress;

    public static void start(int resId, AddrManageInfoBean.MemberAddress memberAddress, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "AddOrderAddr";
        AddOrderAddrFragment fragment = (AddOrderAddrFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new AddOrderAddrFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            bundle.putSerializable(AddrStates.MEMBERADDRESS, memberAddress);
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
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof AddrManageFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commitAllowingStateLoss();
       /* List<Fragment> fragments = manager.getFragments();
        transaction.addToBackStack(null);
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof AddOrderAddrFragment) && frag != null) {
                    if(!frag.isHidden()){
                        transaction.hide(frag);
                    }
                } else if (frag != null) {
//                    transaction.remove(frag);
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RESID, resId);
        bundle.putSerializable(AddrStates.MEMBERADDRESS, memberAddress);
        fragment.setArguments(bundle);
        transaction.add(resId, fragment, TAG);
        transaction.commit();*/
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            /*FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            for (Fragment fragment : manager.getFragments()) {
                if (fragment instanceof AddOrderAddrFragment) {
                    transaction.remove(fragment);
                }
            }
            transaction.remove(this);
            transaction.commit();*/
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /**
         * 触摸屏幕关闭软键盘
         */
        InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.onTouch(v, event);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.goods_address_dialog, container, false);
        initView();
        controller = BaiduMapManager.getInstance(getActivity());
        controller.initSdk(mMapView.getMap());
        memberAddress = (AddrManageInfoBean.MemberAddress) getArguments().getSerializable(AddrStates.MEMBERADDRESS);
        initDate();
        setPresenter();
        arr = new ArrayList<>();
        arrayAdapter = new AutoArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arr);
        detailEdit.setAdapter(arrayAdapter);
        detailEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.setPoint(arr.get(position).getLatLng());
                controller.getPrefecture(arr.get(position).getLatLng(), new IMapController.GetPrefectureCallback() {
                    @Override
                    public void callback(String prefecture) {
                        areaEdit.setText(prefecture);
                    }
                });

            }
        });
        detailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!areaEdit.getText().toString().equals("")) {
                        controller.setArea(areaEdit.getText().toString());
                    }
                }
            }
        });
        detailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    controller.startSearch(s.toString(), new IMapController.SearchCallback() {
                        @Override
                        public void callback(List<MapAddrBean> been) {
                            if (been != null && been.size() > 0) {
                                arr.clear();
                                arr.addAll(been);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        areaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (after == 0) {
                    detailEdit.setText("");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        controller.getPosition(areaEdit.getText().toString(), detailEdit.getText().toString(), new IMapController.SearchCallback() {
            @Override
            public void callback(List<MapAddrBean> been) {
                if (been != null && been.size() > 0) {
                    MapAddrBean addrBean = been.get(0);
                    controller.setPoint(addrBean.getLatLng());
                    if ("2".equals(memberAddress.getFlug())) {
                        detailEdit.setText(addrBean.getAddress());
                        controller.getPrefecture(addrBean.getLatLng(), new IMapController.GetPrefectureCallback() {
                            @Override
                            public void callback(String prefecture) {
                                areaEdit.setText(prefecture);
                            }
                        });
                    }
                }
            }
        });
        setDetailAddr();
        return view;
    }


    public void setDetailAddr() {
        detailEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        if ("1".equals(memberAddress.getFlug())) {
            updateTitletext.setText(AddrStates.UPDATE_PLACE);
            contactsEdit.setText(memberAddress.getName());
            idEdit.setText(memberAddress.getIdCard());
            phoneEdit.setText(memberAddress.getMobile());
            detailEdit.setText(memberAddress.getDetailed());
            areaEdit.setText(memberAddress.getRegion());
        } else {
            updateTitletext.setText(AddrStates.ADD_PLACE);
        }
    }

    /**
     * 初始化EditText
     */
    private void initView() {
        updateTitletext = (TextView) view.findViewById(R.id.addr_title);
        contactsEdit = (EditText) view.findViewById(R.id.contacts_addr_edit);
        phoneEdit = (EditText) view.findViewById(R.id.phone_addr_edit);
        idEdit = (EditText) view.findViewById(R.id.ID_addr_edit);
        areaEdit = (EditText) view.findViewById(R.id.area_addr_edit);
        detailEdit = (AutoCompleteTextView) view.findViewById(R.id.detail_addr_edit);
        save_image = (ImageView) view.findViewById(R.id.save_addr);
        save_image.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (User.getIntance().getMemberId() == null || User.getIntance().getMemberId().equals("")) {
                    showToast("您尚未登录");
                    return;
                }
                if (!checkInput()) {
                    return;
                }

                if ("1".equals(memberAddress.getFlug())) {//修改收货地址
                    presenter.getAddHttpUpdate(memberAddress.getAddressid(), User.getIntance().getMemberId(),
                            contactsEdit.getText().toString().trim(),
                            idEdit.getText().toString(),
                            phoneEdit.getText().toString(),
                            areaEdit.getText().toString(),
                            detailEdit.getText().toString());
                } else if ("2".equals(memberAddress.getFlug())) {//新增收货地址
                    presenter.getAddHttp(User.getIntance().getMemberId(),
                            contactsEdit.getText().toString().trim(),
                            idEdit.getText().toString(),
                            phoneEdit.getText().toString(),
                            areaEdit.getText().toString(),
                            detailEdit.getText().toString());

                }
            }
        });
        mMapView = (TextureMapView) view.findViewById(R.id.addr_baidu_map);

    }

    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.unBind();
        }
        if (controller != null) {
            controller.onDestory();
        }
    }

    @Override
    public void setPresenter() {
        presenter = new AddAddrPresenter(this);

    }

    @Override
    public void setUI() {
        /**
         *  通知地址列表刷新
         */
        org.greenrobot.eventbus.EventBus.getDefault().post(new EventMsg(AddOrderAddrFragment.class));
        getFragmentManager().popBackStack();
    }

    @Override
    public void failedSaveAdress() {
        showToast("保存失败");
    }

    /**
     * 检查输入
     *
     * @return boolean
     */
    private boolean checkInput() {
        if (contactsEdit.getText().toString().trim().equals("") ||
                phoneEdit.getText().toString().trim().equals("") ||
                areaEdit.getText().toString().trim().equals("") ||
                detailEdit.getText().toString().trim().equals("")
                ) {
            showToast("请完善您的个人信息！");
            return false;
        }
        String name = contactsEdit.getText().toString();
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(name);
        if (m.find()) {
            showToast("输入姓名不合法,请重新输入.");
            return false;
        }

        if (!TextUtils.isEmpty(idEdit.getText().toString())) {
            if (!Util.validateIdCard(idEdit.getText().toString())) {
                String phone = phoneEdit.getText().toString();
                Pattern p = Pattern.compile("^[1]([2-5]|[7-8])\\d{9}$");
                Matcher matcher = p.matcher(phone);
                if (!matcher.find()) {
                    showToast("身份证或手机号输入不合法,请检查.");
                    return false;
                }
                showToast("身份证非法，请检查");
                return false;
            }
        }

        String phone = phoneEdit.getText().toString();
        Pattern p = Pattern.compile("^[1]([2-5]|[7-8])\\d{9}$");
        Matcher matcher = p.matcher(phone);
        if (!matcher.find()) {
            showToast("手机号不合法.");
            return false;
        }
        return true;
    }


}
