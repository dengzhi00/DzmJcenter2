package com.dzm.jar.image;

import android.graphics.Bitmap;

/**
 * @author 邓治民
 *         data 2018/7/2 上午9:27
 */

public interface ImgBitmapCallBack {

    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Exception e);

    public static class EmptyCallback implements ImgBitmapCallBack {


        @Override
        public void onBitmapLoaded(Bitmap bitmap) {

        }

        @Override
        public void onBitmapFailed(Exception e) {

        }
    }

}
