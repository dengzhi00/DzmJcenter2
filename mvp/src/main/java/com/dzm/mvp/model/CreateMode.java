package com.dzm.mvp.model;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 邓治民
 *         data 2018/1/26 下午5:57
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateMode {
    Class<? extends BaseMode> value();
}
