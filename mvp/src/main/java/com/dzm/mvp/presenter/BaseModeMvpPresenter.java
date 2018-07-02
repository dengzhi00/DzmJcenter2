package com.dzm.mvp.presenter;

import com.dzm.mvp.BaseMvpView;
import com.dzm.mvp.model.BaseMode;
import com.dzm.mvp.model.CreateMode;

/**
 * @author 邓治民
 *         data 2018/6/27 下午3:25
 */

public class BaseModeMvpPresenter <V extends BaseMvpView,M extends BaseMode> extends BaseMvpPresenter<V>{

    private M mode;

    @Override
    public void createMode() {
        CreateMode createMode = getClass().getAnnotation(CreateMode.class);
        Class<M> aClass = null;
        if(createMode != null){
            aClass = (Class<M>)createMode.value();
        }
        if(null != aClass){
            try {
                mode = aClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("VBaseMvpMode创建失败!，检查是否声明了@VBaseCreateMode(xx.class)注解"+getClass());
            }
        }
    }

    protected M getMode() {
        return mode;
    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
        if(null != mode){
            mode.onDestroyMode();
        }
        mode = null;
    }

}
