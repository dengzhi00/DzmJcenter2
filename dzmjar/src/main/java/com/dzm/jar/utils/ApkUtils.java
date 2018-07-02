package com.dzm.jar.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by dzm on 2018/6/20.
 *
 */

public class ApkUtils {

    /**
     * Install apk
     * @param file apk fileapk
     * @param context context
     */
    public static void apkInstall(File file, Context context) {
        if (context == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //7.0
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * file intent
     * @param context context
     * @param file apk file
     * @return intent
     */
    public static Intent getFileIntent(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //7.0
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        return intent;
    }

}
