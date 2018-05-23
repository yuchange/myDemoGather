package com.zyf.bings.bingos.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zyf.bings.bingos_libnet.MainBuildConfigure;

/**
 * Created by zhangyifei on 17/9/3.
 */

public class ImageLoader {

    public static void load(ImageView imageView, String url, Drawable error) {
        String newUrl = changeUrl(url);
        Glide.with(imageView.getContext()).load(newUrl).error(error).into(imageView);
    }

    private static String changeUrl(String url) {
        String newUrl = "";
        try {
            Log.e("Util", "changeUrl:    老的  " + url);
            if (url.startsWith("http")) {
                //newUrl = url.substring(url.indexOf("/", url.indexOf(".")), url.length());
                //newUrl = MainBuildConfigure.getInstance().getImageService() + newUrl;
            } else {
                newUrl = MainBuildConfigure.IMAGE_SERVER + url;
            }
            Log.e("Util", "changeUrl:    新的 " + newUrl);
            return newUrl;
        } catch (Exception e) {
            Log.e("Util", "changeUrl:   解析异常  " + e.toString());
            return newUrl;
        }

    }

    public static void load(ImageView imageView, String url) {
        String newUrl = changeUrl(url);
        Glide.with(imageView.getContext()).load(newUrl).into(imageView);
    }

    public static void imageTransform(Context context, ImageView imageView, String url) {
        /**
         * Glide加载圆角图片, 重写BitmapTransformation类
         */
        String newUrl = changeUrl(url);
        Glide.with(context).load(newUrl).transform(new GlideRoundTransform(context)).into(imageView);

    }

    public static void gifLoader(ImageView imageView, String url, Drawable error) {
        Glide.with(imageView.getContext()).load(url).asGif().error(error).into(imageView);
    }
}
