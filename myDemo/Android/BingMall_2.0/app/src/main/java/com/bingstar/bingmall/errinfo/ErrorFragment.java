package com.bingstar.bingmall.errinfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.AdsFragment;
import com.bingstar.bingmall.base.BaseFragment;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.cart.CartFragment;
import com.bingstar.bingmall.cart.OneKeyCartFragment;
import com.bingstar.bingmall.collect.CollectFragment;
import com.bingstar.bingmall.cycle.CycleFragment;
import com.bingstar.bingmall.discount.DiscountFragment;
import com.bingstar.bingmall.goods.GoodsDetailFragment;
import com.bingstar.bingmall.goods.GoodsListFragment;
import com.bingstar.bingmall.order.OrderApplyAfter.OrderApplyAfterDialog;
import com.bingstar.bingmall.order.OrderFragment;
import com.bingstar.bingmall.order.view.OrderInfoFragment;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by qianhechen on 17/2/24.
 */

public class ErrorFragment extends BaseFragment {

    private Button err_review_btn;
    private int isFrom;

    public static final int GoodsListFragment = 1;
    public static final int GoodsDetailFragment = 2;
    public static final int DisCountFragment = 3;
    public static final int CartFragment = 4;
    public static final int CycleListFragment = 5;
    public static final int OrderFragment = 6;
    public static final int AddrMangetFragment = 7;
    public static final int AdsFragment = 8;
    public static final int HomeFragment = 9;


    public static final int CycleDialog = 10;
    public static final int AddManageAddrFragment = 11;
    public static final int CollectFragment = 12;

    public static final int OrderInfoFragment = 13;

    public static final int OrderApplayAfter = 14;

    public static final int OneKeyCartFragment = 15;


    public static void start(int resId, FragmentManager manager, int from) {
        if (resId == 0 || manager == null || from == 0) {
            throw new IllegalArgumentException("params can not be null");
        }
        final String TAG = "Error";
        ErrorFragment fragment = (ErrorFragment) manager.findFragmentByTag(TAG);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            fragment = new ErrorFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESID, resId);
            bundle.putInt(KEY_FROM, from);
            fragment.setArguments(bundle);
            transaction.add(resId, fragment, TAG);
            transaction.addToBackStack("Error");
        } else {
            if (!fragment.isHidden()) {
                transaction.commit();
                return;
            }
            transaction.show(fragment);
        }
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if (!(frag instanceof ErrorFragment) && frag != null) {
                    if (!frag.isHidden()) {
                        transaction.hide(frag);
                    }
                }
            }
        }
        transaction.commit();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void close(int resId, FragmentManager manager, int from) {
        final String TAG = "Error";

        ErrorFragment fragment = (ErrorFragment) manager.findFragmentByTag(TAG);

        FragmentTransaction transaction = manager.beginTransaction();
//        if (fragment == null) {
//            fragment = new ErrorFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt(KEY_RESID, resId);
//            fragment.setArguments(bundle);
//            transaction.add(resId, fragment, TAG);
//        } else {
        transaction.hide(fragment);
//        }

        List<Fragment> fragments = manager.getFragments();

        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                switch (from) {
                    case 1:
                        if (frag instanceof GoodsListFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 3:
                        if (frag instanceof DiscountFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 4:
                        if (frag instanceof CartFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 5:
                        if (frag instanceof CycleFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 6:
                        if (frag instanceof OrderFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 7:
                        if (frag instanceof AddrManageFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 8:
                        if (frag instanceof AdsFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 12:
                        if (frag instanceof CollectFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 13:
                        if (frag instanceof OrderInfoFragment) {
                            transaction.show(frag);
                            break;
                        }
                    case 15:
                        if (frag instanceof OneKeyCartFragment) {
                            transaction.show(frag);
                            break;
                        }
                }
            }
//            for (Fragment frag : fragments) {
//                LogUtils.Debug_E(ErrorFragment.class,":::"+frag);
//                if (!(frag instanceof ErrorFragment)&&frag!=null) {
//                    transaction.show(frag);
//                }
//            }
//            for(int i = fragments.size()-1;i>=0;i--){
//                LogUtils.Debug_E(ErrorFragment.class,":::"+fragments.get(i));
//                if(!(fragments.get(i) instanceof ErrorFragment)&&!(fragments.get(i) instanceof SupportRequestManagerFragment)&&fragments.get(i)!=null){
//                    transaction.show(fragments.get(i));
//                    break;
//                }
//            }
        }
        manager.popBackStack("Error", 1);
//        int num = manager.getBackStackEntryCount();
//        String numString = "Fragment回退栈数量："+num;
//        for (int i = 0; i < num; i++) {
//            FragmentManager.BackStackEntry backstatck = manager.getBackStackEntryAt(i);
//            LogUtils.Debug_E(ErrorFragment.class, "iiiii11:"+numString+","+backstatck.getName());
//        }
        transaction.commit();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.err_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.err_tv);
        err_review_btn = (Button) view.findViewById(R.id.err_review_btn);
        isFrom = getArguments().getInt(KEY_FROM);
        textView.setText("加载失败了!");
        err_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().post(new EventMsg(ErrorFragment.class,String.valueOf(isFrom)));
                //getFragmentManager().popBackStack();

//                int num = getFragmentManager().getBackStackEntryCount();
//                String numString = "Fragment回退栈数量："+num;
//                for (int i = 0; i < num; i++) {
//                    FragmentManager.BackStackEntry backstatck = getFragmentManager().getBackStackEntryAt(i);
//                    LogUtils.Debug_E(ErrorFragment.class, "iiiii:"+numString+","+backstatck.getName());
//                }
                try {
                    close(R.id.main_fragment, getFragmentManager(), isFrom);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void setPresenter() {

    }
}
