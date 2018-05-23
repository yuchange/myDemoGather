package com.bingstar.bingmall.cycle.view;

import com.bingstar.bingmall.cycle.ICycleContract;
import com.bingstar.bingmall.goods.bean.BeanTranslater;
import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;
import com.bingstar.bingmall.main.bean.PayEvent;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.order.bean.CreateOrderBeanInfo;
import com.bingstar.bingmall.order.http.OrderClient;
import com.bingstar.bingmall.sdk.pay.PayClient;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.utils.LogUtils;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by John on 2017/3/8.
 */

public class CycleDialogPresenter implements ICycleDialogContract.CycleDialogPresenter {


    private ICycleDialogContract.CycleDialogView cycleDialogView;//周期购弹窗页面view

    private ICycleContract.CycleListView cycleListView;//周期购页面view

    public CycleDialogPresenter(ICycleDialogContract.CycleDialogView cycleDialogView) {
        this.cycleDialogView = cycleDialogView;
    }

    public CycleDialogPresenter(ICycleContract.CycleListView cycleListView) {
        this.cycleListView = cycleListView;
    }


    @Override
    public void unBind() {
        cycleDialogView = null;
    }

    /**
     * 周期购快捷下单
     * @param cycleProductInfoQDetail
     */
    @Override
    public void createQListOrder(CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail cycleProductInfoQDetail) {
        OrderClient.createOrder(BeanTranslater.createQOrder(cycleProductInfoQDetail), new ClientCallback<CreateOrderBeanInfo>() {
            @Override
            public void onSuccess(CreateOrderBeanInfo createOrderBeanInfo) {

                LogUtils.Debug_E(CycleDialogPresenter.class, "创建订单成功===========" + createOrderBeanInfo.getZorderinfo().getZorder_no());
                /**
                 * 支付宝支付查询
                 */
                PayQuery(createOrderBeanInfo.getZorderinfo().getZorder_no());
            }

            @Override
            public void onFail(int code, String error) {
//                cycleListView.error();
                /**
                 *  错误处理
                 */
                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.cycleFragment_final);
            }
        });
    }

    /**
     * 周期购下单
     * @param cycleProductInfoDetail
     * @param totalprice
     * @param totalnumber
     */
    @Override
    public void createOrder(CycleProductInfo.CycleProduct.CycleProductInfoDetail cycleProductInfoDetail,
                            String totalprice, String  totaldate, String totalnumber) {
        OrderClient.createOrder(BeanTranslater.createOrder(cycleProductInfoDetail, totalprice, totaldate, totalnumber), new ClientCallback<CreateOrderBeanInfo>() {
            @Override
            public void onSuccess(CreateOrderBeanInfo createOrderBeanInfo) {
                if (cycleDialogView!=null) {
                    cycleDialogView.setDialog(SynLinearLayout.SHOW_SUCCESS);
                }
                PayQuery(createOrderBeanInfo.getZorderinfo().getZorder_no());
            }

            @Override
            public void onFail(int code, String error) {
//                先关闭dialog再发送消息，cyclefragment接受消息进行消息处理
//                cycleDialogView.error();
//                BingstarErrorParser.toastErr(code, error, BingstarErrorParser.cycledetail_dialog_final);
                if (code>800){
                    if (cycleDialogView!=null) {
                        cycleDialogView.showToast(error);
                    }
                }
                if (cycleDialogView!=null) {
                    cycleDialogView.setDialog(SynLinearLayout.SHOW_ERROR);
                }
            }
        });
    }

    public void PayQuery(String zorder_no){
        EventBus.getDefault().post(new PayEvent(zorder_no));
    }
}
