package com.bingstar.bingmall.order.OrderApplyAfter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.CustomListView;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.net.BingstarNet;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by liumengqiang on 2017/3/17.
 */

public class OrderApplayAdapter extends BaseAdapter {
    private List<OrderListInfo.OrderMoreInfo> list;

    private String stateClick = OrderStates.STATE_NUMBER1; // 1:不可点击？2：可点击

    public OrderApplayAdapter(List<OrderListInfo.OrderMoreInfo> list, String stateClick) {
        this.list = list;
        this.stateClick = stateClick;
    }

    public String getStateClick() {
        return stateClick;
    }

    public void setStateClick(String stateClick) {
        this.stateClick = stateClick;
    }

    public List<OrderListInfo.OrderMoreInfo> getList() {
        return list;
    }

    public void setList(List<OrderListInfo.OrderMoreInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder listViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.order_applay_dapter_layout, null);
            listViewHolder = new ListViewHolder();
            listViewHolder.listView = (CustomListView) convertView.findViewById(R.id.listview);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ListViewHolder) convertView.getTag();
        }

        ApplayItemAdapter applayItemAdapter = new ApplayItemAdapter();
        applayItemAdapter.setOrderGoodsLists(list.get(position).getOrdergoodsList());
        applayItemAdapter.setPosition(position);
        listViewHolder.listView.setAdapter(applayItemAdapter);

        return convertView;
    }

    class ApplayItemAdapter extends BaseAdapter {
        private int parentPosition;

        private static final int TYPE_1 = 0;
        private static final int TYPE_2 = 1;

        public int getPosition() {
            return parentPosition;
        }

        public void setPosition(int position) {
            this.parentPosition = position;
        }

        private List<OrderListInfo.OrderMoreInfo.OrderGoodsList> orderGoodsLists;

        public List<OrderListInfo.OrderMoreInfo.OrderGoodsList> getOrderGoodsLists() {
            return orderGoodsLists;
        }

        public void setOrderGoodsLists(List<OrderListInfo.OrderMoreInfo.OrderGoodsList> orderGoodsLists) {
            this.orderGoodsLists = orderGoodsLists;
        }

        @Override
        public int getCount() {
            return orderGoodsLists.size();
        }

        @Override
        public Object getItem(int position) {
            return orderGoodsLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            MyViewHolder viewHolder = null;
            MyViewHolder2 viewHolder2 = null;
            int type = getItemViewType(position);

            if (convertView == null) {
                switch (type) {
                    case TYPE_1: {
                        viewHolder = new MyViewHolder();
                        convertView = View.inflate(parent.getContext(), R.layout.order_applay_list, null);
                        viewHolder.textorder_id = (TextView) convertView.findViewById(R.id.order_id);
                        viewHolder.applay_state_text = (TextView) convertView.findViewById(R.id.applay_state_text);
                        viewHolder.textMoney = (TextView) convertView.findViewById(R.id.order_limitmoney);
                        viewHolder.textName = (TextView) convertView.findViewById(R.id.order_name);
                        viewHolder.textWeight = (TextView) convertView.findViewById(R.id.order_weight);
                        /**
                         *2017.6.27 去除勾选功能
                         */
                        viewHolder.imageButton = (ImageView) convertView.findViewById(R.id.confirm_or_cancel);
                        viewHolder.applay_linearlayout = (LinearLayout) convertView.findViewById(R.id.applay_linearlayout);
                        viewHolder.order_detail_image = (ImageView) convertView.findViewById(R.id.order_detail_image);
                        convertView.setTag(viewHolder);
                        break;
                    }
                    case TYPE_2: {
                        viewHolder2 = new MyViewHolder2();
                        convertView = View.inflate(parent.getContext(), R.layout.order_applay_list2, null);
                        viewHolder2.textMoney = (TextView) convertView.findViewById(R.id.order_limitmoney);
                        viewHolder2.textName = (TextView) convertView.findViewById(R.id.order_name);
                        viewHolder2.textWeight = (TextView) convertView.findViewById(R.id.order_weight);
                        viewHolder2.imageButton = (ImageView) convertView.findViewById(R.id.confirm_or_cancel);
                        viewHolder2.applay_linearlayout = (LinearLayout) convertView.findViewById(R.id.applay_linearlayout);
                        viewHolder2.order_detail_image = (ImageView) convertView.findViewById(R.id.order_detail_image);
                        convertView.setTag(viewHolder2);
                        break;
                    }
                }
            } else {
                switch (type) {
                    case TYPE_1: {
                        viewHolder = (MyViewHolder) convertView.getTag();
                        break;
                    }
                    case TYPE_2: {
                        viewHolder2 = (MyViewHolder2) convertView.getTag();
                    }
                }
            }

            switch (type) {
                case TYPE_1: {
                    viewHolder.textorder_id.setText(parent.getResources().getString(R.string.order_detail_adapter_title));
//                    00:周期购未激活订单0:待支付1:待发货 2:已发货5:已收货99:失败12:申请退款中13:退款审核中14:退款成功22:申请退货中23:退货审核中24:退货成功
                    String order_state = list.get(parentPosition).getState();
                    viewHolder.applay_state_text.setText(OrderStates.parseOrderState(parent.getResources(), order_state));
                    double weight = Double.parseDouble(orderGoodsLists.get(position).getWeight());
                    viewHolder.textWeight.setText((int) weight + orderGoodsLists.get(position).getUnit()
                            + "*" + orderGoodsLists.get(position).getNumber());
                    viewHolder.textName.setText(orderGoodsLists.get(position).getGoods_name());
                    String money;
                    if (list.get(parentPosition).getCycleflag().equals("01")) {
                        money = list.get(parentPosition).getOrder_total_money();
                    } else {
                        money = orderGoodsLists.get(position).getGoodsTotal_amount();
                    }
                    viewHolder.textMoney.setText(parent.getContext().getResources().getString(R.string.discount_money)
                            + money + "");
                    Util.imageTransform(parent.getContext(), viewHolder.order_detail_image, orderGoodsLists.get(position).getPicUrl());

                    /**
                     * 注释：
                     * 第一层if: 判断是orderApplayEditDialog的adapter还是OrderApplayAfterDialog的adapter，二者的区别就是viewHolderApplay_linearLayout（viewHolder.imageButton）（条目）是否有点击事件
                     * 第二层if：判断图标是否显示 （viewHolder.imageButton是够显示）
                     * 第三层if：判断图标的状态 选中状态 或者 未选中状态（设置viewHolder.imageButton的值）
                     */
                    if (OrderStates.STATE_NUMBER2.equals(stateClick)) {//如果stateClick值为2  就可点击 该if是属于判断orderApplayEditDialog还是OrderApplayAfterDialog，因为该Adapter公用
//
                        if ("1".equals(orderGoodsLists.get(position).getSelectState())) {//设置按钮的值 选中状态
                            viewHolder.imageButton.setImageResource(R.drawable.order_confirm_button);
                        } else {//未选中没状态
                            viewHolder.imageButton.setImageResource(R.drawable.order_cancel_button);
                        }
                        viewHolder.applay_linearlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //传日两个position
//                                OrderApplayBean orderApplayBean = new OrderApplayBean();
//                                orderApplayBean.setItemPosition(position);
//                                orderApplayBean.setPosition(parentPosition);
                                EventBus.getDefault().post(new EventMsg(OrderApplayAdapter.class, parentPosition + "", position + ""));
                            }
                        });

                    } else if (OrderStates.STATE_NUMBER1.equals(stateClick)) {//OrderApplyAfterDiaolg的adapter
//                        viewHolder.imageButton.setVisibility(View.VISIBLE);
                        if ("1".equals(orderGoodsLists.get(position).getSelectState())) {
                            viewHolder.imageButton.setImageResource(R.drawable.order_confirm_button);
                        } else {
                            viewHolder.imageButton.setImageResource(R.drawable.order_cancel_button);//图标都是灰色状态
                        }
                    }
                    break;
                }
                case TYPE_2: {
                    viewHolder2.textWeight.setText(orderGoodsLists.get(position).getWeight() + orderGoodsLists.get(position).getUnit()
                            + "*" + orderGoodsLists.get(position).getNumber());
                    viewHolder2.textName.setText(orderGoodsLists.get(position).getGoods_name());
                    viewHolder2.textMoney.setText(orderGoodsLists.get(position).getGoodsTotal_amount() + "");
                    Util.imageTransform(parent.getContext(), viewHolder2.order_detail_image,orderGoodsLists.get(position).getPicUrl());

                    /**
                     * 注释：
                     * 第一层if: 判断是orderApplayEditDialog的adapter还是OrderApplayAfterDialog的adapter，二者的区别就是viewHolderApplay_linearLayout（viewHolder.imageButton）（条目）是否有点击事件
                     * 第二层if：判断图标是否显示 （viewHolder.imageButton是够显示）
                     * 第三层if：判断图标的状态 选中状态 或者 未选中状态（设置viewHolder.imageButton的值）
                     */
                    if (OrderStates.STATE_NUMBER2.equals(stateClick)) {//如果stateClick值为2  就可点击 该if是属于判断orderApplayEditDialog还是OrderApplayAfterDialog，因为该Adapter公用
//
                        if ("1".equals(orderGoodsLists.get(position).getSelectState())) {//设置按钮的值 选中状态
                            viewHolder2.imageButton.setImageResource(R.drawable.order_confirm_button);
                        } else {//未选中没状态
                            viewHolder2.imageButton.setImageResource(R.drawable.order_cancel_button);
                        }
                        viewHolder2.applay_linearlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //传日两个position
//                                OrderApplayBean orderApplayBean = new OrderApplayBean();
//                                orderApplayBean.setItemPosition(position);
//                                orderApplayBean.setPosition(parentPosition);
                                EventBus.getDefault().post(new EventMsg(OrderApplayAdapter.class, parentPosition + "", position + ""));
                            }
                        });

                    } else if (OrderStates.STATE_NUMBER1.equals(stateClick)) {//OrderApplyAfterDiaolg的adapter

//                        viewHolder2.imageButton.setVisibility(View.VISIBLE);
                        if ("1".equals(orderGoodsLists.get(position).getSelectState())) {
                            viewHolder2.imageButton.setImageResource(R.drawable.order_confirm_button);
                        } else {
                            viewHolder2.imageButton.setImageResource(R.drawable.order_cancel_button);//图标都是灰色状态
                        }
                    }
                    break;
                }
            }


            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_1;
            } else {
                return TYPE_2;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }


    class ListViewHolder {
        public CustomListView listView;
    }

    class MyViewHolder {
        public TextView textorder_id;
        public TextView textName;
        public TextView textWeight;
        public TextView textMoney;
        public ImageView imageButton;
        public LinearLayout applay_linearlayout;
        public ImageView order_detail_image;
        public TextView applay_state_text;
    }

    class MyViewHolder2 {
        public TextView textName;
        public TextView textWeight;
        public TextView textMoney;
        public ImageView imageButton;
        public LinearLayout applay_linearlayout;
        public ImageView order_detail_image;
    }
}

