package com.iszhouhua.blog.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtils {
    private static Gson gson=new Gson();
    private static JsonParser jsonParser=new JsonParser();

    public static String toJson(Object src){
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json,classOfT);
    }

    public static JsonObject parseObject(String json){
        return jsonParser.parse(json).getAsJsonObject();
    }

    public static JsonArray parseArray(String json){
        return jsonParser.parse(json).getAsJsonArray();
    }
}
