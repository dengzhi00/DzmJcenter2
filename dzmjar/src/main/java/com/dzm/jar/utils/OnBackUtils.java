package com.dzm.jar.utils;

import android.content.Context;
import android.widget.Toast;

import com.dzm.jar.R;


/**
 * Created by dzm on 2018/6/7.
 *
 */

public class OnBackUtils {

    private static long exitTime = 0;


    /**
     * onBack
     * @param context context
     * @return bo
     */
    public static boolean onBack(Context context){
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(context, context.getResources().getText(R.string.dzm_exit_app), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            return false;
        } else {
           return true;
        }
    }

}
