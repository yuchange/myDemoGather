package com.bingstar.bingmall.order.OrderApplyAfter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearSmoothScroller;
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
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.order.OrderStates;
import com.bingstar.bingmall.order.bean.CreateOrderBean;
import com.bingstar.bingmall.order.bean.OrderListInfo;
import com.bingstar.bingmall.order.view.IOrderInfoContract;
import com.bingstar.bingmall.order.view.OrderInfoPresenter;
import com.yunzhi.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumengqiang on 2017/3/21.
 */

public class OrderApplayEditDialog  {

}
