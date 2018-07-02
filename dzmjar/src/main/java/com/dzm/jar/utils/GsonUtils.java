package com.dzm.jar.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/6/13.
 */

public class GsonUtils {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static String toJson(Object o){
        return gson.toJson(o);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clz){
        if(TextUtils.isEmpty(json) || TextUtils.equals(json,"null")){
            return null;
        }
        try {
            List<T> list = new ArrayList<>();
            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for(JsonElement jsonElement : arry){
                list.add(gson.fromJson(jsonElement,clz));
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json,Class<T> clz) {
        if(TextUtils.isEmpty(json) || TextUtils.equals(json,"null")){
            return null;
        }
        return gson.fromJson(json,clz);
    }

}
