package com.dzm.jar.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by DELL on 2018/6/19.
 *
 */

public class Permissionutils {

    public static boolean hasContactsPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean startContactsPermission(Activity context,int code){
        if(hasContactsPermission(context)){
            return true;
        }else {
            ActivityCompat.requestPermissions(context,  new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.GET_ACCOUNTS}, code);
            return false;
        }
    }


    public static void settingPermissionActivity(Activity activity) {
        if (TextUtils.equals(BrandUtils.getSystemInfo().getOs(), BrandUtils.SYS_MIUI)) {
            Intent miuiIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            miuiIntent.putExtra("extra_pkgname", activity.getPackageName());
            //
            List<ResolveInfo> resolveInfos = activity.getPackageManager().queryIntentActivities(miuiIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfos.size() > 0) {
                activity.startActivityForResult(miuiIntent, 1000);
                return;
            }
        }
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 1000);
    }
}
