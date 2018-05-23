package com.zyf.bings.bingos.ui.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zyf.bings.bingos.goods.bean.GoodsDetailBean;
import com.zyf.bings.bingos_libnet.BuildConfig;


public class GlideImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择


        /*if (path instanceof GoodsDetailBean.ProductInfoDetailBean.ProductPicListBean) {
            String url = ((GoodsDetailBean.ProductInfoDetailBean.ProductPicListBean) path).getPicUrl();
            url = BuildConfig.URL_IMAGE + url;
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .crossFade()
                    .into(imageView);
            return;
        }*/
        if (path instanceof String) {
            String url = (String) path;
            url = BuildConfig.URL_IMAGE + url;
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .crossFade()
                    .into(imageView);
            return;
        }
        Glide.with(context.getApplicationContext())
                .load(path)
                .crossFade()
                .into(imageView);
    }


}
