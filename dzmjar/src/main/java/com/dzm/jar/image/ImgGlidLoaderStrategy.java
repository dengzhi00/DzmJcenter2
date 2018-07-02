package com.dzm.jar.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dzm.jar.InitJar;
import com.dzm.jar.utils.CommonUtils;

/**
 * @author 邓治民
 *         data 2018/7/2 上午9:24
 */

public class ImgGlidLoaderStrategy implements ImgLoaderStrategy {

    @Override
    public void loadImage(ImgLoaderOptions options) {
        RequestManager requestManager = null;
        if (!CommonUtils.isEmpty(options.context)) {
            requestManager = Glide.with(options.context);
        } else if (!CommonUtils.isEmpty(options.activity)) {
            requestManager = Glide.with(options.activity);
        } else if (!CommonUtils.isEmpty(options.fragment)) {
            requestManager = Glide.with(options.fragment);
        }

        if (null == requestManager) {
            throw new NullPointerException("you must be set your Glid context or activity or fragment at first!");
        }
        DrawableTypeRequest request = null;
        if (!CommonUtils.isEmpty(options.url)) {
            request = requestManager.load(options.url);
        } else if (!CommonUtils.isEmpty(options.uri)) {
            request = requestManager.load(options.uri);
        } else if (!CommonUtils.isEmpty(options.file)) {
            request = requestManager.load(options.file);
        } else if (!CommonUtils.isInt0(options.drawableResId)) {
            request = requestManager.load(options.drawableResId);
        }

        if (CommonUtils.isEmpty(request)) {
            throw new NullPointerException("you must be set your Glid url or uri or file at first!");
        }

        if (!CommonUtils.isInt0(options.placeholderResId)) {
            request.placeholder(options.placeholderResId);
        } else if (!CommonUtils.isEmpty(options.placeholder)) {
            request.placeholder(options.placeholder);
        }

        if (!CommonUtils.isInt0(options.errorResId)) {
            request.error(options.errorResId);
        } else if (!CommonUtils.isEmpty(options.errorDraw)) {
            request.error(options.errorDraw);
        }

        if (!CommonUtils.isInt0(options.targetWidth) && !CommonUtils.isInt0(options.targetHeight)) {
            request.override(options.targetWidth, options.targetHeight);
        }

        if (options.isCenterCrop) {
            request.centerCrop();
        }

        if (options.isCenterInside) {
            request.fitCenter();
        }

        if (options.skipMemoryCache) {
            request.skipMemoryCache(true);
        }

        if (options.skipDiskCache) {
            request.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        if (options.isGif) {
            request.asGif();
        } else {
            request.asBitmap();
        }

        if (null != options.targetView) {
            if (options.targetView instanceof ImageView){
                request.into((ImageView) options.targetView);
            }
        }else if(null != options.callBack){
            request.into(new TagetBitmap(options.callBack));
        }

    }

    @Override
    public void clearMemoryCache() {
        if(null == InitJar.mContext)
            return;
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(InitJar.mContext).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearDiskCache() {
        if(null == InitJar.mContext)
            return;
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(InitJar.mContext).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(InitJar.mContext).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TagetBitmap extends SimpleTarget<Bitmap>{

        private ImgBitmapCallBack callBack;

        TagetBitmap(ImgBitmapCallBack callBack){
            this.callBack = callBack;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            if(null != callBack)
                callBack.onBitmapLoaded(resource);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            if(null != callBack)
                callBack.onBitmapFailed(e);
        }
    }
}
