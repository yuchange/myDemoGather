package com.zyf.bings.bingos.order.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyf.bings.bingos.R;
import com.zyf.bings.bingos.order.NoUnderlineSpan;
import com.zyf.bings.bingos.order.bean.LogisticsInfoBean;
import com.zyf.bings.bingos.ui.NoClickTextView;

import java.util.List;

/**
 * Created by asus on 2017/10/7.
 */
public class LogisticsInfoAdapter extends BaseQuickAdapter<LogisticsInfoBean.ExpressInfoListBean, BaseViewHolder> {
    private List<LogisticsInfoBean.ExpressInfoListBean> data;
    private final NoUnderlineSpan mNoUnderlineSpan;

    public LogisticsInfoAdapter(@LayoutRes int layoutResId, @Nullable List<LogisticsInfoBean.ExpressInfoListBean> data) {
        super(layoutResId, data);
        this.data = data;
        mNoUnderlineSpan = new NoUnderlineSpan();
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsInfoBean.ExpressInfoListBean item) {
        helper.setText(R.id.tv_item_logistics_time, getTime(item.getTime()));
        helper.setText(R.id.tv_item_logistics__date, getDate(item.getTime()));
        helper.setText(R.id.tv_item_logistics__detail_info, item.getContext());

        if (((NoClickTextView) helper.getView(R.id.tv_item_logistics__detail_info)).getText() instanceof Spannable) {
            Spannable s = (Spannable) ((NoClickTextView) helper.getView(R.id.tv_item_logistics__detail_info)).getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);

        }

        helper.setIsRecyclable(false);
        if (helper.getLayoutPosition() == data.size() - 1) {
            helper.getView(R.id.tv_item_logistics).setBackgroundResource(R.mipmap.node);
            helper.setText(R.id.tv_item_logistics, "发");

        }
        if (!TextUtils.isEmpty(item.getContext()) && item.getContext().contains("送达")) {
            helper.getView(R.id.tv_item_logistics).setBackgroundResource(R.mipmap.node);
            helper.setText(R.id.tv_item_logistics, "送");


        }


    }


    private String getDate(String time) {
        String dateString = time.split(" ")[0].split("-")[1] + "-" + time.split(" ")[0].split("-")[2];
        return dateString;
    }

    private String getTime(String time) {
//        2017-03-06 09:31:36
        String timeString = (time.split(" "))[1].split(":")[0] + ":" + (time.split(" "))[1].split(":")[1];
        return timeString;
    }
}
