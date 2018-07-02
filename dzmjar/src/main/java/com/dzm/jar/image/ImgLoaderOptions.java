package com.dzm.jar.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;

import java.io.File;

/**
 * @author 邓治民
 *         data 2018/7/2 上午9:14
 */

public class ImgLoaderOptions {

    public View targetView;//targetView展示图片
    public ImgBitmapCallBack callBack;

    public int placeholderResId;
    public Drawable placeholder;

    public int errorResId;
    public Drawable errorDraw;

    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;

    public ImgLoaderStrategy loader;//实时切换图片加载库

    public Context context;
    public Fragment fragment;
    public Activity activity;

    public int targetWidth;
    public int targetHeight;

    public boolean isCenterCrop;
    public boolean isCenterInside;
    public boolean skipMemoryCache; //是否缓存到内存
    public boolean skipDiskCache;//磁盘缓存

    public boolean isGif;



    public ImgLoaderOptions(Context context) {
        this.context = context;
    }

    public ImgLoaderOptions(Fragment fragment) {
        this.fragment = fragment;
    }

    public ImgLoaderOptions(Activity activity) {
        this.activity = activity;
    }


    public void into(View view){
        this.targetView = view;
        ImageLoader.loadOptions(this);
    }

    public void bitmap(ImgBitmapCallBack callBack){
        this.callBack = callBack;
    }

    public ImgLoaderOptions setTargetView(View targetView) {
        this.targetView = targetView;
        return this;
    }

    public ImgLoaderOptions setCallBack(ImgBitmapCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public ImgLoaderOptions setPlaceholderResId(int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public ImgLoaderOptions setPlaceholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public ImgLoaderOptions setErrorResId(int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public ImgLoaderOptions setErrorDraw(Drawable errorDraw) {
        this.errorDraw = errorDraw;
        return this;
    }

    public ImgLoaderOptions load(String url) {
        this.url = url;
        return this;
    }

    public ImgLoaderOptions load(File file) {
        this.file = file;
        return this;
    }

    public ImgLoaderOptions load(int drawableResId) {
        this.drawableResId = drawableResId;
        return this;
    }

    public ImgLoaderOptions load(Uri uri) {
        this.uri = uri;
        return this;
    }

    public ImgLoaderOptions setLoader(ImgLoaderStrategy loader) {
        this.loader = loader;
        return this;
    }

    public ImgLoaderOptions setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
        return this;
    }

    public ImgLoaderOptions setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
        return this;
    }

    public ImgLoaderOptions setCenterCrop(boolean centerCrop) {
        isCenterCrop = centerCrop;
        return this;
    }

    public ImgLoaderOptions setCenterInside(boolean centerInside) {
        isCenterInside = centerInside;
        return this;
    }

    public ImgLoaderOptions setSkipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }

    public ImgLoaderOptions setSkipDiskCache(boolean skipDiskCache) {
        this.skipDiskCache = skipDiskCache;
        return this;
    }

    public ImgLoaderOptions setGif(boolean gif) {
        isGif = gif;
        return this;
    }
}
