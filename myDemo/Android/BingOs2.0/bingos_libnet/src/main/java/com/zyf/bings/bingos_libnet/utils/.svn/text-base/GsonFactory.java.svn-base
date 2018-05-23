package com.zyf.bings.bingos_libnet.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * Created by zhangyifei on 17/9/3.
 */

public class GsonFactory {
    public static Gson sGson;

    public static Gson getGson() {
        if (sGson == null) {
            synchronized (GsonFactory.class) {
                if (sGson == null) {
                    GsonBuilder builder = new GsonBuilder();
                    //builder.excludeFieldsWithoutExposeAnnotation();
                    sGson = builder.create();
                }
            }
        }
        return sGson;
    }

    public static final String map2Json(Map<String, String> map) {
        return getGson().toJson(map);
    }

    public static final String mapToJson(Map<String, Object> map) {
        return getGson().toJson(map);
    }

    public static final <V> V fromJson(String json, Class<V> type) {
        return getGson().fromJson(json, type);
    }
}
