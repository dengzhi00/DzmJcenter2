package com.dzm.mvp.proxy;


import com.dzm.mvp.BaseMvpView;
import com.dzm.mvp.factory.PresenterMvpFactory;
import com.dzm.mvp.presenter.BaseMvpPresenter;

/**
 * @author 刘镓旗
 * 2017/11/20
 * 代理接口
 */
public interface PresenterProxyInterface<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {


    /**
     * 设置创建Presenter的工厂
     * @param presenterFactory PresenterFactory类型
     */
    void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory);

    /**
     * 获取Presenter的工厂类
     * @return 返回PresenterMvpFactory类型
     */
    PresenterMvpFactory<V,P> getPresenterFactory();


    /**
     * 获取创建的Presenter
     * @return 指定类型的Presenter
     */
    P getMvpPresenter();


}
