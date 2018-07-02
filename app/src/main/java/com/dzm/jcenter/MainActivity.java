package com.dzm.jcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dzm.jar.InitJar;
import com.dzm.jar.helper.ContactsPhoneHelper;
import com.dzm.jar.helper.callback.ContactsCallback;
import com.dzm.jar.image.ImageLoader;
import com.dzm.jar.utils.LogUtils;
import com.dzm.jcenter.mvp.MvpTestActivity;


public class MainActivity extends AppCompatActivity implements ContactsCallback {

    private ContactsPhoneHelper contactsPhoneHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitJar.init(this);
        contactsPhoneHelper = new ContactsPhoneHelper(this);
        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MvpTestActivity.class));
            }
        });

        ImageView iv_glid = findViewById(R.id.iv_glid);
        iv_glid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageLoader.with(v.getContext())
                        .load("https://cbu01.alicdn.com/cms/upload/2017/071/259/3952170_1195227291.jpg")
                        .setPlaceholderResId(R.mipmap.ic_launcher)
                        .into(v);
            }
        });

    }

    @Override
    public void onContactsStart() {
        LogUtils.d("onContactsStart");
    }

    @Override
    public void onContactsComplite(String phone) {
        LogUtils.d(phone);
    }

    @Override
    public void onNoPermission() {
        LogUtils.d("onNoPermission");
    }
}
