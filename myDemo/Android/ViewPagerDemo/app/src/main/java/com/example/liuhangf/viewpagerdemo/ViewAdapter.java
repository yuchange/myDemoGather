package com.example.liuhangf.viewpagerdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by liuhangf on 2017/9/18.
 */

public class ViewAdapter extends PagerAdapter {

    private List<String> datas;
    private Context context;
    private FinishactivityInterface finishactivityInterface;
    private boolean isSeleted = true;

    public ViewAdapter(Activity context) {
        this.context = context;
       finishactivityInterface = (FinishactivityInterface) context;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_viewpager_img);


        Bitmap bitmap = ratio(datas.get(position),1080,720);
        imageView.setImageBitmap(bitmap);

        container.addView(view);
       return view;

    }


    //下面这个方法是压缩图片的方法  建议放在工具类中 上传图片是也可以用到下面的方法

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
        if (be <= 0){ be = 1;}
        newOpts.inSampleSize = be;
        //设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }
//    public static void saveImageToGallery(Context context, Bitmap bmp) {
////        String path =
//        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}
