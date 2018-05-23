package com.zyf.bings.bingos.address;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.address.bean.AddressList;
import com.zyf.bings.bingos.base.BaseFragment;
import com.zyf.bings.bingos.cart.DeleteDialog;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.goods.events.AddressChangeEvent;
import com.zyf.bings.bingos.ui.statelayout.ProgressFrameLayout;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.PBUtil;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhangyifei on 17/9/1.
 */

public class AddressFragment extends BaseFragment implements AddressAdapter.UpdateSuccess,
        AddAddressFragment.AddSuccess, IAddressContract.AddressView {


    @BindView(R.id.iv_address_user_logo)
    ImageView mIvUserLogo;
    @BindView(R.id.tv_address_commonmsg)
    TextView mTvCommonmsg;
    @BindView(R.id.tv_address_countmsg)
    TextView mTvCountmsg;
    @BindView(R.id.btn_address_add)
    Button mBtnAddAdress;
    Unbinder unbinder;
    @BindView(R.id.recycler_address_list)
    RecyclerView mRecyclerAddressList;

    IAddressContract.AddressPresenter mAddressPresenter;


    List<AddressList.MemberAddressListBean> mAddressList;
    @BindView(R.id.tv_address_edit)
    TextView mTvAddressEdit;
    @BindView(R.id.tv_address_delete)
    TextView mTvAddressDelete;
    @BindView(R.id.tv_address_delete_cancle)
    TextView mTvAddressDeleteCancle;
    @BindView(R.id.linear_edit_container)
    LinearLayout mLinearEditContainer;
    @BindView(R.id.linear_address_null)
    LinearLayout mLinearAddressNull;
    @BindView(R.id.frame_address_progress)
    ProgressFrameLayout mFrameAddressProgress;
    private AddressAdapter mAddressAdapter;
    private String mMemberId;
    private LinearLayoutManager mLinearLayoutManager;


    public static void start(int resId, FragmentManager manager) {
        if (resId == 0 || manager == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "AddressFragment";
        AddressFragment fragment = (AddressFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof AddressFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }

        if (fragment == null) {
            fragment = AddressFragment.newInstance();
            transaction.add(resId, fragment, TAG);
            transaction.addToBackStack(null);
        } else {
            if (!fragment.isHidden()) {
                transaction.commitAllowingStateLoss();
                EventBus.getDefault().postSticky(new MainTitleEvent("收货地址"));
                return;
            }
            transaction.show(fragment);
        }

        transaction.commitAllowingStateLoss();
        EventBus.getDefault().postSticky(new MainTitleEvent("收货地址"));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
            EventBus.getDefault().postSticky(new MainTitleEvent("收货地址"));
        }
    }

    public static AddressFragment newInstance() {
        Bundle args = new Bundle();
        AddressFragment fragment = new AddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.address_manager_fragment, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        setPresenter();
        initData();
        return inflate;
    }

    private void initData() {
        if (mAddressPresenter != null) mAddressPresenter.subscribe();
        mAddressPresenter.getAddressList(PBUtil.getPD(getActivity()));
        mAddressList = new ArrayList<>();
        mMemberId = SPUtil.getString(getActivity(), Config.MEMBER_ID);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAddressAdapter = new AddressAdapter(mAddressList);
        mAddressAdapter.setHide(true);
        mRecyclerAddressList.setLayoutManager(mLinearLayoutManager);
        mRecyclerAddressList.setAdapter(mAddressAdapter);
        mAddressAdapter.setUpdateSuccess(this);
        mAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddAddressFragment.start(getFragmentManager(), AddressFragment.this, (AddressList.MemberAddressListBean) adapter.getItem(position));
            }
        });
    }

    @OnClick({R.id.btn_address_add, R.id.tv_address_edit,
            R.id.tv_address_delete, R.id.tv_address_delete_cancle})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_address_add:
                AddAddressFragment.start(getFragmentManager(), this, null);
                break;
            case R.id.tv_address_edit:
                mTvAddressEdit.setVisibility(View.GONE);
                mLinearEditContainer.setVisibility(View.VISIBLE);
                mAddressAdapter.setHide(false);
                mAddressAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_address_delete:
                deleteAddress();
                break;
            case R.id.tv_address_delete_cancle:
                mTvAddressEdit.setVisibility(View.VISIBLE);
                mLinearEditContainer.setVisibility(View.GONE);
                mAddressAdapter.setHide(true);
                mAddressAdapter.notifyDataSetChanged();
                break;
            default:
                break;

        }

    }

    private void deleteAddress() {
        int count = 0;
        for (int i = 0; i < mAddressAdapter.getData().size(); i++) {
            AddressList.MemberAddressListBean memberAddressListBean = mAddressAdapter.getData().get(i);
            if (memberAddressListBean.getIsSelect() == 1) {
                count++;
            }
        }
        if (count == 0) {
            ToastUtils.showToast("请选择要删除的地址.");
            return;
        }

        StringBuffer mAddressIds = new StringBuffer();


        final DeleteDialog deleteDialog = DeleteDialog.newInstance();
        if (!deleteDialog.isAdded()) {
            deleteDialog.show(getActivity().getSupportFragmentManager(), "Delete");
            deleteDialog.setOnDeleteItem(new DeleteDialog.OnDeleteItem() {
                @Override
                public void onDeleteItem() {
                    for (int i = 0, len = mAddressAdapter.getData().size(); i < len; i++) {
                        if (mAddressAdapter.getData().get(i).getIsSelect() == 1) {
                            mAddressIds.append("," + mAddressAdapter.getData().get(i).getAddressid());
                        }
                    }
                    if (mAddressIds.length() > 1) {
                        mAddressPresenter.deleteAddress(mAddressIds.toString().substring(1));//去掉第一个逗号
                    }
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mAddressPresenter != null) {
            mAddressPresenter.unsubscribe();
            mAddressPresenter = null;
        }
    }


    @Override
    public void setPresenter() {
        mAddressPresenter = new AddressPresenter(this);
    }

    /**
     * @param addressBean
     * @param holder      为了动画效果  本地做了 数据刷新 没有重新请求借口
     */
    @Override
    public void updateSuccess(AddressList.MemberAddressListBean addressBean, BaseViewHolder holder) {
        int layoutPosition = holder.getLayoutPosition();
        int change = mAddressAdapter.getData().indexOf(addressBean);
        Log.e("RxOkClient", "updateSuccess: " + mAddressAdapter.getData());

        //改变默认地址参数
        mAddressAdapter.getData().get(0).setUsed("0");
        addressBean.setUsed("1");
        //保持内外数据集同步
        mAddressAdapter.notifyItemMoved(layoutPosition, 0);
        mAddressAdapter.getData().remove(change);
        mAddressAdapter.getData().add(0, addressBean);
        mAddressAdapter.notifyItemRangeChanged(0, layoutPosition + 1);  //变换是否为默认地址颜色


        // 解决设置成功后没有滑动到顶部问题
        mLinearLayoutManager.scrollToPosition(0);


        Log.e("RxOkClient", "updateSuccess: layoutPosition " + layoutPosition);

        Log.e("RxOkClient", "updateSuccess: " + mAddressAdapter.getData());
    }

    @Override
    public void addSuccess() {  //重新刷新数据
        mAddressPresenter.getAddressList(PBUtil.getPD(getActivity()));
    }

    @Override
    public void notifyAddressRefresh(List<AddressList.MemberAddressListBean> addressListBeen) {
        mFrameAddressProgress.showContent();
        if (addressListBeen.size() == 0) {
            mRecyclerAddressList.setVisibility(View.GONE);
            mLinearAddressNull.setVisibility(View.VISIBLE);
        } else {
            mRecyclerAddressList.setVisibility(View.VISIBLE);
            mLinearAddressNull.setVisibility(View.GONE);
        }
        mAddressAdapter.setNewData(addressListBeen);

        if (addressListBeen != null && addressListBeen.size() == 1) {
            AddressList.MemberAddressListBean memberAddressListBean = addressListBeen.get(0);
            AddressChangeEvent event = new AddressChangeEvent();
            event.setMemberAddressListBean(memberAddressListBean);
            EventBus.getDefault().postSticky(event);
        }
    }

    @Override
    public void onErr(String errMsg) {
        mFrameAddressProgress.showError(R.mipmap.syn_error_icon, "加载失败...", errMsg, "点击重试", v -> {
            mAddressPresenter.getAddressList(PBUtil.getPD(getActivity()));
        });
    }

    @Override
    public void deleteSuccess() { //重新刷新数据
        mAddressPresenter.getAddressList(null);
    }

    @Override
    public void deleteError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }
}
