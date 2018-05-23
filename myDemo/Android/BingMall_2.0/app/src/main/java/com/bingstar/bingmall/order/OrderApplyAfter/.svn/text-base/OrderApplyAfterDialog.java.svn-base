package com.bingstar.bingmall.order.OrderApplyAfter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.bingstar.bingmall.order.view.IOrderInfoContract;
import com.yunzhi.lib.utils.LogUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * Created by liumengqiang on 2017/3/17 下午5:11
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017/6/27 下午5:11
 * @modify by reason:{方法名}:变更售后处理方式 （临时处理，部分代码未做删除处理） 隐藏订单金额 改售后金额为显示支付价格  不对具体退货商品进行选择
 */

public class OrderApplyAfterDialog extends DialogFragment implements IOrderInfoContract.ApplaySearchView,View.OnClickListener{

    private String orderState = ""; // // 其他: 待审核中 ？ 13和23： 审核中

    private String zorderinfo_id;

//    private List<OrderListInfo.OrderMoreInfo> order_goodsList;
    /**
     * 提交申请后底部按钮
     */
    private ImageView image_eidt;

    private IOrderInfoContract.ApplaySearchPresenter presenter;

    private TextView text_money_after;//提交申请后底部 总金额

    private View view;

    private ListView listView;

    private AfterSaleBeanInfo afterSaleBeanInfo;

    private EditText order_applay_foot_edit;

    private Toast toast;

    /**
     * 提交申请后的按钮
     */
    private ImageView back_money_and_goods_image;

    private OrderListInfo orderListinfo;

    private OrderApplayAdapter adapter;

    private View footView;

//    private double totalMoney = 0;

    private String zstate;

    private TextView text_money_title_state;//状态

//    private double totalMoney_EditDialog = 0;

    /**
     * 售后页面底部两个按钮
     */
    private ImageView image_confirm;//确定按钮
    private ImageView image_cancel;//取消按钮

    private TextView text_money;//售后页面底部总金额

    private LinearLayout after_layout;//提交申请后底部LinearLayout(总金额)
    private RelativeLayout befor_layout;//售后页面底部RelativeLayout(总金额)

    private List<OrderListInfo.OrderMoreInfo> editList = new ArrayList<OrderListInfo.OrderMoreInfo>();

    public static OrderApplyAfterDialog newInstance(OrderListInfo orderListinfo, String order_id, String orderState, String zstate,String orderLimitMoney) {

        Bundle bundle = new Bundle();
        bundle.putString(OrderStates.ORDER_APPLY_FLUG, orderState);
        bundle.putString(OrderStates.ZORDERINFO_ID , order_id);
        bundle.putSerializable(OrderStates.ORDERLISTINFO, orderListinfo);
        bundle.putSerializable(OrderStates.ORDERLIMITMONEY, orderLimitMoney);
        bundle.putString(OrderStates.ZSTATE, zstate);
        OrderApplyAfterDialog fragment = new OrderApplyAfterDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT);

