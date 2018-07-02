package com.dzm.jar.helper.callback;

/**
 * Created by dzm on 2018/5/17.
 *
 */

public interface RunnableCallback<T> {

    T runThread();

    void runMain(T t);

}
