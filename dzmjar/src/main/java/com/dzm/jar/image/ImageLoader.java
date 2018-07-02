package com.dzm.jar.image;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author 邓治民
 *         data 2018/7/2 上午9:12
 */

public class ImageLoader {

    private static ImgLoaderStrategy glidLoaderStrategy = new ImgGlidLoaderStrategy();

    public static ImgLoaderOptions with(Context context){
        return new ImgLoaderOptions(context);
    }

    public static ImgLoaderOptions with(Fragment fragment){
        return new ImgLoaderOptions(fragment);
    }

    public static ImgLoaderOptions with(Activity activity){
        return new ImgLoaderOptions(activity);
    }

    static void loadOptions(ImgLoaderOptions loaderOptions){
        if(null != loaderOptions.loader){
            loaderOptions.loader.loadImage(loaderOptions);
        }else {
            glidLoaderStrategy.loadImage(loaderOptions);
        }
    }

    public static void clearMemoryCache(){
        glidLoaderStrategy.clearMemoryCache();
    }

    public static void clearDiskCache(){
        glidLoaderStrategy.clearDiskCache();
    }

}
