package com.dzm.mvp.factory;


import com.dzm.mvp.BaseMvpView;
import com.dzm.mvp.presenter.BaseMvpPresenter;

/**
 * @author 刘镓旗
 * 2017/11/17
 * Presenter工厂接口
 */
public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
