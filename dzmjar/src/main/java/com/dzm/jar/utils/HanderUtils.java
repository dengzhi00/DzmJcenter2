package com.dzm.jar.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by dzm on 2018/5/17.
 *
 */

public class HanderUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static Handler getHandler() {
        return handler;
    }

    public static void post(Runnable runnable){
        handler.post(runnable);
    }

    public static void postDelay(Runnable runnable,int delayMillis){
        handler.postDelayed(runnable,delayMillis);
    }


    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}
