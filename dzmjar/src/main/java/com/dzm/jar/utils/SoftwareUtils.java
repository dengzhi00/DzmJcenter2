package com.dzm.jar.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by dzm on 2018/6/11.
 *
 */

public class SoftwareUtils {

    /**
     * Software english
     *
     * @param et et
     */
    public static void setEnglishSoftware(EditText et) {
        //
        et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        //
        et.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    /**
     * close software
     *
     * @param context context
     */
    public static void hideSoftwareInputMethod(Context context) {
        if (context instanceof Activity) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != inputMethodManager) {
                View view = ((Activity) context).getCurrentFocus();
                if (null != view) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    /**
     * close software
     *
     * @param view context
     */
    public static void hideSoftwareInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
