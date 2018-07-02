package com.dzm.jar.utils;

import android.util.Log;

/**
 * Created by dzm on 2018/6/22.
 *
 */

public class LogUtils {

    private static final String TAG = "***LogUtils***";

    private static boolean isDebug = false;

    public static void setIsDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    public static void d(String msg) {
        if (!isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (!isDebug)
            Log.e(TAG, msg);
    }

    public static void i(String msg) {
        if (!isDebug)
            Log.i(TAG, msg);
    }

    public static void v(String msg) {
        if (!isDebug)
            Log.v(TAG, msg);
    }

    public static void w(String msg) {
        if (!isDebug)
            Log.w(TAG, msg);
    }

}
