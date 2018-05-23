package com.bingstar.bingmall.cycle;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.bingstar.bingmall.cycle.bean.CycleProductInfo;
import com.bingstar.bingmall.cycle.bean.CycleProductQInfo;
import com.bingstar.bingmall.cycle.http.CycleClient;
import com.bingstar.bingmall.cycle.http.CycleStates;
import com.bingstar.bingmall.net.BingstarErrorParser;
import com.bingstar.bingmall.net.ClientCallback;
import com.bingstar.bingmall.user.bean.User;
import com.bingstar.bingmall.user.bean.UserStates;
import com.bingstar.bingmall.video.lib.SynLinearLayout;
import com.yunzhi.lib.utils.LogUtils;


import java.util.ArrayList;
import java.util.Map;

/**
 * Created by qianhechen on 17/2/14.
 */

public class CyclePresenter implements ICycleContract.CyclePresenter {

    private ICycleContract.CycleListView cycleListView;

    public CyclePresenter(ICycleContract.CycleListView cycleListView) {
        this.cycleListView = cycleListView;
    }

    @Override
    public void unBind() {
        cycleListView = null;
    }

    @Override
    public void getCycleProductList(final ArrayList<CycleProductInfo.CycleProduct.CycleProductInfoDetail> cycleProductInfoList, String categoryId) {
        Map<String, String> map = new ArrayMap<>();
        map.put(CycleStates.CATEGORYID, categoryId);
        map.put(UserStates.MEMBER_ID, User.getIntance().getMemberId());
        CycleClient.getCycleProductList(map, new ClientCallback<CycleProductInfo.CycleProduct>() {
            @Override
            public void onSuccess(CycleProductInfo.CycleProduct cycleProduct) {
                cycleProductInfoList.clear();
                cycleProduct.sortList();
                cycleProductInfoList.addAll(cycleProduct.getCycleProductInfoList());
                if (cycleListView != null && cycleProduct.getCycleProductInfoList().size() != 0) {
                    cycleListView.notifyList(SynLinearLayout.SHOW_SUCCESS);
                }else if(cycleListView != null && cycleProduct.getCycleProductInfoList().size() == 0){
                    cycleListView.notifyList(SynLinearLayout.SHOW_EMPTY);
                }
            }

            @Override
            public void onFail(int code, String error) {
//                cycleListView.error();
                cycleListView.notifyList(SynLinearLayout.SHOW_ERROR);
            }
        });
    }

    @Override
    public void getCycleProductQList(final ArrayList<CycleProductQInfo.CycleProductQ.CycleProductInfoQDetail> cycleProductInfoQList, String categoryId) {
        Map<String, String> map = new ArrayMap<>();
        map.put(CycleStates.CATEGORYID, "");
        CycleClient.getCycleProductQList(map, new ClientCallback<CycleProductQInfo.CycleProductQ>() {
            @Override
            public void onSuccess(CycleProductQInfo.CycleProductQ cycleProductQ) {
                cycleProductInfoQList.clear();
                cycleProductInfoQList.addAll(cycleProductQ.getCycleProductQInfoList());
                if (cycleListView!=null && cycleProductQ.getCycleProductQInfoList().size() != 0) {
                    cycleListView.notifyQList(SynLinearLayout.SHOW_SUCCESS);
                }else if(cycleListView != null && cycleProductQ.getCycleProductQInfoList().size() == 0){
                    cycleListView.notifyQList(SynLinearLayout.SHOW_EMPTY);
                }
            }

            @Override
            public void onFail(int code, String error) {
                cycleListView.notifyQList(SynLinearLayout.SHOW_ERROR);
            }
        });
    }

}
