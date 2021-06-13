package com.iszhouhua.blog.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouhua
 */
@Slf4j
public class JsonUtils {
    private static Gson gson=new Gson();
    private static JsonParser jsonParser=new JsonParser();

    public static String toJson(Object o){
        String src = null;
        try {
            src = gson.toJson(o);
        }catch (Exception e){
            log.error("toJson error",e);
        }
        return src;
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        T t = null;
        try {
            t = gson.fromJson(json,classOfT);
        }catch (Exception e){
            log.error("fromJson error",e);
        }
        return t;
    }

    public static JsonObject parseObject(String json){
        return jsonParser.parse(json).getAsJsonObject();
    }

    public static JsonArray parseArray(String json){
        return jsonParser.parse(json).getAsJsonArray();
    }
}
