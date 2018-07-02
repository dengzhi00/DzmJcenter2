package com.dzm.jcenter.mvp;

import com.dzm.jar.utils.ToastUtils;
import com.dzm.mvp.model.BaseMode;

/**
 * @author 邓治民
 *         data 2018/6/27 下午3:48
 */

public class MvpTestMode implements BaseMode{
    @Override
    public void onDestroyMode() {

    }

    public void login(){
        ToastUtils.showLongToast("login");
    }
}
