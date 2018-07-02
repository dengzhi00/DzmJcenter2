package com.dzm.jar.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.dzm.jar.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * dzm
 * 2016/12/11 15:11
 * v1.0.0
 * code djs
 */

public class CodeHelper {

    /** service */
    private ScheduledExecutorService service;

    /** time */
    private int index = 60;

    /** hander */
    private Handler hander;
    /** TextView */
    private TextView tvCode;

    private String txt;


    public CodeHelper() {
        hander = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (null == tvCode) {
                    return;
                }
                if (msg.what == 0x00) {
                    tvCode.setEnabled(true);
                    tvCode.setText(txt);
                    service.shutdownNow();
                } else if (msg.what == 0x01) {
                    tvCode.setText(String.valueOf(index));
                }
            }
        };
    }

    /**
     * start
     * @param tvCode view
     */
    public void startCode(TextView tvCode) {
        startCode(tvCode,null);

    }

    public void startCode(final TextView tvCode,String msg) {
        if (null == tvCode) {
            return;
        }
        if(TextUtils.isEmpty(msg)){
            this.txt = tvCode.getResources().getString(R.string.dzm_get_code);
        }else {
            this.txt = msg;
        }
        this.tvCode = tvCode;
        //矫正 参数
        index = 60;
        //取消 点击事件
        tvCode.setEnabled(false);
        tvCode.setText(String.valueOf(index));
        if (null != service) {
            service.shutdownNow();
        }
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                index--;
                if (index == 0) {
                    if (null != hander) {
                        hander.sendEmptyMessage(0x00);
                    }
                } else if (index > 0) {
                    if (null != hander) {
                        hander.sendEmptyMessage(0x01);
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    public void stop(){
        if(null != tvCode){
            exit();
            tvCode.setEnabled(true);
            tvCode.setText(txt);
        }

    }

    /**
     * exit
     */
    public void exit() {
        if (null != service) {
            service.shutdownNow();
        }
        if (null != hander) {
            hander.removeMessages(0x00);
        }

    }

}