        WindowManager wm = getActivity().getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        getDialog().getWindow().setLayout(width/2 + 30, height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.order_apply_after_layout, container);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 触摸屏幕关闭软键盘
                 */
                View view = getActivity().getWindow().peekDecorView();
                if(view != null && view.getWindowToken() != null){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        orderState = (String)getArguments().getString(OrderStates.ORDER_APPLY_FLUG);
        zorderinfo_id = (String)getArguments().getString(OrderStates.ZORDERINFO_ID);
        String orderlimitmoney = getResources().getString(R.string.discount_money)+(String)getArguments().getString(OrderStates.ORDERLIMITMONEY);
        orderListinfo = (OrderListInfo) getArguments().getSerializable(OrderStates.ORDERLISTINFO);
//        for(int i = 0;i < orderListinfo.getOrderList().size() ;i++){//遍历afterSaleInfo
//            for(int j  = 0 ;j < orderListinfo.getOrderList().get(i).getOrdergoodsList().size();j++){
//                orderListinfo.getOrderList().get(i).getOrdergoodsList().get(j).setSelectState("0");  //-1:不能点击 ？1：选中状态？ 0：为选中状态
//            }
//        }
        text_money_after = (TextView)view.findViewById(R.id.text_money_after);
        text_money = (TextView)view.findViewById(R.id.text_money);
        text_money_after.setText(orderlimitmoney);
        text_money.setText(orderlimitmoney);
        zstate = getArguments().getString(OrderStates.ZSTATE);
        findView();
        setDate();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);


//        总订单标记0:待支付1:待发货 2:已发货5:已收货99:失败
        if(OrderStates.ORDER_LIST_QUERY_PAY.equals(zstate)){
            back_money_and_goods_image.setImageResource(R.drawable.order_after_sale_money);
        }else{
            back_money_and_goods_image.setImageResource(R.drawable.order_after_sale_money_goods);
        }
        return view;
    }

    private void setListener() {
        image_eidt.setOnClickListener(this);
    }

    private void setDate() {
        /**
         * 设置编辑状态(退款审核中和退货申请中不能够点击，也就是不可编辑状态)
         */
         if(orderState != null && (OrderStates.ORDER_LIST_QUERY_CHECK_BACK_GOODS.equals(orderState) || OrderStates.ORDER_LIST_QUERY_CHECK_BACK_MONEY.equals(orderState))){//审核中
             image_eidt.setImageResource(R.drawable.order_applay_edit_un);
        }else{
             image_eidt.setImageResource(R.drawable.order_applay_edit);
             setListener();
         }

        setPresenter();
        presenter.getHttpApplayList(zorderinfo_id);

        /**
         * 设置底部价格
         */
//        text_money_after.setText(getResources().getString(R.string.discount_money ) + (new DecimalFormat("0.00").format(totalMoney)));

    }

    /**
     * findview
     */
    private void findView() {
        listView = (ListView)view.findViewById(R.id.applay_listview);
        back_money_and_goods_image = (ImageView)view.findViewById(R.id.back_money_and_goods_image);
        image_eidt = (ImageView)view.findViewById(R.id.order_applay_edit);
        text_money_after = (TextView)view.findViewById(R.id.text_money_after);
        text_money_title_state = (TextView)view.findViewById(R.id.text_money_title_state);

        image_cancel = (ImageView) view.findViewById(R.id.order_applay_cancel);
        image_confirm = (ImageView) view.findViewById(R.id.order_applay_confirm);
        text_money = (TextView) view.findViewById(R.id.text_money);
        after_layout = (LinearLayout)view.findViewById(R.id.after_layout);
        befor_layout = (RelativeLayout)view.findViewById(R.id.befor_layout);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        if(presenter != null){
            presenter.unBind();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.order_applay_edit) {
            if (this.afterSaleBeanInfo == null) {
                return;
            }
            setEditDate();
            setEditVisiable();
        }
        if (i == R.id.order_applay_confirm) {
            if ("".equals(order_applay_foot_edit.getText().toString()) || order_applay_foot_edit.getText() == null) {
                showToast("请填写退货退款理由！");
                return;
            }
            presenter.getHttpApplayAfter(editList, order_applay_foot_edit.getText().toString(), zstate);
        } else if (i == R.id.order_applay_cancel) {
            dismiss();
        }
    }

    private String resaonString;

    @Override
    public void setnotifyDate(AfterSaleBeanInfo afterSaleBeanInfo) {
        this.afterSaleBeanInfo = afterSaleBeanInfo;

        /**
         * 设置adapter数据
         */
        footView = View.inflate(getContext(), R.layout.order_applay_buttom, null);
        order_applay_foot_edit = (EditText) footView.findViewById(R.id.order_applay_foot_edit);
        if(afterSaleBeanInfo != null && afterSaleBeanInfo.getAfterSaleInfo() != null && afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleReason() != null){
            order_applay_foot_edit.setText(afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleReason()+"");
            resaonString = afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleReason()+"";
        }
        listView.addFooterView(footView);

//        order_goodsList = new ArrayList<>();
//        for(int i = 0; i < orderListinfo.getOrderList().size();i++){
////            order_goodsList.add(orderListinfo.getOrderList().get(i));
//            totalMoney = totalMoney + Double.parseDouble(orderListinfo.getOrderList().get(i).getOrder_total_money());
//        }
        /**
         *
         * 总体思路是：1：在orderListInfo中添加两个字段 一个是ifClick是否可点击（图标是否显示），一个是selectState；
         *             2：将afterSaleBeanInfo中的数据添加到orderListinfo中；
         *             3：维护orderListInfo中的数据(selectState的值)；也就是每点击一次就更新一次orderListInfo中的数据
         *             4：将orderListInfo中的数据添加到aftersaleBeanInfo中；
         *             5：把afterSaleBeanInfo当作售后申请的参数
         *
         *
         * 2：将afterSaleBeanInfo内部的数据 添加到orderListinfo中
         */

        /**
         * TODO 优化
         */
//        for(int i = 0;i < afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleOrderList().size() ;i++){//遍历afterSaleInfo
//            for(int j  = 0 ;j < afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleOrderList().get(i).getAfterSaleGoodsList().size();j++){
//                orderListinfo.getOrderList().get(i).getOrdergoodsList().get(j).setSelectState(afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleOrderList().get(i).getAfterSaleGoodsList().get(j).getSelectState());  //-1:不能点击 ？1：选中状态？ 0：为选中状态
//                /**
//                 * 更新Adapter的数据order_goodsList的值
//                 */
////                order_goodsList.get(i).getOrdergoodsList().get(j).setSelectState(afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleOrderList().get(i).getAfterSaleGoodsList().get(j).getSelectState());//显示灰色图标，适配器的数据
//
//                if("1".equals(afterSaleBeanInfo.getAfterSaleInfo().getAfterSaleOrderList().get(i).getAfterSaleGoodsList().get(j).getSelectState())){
//                    if(orderListinfo.getOrderList().get(i).getCycleflag().equals("01")){
//                        totalMoney += Double.parseDouble( orderListinfo.getOrderList().get(i).getOrder_total_money());
//                    }else{
//                        totalMoney += Double.parseDouble( orderListinfo.getOrderList().get(i).getOrdergoodsList().get(j).getGoodsTotal_amount());
//                    }
//                    text_money_after.setText(getResources().getString(R.string.discount_money ) + (new DecimalFormat("0.00").format(totalMoney)));
//                }
//            }
//
//        }

        adapter = new OrderApplayAdapter(orderListinfo.getOrderList(), OrderStates.STATE_NUMBER1);
        listView.setAdapter(adapter);
    }

    @Override
    public void setDismissView() {
        EventBus.getDefault().post(new EventMsg(OrderApplyAfterDialog.class));//售后申请成功关闭订单详情和售后申请查询Dialog
        dismiss();
    }

    @Override
    public void setPresenter() {
        presenter = new OrderApplayPresenter(this);
    }

    @Override
    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
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
            toast = Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT);
            setToastTextSize(toast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(strId);
        }
        toast.show();
    }

    @Subscribe
    public void onEvent(EventMsg msg){
        if(msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(ErrorFragment.OrderApplayAfter)){
            dismiss();
        }else if(msg.getClassName().equals(BingstarErrorParser.class) && BingstarErrorParser.OrderApplyDialog_final.equals(msg.getMsg())){
            /**
             * 错误处理 重新请求数据
             */
            BingstarErrorParser.toastErr(BingstarErrorParser.DATA_ERROR, "",BingstarErrorParser.OrderInfoFragment_final);//在关闭dialog
            dismiss();
        }else if(msg.getClassName().equals(OrderApplayAdapter.class)){
//            int position = Integer.parseInt(msg.getMsg());
//            int itemPosition = Integer.parseInt(msg.getIsFrom());
            /**
             * 3：维护orderListInfo中的数据；也就是每点击一次就更新一次orderListInfo中的数据
             *
             */
//            if("1".equals(editList.get(position).getOrdergoodsList().get(itemPosition).getSelectState())){
//                if(editList.get(position).getCycleflag().equals("01")){//判断是否是周期购 周期购子订单只可能有一个商品
//                    totalMoney = totalMoney - Double.parseDouble(editList.get(position).getOrder_total_money());
//                }else{
//                    totalMoney = totalMoney - Double.parseDouble(editList.get(position).getOrdergoodsList().get(itemPosition).getGoodsTotal_amount());
//                }
//                editList.get(position).getOrdergoodsList().get(itemPosition).setSelectState("0");
//            }else{
//                if(editList.get(position).getCycleflag().equals("01")){
//                    totalMoney = totalMoney + Double.parseDouble(editList.get(position).getOrder_total_money());
//                }else{
//                    totalMoney = totalMoney + Double.parseDouble(editList.get(position).getOrdergoodsList().get(itemPosition).getGoodsTotal_amount());
//                }
//                editList.get(position).getOrdergoodsList().get(itemPosition).setSelectState("1");
//            }
            adapter.notifyDataSetChanged();


//            text_money.setText(getResources().getString(R.string.discount_money) + (new DecimalFormat("0.00").format(totalMoney))+"");
        }
    }

    private void setEditDate() {
        order_applay_foot_edit.setEnabled(true);
//        text_money.setText(getResources().getString(R.string.discount_money) + (new DecimalFormat("0.00").format(totalMoney)));
//        adapter = new OrderApplayAdapter(orderListinfo.getOrderList(),OrderStates.STATE_NUMBER2);
        adapter.setStateClick(OrderStates.STATE_NUMBER2);
        editList.clear();
        editList.addAll(orderListinfo.getOrderList());
        adapter.notifyDataSetChanged();
    }
    private void setEditVisiable() {
        after_layout.setVisibility(View.GONE);
        image_cancel.setVisibility(View.VISIBLE);
        image_confirm.setVisibility(View.VISIBLE);
        befor_layout.setVisibility(View.VISIBLE);

        image_eidt.setVisibility(View.GONE);


        image_confirm.setOnClickListener(this);
        image_cancel.setOnClickListener(this);
    }
    public void serApplayVisiable(){
        after_layout.setVisibility(View.VISIBLE);
        image_cancel.setVisibility(View.GONE);
        image_confirm.setVisibility(View.GONE);
        befor_layout.setVisibility(View.GONE);
        image_eidt.setVisibility(View.VISIBLE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean judgeEditList(){
        if(editList == null || editList.size() == 0){
            return false;
        }else{
            for(int i = 0;i < editList.size();i++){
                for(int j = 0 ;j < editList.get(i).getOrdergoodsList().size();j++){
                    if("1".equals(editList.get(i).getOrdergoodsList().get(j).getSelectState())){
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
