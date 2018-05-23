package com.zyf.bings.bingos.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by Administrator on 2017/9/4.
 */

public class ImageUtils {



    public static Bitmap encodeAsBitmap(String str) {
        if (str == null) {
            return null;
        }
        Bitmap bitmap = null;
        BitMatrix result;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 180, 180);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 180, 0, 0, w, h);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }




    public static Bitmap encodeWechatBitmap(String str) {
        if (str == null) {
            return null;
        }
        Bitmap bitmap = null;
        BitMatrix result;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 350, 350);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Log.i("ImgeUtils","Wight"+w);
            Log.i("ImgeUtils","Heiht"+h);
            bitmap.setPixels(pixels, 0, 350, 0, 0, w, h);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
