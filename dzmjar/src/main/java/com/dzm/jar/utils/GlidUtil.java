package com.dzm.jar.utils;

import android.content.Context;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by dzn on 2017/3/31.
 *
 */

public class GlidUtil {


    public static void loadUrl(String url, ImageView imageView, Context context,int error) {
        Glide.with(context).load(url)
                .error(error) //
                .into(imageView);
    }

    public static void loadUrl(String url, ImageView imageView, Fragment fragment,int error) {
        Glide.with(fragment).load(url)
                .error(error) //
                .into(imageView);
    }

    public static void loadUrl(String url, ImageView imageView, Context context) {
        Glide.with(context).load(url)
//                .placeholder(url) //
//                .error(url) //
//                .listener(listener)
                .into(imageView);
    }

    public static void loadUrl(int url, ImageView imageView, Context context) {
        Glide.with(context).load(url)
//                .placeholder(url) //
//                .error(url) //
//                .listener(listener)
                .into(imageView);
    }

    public static void loadUrl(String url, ImageView imageView, Fragment fragment) {
        Glide.with(fragment).load(url)
//                .placeholder(R.drawable.loading) //
//                .error(error) //
//                .listener(listener)
                .into(imageView);
    }

    public static void loadUrl(int url, ImageView imageView, Fragment fragment) {
        Glide.with(fragment).load(url)
//                .placeholder(R.drawable.loading)
//                .listener(listener)
                .into(imageView);
    }

    //
    public static boolean clearCacheMemory(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
