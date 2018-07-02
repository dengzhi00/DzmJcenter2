package com.dzm.mvp.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dzm.mvp.BaseMvpView;
import com.dzm.mvp.factory.PresenterMvpFactory;
import com.dzm.mvp.factory.PresenterMvpFactoryImpl;
import com.dzm.mvp.presenter.BaseMvpPresenter;
import com.dzm.mvp.proxy.BaseMvpProxy;
import com.dzm.mvp.proxy.PresenterProxyInterface;

/**
 * @author 邓治民
 *         data 2018/6/27 下午3:27
 */

public abstract class BaseMvpActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends AppCompatActivity implements PresenterProxyInterface<V,P> {

    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";

    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,P>createFactory(getClass()));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforContentView();
        setContentView(getLayout());
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        if(this instanceof BaseMvpView){
            mProxy.onCreate((V) this);
        }

        init();
    }

    protected abstract @LayoutRes
    int getLayout();

    protected void initBeforContentView(){}

    protected abstract void init();


    @Override
    protected void onResume() {
        super.onResume();
        if(this instanceof BaseMvpView){
            mProxy.onResume((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("perfect-mvp","V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }
}
