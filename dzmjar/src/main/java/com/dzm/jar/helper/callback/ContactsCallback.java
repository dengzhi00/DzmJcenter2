package com.dzm.jar.helper.callback;

/**
 * Created by DELL on 2018/6/22.
 */

public interface ContactsCallback {

    void onContactsStart();

    void onContactsComplite(String phone);

    void onNoPermission();
}
