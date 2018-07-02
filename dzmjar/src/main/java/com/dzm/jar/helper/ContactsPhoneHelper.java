package com.dzm.jar.helper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.dzm.jar.helper.callback.ContactsCallback;
import com.dzm.jar.helper.callback.RunnableCallback;
import com.dzm.jar.utils.LogUtils;
import com.dzm.jar.utils.Permissionutils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dzm on 2018/6/22.
 *
 */

public class ContactsPhoneHelper {

    private static final String NAME = "name";
    private static final String PHONE = "phone";


    private boolean mIsFetching = false;

    private boolean mCancel = false;

    private Context mContext;

    private ContactsCallback callback;

    public ContactsPhoneHelper(ContactsCallback callback) {
        this.callback = callback;
    }

    public void onDestroy() {
        mCancel = true;
        mContext = null;
    }

    public void start(Context context) {
        if (mIsFetching)
            return;
        if (!Permissionutils.hasContactsPermission(context)){
            LogUtils.d("not has hasContactsPermission");
            return;
        }

        mIsFetching = true;
        mCancel = false;
        mContext = context;
        if (null != callback)
            callback.onContactsStart();
        new Thread(new RunnableAsync<>(new RunnableCallback<String>() {
            @Override
            public String runThread() {
                Set<String> set = new HashSet<>();
                JSONArray jsonArray = new JSONArray();
                if (!mCancel) {
                    LogUtils.d("getPhoneContactHighVersion");
                    if (!getPhoneContactHighVersion(jsonArray, set, mContext))
                        return "nopermission";
                }
                if (!mCancel) {
                    LogUtils.d("getSimContact");
                    getSimContact("content://icc/adn", jsonArray, set, mContext);
                }

                if (!mCancel) {
                    LogUtils.d("getSimContact");
                    getSimContact("content://sim/adn", jsonArray, set, mContext);
                }
                return jsonArray.toString();
            }

            @Override
            public void runMain(String phone) {
                mIsFetching = false;
                if (mCancel)
                    return;
                if (null != callback) {
                    if (TextUtils.equals(phone, "nopermission")) {
                        if (mContext instanceof Activity) {
                            Permissionutils.settingPermissionActivity((Activity) mContext);
                        } else {
                            callback.onNoPermission();
                        }
                    } else {
                        callback.onContactsComplite(phone);
                    }
                }

            }
        })).start();
    }


    // phone get
    private boolean getPhoneContactHighVersion(JSONArray list, Set<String> set, Context mContext) {
        try {
            if (mCancel)
                return true;
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            Cursor cursor = null;
            try {
                String[] projection = {ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME, "sort_key"};
                cursor = cr.query(uri, projection, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == cursor) {
                return false;
            }
            while (!mCancel && cursor.moveToNext()) {
                int nameFieldColumnIndex = cursor
                        .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                int idCol = cursor.getInt(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
//                int sort_key_index = cursor.getColumnIndex("sort_key");

                // get name
                // get id
                Cursor phone = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{Integer.toString(idCol)}, null);
                LogUtils.d("phoneContactHighVersion" );
                while (null != phone && !mCancel && phone.moveToNext()) {
                    String strPhoneNumber = formatMobileNumber(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    boolean b = isUserNumber(strPhoneNumber);
                    if (b) {
                        LogUtils.d("number=" + strPhoneNumber);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(NAME, cursor.getString(nameFieldColumnIndex));
                        jsonObject.put(PHONE, strPhoneNumber);
                        list.put(jsonObject);
                        set.add(strPhoneNumber);
                    }
                }
                if (null != phone)
                    phone.close();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void getSimContact(String adn, JSONArray list, Set<String> set, Context mContext) {
        Cursor cursor = null;
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(adn));
            Uri uri = intent.getData();
            if (null == uri)
                return;
            LogUtils.d("getSimContact uri= " + uri.toString());
            if (mCancel)
                return;
            cursor = mContext.getContentResolver().query(uri, null,
                    null, null, null);
            if (cursor != null) {
                while (!mCancel && cursor.moveToNext()) {
                    int nameIndex = cursor.getColumnIndex("name");
                    int numberIndex = cursor.getColumnIndex("number");
                    String number = cursor.getString(numberIndex);
                    LogUtils.d("number :  " + number);
                    if (isUserNumber(number)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(NAME, cursor.getString(nameIndex));
                        String phone = formatMobileNumber(number);
                        jsonObject.put(PHONE, phone);
                        list.put(jsonObject);
                        if (!isContain(set, phone)) {
                            set.add(phone);
                        }
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            LogUtils.i(e.toString());
            if (cursor != null)
                cursor.close();
        }
    }

    private String formatMobileNumber(String num2) {
        String num;
        if (num2 != null) {
            // 135-1568-1234
            num = num2.replaceAll("-", "");
            num = num.replaceAll(" ", "");
            if (num.startsWith("+86")) {
                num = num.substring(3);
            } else if (num.startsWith("86")) {
                num = num.substring(2);
            } else if (num.startsWith("86")) {
                num = num.substring(2);
            }
        } else {
            num = "";
        }
        return num.trim();
    }

    private boolean isUserNumber(String num) {
        if (null == num || "".equalsIgnoreCase(num)) {
            return false;
        }
        boolean re = false;
        if (num.length() == 11) {
            if (num.startsWith("1")) {
                re = true;
            }
        }
        return re;
    }

    private boolean isContain(Set<String> set, String un) {
        return set.contains(un);
    }

}
