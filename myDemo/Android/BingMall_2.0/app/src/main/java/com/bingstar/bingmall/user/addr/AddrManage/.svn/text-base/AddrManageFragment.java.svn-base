package com.bingstar.bingmall.user.addr.AddrManage;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.WrapGridLayoutManager;
import com.bingstar.bingmall.cart.CartFragment;
import com.bingstar.bingmall.cart.bean.DeleteDialogBean;
import com.bingstar.bingmall.cart.view.DeleteDialog;
import com.bingstar.bingmall.errinfo.ErrorFragment;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.OrderFragment;
import com.bingstar.bingmall.user.addr.AddOrderAddr.AddAddrInfo;
import com.bingstar.bingmall.user.addr.AddOrderAddr.AddAddrOrderClient;
import com.bingstar.bingmall.user.addr.AddOrderAddr.AddAddrPresenter;
import com.bingstar.bingmall.user.addr.AddOrderAddr.AddOrderAddrDialog;
import com.bingstar.bingmall.user.addr.AddOrderAddr.AddOrderAddrFragment;
import com.bingstar.bingmall.user.addr.AddrStates;
import com.bingstar.bingmall.user.bean.User;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liumengqiang on 2017/3/3.
 */

public class AddrManageFragment extends BaseFragment implements IAddrManageContract.IAddrManageView {
    private RecyclerView recyclerView;

    private ArrayList<AddrManageInfoBean.MemberAddress> addrManageInfoList;

    private IAddrManageContract.IAddrManagePresenter iAddrManagePresenter;

    private AddrManageAdapter addrManageAdapter;//适配器

    private FragmentTransaction transaction;

    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "AddrManage";
        AddrManageFragment fragment = (AddrManageFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new AddrManageFragment();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setParentResId(getArguments().getInt(KEY_RESID));
        View view = inflater.inflate(R.layout.addrmanage_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.addr_recycleview);
        addrManageInfoList = new ArrayList<>();
        setPresenter();
        WrapGridLayoutManager gridLayoutManager = new WrapGridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        addrManageAdapter = new AddrManageAdapter(addrManageInfoList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(addrManageAdapter);
        EventBus.getDefault().register(this);
        addrManageAdapter.setClickItemListener(new AddrManageAdapter.setOnClickItemListener() {
            @Override
            public void OnClickItemListener(View view, int position) {
                if (position == 0) {
                    return;
                }
                /**
                 * 默认地址设定
                 */
                AddrManageInfoBean.MemberAddress memberAddress = addrManageInfoList.get(position-1);
                iAddrManagePresenter.getUpdateUsedHttp(memberAddress, addrManageInfoList, position-1);
                EventBus.getDefault().post(new EventMsg(AddrManageFragment.class, addrManageInfoList.get(position-1).getDetailed(), addrManageInfoList.get(position-1).getName(), addrManageInfoList.get(position-1).getMobile()));
            }
        });
        //iAddrManagePresenter.getHttpDate(User.getIntance().getMemberId(), addrManageInfoList);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        iAddrManagePresenter.getHttpDate(BeanTranslater.getUserId(User.getIntance().getMemberId(), ErrorFragment.AddrMangetFragment), addrManageInfoList);
    }

    @Subscribe
    public void deleteDialogEvent(DeleteDialogBean deleteDialogBean) {
        if (AddrStates.DELETE_CONFIRM.equals(deleteDialogBean.getConfirmOrCancel()) && AddrStates.DELETE_ADDR.equals(deleteDialogBean.getDeleteFlug())) {
            /**
             * deleteDialog发送的EventBus
             */
            Map<String, String> map = new ArrayMap<String, String>();
            map.put(AddrStates.ADDRESSID, addrManageInfoList.get(position).getAddressid());
            map.put(AddrStates.MEMBER_ID, addrManageInfoList.get(position).getMember_id());
            AddAddrOrderClient.getClientDELETE(map, new ClientCallback<AddAddrInfo>() {
                @Override
                public void onSuccess(AddAddrInfo addAddrInfo) {
                    iAddrManagePresenter.getHttpDate(BeanTranslater.getUserId(User.getIntance().getMemberId(), ErrorFragment.AddrMangetFragment), addrManageInfoList);
                }

                @Override
                public void onFail(int code, String error) {
                    error();
                }
            });
        }
    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(BeanTranslater.class) && msg.getMsg().equals(String.valueOf(ErrorFragment.AddrMangetFragment))) {
            error();
        } else if (msg.getClassName().equals(AddrManageAdapter.class) && (msg.getObj() != null)) {
            /**
             * 添加收货地址和修改收货地址
             */
            AddOrderAddrFragment.start(R.id.main_fragment, msg.getObj(), getFragmentManager());

        } else if (msg.getClassName().equals(AddrManageAdapter.class) && (msg.getMsg() instanceof String)) {
            /**
             * 删除收货地址
             * 思路：1：Adapter传入的position记录下来
             * 2： 弹出删除对话框
             * 3：接受confirm和cancel的EventBus，处理业务逻辑
             */
            setPosition(Integer.parseInt(msg.getMsg()));
            DeleteDialog deleteDialog = DeleteDialog.newInstance(getResources().getString(R.string.add_order_manager_delete), AddrStates.DELETE_ADDR);
            deleteDialog.show(getChildFragmentManager(), "DeleteDialog");
        } else if (msg.getClassName().equals(AddOrderAddrFragment.class)) {
            addrManageInfoList.clear();
            iAddrManagePresenter.getHttpDate(BeanTranslater.getUserId(User.getIntance().getMemberId(), ErrorFragment.AddrMangetFragment), addrManageInfoList);

            /**
             * 直接将添加的设置成默认地址
             */
        }
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (null != iAddrManagePresenter) {
            iAddrManagePresenter.unBind();
        }
    }

    @Override
    public void notifyDatasetChange() {
        /**
         * 初始化User
         */
        if (User.getIntance().getName() == null) {
            AddrManagePresenter.getHttpDateUser(User.getIntance().getMemberId());
        }

        addrManageAdapter.notifyDataSetChanged();
    }


    public void error() {
        ErrorFragment.start(R.id.main_fragment, getFragmentManager(), ErrorFragment.AddrMangetFragment);
    }

    @Override
    public void setPresenter() {
        iAddrManagePresenter = new AddrManagePresenter(this);
    }

}

