package com.zyf.bings.bingos.address;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.address.http.AddressStates;
import com.zyf.bings.bingos.business.UserBusiness;
import com.zyf.bings.bingos.goods.events.AddressChangeEvent;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos_libnet.RxOkClient;
import com.zyf.bings.bingos_libnet.action.WebAction;
import com.zyf.bings.bingos_libnet.constant.BingNetStates;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyifei on 17/9/1.
 */

public class AddressAdapter extends BaseQuickAdapter<AddressList.MemberAddressListBean, BaseViewHolder> {


    public boolean hide;  //是否隐藏编辑按钮

    public AddressAdapter(@Nullable List<AddressList.MemberAddressListBean> data) {
        super(R.layout.address_item, data);
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final AddressList.MemberAddressListBean item) {
        holder.setText(R.id.tv_address_username, item.getName());
        holder.setText(R.id.tv_address_usertel, item.getMobile());
        holder.setText(R.id.tv_address_useraddress, item.getRegion());
        if (item.getUsed().equals("1")) {
            holder.setText(R.id.btn_address_default, "默认地址");
            holder.setBackgroundRes(R.id.btn_address_default, R.mipmap.address_btn_bgo);
        } else {
            holder.setText(R.id.btn_address_default, "设为默认地址");
            holder.setBackgroundRes(R.id.btn_address_default, R.mipmap.address_btn_bgb);
        }

        holder.getView(R.id.btn_address_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!item.getUsed().equals("1")) {
                    updateAddress(item, holder);
                } else {
                    ToastUtils.showToast("已经是默认地址啦！！");
                }

            }
        });
        final ImageView IvSelect = holder.getView(R.id.iv_address_select);
        IvSelect.setTag(2);
        item.setIsSelect(2);
        IvSelect.setImageResource(R.mipmap.shopcart_goodsitem_unselect);


        if (hide) {
            IvSelect.setVisibility(View.INVISIBLE);
        } else {
            IvSelect.setVisibility(View.VISIBLE);
        }

        IvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag instanceof Integer) {
                    int flag = (int) tag;
                    switch (flag) {
                        case 1:
                            IvSelect.setTag(2);
                            IvSelect.setImageResource(R.mipmap.shopcart_goodsitem_unselect);
                            item.setIsSelect(2);
                            break;
                        case 2:
                            IvSelect.setTag(1);
                            IvSelect.setImageResource(R.mipmap.shopcart_goodsitem_select);
                            item.setIsSelect(1);
                            break;
                        default:

                    }
                }
            }
        });

    }

    /**
     * 修改默认地址
     */
    private final static String TITLE_SET = "/memberAddressUpdateUsed.shtml";
    private final static String METHOD_SET = "memberAddress.updateUsed";

    public void updateAddress(final AddressList.MemberAddressListBean addressBean, final BaseViewHolder holder) {

        Map<String, String> params = new ArrayMap<>();
        params.put(AddressStates.ADDRESSID, addressBean.getAddressid());
        params.put(AddressStates.MEMBER_ID, addressBean.getMember_id());
        params.put(AddressStates.USED, "1");//是否设为常用地址(设定值为：1)

        Map<String, String> map = new ArrayMap<String, String>();
        map.put(BingNetStates.METHOD, METHOD_SET);
        map.put(BingNetStates.REQUEST_DATA, GsonFactory.map2Json(params));

        RxOkClient.doPost(map, TITLE_SET, getClass().getSimpleName(), PBUtil.getPD(mContext))
                .subscribe(new WebAction() {
                    @Override
                    public void onSuccess(String data) {
                        if (mUpdateSuccess != null) {
                            mUpdateSuccess.updateSuccess(addressBean, holder);
                        }
                        UserBusiness.saveAddressData(addressBean);  //暂时没用

                        AddressChangeEvent event = new AddressChangeEvent();
                        event.setMemberAddressListBean(addressBean);
                        EventBus.getDefault().postSticky(event);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showToast("修改默认地址错误");
                    }
                });
    }


    public void setUpdateSuccess(UpdateSuccess updateSuccess) {
        mUpdateSuccess = updateSuccess;
    }

    UpdateSuccess mUpdateSuccess;

    interface UpdateSuccess {
        void updateSuccess(AddressList.MemberAddressListBean addressBean, BaseViewHolder position);
    }

}
