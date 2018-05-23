package com.zyf.bings.bingos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.base.BaseActivity;
import com.zyf.bings.bingos.brand.view.BrandFragment;
import com.zyf.bings.bingos.cart.ShopCartFragment;
import com.zyf.bings.bingos.event.LoginSuccessEvent;
import com.zyf.bings.bingos.event.MainCartCount;
import com.zyf.bings.bingos.event.MainTitleEvent;
import com.zyf.bings.bingos.event.SetMainTitleEvent;
import com.zyf.bings.bingos.event.ToOrderDetailFragEvent;
import com.zyf.bings.bingos.goods.GoodsDetailFragment;
import com.zyf.bings.bingos.goods.OrderConfirmFragment;
import com.zyf.bings.bingos.http.CartCountClient;
import com.zyf.bings.bingos.login.view.SweepCodeFragment;
import com.zyf.bings.bingos.manager.ManagerFragment;
import com.zyf.bings.bingos.order.OrderFragment;
import com.zyf.bings.bingos.order.http.OrderCountClient;
import com.zyf.bings.bingos.order.view.OrderDetailFragment;
import com.zyf.bings.bingos.utils.Config;
import com.zyf.bings.bingos.utils.SPUtil;
import com.zyf.bings.bingos_libnet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private TextView mTvIceBoxTime;
    private ImageView mIvCart;
    private ImageView mIvHome;
    private ImageView mIvBack, mIvSet;
    private TextView mTvTitle;
    private TextView mainTitle;
    private TextView mTvCartCount;
    private LinearLayout mainTitleLinerLayout;
    private FragmentManager fragmentManager;
    private String comeFrom;
    private static final String FROM_CART = "cart";
    private static final String FROM_MANAGER = "manager";
    private SimpleDateFormat mFormatter;
    private int mOrderTotalCount; //总订单数量
    private int mOrderWaitPayCount; //待付款数量
    private int mOrderWaitSendCount; //待发货数量
    private int mOrderWaitReceiveCount; //待收货数量
    private int mOrderFinishCount; //已完成数量
    private int mOrderCancelCount; //已取消数量
    private int mOrderAfterSaleCount; //售后中数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SPUtil.put(this, Config.MEMBER_ID, "15869153840");
        // SPUtil.put(this, Config.MEMBER_ID, "13135677906");
     // SPUtil.put(this,Config.MEMBER_ID,"oGtzZv80UURYv1t6BKNnoMaC2Wb4");
        initView();
        initListener();
        initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    private void initData() {

        Intent intent = getIntent();
        comeFrom = intent.getStringExtra("manager");
        // 判断已登录而且是点击管家进入到管家页面
        if (!TextUtils.isEmpty(SPUtil.getString(this, Config.MEMBER_ID)) && !TextUtils.isEmpty(comeFrom) && FROM_MANAGER.equals(comeFrom)) {
            ManagerFragment.start(R.id.fl_container, getSupportFragmentManager());
            mainTitleLinerLayout.setVisibility(View.GONE);
            mTvTitle.setText("冰果管家");
            CartCountClient.getCartCount();
        } else if (!TextUtils.isEmpty(comeFrom) && "mall".equals(comeFrom)) {//去品牌馆页面
            BrandFragment.start(R.id.fl_container, getSupportFragmentManager());
            mainTitleLinerLayout.setVisibility(View.VISIBLE);
            CartCountClient.getCartCount();
        } else if (!TextUtils.isEmpty(SPUtil.getString(this, Config.MEMBER_ID)) && !TextUtils.isEmpty(comeFrom) && "order".equals(comeFrom)) { // 去订单页面
            CartCountClient.getCartCount();
            OrderFragment.start(R.id.fl_container, getSupportFragmentManager(), mOrderWaitPayCount, mOrderWaitSendCount,
                    mOrderWaitReceiveCount, mOrderFinishCount, mOrderCancelCount, mOrderAfterSaleCount);
        } else {
            //去登录界面
            SweepCodeFragment.start(R.id.fl_container, getSupportFragmentManager(), comeFrom);

            mTvTitle.setText("");
            mTvCartCount.setVisibility(View.GONE);

        }


    }

    private void initListener() {
        mIvCart.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mIvHome.setOnClickListener(this);
        mIvSet.setOnClickListener(this);
    }

    private void initView() {
        mTvIceBoxTime = (TextView) findViewById(R.id.tv_icebox_time);
        mIvCart = (ImageView) findViewById(R.id.iv_cart);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mIvSet = (ImageView) findViewById(R.id.iv_set);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mainTitle = (TextView) findViewById(R.id.main_title);
        mTvCartCount = (TextView) findViewById(R.id.tv_main_cartcount);
        mainTitleLinerLayout = (LinearLayout) findViewById(R.id.main_title_ll);
        fragmentManager = getSupportFragmentManager();
        mTvCartCount.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
            case R.id.iv_cart:
                mainTitleLinerLayout.setVisibility(View.GONE);
                if (TextUtils.isEmpty(SPUtil.getString(this, Config.MEMBER_ID))) {
                    SweepCodeFragment.start(R.id.fl_container, getSupportFragmentManager(), Config.FROM_CART);
                    mTvTitle.setText("");
                    mTvCartCount.setVisibility(View.GONE);
                    ToastUtils.showToast("您尚未登录,请先登录");
                    return;
                }
                OrderConfirmFragment orderConfirmFragment = (OrderConfirmFragment) fragmentManager.findFragmentByTag("OrderConfirmFragment");
                GoodsDetailFragment goodsDetailFragment = (GoodsDetailFragment) fragmentManager.findFragmentByTag("GoodsDetailFragment");
                if (goodsDetailFragment != null && !goodsDetailFragment.isHidden()) {
                    ShopCartFragment.start(R.id.fl_container, getSupportFragmentManager());
                } else {
                    if (orderConfirmFragment != null && !orderConfirmFragment.isHidden()) {
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ShopCartFragment.start(R.id.fl_container, getSupportFragmentManager());
                    }
                }
                break;
            case R.id.iv_back:
                if (fragmentManager.getBackStackEntryCount() == 1) {
                    finish();
                } else {

                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.iv_home:
                finish();

                break;
            case R.id.iv_set:
                mainTitleLinerLayout.setVisibility(View.VISIBLE);
//                BrandFragment.start(R.id.fl_container, getSupportFragmentManager());
                ToastUtils.showToast("点击了设置");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 修改中间标题
     *
     * @param mainTitleEvent
     */
    @Subscribe(sticky = true)
    public void changeTitle(MainTitleEvent mainTitleEvent) {
        if (mainTitleLinerLayout != null) {
            mainTitleLinerLayout.setVisibility(View.GONE);
        }
        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            Log.e("MainActivity", "changeTitle: " + mainTitleEvent.getTitle());
            mTvTitle.setText(mainTitleEvent.getTitle());
        }
    }


    /**
     * 修改购物车角标数量
     *
     * @param mainCartCount
     */
    @Subscribe(sticky = true)
    public void changeCartCount(MainCartCount mainCartCount) {

        String cartCount = mainCartCount.getCartCount();
        if (!TextUtils.isEmpty(cartCount) && cartCount.equals("0")) {
            mTvCartCount.setVisibility(View.GONE);
        } else {
            mTvCartCount.setVisibility(View.VISIBLE);
            mTvCartCount.setText(mainCartCount.getCartCount());
        }

    }

    @Subscribe
    public void setBrandsTitle(SetMainTitleEvent setMainTitleEvent) {

        if (mainTitleLinerLayout != null) {
            mainTitleLinerLayout.setVisibility(View.VISIBLE);
        }
        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.GONE);
        }
    }


    @Subscribe
    public void loginSuccessEvent(LoginSuccessEvent loginSuccessEvent) {
        CartCountClient.getCartCount();
        OrderCountClient.getOrderCount();
    }

    /**
     * 订单数量
     *
     * @param
     */


    @Subscribe
    public void toOrderDetailFraEvent(ToOrderDetailFragEvent toOrderDetailFragEvent) {

        onBackPressed();

        OrderDetailFragment.start(R.id.fl_container, getSupportFragmentManager(), toOrderDetailFragEvent.getZorderId(), toOrderDetailFragEvent.getZorderNo());


    }


}
