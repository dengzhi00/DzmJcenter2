package com.dzm.jar;

import android.content.Context;

import com.dzm.jar.utils.ResourcesUtils;
import com.dzm.jar.utils.ToastUtils;

/**
 * Created by DELL on 2018/6/22.
 */

public class InitJar {

    public static Context mContext;

    public static void init(Context context){
        mContext = context.getApplicationContext();
        ToastUtils.init(context);
        ResourcesUtils.init(context);

    }

}
