package com.dzm.mvp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzm.mvp.BaseMvpView;
import com.dzm.mvp.factory.PresenterMvpFactory;
import com.dzm.mvp.factory.PresenterMvpFactoryImpl;
import com.dzm.mvp.presenter.BaseMvpPresenter;
import com.dzm.mvp.proxy.BaseMvpProxy;
import com.dzm.mvp.proxy.PresenterProxyInterface;

/**
 * @author 邓治民
 *         data 2018/6/27 下午3:35
 */

public abstract class BaseMvpFragment <V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends Fragment implements PresenterProxyInterface<V, P> {

    /**
     * 调用onSaveInstanceState时存入Bundle的key
     */
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));

    protected Context mContext;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this instanceof BaseMvpView){
            mProxy.onCreate((V) this);
        }
        mContext = getActivity();
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayout(), null);
            init();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void init();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this instanceof BaseMvpView){
            mProxy.onResume((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
