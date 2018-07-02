package com.dzm.http.http;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dzm.http.intercept.HttpInterceptInterface;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * @author 邓治民
 *         data 2018/3/8 下午3:05
 */

public class Http implements HttpInterface, LifecycleObserver {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private int code;

    void init(Context context) {
        code = context.hashCode();
        if (context instanceof LifecycleOwner)
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
    }

    void init(Fragment fragment) {
        code = fragment.hashCode();
        fragment.getLifecycle().addObserver(this);
    }

    @Override
    public void build(Build build) {
        HttpInterceptInterface<Observable> interceptInterface;
        if (null == build.gethInterface()) {
            interceptInterface = HttpSir.getBuild().getDefult();
        } else {
            interceptInterface = HttpSir.getBuild().getIntercept(build.gethInterface());
        }
        if (null == interceptInterface) {
            throw new NullPointerException("interceptInterface is null");
        }
        if (code == 0) {
            new HttpObserver(build).execute(interceptInterface.input(build.getData(), build.getOther(), build.getTag()));
        } else {
            new HttpObserver(build).execute(interceptInterface.input(build.getData(), build.getOther(), build.getTag()), mCompositeSubscription);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        Log.d("Http", "onDestroy");
        HttpSir.removeHttp(code);
        if (!mCompositeSubscription.isUnsubscribed())
            mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
    }
}
