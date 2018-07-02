package com.dzm.jcenter.mvp;

import com.dzm.mvp.model.CreateMode;
import com.dzm.mvp.presenter.BaseModeMvpPresenter;

/**
 * @author 邓治民
 *         data 2018/6/27 下午3:48
 */

@CreateMode(MvpTestMode.class)
public class MvpTestPresenter extends BaseModeMvpPresenter<MvpTestView,MvpTestMode>  {


    public void login(){

//        getMode().login();
    }


}
