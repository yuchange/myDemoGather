package com.zyf.bings.bingos_libnet.utils;

import com.google.gson.Gson;
import com.zyf.bings.bingos_libnet.bean.BaseResponse;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import ikidou.reflect.TypeBuilder;

/**
 * Created by zhangyifei on 17/4/25.
 */

public class GsonUtils {

    public static <T> T fromJson(String strjson, Class<T> cls) {
        try {
            Gson gs = new Gson();
            T target = (T) gs.fromJson(strjson, cls);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param json  json 数据包
     * @param clazz 泛型类
     * @param <T>   泛型类
     * @return 基类<泛型类>
     */

    /*public static <T> BaseResponse<T> json2Object(String json, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(BaseResponse.class)
                .addTypeParam(clazz)
                .build();
        return new Gson().fromJson(json, type);
    }*/

    public static String map2Json(Map<String, Object> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }



}
