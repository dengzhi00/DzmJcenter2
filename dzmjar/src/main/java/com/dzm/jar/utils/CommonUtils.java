package com.dzm.jar.utils;

import android.text.TextUtils;

/**
 * @author 邓治民
 *         data 2018/7/2 上午10:02
 */

public class CommonUtils {

    public static boolean isEmpty(Object o){
        if(o instanceof String){
            return TextUtils.isEmpty((String)o);

        }
        return null== o;
    }

    public static boolean isInt0(int i){
        return i == 0;
    }
}
