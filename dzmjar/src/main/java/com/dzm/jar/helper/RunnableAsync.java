package com.dzm.jar.helper;

import com.dzm.jar.helper.callback.RunnableCallback;
import com.dzm.jar.utils.HanderUtils;

/**
 * Created by dzm on 2018/6/22.
 *
 */

public class RunnableAsync<T> implements Runnable{

    private RunnableCallback<T> runnableCallback;

    private boolean isMain;

    private T t;

    public RunnableAsync(RunnableCallback<T> runnableCallback){
        this.runnableCallback = runnableCallback;
    }

    @Override
    public void run() {
        if(isMain){
            runnableCallback.runMain(t);
        }else {
            t = runnableCallback.runThread();
            isMain = true;
            HanderUtils.post(this);
        }
    }
}
