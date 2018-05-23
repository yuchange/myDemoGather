package com.bingstar.bingmall.user.addr.AddrManage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseRecycleAdapter;
import com.bingstar.bingmall.base.EventMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by John on 2017/3/3.
 */

public class AddrManageAdapter extends RecyclerView.Adapter<AddrManageAdapter.AddrManageView> {
    private List<AddrManageInfoBean.MemberAddress> addrManageInfoList;

    private Context context;

    private View view;

    private setOnClickItemListener onclickItemListener;

    private final static int number1 = 1;

    private final static int number2 = 2;

    public void setClickItemListener(setOnClickItemListener onclickItemListener) {
        this.onclickItemListener = onclickItemListener;
    }

    public AddrManageAdapter(List<AddrManageInfoBean.MemberAddress> addrManageInfoList) {
        this.addrManageInfoList = addrManageInfoList;
    }

    @Override
    public int getItemViewType(int position) {
        //判断以什么结束的属性
        if (position == 0) {
            return number2;
        }

        return number1;
    }

    @Override
    public AddrManageView onCreateViewHolder(ViewGroup parent, int viewType) {
        View createView = null;
        if (viewType == number1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addrmanage_item, parent,
                    false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addrmanage_item2, parent,
                    false);
        }

        final AddrManageView addrManageView = new AddrManageView(view, viewType);
        context = parent.getContext();

        /**
         * 设置默认地址
         */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickItemListener.OnClickItemListener(addrManageView.itemView, addrManageView.getLayoutPosition());
            }
        });


        return addrManageView;
    }

    @Override
    public void onBindViewHolder(AddrManageView holder, final int position) {

        switch (getItemViewType(position)) {
            case number1: {

                AddrManageInfoBean.MemberAddress memberAddress = addrManageInfoList.get(position-1);

                if (memberAddress.getUsed().equals("1")) {
                    holder.relativelayout.setBackgroundResource(R.drawable.addr_item_select);
                    holder.imageDelete.setImageResource(R.drawable.addr_delete_button);
                    holder.imageEdit.setImageResource(R.drawable.addr_edit_button);
                    holder.text_place.setTextColor(context.getResources().getColor(R.color.addr_manage_adapter));
                    holder.text_phone.setTextColor(context.getResources().getColor(R.color.addr_manage_adapter));
                    holder.text_name.setTextColor(context.getResources().getColor(R.color.addr_manage_adapter));
                    EventBus.getDefault().post(new EventMsg(AddrManageFragment.class, addrManageInfoList.get(position-1).getDetailed(), addrManageInfoList.get(position-1).getName(), addrManageInfoList.get(position-1).getMobile()));
                } else {
                    holder.relativelayout.setBackgroundResource(R.drawable.addr_item_unselect);
                    holder.imageDelete.setImageResource(R.drawable.addr_delete_un);
                    holder.imageEdit.setImageResource(R.drawable.addr_edit_un);
                    holder.text_name.setTextColor(context.getResources().getColor(R.color.addr_manage_adapter_name));
//            holder.text_phone.setTextColor(Color.rgb(179, 179, 179));
                    holder.text_phone.setTextColor(context.getResources().getColor(R.color.addr_manage_adapter_phone));
                    holder.text_place.setTextColor(context.getResources().getColor(R.color.addr_manage_adapter_phone));
                }
                holder.text_name.setText(memberAddress.getName());
                holder.text_phone.setText(memberAddress.getMobile());
                holder.text_place.setText(memberAddress.getDetailed());

                /**
                 * 删除地址
                 */
                holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EventBus.getDefault().post(new EventMsg(AddrManageAdapter.class, (position-1) + ""));

                    }
                });
                /**
                 * 修改地址
                 */
                holder.imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddrManageInfoBean.MemberAddress memberAddress = addrManageInfoList.get(position-1);
                        memberAddress.setFlug("1");
                        EventBus.getDefault().post(new EventMsg(AddrManageAdapter.class, memberAddress));
                    }
                });
                break;
            }
            case number2: {
                holder.addrmanageimagelast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //添加收货地址
                        if (addrManageInfoList != null) {
                            AddrManageInfoBean.MemberAddress memberAddress = (new AddrManageInfoBean()).getMemberAddress();
                            memberAddress.setFlug("2");
                            EventBus.getDefault().post(new EventMsg(AddrManageAdapter.class, memberAddress));
                        }
                    }
                });
                break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return addrManageInfoList.size() + 1;
    }


    public class AddrManageView extends RecyclerView.ViewHolder {

        private ImageView imageEdit, imageDelete;
        private TextView text_name, text_phone, text_place, addrmanageimagelast;
        private RelativeLayout relativelayout, relativelayout_click;
        private int tag;

        public AddrManageView(View itemView, int viewType) {
            super(itemView);
            if (viewType == number1) {
                imageEdit = (ImageView) itemView.findViewById(R.id.addrmanage_edit_image);
                imageDelete = (ImageView) itemView.findViewById(R.id.addrmanage_delete_image);
                text_name = (TextView) itemView.findViewById(R.id.addrmanage_name);
                text_place = (TextView) itemView.findViewById(R.id.addrmanage_place);
                text_phone = (TextView) itemView.findViewById(R.id.addrmanage_phone);
                relativelayout = (RelativeLayout) itemView.findViewById(R.id.relayout);
                relativelayout_click = (RelativeLayout) itemView.findViewById(R.id.relayout_click);
            } else {
                addrmanageimagelast = (TextView) itemView.findViewById(R.id.addrmanage_image_last);
            }
        }

    }

    public interface setOnClickItemListener {
        void OnClickItemListener(View view, int position);
    }
}