package com.dzm.jar.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * Created by dzm on 2016/7/7.
 *
 */
public class ToastUtils {

    private ToastUtils() {
    }

    private static Context contexts;

    public static void init(Context context){
        contexts = context.getApplicationContext();
    }


    private static Toast toasts;


    public static void showLongToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if(HanderUtils.isMainThread()){
                showMain(text,Toast.LENGTH_LONG);
            }else {
                showThread(text,Toast.LENGTH_LONG);
            }
        }
    }

    public static void showShortToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if(HanderUtils.isMainThread()){
                showMain(text,Toast.LENGTH_SHORT);
            }else {
                showThread(text,Toast.LENGTH_SHORT);
            }
        }
    }

    public static void showLongToast(@StringRes int text) {
        if (text != 0) {
            if(HanderUtils.isMainThread()){
                showMain(text,Toast.LENGTH_LONG);
            }else {
                showThread(text,Toast.LENGTH_LONG);
            }
        }
    }

    public static void showShortToast(@StringRes int text) {
        if (text != 0) {
            if(HanderUtils.isMainThread()){
                showMain(text,Toast.LENGTH_SHORT);
            }else {
                showThread(text,Toast.LENGTH_SHORT);
            }
        }
    }

    private static void showThread(final String txt, final int duration){
        HanderUtils.post(new Runnable() {
            @Override
            public void run() {
                if (null != toasts) {
                    toasts.cancel();
                    toasts = null;
                }
                toasts = Toast.makeText(contexts, txt, duration);
                toasts.show();
            }
        });
    }

    private static void showMain(String txt,int duration){
        if (null != toasts) {
            toasts.cancel();
            toasts = null;
        }
        toasts = Toast.makeText(contexts, txt, duration);
        toasts.show();
    }

    private static void showThread(final int txt, final int duration){
        HanderUtils.post(new Runnable() {
            @Override
            public void run() {
                if (null != toasts) {
                    toasts.cancel();
                    toasts = null;
                }
                toasts = Toast.makeText(contexts, txt, duration);
                toasts.show();
            }
        });
    }

    private static void showMain(int txt,int duration){
        if (null != toasts) {
            toasts.cancel();
            toasts = null;
        }
        toasts = Toast.makeText(contexts, txt, duration);
        toasts.show();
    }
}
