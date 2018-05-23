package com.bingstar.bingmall.user.addr.AddOrderAddr;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.sdk.map.AutoArrayAdapter;
import com.bingstar.bingmall.sdk.map.BaiduMapManager;
import com.bingstar.bingmall.sdk.map.IMapController;
import com.bingstar.bingmall.sdk.map.MapAddrBean;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageInfoBean;
import com.bingstar.bingmall.user.addr.AddrStates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumengqiang on 2017/2/27.
 */

/**
 * 新增收货地址，修改收货地址，查看收货地址
 */
public class AddOrderAddrDialog extends DialogFragment implements IAddOrderAddrContract.IAddAddrView,View.OnClickListener{
    private View view;
    private Spinner privanceSpinner, citySpinner, areaSpinner;
    private ArrayAdapter<String> privanceAdapter ,cityAdapter, areaAdapter;
    private List<String> privanceList, cityList, areaList;
    private IAddOrderAddrContract.IAddAddrPresenter presenter;
    private int privancePosition = 0 , cityPosition = 0;
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
    private MapView mMapView;

    /**
     * 修改地址
     */
    private String contactsBundle;

    private String idBundle;

    private String phoneBundle;

    private String areaBundle;

    private String detailBundle;
//
//    private static String flugBundle;// 1:修改收货地址 2:新增修改地址 3：查看收货地址
//
//    private static String addressidBundle;//地址ID（修改收货地址用到）

    private static AddrManageInfoBean.MemberAddress memberAddress;

    public static AddOrderAddrDialog newInstance(AddrManageInfoBean.MemberAddress memberAddress) {
        throw new IllegalArgumentException("no use");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.goods_address_dialog, container);

        memberAddress = (AddrManageInfoBean.MemberAddress) getArguments().getSerializable(AddrStates.MEMBERADDRESS);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                点击屏幕 关闭软键盘
                hideInput();
            }
        });

        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideInput();
            }
        });

        initView();

        initDate();

        setPresenter();

        return view;
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        if("1".equals(memberAddress.getFlug())){
            updateTitletext.setText(AddrStates.UPDATE_PLACE);
        }else if("2".equals(memberAddress.getFlug())){
            updateTitletext.setText(AddrStates.ADD_PLACE);
            return;
        }
//        else if("3".equals(memberAddress.getFlug())){
//            updateTitletext.setText("收货地址详情");
//            setFocus();
//        }
        contactsEdit.setText(memberAddress.getName());
        idEdit.setText(memberAddress.getIdCard());
        phoneEdit.setText(memberAddress.getMobile());
        detailEdit.setText(memberAddress.getDetailed());
        areaEdit.setText(memberAddress.getRegion());
    }

    /**
     * 关闭软键盘
     */
    public void hideInput(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm=(InputMethodManager)getActivity(). getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置Edittext失去焦点
     */
    public void setFocus(){
        contactsEdit.clearFocus();
        idEdit.clearFocus();
        phoneEdit.clearFocus();
        detailEdit.clearFocus();
        areaEdit.clearFocus();
        contactsEdit.setFocusable(false);
        idEdit.setFocusable(false);
        phoneEdit.setFocusable(false);
        detailEdit.setFocusable(false);
        areaEdit.setFocusable(false);
    }

    /**
     * 初始化EditText
     */
    private void initView() {
//        privanceSpinner = (Spinner) view.findViewById(R.id.id_privance);
//        citySpinner = (Spinner) view.findViewById(R.id.id_city);
//        areaSpinner = (Spinner) view.findViewById(R.id.id_area);
        updateTitletext = (TextView)view.findViewById(R.id.addr_title);
        contactsEdit = (EditText)view.findViewById(R.id.contacts_addr_edit);
        phoneEdit = (EditText)view.findViewById(R.id.phone_addr_edit);
        idEdit = (EditText)view.findViewById(R.id.ID_addr_edit);
        areaEdit = (EditText)view.findViewById(R.id.area_addr_edit);
        detailEdit = (AutoCompleteTextView)view.findViewById(R.id.detail_addr_edit);
        save_image = (ImageView)view.findViewById(R.id.save_addr);
        save_image.setOnClickListener(this);
        mMapView=(MapView) view.findViewById(R.id.addr_baidu_map);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arr = new ArrayList<>();
        controller = BaiduMapManager.getInstance(getActivity());
        arrayAdapter = new AutoArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arr);
        detailEdit.setAdapter(arrayAdapter);
        detailEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.setPoint(arr.get(position).getLatLng());
//                areaEdit.setText(arr.get(position).getCity());
            }
        });
        detailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                controller.setCity(areaEdit.getText().toString());

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
        controller.initSdk(mMapView.getMap());
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (controller.getCity().equals("")&&!controller.getCity().equals("中国")){
//            areaEdit.setText(controller.getCity());
//        }
    }

    @Override
    public void setPresenter() {
//        presenter = new AddAddrPresenter(this);
//        presenter.getDate();
        presenter = new AddAddrPresenter(this);

    }

    @Override
    public void showToast(String str) {

    }

    @Override
    public void showToast(@StringRes int strId) {

    }

    @Override
    public void setUI() {
        /**
         *  关闭dialog,通知地址列表刷新
         */
        getDialog().dismiss();
    }

    @Override
    public void failedSaveAdress() {

    }

//    @Override
//    public void setPrivanceSpinner(List<String> privanceList) {
////        privanceAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, privanceList);
////        privanceSpinner.setAdapter(privanceAdapter);
////        privanceSpinner.setSelection(0);
//    }
//
//    @Override
//    public void setCitySpinner(List<String> cityList) {
////        cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,cityList);
////        citySpinner.setAdapter(cityAdapter);
////        citySpinner.setSelection(0);
//    }
//
//    @Override
//    public void setAreaSpinner(List<String> areaList) {
////        areaAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,areaList);
////        areaSpinner.setAdapter(areaAdapter);
////        areaSpinner.setSelection(0);
//    }

//    @Override
//    public Context setContext() {
//        return getContext();
//    }

    @Override
    public void onClick(View v) {
        /**
         * TODO 修改  edittext.getText().toString();
         */
//        presenter.getAddHttp("",contactsEdit.getText().toString(),idEdit.getText().toString(),phoneEdit.getText().toString(),areaEdit.getText().toString(),
//                detailEdit.getText().toString());

        /**
         * TODO  修改
         */
        if("1".equals(memberAddress.getFlug())){//修改收货地址
            presenter.getAddHttpUpdate(memberAddress.getAddressid(),memberAddress.getMember_id(),
                    "张三三" ,"330825198809091299","12333333333","浙江省杭州市滨江区","海创园9号楼");
        }else if("2".equals(memberAddress.getFlug())){//新增收货地址
            presenter.getAddHttp(memberAddress.getMember_id(),
                    "老王","123456789123123","12333333333","浙江省杭州市滨江区","东方通信科技园启迪楼2楼");

        }

    }

}
