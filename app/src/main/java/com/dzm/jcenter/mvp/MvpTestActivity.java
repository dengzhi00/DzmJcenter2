package com.dzm.jcenter.mvp;

import android.view.View;

import com.dzm.jcenter.R;
import com.dzm.mvp.factory.CreatePresenter;
import com.dzm.mvp.ui.BaseMvpActivity;

/**
 * @author 邓治民
 *         data 2018/6/27 下午3:46
 */
@CreatePresenter(MvpTestPresenter.class)
public class MvpTestActivity extends BaseMvpActivity<MvpTestView,MvpTestPresenter>{
    @Override
    protected int getLayout() {
        return R.layout.acrtivity_mvp;
    }

    @Override
    protected void init() {
        findViewById(R.id.bt_mvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMvpPresenter().login();
            }
        });
    }
}
