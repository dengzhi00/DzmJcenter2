package com.dzm.jar.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by dzm on 2018/6/22.
 *
 */

public class DpUtils {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    /**
     * dp shi pei
     * @param activity activity
     * @param application application
     * @param uiWidth uiWidth she ji ui width
     */
    public static void utils(Activity activity, final Application application,int uiWidth){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if(sNoncompatDensity == 0){
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(null != newConfig && newConfig.fontScale > 0){
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() {
                }
            });
//            float targetDensity = appDisplayMetrics.widthPixels / 360;
            float targetDensity = appDisplayMetrics.widthPixels / uiWidth;
            float targetScaledDensity = targetDensity*(sNoncompatScaledDensity/sNoncompatDensity);
            int targetDensityDpi = (int) (160*targetDensity);
            DisplayMetrics activityDisplayMerics = activity.getResources().getDisplayMetrics();
            activityDisplayMerics.density = targetDensity;
            activityDisplayMerics.scaledDensity = targetScaledDensity;
            activityDisplayMerics.densityDpi = targetDensityDpi;
        }
    }

}
