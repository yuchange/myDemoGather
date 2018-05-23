package com.bingstar.bingmall.order.LogisticsSearch;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingstar.bingmall.R;

import java.util.List;

/**
 * Created by liumengqiang on 2017/3/20.
 */

public class LogisticsSearchAdapter extends BaseAdapter {
    private List<LogisticsInfo.ExpressInfoList> list;

    public List<LogisticsInfo.ExpressInfoList> getList() {

        return list;
    }

    public void setList(List<LogisticsInfo.ExpressInfoList> list) {

        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.logistics_item_layout, null);
            myViewHolder = new MyViewHolder();
            myViewHolder.text = (TextView)convertView.findViewById(R.id.logistics_item_text);
            myViewHolder.logistics_last_text = (TextView)convertView.findViewById(R.id.logistics_last_text);
            myViewHolder.logistics_detail_text = (TextView)convertView.findViewById(R.id.logistics_detail_text);
            myViewHolder.logistics_item_date_text = (TextView)convertView.findViewById(R.id.logistics_item_date);
            myViewHolder.logistics_image = (ImageView)convertView.findViewById(R.id.logistics_image);
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
            if (position != list.size() - 1){
                myViewHolder.logistics_last_text.setVisibility(View.VISIBLE);
            }
        }
        myViewHolder.text.setText(getTime(list.get(position).getTime()));
        myViewHolder.logistics_item_date_text.setText(getDate(list.get(position).getTime()));
        myViewHolder.logistics_detail_text.setText(list.get(position).getContext());
        if(position == list.size() - 1){//最后一个时间轴是否显示
            myViewHolder.logistics_last_text.setVisibility(View.INVISIBLE);
        }
        /**
         * 设置订单小图标
         */
        if(list.get(position).getContext().contains("送达")){
            myViewHolder.logistics_image.setImageResource(R.drawable.delivery);
        }else if(position == list.size() - 1){
            myViewHolder.logistics_image.setImageResource(R.drawable.send);
        }else{
            myViewHolder.logistics_image.setImageResource(R.drawable.process);
        }
        return convertView;
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


    class MyViewHolder{
        public TextView text;//时间
        public TextView  logistics_item_date_text;//日期
        public TextView logistics_last_text;//时间轴
        public TextView logistics_detail_text;//物流信息
        public ImageView logistics_image;//物流标记
    }
}
