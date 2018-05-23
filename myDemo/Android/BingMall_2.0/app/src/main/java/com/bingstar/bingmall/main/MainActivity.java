package com.bingstar.bingmall.main;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bingstar.bingmall.BuildConfig;
import com.bingstar.bingmall.R;
import com.bingstar.bingmall.ads.AdsFragment;
import com.bingstar.bingmall.base.BaseActivity;
import com.bingstar.bingmall.base.EventMsg;
import com.bingstar.bingmall.base.Util;
import com.bingstar.bingmall.cart.CartFragment;
import com.bingstar.bingmall.cart.OneKeyCartFragment;
import com.bingstar.bingmall.cart.bean.OneKeyCartOrder;
import com.bingstar.bingmall.cart.http.CartClient;
import com.bingstar.bingmall.cart.http.CartStates;
import com.bingstar.bingmall.cart.view.DiscountCardFragment;
import com.bingstar.bingmall.collect.CollectFragment;
import com.bingstar.bingmall.discount.DiscountFragment;
import com.bingstar.bingmall.goods.bean.OneKeyEvent;
import com.bingstar.bingmall.home.HomeTitleView;
import com.bingstar.bingmall.main.bean.ActivePage;
import com.bingstar.bingmall.main.bean.LoginEvent;
import com.bingstar.bingmall.main.bean.PayEvent;
import com.bingstar.bingmall.main.bean.ToastEvent;
import com.bingstar.bingmall.main.bottom.CircleMenuLayout;
import com.bingstar.bingmall.main.http.MainClient;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.OrderFragment;
import com.bingstar.bingmall.sdk.map.BaiduMapManager;
import com.bingstar.bingmall.user.addr.AddrManage.AddrManageFragment;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.pay.AliPayManager;
import com.bingstar.bingmall.user.pay.HaierPayManager;
import com.bingstar.bingmall.user.pay.IPayManager;
import com.bingstar.bingmall.user.pay.PayStates;
import com.yunzhi.lib.utils.L;
import com.yunzhi.lib.view.OnSingleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private CircleMenuLayout menuLayout;

    private Toast toast;
    private RelativeLayout mBottom;
    private static final String GOODS_ID = "goods_id";
    private static final String COUNT = "count";
    private Button mCouponInfo;

    public static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.init(BuildConfig.DEBUG);
        BaiduMapManager.getInstance(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        EventBus.getDefault().register(this);
        /**
         * 加载城市地址
         */
        // LoadAddr.getValue(this);
        initView();
        initListener();
        /**
         * 今日特惠
         */
        menuLayout.setViewOneClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFragment.start(R.id.main_fragment, getSupportFragmentManager());
            }
        });
        /**
         * 地址管理
         */
        menuLayout.setViewTwoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.checkMemberId(MainActivity.this, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        AddrManageFragment.start(R.id.main_fragment, getSupportFragmentManager());
                    }
                });
            }
        });

        //优惠券页面
        menuLayout.setViewThreeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.checkMemberId(MainActivity.this, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        DiscountCardFragment.start(R.id.main_fragment, getSupportFragmentManager());
                        mCouponInfo.setVisibility(View.GONE);
                        menuLayout.badgeView(false);
                    }
                });
            }
        });
        //点赞页面
        menuLayout.setViewFourClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.checkMemberId(MainActivity.this, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        CollectFragment.start(R.id.main_fragment, getSupportFragmentManager());
                    }
                });
            }
        });
        /**
         * 购物车
         */
        findViewById(R.id.main_right_cart).setOnClickListener(new OnSingleClickListener() {

            @Override
            public void onSingleClick(View view) {
                User.checkMemberId(MainActivity.this, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        CartFragment.start(R.id.main_fragment, getSupportFragmentManager());
                    }
                });
            }
        });
        /**
         * 订单列表
         */
        findViewById(R.id.main_right_order).setOnClickListener(new OnSingleClickListener() {

            @Override
            public void onSingleClick(View view) {
                User.checkMemberId(MainActivity.this, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        OrderFragment.start(R.id.main_fragment, getSupportFragmentManager());
                    }
                });
            }
        });
        findViewById(R.id.main_home).setOnClickListener(new OnSingleClickListener() {

            @Override
            public void onSingleClick(View view) {
                finish();
            }
        });
        findViewById(R.id.main_back).setOnClickListener(new OnSingleClickListener() {//回退

            @Override
            public void onSingleClick(View view) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        int type = getIntent().getIntExtra(MainStates.START_KEY, MainStates.START_MAIN);
        String action = getIntent().getAction();
        if (action != null) {  // 海尔启动参数
            if (action.equals("com.bingstar.bingmall.SUPPORT")) {
                type = MainStates.START_SUPPORT;
            } else if (action.equals("com.bingstar.bingmall.DISCOUNTCARD")) {
                type = MainStates.START_DISCOUNT_CARD;
            } else if (action.equals("com.bingstar.bingmallHair.MAIN")) {
                type = MainStates.START_MAIN;
            }
        }
        switch (type) {
            case MainStates.START_MAIN:
                User.getIntance(this).initUser(User.TYPE_BINGMALL);
                break;
            case MainStates.START_ORDER:
                User.getIntance(this).initUser(User.TYPE_BINGMALL_ORDER);
                break;
            case MainStates.START_ADDR:
                User.getIntance(this).initUser(User.TYPE_BINGMALL_ADDR);
                break;
        }
        setDefaultFragment(type);
        getActivePage();
    }

    private void initListener() {
        mBottom.setOnClickListener(this);
    }

    private void initView() {
        menuLayout = (CircleMenuLayout) findViewById(R.id.main_circle_menu);
        mBottom = (RelativeLayout) findViewById(R.id.main_bottom_center_btn_rel);
        mCouponInfo = (Button) findViewById(R.id.coumpon_bottom_info);
    }

    private void getActivePage() {
        Map<String, String> map = new ArrayMap<>();
        map.put(MainStates.MAC_ID, Util.getWifiMac());
        map.put(MainStates.TYPE_ID, User.getIntance(this).getFridgeType());
        MainClient.getCouponActive(map, new ClientCallback<ActivePage>() {
            @Override
            public void onSuccess(ActivePage activePage) {
                if (!TextUtils.isEmpty(activePage.getUrl())) {
                    //新人礼包弹框
                    WebDialog.start(getSupportFragmentManager(), activePage.getCouponIds(), activePage.getUrl());
                }
            }

            @Override
            public void onFail(int code, String error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_bottom_center_btn_rel) {
            Log.e("Main", "点击事件执行了    ");
            menuLayout.toggleMenu();
        }

    }


    private void setDefaultFragment(int type) {
        switch (type) {
            case MainStates.START_MAIN:
                AdsFragment.start(R.id.main_fragment, getSupportFragmentManager());
                break;
            case MainStates.START_ORDER:
                if (checkMemberId()) {
                    OrderFragment.start(R.id.main_fragment, getSupportFragmentManager());
                } else {
                    AdsFragment.start(R.id.main_fragment, getSupportFragmentManager());
                }
                break;
            case MainStates.START_ADDR:
                if (checkMemberId()) {
                    AddrManageFragment.start(R.id.main_fragment, getSupportFragmentManager());
                } else {
                    AdsFragment.start(R.id.main_fragment, getSupportFragmentManager());
                }
                break;
            case MainStates.START_SUPPORT:
                if (checkMemberId()) {
                    CollectFragment.start(R.id.main_fragment, getSupportFragmentManager());
                } else {
                    AdsFragment.start(R.id.main_fragment, getSupportFragmentManager());
                }
                break;
            case MainStates.START_DISCOUNT_CARD:
                if (checkMemberId()) {
                    DiscountCardFragment.start(R.id.main_fragment, getSupportFragmentManager());
                } else {
                    AdsFragment.start(R.id.main_fragment, getSupportFragmentManager());
                }
                break;
            default:
                AdsFragment.start(R.id.main_fragment, getSupportFragmentManager());
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Main", "onResume执行了");
        User.getIntance(this).initUser(null);
        if (!TextUtils.isEmpty(User.getIntance(this).getMemberId())) {
            changeCoupon();
        }
    }

    @Subscribe
    public void LoginNew(LoginEvent event) {
        if (!TextUtils.isEmpty(User.getIntance(this).getMemberId())) {
            changeCoupon();
        }
    }

    //查询优惠卷状态
    public void changeCoupon() {
        Map<String, String> map = new ArrayMap<>();
        map.put(CartStates.MEMBER_ID, User.getIntance(this).getMemberId());
        MainClient.remindCoupon(map, new ClientCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (!TextUtils.isEmpty(s) && s.equals("1")) {
                    mCouponInfo.setVisibility(View.VISIBLE);
                    menuLayout.badgeView(true);
                } else {
                    mCouponInfo.setVisibility(View.GONE);
                    menuLayout.badgeView(false);
                }
            }

            @Override
            public void onFail(int code, String error) {
                mCouponInfo.setVisibility(View.GONE);
                menuLayout.badgeView(false);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Main", "onPause执行了");
    }

    @Subscribe
    public void ThreadMain(Class<CartFragment> cl) {
        AddrManageFragment.start(R.id.main_fragment, getSupportFragmentManager());//地址管理
    }

    @Subscribe
    public void oneKeyPay(final OneKeyEvent oneKeyEvent) {
        Log.e(TAG, "oneKeyPay: 86786868");
        OneKeyCartFragment.setBsjProductId(oneKeyEvent.getBsjProductId());
        OneKeyCartFragment.setCount(oneKeyEvent.getCount());
        Map<String, String> map = new ArrayMap<String, String>();
        map.put(GOODS_ID, oneKeyEvent.getBsjProductId());
        map.put(COUNT, oneKeyEvent.getCount());
        CartClient.getOneKeyOrder(map, new ClientCallback<OneKeyCartOrder>() {
            @Override
            public void onSuccess(OneKeyCartOrder oneKeyCartOrder) {

                if (oneKeyEvent.getFragment() != null) {
                    if (oneKeyEvent.getFragment().getDialog() != null && oneKeyEvent.getFragment().getDialog().isShowing()) {
                        oneKeyEvent.getFragment().dismiss();
                    }
                }

                OneKeyCartFragment.start(R.id.main_fragment, getSupportFragmentManager(), oneKeyEvent.getBsjProductId(), oneKeyEvent.getCount(), oneKeyCartOrder);//去一键购页面


            }

            @Override
            public void onFail(int code, String error) {

                if (code == -1) {//商品库存不足
                    showToast(error);
                }


            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("Main", "onDestory" + "执行了");
    }

//    @Subscribe
//    public void cartEvent(CartEvent event){
//        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
//    }

    @Subscribe
    public void onEvent(EventMsg msg) {
        if (msg.getClassName().equals(CartClient.class) && msg.getMsg().equals(EventMsg.CARTADDERR)) {
            if (!TextUtils.isEmpty(msg.getDetailMsg())) {
                showToast(msg.getDetailMsg());
            } else {
                showToast(R.string.cart_add_fail);
            }
        }
        if (msg.getClassName().equals(CartClient.class) && msg.getMsg().equals(EventMsg.CARTADDSUCCESS)) {
            showToast(R.string.cart_add_success);
        }
        if (msg.getClassName().equals(HomeTitleView.class) && msg.getMsg().equals(EventMsg.NOCATEGORYID)) {
            showToast(R.string.search_nocategoryid);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction()==MotionEvent.ACTION_DOWN){
//            menuLayout.dissMissChildView();
//        }
//        return super.onTouchEvent(event);
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            float x = ev.getX();
//            float y = ev.getY();
//            if ((x<535||x>745)&&y<720) {
////                menuLayout.dissMissChildView();
//            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Subscribe
    public void payEvent(PayEvent payEvent) {
        String orderInfo = payEvent.getOrderInfo();
        if (orderInfo == null) {
            return;
        }
        if (MainBuildConfigure.getInstance().getBuildType() == MainBuildConfigure.BUILD_HAIER) {
            IPayManager payManager = new HaierPayManager(this);
            payManager.pay(orderInfo, null);
        } else if (MainBuildConfigure.getInstance().getBuildType() == MainBuildConfigure.BUILD_BINGOS) {
            IPayManager payManager = new AliPayManager(this);
            payManager.pay(orderInfo, new IPayManager.PayCallback() {
                @Override
                public void onSuccess() {
                    EventBus.getDefault().post(new EventMsg(MainActivity.class, PayStates.PAY_SUCCESS));
                }

                @Override
                public void onFail() {
                    EventBus.getDefault().post(new EventMsg(MainActivity.class, PayStates.PAY_FAIL));
                }
            });
        } else {
            IPayManager payManager = new HaierPayManager(this);
            payManager.pay(orderInfo, null);
        }
    }

    @Subscribe
    public void toastEvent(ToastEvent toastEvent) {
        String msg;
        if (toastEvent.getMsgString() == null) {
            msg = getResources().getString(toastEvent.getMsg());
        } else {
            msg = toastEvent.getMsgString();
        }
        showToast(msg);
    }


    private boolean checkMemberId() {
        String memberId = User.getIntance(this).getMemberId();
        if (memberId == null || memberId.equals("")) {
            showToast(getString(R.string.no_memeber_id));
            return false;
        }
        return true;
    }

}
