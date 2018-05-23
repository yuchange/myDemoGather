package com.bingstar.bingmall.launcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.main.MainActivity;
import com.bingstar.bingmall.main.MainBuildConfigure;
import com.bingstar.bingmall.main.MainStates;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.sdk.map.BaiduMapManager;
import com.bingstar.bingmall.sdk.map.IMapController;
import com.bingstar.bingmall.sdk.map.LatLng;
import com.bingstar.bingmall.user.bean.User;

/**
 * 功能：
 * Created by yaoyafeng on 17/3/30 下午5:59
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/3/30 下午5:59
 * @modify by reason:{方法名}:{原因}
 */

public class BingMallOrderView extends LinearLayout {
    private TextView launcher_wait_pay_num;
    private TextView launcher_wait_trans_num;
    private TextView launcher_wait_check_num;

    public BingMallOrderView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public BingMallOrderView(Context context) {
        super(context);
        initData(context);
    }

    public BingMallOrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);

    }


    private void initData(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.launcher_order_view, this);
        launcher_wait_pay_num = (TextView) findViewById(R.id.launcher_wait_pay_num);
        launcher_wait_trans_num = (TextView) findViewById(R.id.launcher_wait_trans_num);
        launcher_wait_check_num = (TextView) findViewById(R.id.launcher_wait_to_check_num);
        launcher_wait_pay_num.setText("0");
        launcher_wait_trans_num.setText("0");
        launcher_wait_check_num.setText("0");
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                User.checkMemberId(context, new User.CheckMemberCallback() {
                    @Override
                    public void hasLogin() {
                        /*Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra(MainStates.START_KEY, MainStates.START_ORDER);
                        context.startActivity(intent);*/

                        Intent intent = new Intent();
                        intent.setAction("com.bingstar.bingmall.MAIN");
                        intent.putExtra(MainStates.START_KEY, MainStates.START_ORDER);
                        context.startActivity(intent);
                    }
                });
            }
        });

        /*//初始化事件
        if (User.getIntance(getContext()).getFridgeInit()) {
            User.getIntance(getContext()).setFridgeInit(false);
            BaiduMapManager.getInstance(getContext()).getLatLng(new IMapController.LocationCallback() {
                @Override
                public void callback(LatLng latLng) {
                    if (latLng != null) {
                        User.getIntance(getContext()).initUser(User.TYPE_INIT_VIEW, latLng);
                    } else {
                        User.getIntance(getContext()).initUser(User.TYPE_INIT_VIEW, null);
                    }
                }
            });
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public void getOrderData() {
        Context context = getContext();
        if (context != null) {
            String memberId = User.getIntance(context).getHaierPhone();
            if (memberId != null && !memberId.equals("")) {
                LauncherClient.getOrderNum(memberId, new ClientCallback<OrderNumBean>() {
                    @Override
                    public void onSuccess(OrderNumBean orderNumBean) {
                        if (orderNumBean != null) {
                            launcher_wait_pay_num.setText(orderNumBean.getUnPayNum());
                            launcher_wait_trans_num.setText(orderNumBean.getUnTransNum());
                            launcher_wait_check_num.setText(orderNumBean.getUnCheckNum());
                        }
                    }

                    @Override
                    public void onFail(int code, String error) {

                    }
                });
            } else {
                launcher_wait_pay_num.setText("0");
                launcher_wait_trans_num.setText("0");
                launcher_wait_check_num.setText("0");
            }
        }
    }


    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public void refreshData(){
        getOrderData();
    }
}
