package com.updayup.yuchance.indexdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.ReleaseStepHelper;
//import com.xinniu.android.qiqueqiao.adapter.ResouceLeftAdapter;
//import com.xinniu.android.qiqueqiao.adapter.ResouceRightAdapter;
//import com.xinniu.android.qiqueqiao.base.BaseActivity;
//import com.xinniu.android.qiqueqiao.bean.SelectCategory;
//import com.xinniu.android.qiqueqiao.common.Constants;
//import com.xinniu.android.qiqueqiao.request.RequestManager;
//import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by yuchance on 2018/3/26.
 */

//public class SupplyDemandActivity extends BaseActivity implements ResouceLeftAdapter.OnLeftItemSelect,ResouceRightAdapter.OnRigheItemSelect{


//    @BindView(R.id.leftListView)
//    RecyclerView leftListView;
//    @BindView(R.id.rightListView)
//    RecyclerView rightListView;
//    @BindView(R.id.tvSubmit)
//    TextView tvSubmit;
//    private int selectedType = RELEASE_OFFER;
//
//
//    private ResouceLeftAdapter leftAdapter;
//    private ResouceRightAdapter rightAdapter;
//    private List<SelectCategory> leftList = new ArrayList<>();
//    private List<SelectCategory> rightList = new ArrayList<>();
//    public final static int RELEASE_NEED = 2;
//    public final static int RELEASE_OFFER = 1;
//    private int needSelectedLeftItem = ReleaseStepHelper.getInstance().getNeedSelectedLeftItem();
//    private int offerSelectedLeftItem = ReleaseStepHelper.getInstance().getOfferSelectedLeftItem();
//
//    private Map<String,List<SelectCategory>> leftCache = ReleaseStepHelper.getInstance().getLeftCache();
//    private Map<String,List<SelectCategory>> offerRightCache = ReleaseStepHelper.getInstance().getOfferRightCache();
//    private Map<String,List<SelectCategory>> neddRightCache = ReleaseStepHelper.getInstance().getNeddRightCache();
//    public final static String RELEASE_NEED_TAG = "Need";
//    public final static String RELEASE_OFFER_TAG = "Offer";
//
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_supply_demand;
//    }
//
//    @Override
//    public void initViews(Bundle savedInstanceState) {
//        leftListView.setLayoutManager(new LinearLayoutManager(mContext));
//        rightListView.setLayoutManager(new LinearLayoutManager(mContext));
//        leftAdapter = new ResouceLeftAdapter(mContext, leftList);
//        leftAdapter.setOnLeftItemSelect(this);
//        rightAdapter = new ResouceRightAdapter(mContext, rightList);
//        rightAdapter.setOnRigheItemSelect(this);
//        leftListView.setAdapter(leftAdapter);
//        rightListView.setAdapter(rightAdapter);
//
//    }
//
//
//    @Override
//    public void onLeftItemSelect(SelectCategory selectCategory) {
//        int leftSelectId = selectCategory.getId();
//        if (selectedType == RELEASE_OFFER){
//            offerSelectedLeftItem = leftSelectId;
//        }
//        if (selectedType == RELEASE_NEED){
//            needSelectedLeftItem = leftSelectId;
//        }
//        rightAdapter.setSelectLeftId(leftSelectId);
//        refreshRight(leftSelectId);
//        isSelectedSuccess();
//
//    }
//
//    private boolean isSelectedSuccess(){
//        if (selectedType == RELEASE_OFFER){
//            return isItemSelected(RELEASE_OFFER_TAG,offerRightCache);
//        }
//        if (selectedType == RELEASE_NEED){
//            return isItemSelected(RELEASE_NEED_TAG,neddRightCache);
//        }
//        return false;
//    }
//    private boolean isItemSelected(String key,Map<String,List<SelectCategory>> cache){
//        int leftSelectedItem = 0;
//        if (leftCache.get(key) == null){
//            return false;
//        }
//        for (int i = 0 ;i<leftCache.get(key).size();i++){
//            if (leftCache.get(key).get(i).isCheck()){
//                leftSelectedItem = leftCache.get(key).get(i).getId();
//            }
//        }
//        String leftKey = String.valueOf(leftSelectedItem);
//        if (cache == null){
//            return false;
//        }
//        if (cache.containsKey(leftKey)){
//            int size = cache.get(leftKey).size();
//            for (int i =0; i<size;i++){
//                if (!cache.get(leftKey).get(i).isCheck()){
//                    tvSubmit.setSelected(false);
//                    return false;
//                }
//            }
//            tvSubmit.setSelected(true);
//            return true;
//        }
//        return false;
//    }
//
//
//
//
//    @Override
//    public void onRightItemSelect(int id, List<SelectCategory> selectCategories) {
//        isSelectedSuccess();
//    }
//    public void refreshRight(final Integer id) {
//        if (selectedType == RELEASE_OFFER){
//            if (offerRightCache.containsKey(String.valueOf(id))){
//                rightList.clear();
//                rightList.addAll(offerRightCache.get(String.valueOf(id)));
//                rightAdapter.notifyDataSetChanged();
//            }else{
//                showLoadingDialog(0);
//                RequestManager.getInstance().getCategoryByPid(id, new GetCategoryCallback() {
//                    @Override
//                    public void onSuccess(List<SelectCategory> list) {
//                        dissMissDialog();
//                        rightList.clear();
//                        rightList.addAll(list);
//                        offerRightCache.put(String.valueOf(id),list);
//                        addCustomButton();
//                        rightAdapter.notifyDataSetChanged();
//                        isSelectedSuccess();
//                    }
//
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        dissMissDialog();
//                        isSelectedSuccess();
//                    }
//                });
//            }
//        }
//        if (selectedType == RELEASE_NEED){
//            if (neddRightCache.containsKey(String.valueOf(id))){
//                rightList.clear();
//                rightList.addAll(neddRightCache.get(String.valueOf(id)));
//                rightAdapter.notifyDataSetChanged();
//            }else{
//                showLoadingDialog(0);
//                RequestManager.getInstance().getCategoryByPid(id, new GetCategoryCallback() {
//                    @Override
//                    public void onSuccess(List<SelectCategory> list) {
//                        dissMissDialog();
//                        rightList.clear();
//                        rightList.addAll(list);
//                        neddRightCache.put(String.valueOf(id),list);
//                        addCustomButton();
//                        rightAdapter.notifyDataSetChanged();
//                        isSelectedSuccess();
//                    }
//
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        dissMissDialog();
//                        isSelectedSuccess();
//                    }
//                });
//            }
//        }
//
//
//
//
//    }
//    private void addCustomButton(){
//        for (SelectCategory item:rightList){
//            if (item.getIs_custom() == 1){
//                SelectCategory addTag = new SelectCategory();
//                addTag.setId(Constants.ID_TAG_ADD);
//                addTag.setName("自定义");
//                item.getZ_index().add(addTag);
//            }
//        }
//    }

//}
