package com.example.liuhangf.viewpagerdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhangf on 2017/9/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
//    private List<Bitmap> datas;
//    private List<byte[]> listbyte;
    private ArrayList<String> datas;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;
    private AddPhotoInterface addPhotoInterface;

    public RecyclerAdapter(Activity context) {
        this.context = context;
//        listbyte = new ArrayList<>();
        datas = new ArrayList<>();
        addPhotoInterface = (AddPhotoInterface) context;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        if (viewType == ONE_ITEM) {
            View view = inflater.inflate(R.layout.item_bitmap, parent, false);
            holder = new ViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.item_addimg,parent,false);
            holder = new AddHolder(view);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){

            /**
             * 你要的在这里
             */

             //这个是进入下个activity的步骤,要把那几个值传过去
            Bitmap bitmap = ratio(datas.get(position),1080,720);
            ((ViewHolder)holder).imageView.setImageBitmap(bitmap);
            ((ViewHolder)holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ViewActivity.class);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("list", datas);
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                context.startActivity(intent,bundle);
                }
            });

            //这个是图片上边那个删除功能你应该用不到
            ((ViewHolder)holder).deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(position);
                notifyDataSetChanged();
                }
            });






        }else {
            ((AddHolder)holder).addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPhotoInterface.setAddPhotoInterface(v);
                }
            });

        }
    }


    @Override
    public int getItemViewType(int position) {
        int x = datas.size();
       if (x==position){
           return TWO_ITEM;
       }else {
           return ONE_ITEM;
       }

    }

    @Override
    public int getItemCount() {
        return datas.size()==0?1:datas.size()+1;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageView deleteImg;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_bitmap);
            deleteImg = (ImageView) itemView.findViewById(R.id.item_delete);
        }
    }
    class AddHolder extends RecyclerView.ViewHolder{
        ImageView addImg;

        public AddHolder(View itemView) {
            super(itemView);
            addImg = (ImageView) itemView.findViewById(R.id.item_addImg_img);
        }
    }







    public Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath,newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }


}



