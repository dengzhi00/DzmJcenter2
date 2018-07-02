package com.dzm.jar.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

/**
 * Created by dzm on 2018/6/11.
 *
 */

public class ResourcesUtils {

    private static Context context;
    public static int width ;
    public static int height;
    public static float density;
    public static float scaledDensity;
    public static int densityDpi;

    public static void init(Context ct){
        context = ct.getApplicationContext();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        density = dm.density;
        scaledDensity = dm.scaledDensity;
        densityDpi = dm.densityDpi;
    }

    public static String getString(@StringRes int id){
        return context.getResources().getString(id);
    }

    public static int getColor(@ColorRes int color){
        return context.getResources().getColor(color);
    }
}
